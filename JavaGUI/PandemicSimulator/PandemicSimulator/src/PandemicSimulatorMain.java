/**
 * Program Name: PandemicSimulatorMain.java
 * Purpose: This program will allow the user to input certain parameters 
 * 					about a population in terms of how many people are vaccinated or unvaccinated, 
 * 					and then run a simulation to see how quickly a disease will spread through a population of a given size.
 *         
 * 
 * @author			Seolan Jin
 * @author			Junyeong Jo
 * @author			Soohwan Kim
 * @author			M Sadat Rahman
 * @version		  1.0
 * @since       Jul 25, 2022
 */


import PeopleManager.PeopleManager;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


public class PandemicSimulatorMain extends JFrame
{
	private JFrame frame;
	private JTabbedPane tPane;
	private Container errorPane;
	
	// will be added to tPane
	private JPanel pnlUserInputMenu, pnlAboutMenu;
	
	// will be added to pnlUserInputMenu
	private JSlider sldrNone, sldrMild, sldrModerate, sldrGood, sldrNatural;
	private JLabel lblSldrNone, lblSldrMild, lblSldrModerate, lblSldrGood, lblSldrNatural, lblTotalPercentage;
	
	private final int INPUT_SIZE = 6;
	private int[] userInput;
	
	private PandemicSimulator simulator;
	
	//constructor
	public PandemicSimulatorMain() 
	{
		// build the JFrame here in the main
		frame = new JFrame("Pandemic Simulator Center");
			 
		//boilerplate 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300,650); 
		frame.setLocationRelativeTo(null);
			 
		//create a JTabbedPane object 
		tPane = new JTabbedPane();

		// will be passed to PandemicSimulator class
		userInput = new int[INPUT_SIZE];
		
		BuildUserInputMenuPanel();	
		BuildAboutMenuPanel();
		
		// Build tPane 
		tPane.add("Simulator", pnlUserInputMenu);
		tPane.add("About", pnlAboutMenu);

		//add the JTabbedPane to the JFrame 
		frame.add(tPane);
			  
		
		frame.setVisible(true);	
	}

	/**
	* Build pnlUserInputMenu
	*/
	public void BuildUserInputMenuPanel()
	{
		// set pnlUserInputMenu
		pnlUserInputMenu = new JPanel();
		pnlUserInputMenu.setLayout(new BorderLayout());

		// will be added to pnlUserInputMenu
		JPanel pnlUserInput = new JPanel();
		pnlUserInput.setLayout(new GridLayout(13, 1));

		JLabel lblPopulation = new JLabel("Size of the population: ");
		pnlUserInput.add(lblPopulation);
		JTextField fldPopulation = new JTextField(10);
		pnlUserInput.add(fldPopulation);

		
		// set slider variables
		sldrNone = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
		sldrNone.setMajorTickSpacing(20);
		sldrNone.setMinorTickSpacing(5);
		sldrNone.setPaintTicks(true);
		sldrNone.setPaintLabels(true);

		sldrMild = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
		sldrMild.setMajorTickSpacing(20);
		sldrMild.setMinorTickSpacing(5);
		sldrMild.setPaintTicks(true);
		sldrMild.setPaintLabels(true);

		sldrModerate = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
		sldrModerate.setMajorTickSpacing(20);
		sldrModerate.setMinorTickSpacing(5);
		sldrModerate.setPaintTicks(true);
		sldrModerate.setPaintLabels(true);

		sldrGood = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
		sldrGood.setMajorTickSpacing(20);
		sldrGood.setMinorTickSpacing(5);
		sldrGood.setPaintTicks(true);
		sldrGood.setPaintLabels(true);

		sldrNatural = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
		sldrNatural.setMajorTickSpacing(20);
		sldrNatural.setMinorTickSpacing(5);
		sldrNatural.setPaintTicks(true);
		sldrNatural.setPaintLabels(true);

		lblSldrNone = new JLabel("Unvaccinated Persons: 0%");
		lblSldrMild = new JLabel("1 Shot Vaccinated Persons: 0%");
		lblSldrModerate = new JLabel("2 Shot Vaccinated Persons: 0%");
		lblSldrGood = new JLabel("3 Shot Vaccinated Persons: 0%");
		lblSldrNatural = new JLabel("Natual Immunity Persons: 0%");
		
		lblTotalPercentage = new JLabel("Total Percentage (%): 0 / 100");
		lblTotalPercentage.setForeground(Color.RED); // set lblTotalPercentage's font colour to red (default)

		// register listener to slider variables
		SliderListener sl = new SliderListener();
		sldrNone.addChangeListener(sl);
		sldrMild.addChangeListener(sl);
		sldrModerate.addChangeListener(sl);
		sldrGood.addChangeListener(sl);
		sldrNatural.addChangeListener(sl);

		// add to pnlUserInput
		pnlUserInput.add(lblSldrNone);
		pnlUserInput.add(sldrNone);
		pnlUserInput.add(lblSldrMild);
		pnlUserInput.add(sldrMild);
		pnlUserInput.add(lblSldrModerate);
		pnlUserInput.add(sldrModerate);
		pnlUserInput.add(lblSldrGood);
		pnlUserInput.add(sldrGood);
		pnlUserInput.add(lblSldrNatural);
		pnlUserInput.add(sldrNatural);
		pnlUserInput.add(lblTotalPercentage);

		// add pnlUserInput to pnlUserInputMenu
		pnlUserInputMenu.add(pnlUserInput, BorderLayout.CENTER);

		// will be added to pnlUserInputMenu
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// if user didn't set population size or Total Percentage is not 100%, show error message
				if (fldPopulation.getText() == null || lblTotalPercentage.getForeground() == Color.RED){
					JOptionPane.showMessageDialog(new JFrame(), "This error occurs when:\n- Population is null\n- Total Percentage is not 100%", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				try {
					userInput[0] = Integer.valueOf(fldPopulation.getText());
				}
				catch (NumberFormatException ex) { // if user entered non-numeric value, show error message 
					JOptionPane.showMessageDialog(new JFrame(), "Population only accept numeric value", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				// populate userInput
				userInput[1] = (int)(userInput[0] * ((float)sldrNone.getValue() / 100) + 0.5);
				userInput[2] = (int)(userInput[0] * ((float)sldrMild.getValue() / 100) + 0.5);
				userInput[3] = (int)(userInput[0] * ((float)sldrModerate.getValue() / 100) + 0.5);
				userInput[4] = (int)(userInput[0] * ((float)sldrGood.getValue() / 100) + 0.5);
				userInput[5] = (int)(userInput[0] * ((float)sldrNatural.getValue() / 100) + 0.5);
				
				// create PandemicSimulator
				simulator = new PandemicSimulator(userInput);
			} // end actionPerformed
		});
		
		// add btnStart to pnlUserInputMenu
		pnlUserInputMenu.add(btnStart, BorderLayout.SOUTH);
	}
	
	
	public class SliderListener implements ChangeListener
	{
		public void stateChanged(ChangeEvent ev)
		{
			int none, mild, moderate, good, natural, total;

			// get values from sliders
			none = sldrNone.getValue();
			mild = sldrMild.getValue();
			moderate = sldrModerate.getValue();
			good = sldrGood.getValue();
			natural = sldrNatural.getValue();

			lblSldrNone.setText("Unvaccinated Persons: " + none + "%");
			lblSldrMild.setText("1 Shot Vaccinated Persons: " + mild + "%");
			lblSldrModerate.setText("2 Shot Vaccinated Persons: " + moderate + "%");
			lblSldrGood.setText("3 Shot Vaccinated Persons: " + good + "%");
			lblSldrNatural.setText("Natual Immunity Persons: " + natural + "%");

			// check if total percentage is 100
			total = none + mild + moderate + good + natural;
			lblTotalPercentage.setText("Total Percentage (%): " + total + " / 100");
			if (total != 100)
				lblTotalPercentage.setForeground(Color.RED);
			else
				lblTotalPercentage.setForeground(Color.BLACK);
		}// end method
	}// end inner class
	
	
	/**
	* Build pnlAboutMenu
	*/
	public void BuildAboutMenuPanel()
	{
		pnlAboutMenu = new JPanel();

		pnlAboutMenu.setLayout(new GridLayout(4, 1));

		pnlAboutMenu.add(new JLabel("Soohwan Kim (Section 4)"));
		pnlAboutMenu.add(new JLabel("Seolan Jin (Section 4)"));
		pnlAboutMenu.add(new JLabel("Junyeong Jo (Section 3)"));
		pnlAboutMenu.add(new JLabel("M Sadat Rahman (Section 4)"));
	}

	public static void main(String[] args)
	{
		PandemicSimulatorMain simulator = new PandemicSimulatorMain();
		
	} // end main
}
//End class