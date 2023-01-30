/**
 * Program Name: PandemicSimulator.java
 * Purpose: When user clicks 'start' button in PandemicSimulatorMain,
 * 				  this JFrame will be popped up and runs pandemic simulator
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
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner; 



public class PandemicSimulator extends JPanel
{
	
	private final int WIDTH = 600, HEIGHT = 510;
	private final int LAG_TIME = 200; // 200 time in milliseconds between re-paints of screen
	private Timer time;	// Timer class object that will fire events every LAG_TIME interval
	private final int DIAMETER = 10;
	private int timeCount = 0;
	private final int CYCLE = 450; // represents a 21 day period
	
    
	private JFrame frame;
	private JTabbedPane tPane;

	// will be added to tPane
	private JPanel pnlSimulatorMenu, pnlFinalReportMenu;
	
	// will be added to pnlSimulatorMenu
	private JPanel pnlSimulator, pnlRealTime, pnlInfo;
	
	private int population, numNone, numMild, numModerate, numGood, numNatural;
	
	// for pnlRealTime (Real Time Report)
	private JTextField fldNumInfected, fldNumNone ,fldNumMild ,fldNumModerate ,fldNumGood ,fldNumNatural ,fldNumRecovered ,fldNumDead;
	private JButton btnStop, btnResume;
	
	// for pnlFinalReportMenu (Final Report)
	private JTextField fldFinalPopulation, fldFinalNone, fldFinalMild, fldFinalModerate, fldFinalGood, fldFinalNatural, fldFinalRecovered;
	private JTextField fldFinalDeadNone, fldFinalDeadMild, fldFinalDeadModerate, fldFinalDeadGood, fldFinalDeadNatural, fldFinalDeadTotal, fldCycleInfo;
		
	
	private PeopleManager peopleManager;
	
	// userInput's size
	final int INPUT_SIZE = 6;
	 
	
	//variables for graph
	private int histogramHeight = 200;
	private int barWidth = 20;
	private int barGap = 10;
  private JPanel pnlLabel;
  private JPanel pnlBar;
  private JPanel pnlGraphic;
  private List<Bar> bars = new ArrayList<Bar>();

	//constructor
	/**
	 * Constructs a new PandemicSimulator object
	 * @param userInput
	 */
	public PandemicSimulator(int[] userInput) 
	{
		frame = new JFrame("Pandemic Simulator");
  	//boilerplate
		frame.setLocationByPlatform( true );
		frame.setSize(900,600);
		frame.setResizable(false);
		
		tPane = new JTabbedPane();
		
		// set variables to call PeopleManager constructor and setNumberOfPeopleWithImmunity method
		population = userInput[0];
		numNone = userInput[1];
		numMild = userInput[2];
		numModerate = userInput[3];
		numGood = userInput[4];
		numNatural = userInput[5];	
		
		pnlSimulatorMenu = new JPanel();
		pnlSimulatorMenu.setLayout(new BorderLayout());
		
		// create PeopleManager
		peopleManager = new PeopleManager(WIDTH, HEIGHT, population, DIAMETER);
		pnlSimulator = new JPanel() {
			@Override public void paintComponent(Graphics g)
			{
				//call super version of this method to "throw the bucket of paint onto the canvas"
				super.paintComponent(g);
				
				//set brush color
				g.setColor(Color.PINK);
				
				// iterate through the loop to paint the balls(person) onto the panel
				// and set the color using the People immunityStatus's color value
				for(int i = 0; i < peopleManager.getPeople().length; i++)
				{
					//get the color
					g.setColor(peopleManager.getPeople()[i].getColor());
					g.fillOval(peopleManager.getPeople()[i].getxCoord(), 
										 peopleManager.getPeople()[i].getyCoord(),  
										 peopleManager.getPeople()[i].getDiameter(), 
										 peopleManager.getPeople()[i].getDiameter());
				}
			}
		};
		
		// set Timer
		time = new Timer(LAG_TIME, new TimerListener());
		
		
		try 
    {
			peopleManager.setNumberOfPeopleWithImmunity(numNone, numMild, numModerate, numGood, numNatural);
    }
    catch (Exception e)
    {
        System.out.println("Population does not match.");
    }
		
		pnlSimulator.setPreferredSize(new Dimension(WIDTH, HEIGHT) );
		pnlSimulator.setBackground(Color.WHITE);
		
		time.start();	// start timer
		
		//add pnlSimulator to pnlSimulatorMenu
		pnlSimulatorMenu.add(pnlSimulator, BorderLayout.CENTER);
		
		// build and add pnlRealTime to pnlSimulatorMenu
		BuildRealTimePanel(); 
		pnlSimulatorMenu.add(pnlRealTime, BorderLayout.EAST);

		// build and add pnlInfo to pnlSimulatorMenu
		BuildInfoPanel();
		pnlSimulatorMenu.add(pnlInfo, BorderLayout.SOUTH);

		// add pnlSimulatorMenu to tPane
		tPane.add("Pandemic Simulator", pnlSimulatorMenu);
		
		// build and add pnlFinalReportMenu
		BuildFinalReportMenuPanel(); 
		tPane.add("Final Report", pnlFinalReportMenu);
		tPane.setEnabledAt(1, false);	// user cannot access to pnlFinalReportMenu until the simulator ends
		
		// add tPane to main frame
		frame.add(tPane);
		frame.setVisible(true);		
	}
	
	
	/**
	* Build pnlFinalReportMenu
	*/
	public void BuildFinalReportMenuPanel()
	{
		pnlFinalReportMenu = new JPanel();
		pnlFinalReportMenu.setLayout(new GridLayout(14, 2, 0, 10));

		// create report variables
		JLabel lblFinalPopulation = new JLabel("Total population that contracted the disease (%): ");
		JLabel lblFinalNone = new JLabel("Unvaccinated persons who contracted the disease (%): ");
		JLabel lblFinalMild = new JLabel("One-shot-vaccinated persons who contracted the disease (%): ");
		JLabel lblFinalModerate = new JLabel("Two-shot-vaccinated persons who contracted the disease (%): ");
		JLabel lblFinalGood = new JLabel("Three-shot-vaccinated persons who contracted the disease (%): ");
		JLabel lblFinalNatural = new JLabel("Naturally immune persons who got re-infected (%): ");
		JLabel lblFinalRecovered = new JLabel("All those who contracted the disease that recovered (%): ");
		
		JLabel lblFinalDeadNone = new JLabel("Unvaccinated persons who contracted the disease that died (%): ");
		JLabel lblFinalDeadMild = new JLabel("One-shot-vaccinated persons who contracted the disease that died (%): ");
		JLabel lblFinalDeadModerate = new JLabel("Two-shot-vaccinated persons who contracted the disease that died (%): ");
		JLabel lblFinalDeadGood = new JLabel("Three-shot-vaccinated persons who contracted the disease that died (%): ");
		JLabel lblFinalDeadNatural = new JLabel("Naturally immune persons who contracted the disease that died (%): ");
		JLabel lblFinalDeadTotal = new JLabel("All those who contracted the disease that died (%): ");

		// set report variables
		fldFinalPopulation = new JTextField();
		fldFinalNone = new JTextField();
		fldFinalMild = new JTextField();
		fldFinalModerate = new JTextField();
		fldFinalGood = new JTextField();
		fldFinalNatural = new JTextField();
		fldFinalRecovered = new JTextField();
		
		fldFinalDeadNone = new JTextField();
		fldFinalDeadMild = new JTextField();
		fldFinalDeadModerate = new JTextField();
		fldFinalDeadGood = new JTextField();
		fldFinalDeadNatural = new JTextField();
		fldFinalDeadTotal = new JTextField();

		fldFinalPopulation.setEditable(false);
		fldFinalNone.setEditable(false);
		fldFinalMild.setEditable(false);
		fldFinalModerate.setEditable(false);
		fldFinalGood.setEditable(false);
		fldFinalNatural.setEditable(false);
		fldFinalRecovered.setEditable(false);
		
		fldFinalDeadNone.setEditable(false);
		fldFinalDeadMild.setEditable(false);
		fldFinalDeadModerate.setEditable(false);
		fldFinalDeadGood.setEditable(false);
		fldFinalDeadNatural.setEditable(false);
		fldFinalDeadTotal.setEditable(false);
		
		// add report variables
		pnlFinalReportMenu.add(lblFinalPopulation);
		pnlFinalReportMenu.add(fldFinalPopulation);
		
		pnlFinalReportMenu.add(lblFinalNone);
		pnlFinalReportMenu.add(fldFinalNone);
		
		pnlFinalReportMenu.add(lblFinalMild);
		pnlFinalReportMenu.add(fldFinalMild);
		
		pnlFinalReportMenu.add(lblFinalModerate);
		pnlFinalReportMenu.add(fldFinalModerate);
		
		pnlFinalReportMenu.add(lblFinalGood);
		pnlFinalReportMenu.add(fldFinalGood);
		
		pnlFinalReportMenu.add(lblFinalNatural);
		pnlFinalReportMenu.add(fldFinalNatural);
		
		pnlFinalReportMenu.add(lblFinalRecovered);
		pnlFinalReportMenu.add(fldFinalRecovered);

		
		
		pnlFinalReportMenu.add(lblFinalDeadNone);
		pnlFinalReportMenu.add(fldFinalDeadNone);
		
		pnlFinalReportMenu.add(lblFinalDeadMild);
		pnlFinalReportMenu.add(fldFinalDeadMild);
		
		pnlFinalReportMenu.add(lblFinalDeadModerate);
		pnlFinalReportMenu.add(fldFinalDeadModerate);
		
		pnlFinalReportMenu.add(lblFinalDeadGood);
		pnlFinalReportMenu.add(fldFinalDeadGood);
		
		pnlFinalReportMenu.add(lblFinalDeadNatural);
		pnlFinalReportMenu.add(fldFinalDeadNatural);
		
		pnlFinalReportMenu.add(lblFinalDeadTotal);
		pnlFinalReportMenu.add(fldFinalDeadTotal);
		
		JButton btnGraph = new JButton("View Graph");
		JButton btnSave = new JButton("Save Report");
		
		btnGraph.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// show graphic frame
				BuildandRunGraphicPanel();
			}
		});
		btnSave.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// save report
				SaveReport();
			}
		});
		
		// add buttons to pnlFinalReportMenu
		pnlFinalReportMenu.add(btnGraph);
		pnlFinalReportMenu.add(btnSave);
		
	}
	
	
	/**
	* Build pnlInfo 
	*/
	private void BuildInfoPanel()
	{
		pnlInfo = new JPanel();
		
		pnlInfo.setLayout(new GridLayout(1,8));
		
		// create variables to show information of immunityStatus colour
		JLabel lblInfectedColour = new JLabel("Infected", JLabel.CENTER);
		JLabel lblNoneColour = new JLabel("Unvaccinated", JLabel.CENTER);
		JLabel lblMildColour = new JLabel("1 Shot Vaccinated", JLabel.CENTER);
		JLabel lblModerateColour = new JLabel("2 Shot Vaccinated", JLabel.CENTER);
		JLabel lblGoodColour = new JLabel("3 Shot Vaccinated", JLabel.CENTER);
		JLabel lblNaturalColour = new JLabel("Natual Immunity", JLabel.CENTER);
		JLabel lblDeadColour = new JLabel("Dead", JLabel.CENTER);
		
		lblInfectedColour.setBackground(Color.RED);
		lblNoneColour.setBackground(Color.BLUE);
		lblMildColour.setBackground(Color.CYAN);
		lblModerateColour.setBackground(Color.YELLOW);
		lblGoodColour.setBackground(Color.MAGENTA);
		lblNaturalColour.setBackground(Color.GREEN);
		lblDeadColour.setBackground(Color.BLACK);
		lblDeadColour.setForeground(Color.WHITE);
		
		// this will enable to show the background colour of JLabel
		lblInfectedColour.setOpaque(true);
		lblNoneColour.setOpaque(true);
		lblMildColour.setOpaque(true);
		lblModerateColour.setOpaque(true);
		lblGoodColour.setOpaque(true);
		lblNaturalColour.setOpaque(true);
		lblDeadColour.setOpaque(true);
		
		pnlInfo.add(lblInfectedColour);
		pnlInfo.add(lblNoneColour);
		pnlInfo.add(lblMildColour);
		pnlInfo.add(lblModerateColour);
		pnlInfo.add(lblGoodColour);
		pnlInfo.add(lblNaturalColour);
		pnlInfo.add(lblDeadColour);
		
		// set to show current cycle
		fldCycleInfo = new JTextField("0 / 450 cycle");
		fldCycleInfo.setEditable(false);
		pnlInfo.add(fldCycleInfo);
		
	}

	
	
	private class TimerListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// if current time count is reached to the final cycle we set...
			if (timeCount == CYCLE) {
				time.stop(); // stop the simulator
				
				// set final value for Final Report
				fldFinalPopulation.setText(String.valueOf((int)(peopleManager.getFinalReport().
						percentageOfPopulationContractedDisease_Total() * 100)));
				fldFinalNone.setText(String.valueOf((int)(peopleManager.getFinalReport().
						percentageOfPopulationContractedDisease_NoImmunity() * 100)));
				fldFinalMild.setText(String.valueOf((int)(peopleManager.getFinalReport().
						percentageOfPopulationContractedDisease_MildImmunity() * 100)));
				fldFinalModerate.setText(String.valueOf((int)(peopleManager.getFinalReport().
						percentageOfPopulationContractedDisease_ModerateImmunity() * 100)));
				fldFinalGood.setText(String.valueOf((int)(peopleManager.getFinalReport().
						percentageOfPopulationContractedDisease_GoodImmunity() * 100)));
				fldFinalNatural.setText(String.valueOf((int)(peopleManager.getFinalReport().
						percentageOfPopulationContractedDisease_NaturalImmunity() * 100)));
				fldFinalRecovered.setText(String.valueOf((int)(peopleManager.getFinalReport().
						percentageOfPopulationRecovered_Total() * 100)));
				
				fldFinalDeadNone.setText(String.valueOf((int)(peopleManager.getFinalReport().
						percentageOfPopulationDied_NoImmunity() * 100)));
				fldFinalDeadMild.setText(String.valueOf((int)(peopleManager.getFinalReport().
						percentageOfPopulationDied_MildImmunity() * 100)));
				fldFinalDeadModerate.setText(String.valueOf((int)(peopleManager.getFinalReport().
						percentageOfPopulationDied_ModerateImmunity() * 100)));
				fldFinalDeadGood.setText(String.valueOf((int)(peopleManager.getFinalReport().
						percentageOfPopulationDied_GoodImmunity() * 100)));
				fldFinalDeadNatural.setText(String.valueOf((int)(peopleManager.getFinalReport().
						percentageOfPopulationDied_NaturalImmunity() * 100)));
				fldFinalDeadTotal.setText(String.valueOf((int)(peopleManager.getFinalReport().
						percentageOfPopulationDied_Total() * 100)));
								
				// enable Final Report tab
				tPane.setEnabledAt(1, true);
				
				// disable Stop and Resume Button
				btnStop.setEnabled(false);
				btnResume.setEnabled(false);
			}
			peopleManager.movePeople();
			timeCount++;
			pnlSimulator.repaint(); // call paintComponent in pnlSimulator
			
			// change the value of Real Time Report
			fldNumInfected.setText(String.valueOf(peopleManager.report_NumberOfTotalInfectedPersons()));
			fldNumNone.setText(String.valueOf(peopleManager.report_NumberOfNoImmunePersonsInfected()));
			fldNumMild.setText(String.valueOf(peopleManager.report_NumberOfMildImmunePersonsInfected()));
			fldNumModerate.setText(String.valueOf(peopleManager.report_NumberOfModerateImmunePersonsInfected()));
			fldNumGood.setText(String.valueOf(peopleManager.report_NumberOfGoodImmunePersonsInfected()));
			fldNumNatural.setText(String.valueOf(peopleManager.report_NumberOfNaturalImmunePersonsInfected()));
			fldNumRecovered.setText(String.valueOf(peopleManager.report_NumberOfRecoveredPeople()));
			fldNumDead.setText(String.valueOf(peopleManager.report_NumberOfDeadPeople()));
			
			if(timeCount == CYCLE + 1)
				fldCycleInfo.setText("450 / 450 cycle");
			else
				fldCycleInfo.setText(timeCount + " / 450 cycle");
			
			
		}//end method
		
	}
	
	/**
	* Build pnlRealTime 
	*/
	public void BuildRealTimePanel()
	{
		pnlRealTime = new JPanel();
		pnlRealTime.setLayout(new BorderLayout());
		
		JPanel pnlOutput = new JPanel();
		pnlOutput.setLayout(new GridLayout(16,1));
				
		// create variables for Real Time Report
		JLabel lblNumInfected = new JLabel("Number of infected persons");
		JLabel lblNumNone = new JLabel("Number of non-vaccinated persons infected");
		JLabel lblNumMild = new JLabel("Number of one-shot-vaccinated people infected");
		JLabel lblNumModerate = new JLabel("Number of two-shot-vaccinated people infected");
		JLabel lblNumGood = new JLabel("Number of three-shot-vaccinated people infected");
		JLabel lblNumNatural = new JLabel("Number of naturally immune people re-infected");
		JLabel lblNumRecovered= new JLabel("Number of infected people who have recovered");
		JLabel lblNumDied = new JLabel("Number of infected people who have died");
		
		
		// set variables
		fldNumInfected = new JTextField(4);
		fldNumNone = new JTextField(4);
		fldNumMild = new JTextField(4);
		fldNumModerate = new JTextField(4);
		fldNumGood = new JTextField(4);
		fldNumNatural = new JTextField(4);
		fldNumRecovered = new JTextField(4);
		fldNumDead = new JTextField(4);

		fldNumInfected.setEditable(false);
		fldNumNone.setEditable(false);
		fldNumMild.setEditable(false);
		fldNumModerate.setEditable(false);
		fldNumGood.setEditable(false);
		fldNumNatural.setEditable(false);
		fldNumRecovered.setEditable(false);
		fldNumDead.setEditable(false);
		
		// add to pnlOutput
		pnlOutput.add(lblNumInfected);
		pnlOutput.add(fldNumInfected);
		pnlOutput.add(lblNumNone);
		pnlOutput.add(fldNumNone);
		pnlOutput.add(lblNumMild);
		pnlOutput.add(fldNumMild);
		pnlOutput.add(lblNumModerate);
		pnlOutput.add(fldNumModerate);
		pnlOutput.add(lblNumGood);
		pnlOutput.add(fldNumGood);
		pnlOutput.add(lblNumNatural);
		pnlOutput.add(fldNumNatural);
		pnlOutput.add(lblNumRecovered);
		pnlOutput.add(fldNumRecovered);
		pnlOutput.add(lblNumDied);
		pnlOutput.add(fldNumDead);
		
		// add pnlOutput to pnlRealTime
		pnlRealTime.add(pnlOutput, BorderLayout.CENTER);

		
		
		// panel for buttons
		JPanel pnlBtn = new JPanel();
		pnlBtn.setLayout(new GridLayout(1,2));
		
		btnStop = new JButton("Stop");
		btnResume = new JButton("Resume");

		btnStop.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				time.stop(); // stop the simulator
			}
		});
		
		btnResume.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				time.restart(); // resume the simulator
			}
		});
		
		// add buttons to pnlBtn
		pnlBtn.add(btnStop);
		pnlBtn.add(btnResume);
		
		// add pnlBtn to pnlRealTime
		pnlRealTime.add(pnlBtn, BorderLayout.SOUTH);
	}


	/**
	* Build pnlGraphic and Run  
	*/
	private void BuildandRunGraphicPanel() {
		
		// create pnlGraphic
		pnlGraphic = new JPanel();
		pnlGraphic.setBorder( new EmptyBorder(10, 10, 10, 10) );
		pnlGraphic.setLayout( new BorderLayout() );

		// create pnlBar
		pnlBar = new JPanel( new GridLayout(1, 0, barGap, 0) );
    
    // set pnlBar border
    Border outer = new MatteBorder(1, 1, 1, 1, Color.BLACK);
    Border inner = new EmptyBorder(10, 10, 0, 10);
    pnlBar.setBorder(new CompoundBorder(outer, inner));

    pnlLabel = new JPanel( new GridLayout(1, 0, barGap, 0) );
    pnlLabel.setBorder( new EmptyBorder(5, 10, 0, 10) );

    // clear previous data
    bars.clear();
    
    // populate bar list
    addHistogramColumn("A", (int)(peopleManager.getFinalReport().
    		percentageOfPopulationContractedDisease_Total() * 100), Color.RED);
    addHistogramColumn("B", (int)(peopleManager.getFinalReport().
    		percentageOfPopulationContractedDisease_NoImmunity() * 100), Color.YELLOW);
    addHistogramColumn("C", (int)(peopleManager.getFinalReport().
    		percentageOfPopulationContractedDisease_MildImmunity() * 100), Color.BLUE);
    addHistogramColumn("D", (int)(peopleManager.getFinalReport().
    		percentageOfPopulationContractedDisease_ModerateImmunity() * 100), Color.ORANGE);
    addHistogramColumn("E", (int)(peopleManager.getFinalReport().
    		percentageOfPopulationContractedDisease_GoodImmunity() * 100), Color.MAGENTA);
    addHistogramColumn("F", (int)(peopleManager.getFinalReport().
    		percentageOfPopulationContractedDisease_NaturalImmunity() * 100), Color.CYAN);
    addHistogramColumn("G", (int)(peopleManager.getFinalReport().
    		percentageOfPopulationRecovered_Total() * 100), Color.DARK_GRAY);
    addHistogramColumn("H", (int)(peopleManager.getFinalReport().
    		percentageOfPopulationDied_NoImmunity() * 100), Color.PINK);
    addHistogramColumn("I", (int)(peopleManager.getFinalReport().
    		percentageOfPopulationDied_MildImmunity() * 100), Color.WHITE);
    addHistogramColumn("J", (int)(peopleManager.getFinalReport().
    		percentageOfPopulationDied_ModerateImmunity() * 100), Color.BLACK);
    addHistogramColumn("K", (int)(peopleManager.getFinalReport().
    		percentageOfPopulationDied_GoodImmunity() * 100), new Color(40,20,100));
    addHistogramColumn("L", (int)(peopleManager.getFinalReport().
    		percentageOfPopulationDied_NaturalImmunity() * 100), new Color(0,140,110));
    addHistogramColumn("M", (int)(peopleManager.getFinalReport().
    		percentageOfPopulationDied_Total() * 100), new Color(0,204,170));

    // add
    pnlGraphic.add(pnlBar, BorderLayout.CENTER);
    pnlGraphic.add(pnlLabel, BorderLayout.SOUTH);    
        
		pnlBar.removeAll();
    pnlLabel.removeAll();

    int maxValue = 0;

    // get maximum value 
    for (Bar bar: bars)
        maxValue = Math.max(maxValue, bar.getValue());

    // add bar elements
    for (Bar bar: bars)
    {
        JLabel lblBarLabel = new JLabel(bar.getValue() + "");
        lblBarLabel.setHorizontalTextPosition(JLabel.CENTER);
        lblBarLabel.setHorizontalAlignment(JLabel.CENTER);
        lblBarLabel.setVerticalTextPosition(JLabel.TOP);
        lblBarLabel.setVerticalAlignment(JLabel.BOTTOM);
        int barHeight = (bar.getValue() * histogramHeight) / maxValue;
        Icon icon = new ColorIcon(bar.getColor(), barWidth, barHeight);
        lblBarLabel.setIcon(icon);
        pnlBar.add(lblBarLabel);

        JLabel lblBar = new JLabel(bar.getLabel());
        lblBar.setHorizontalAlignment(JLabel.CENTER);
        pnlLabel.add(lblBar);
    }
    
    // create histogram label variables
		JLabel lblFinalPopulation = new JLabel("A - Total population that contracted the disease (%) ");
		JLabel lblFinalNone = new JLabel("B - Unvaccinated persons who contracted the disease (%) ");
		JLabel lblFinalMild = new JLabel("C - One-shot-vaccinated persons who contracted the disease (%) ");
		JLabel lblFinalModerate = new JLabel("D - Two-shot-vaccinated persons who contracted the disease (%) ");
		JLabel lblFinalGood = new JLabel("E - Three-shot-vaccinated persons who contracted the disease (%) ");
		JLabel lblFinalNatural = new JLabel("F - Naturally immune persons who got re-infected (%) ");
		JLabel lblFinalRecovered = new JLabel("G - All those who contracted the disease that recovered (%) ");
		
		JLabel lblFinalDeadNone = new JLabel("H - Unvaccinated persons who contracted the disease that died (%) ");
		JLabel lblFinalDeadMild = new JLabel("I - One-shot-vaccinated persons who contracted the disease that died (%) ");
		JLabel lblFinalDeadModerate = new JLabel("J - Two-shot-vaccinated persons who contracted the disease that died (%) ");
		JLabel lblFinalDeadGood = new JLabel("K - Three-shot-vaccinated persons who contracted the disease that died (%) ");
		JLabel lblFinalDeadNatural = new JLabel("L - Naturally immune persons who contracted the disease that died (%) ");
		JLabel lblFinalDeadTotal = new JLabel("M - All those who contracted the disease that died (%) ");

		// create new frame for graph
		JFrame frameGraph = new JFrame("Report Graph");
		frameGraph.add(pnlGraphic);
    
    JPanel pnlLabel = new JPanel(new GridLayout(13,1,0,4));
        
    // add labels (description for bars)
    pnlLabel.add(lblFinalPopulation);
    pnlLabel.add(lblFinalNone);
    pnlLabel.add(lblFinalMild);
    pnlLabel.add(lblFinalModerate);
    pnlLabel.add(lblFinalGood);
    pnlLabel.add(lblFinalNatural);
    pnlLabel.add(lblFinalRecovered);		
    pnlLabel.add(lblFinalDeadNone);
    pnlLabel.add(lblFinalDeadMild);
    pnlLabel.add(lblFinalDeadModerate);
    pnlLabel.add(lblFinalDeadGood);
    pnlLabel.add(lblFinalDeadNatural);
    pnlLabel.add(lblFinalDeadTotal);
		
    // add to pnlLabel to frameGraph
    frameGraph.add(pnlLabel, BorderLayout.SOUTH);

    frameGraph.setLocationByPlatform( true );
    frameGraph.pack();
    frameGraph.setVisible( true );    
	}
	

	/**
	 * Populate bars list 
	 * @param label
	 * @param value
	 * @param color
	 */
  public void addHistogramColumn(String label, int value, Color color)
  {
      Bar bar = new Bar(label, value, color);
      bars.add(bar);
  }

  private class Bar
  {
      private String label;
      private int value;
      private Color color;

      /**
       * Constructs a new Bar object
       * @param label
       * @param value
       * @param color
       */
      public Bar(String label, int value, Color color)
      {
          this.label = label;
          this.value = value;
          this.color = color;
      }

      // getters
      public String getLabel()
      {
          return label;
      }

      public int getValue()
      {
          return value;
      }

      public Color getColor()
      {
          return color;
      }
  }


  private class ColorIcon implements Icon
  {
      private int shadow = 3;

      private Color color;
      private int width;
      private int height;

      /**
       * Constructs a new ColorIcon object
       * @param color
       * @param width
       * @param height
       */
      public ColorIcon(Color color, int width, int height)
      {
          this.color = color;
          this.width = width;
          this.height = height;
      }

      public int getIconWidth()
      {
          return width;
      }

      public int getIconHeight()
      {
          return height;
      }

      public void paintIcon(Component c, Graphics g, int x, int y)
      {
          g.setColor(color);
          g.fillRect(x, y, width - shadow, height);
          g.setColor(Color.GRAY);
          g.fillRect(x + width - shadow, y + shadow, shadow, height - shadow);
      }
  }


	/**
	 * Saves current report
	 */
	private void SaveReport() 
	{
		try {
			// create file
      File file = new File("userInputAndResults.txt");
      
      
      if (!file.createNewFile()){ // if file already exists delete the file
      	System.out.println("File already exists. Replace the file: " + file.getName());
      	file.delete();
      }
      else {
        System.out.println("File created: " + file.getName());
      }
      
      // create FileWriter
      FileWriter writer = new FileWriter("userInputAndResults.txt");
      
      // Input part
      writer.write("Input - \n");
      writer.write("Population - " + population + " \n");
      writer.write("Non vaccinated - " + numNone+ " \n");
      writer.write("One shot vaccinated - " + numMild+ " \n");
      writer.write("Double vaccinated - " + numModerate+ " \n");
      writer.write("Triple vaccinated - " + numGood+ " \n");
      writer.write("Natural immune - " + numNatural+ " \n");
      
      // Result part
      writer.write("\nResults - \n");
      writer.write("percentageOfPopulationContractedDisease_Total - " +  (int)(peopleManager.getFinalReport().
      		percentageOfPopulationContractedDisease_Total() * 100) + " \n");
      writer.write("percentageOfPopulationContractedDisease_NoImmunity - " + (int)(peopleManager.getFinalReport().
      		percentageOfPopulationContractedDisease_NoImmunity() * 100) + " \n");
      writer.write("percentageOfPopulationContractedDisease_MildImmunity - " + (int)(peopleManager.getFinalReport().
      		percentageOfPopulationContractedDisease_MildImmunity() * 100) + " \n");
      writer.write("percentageOfPopulationContractedDisease_ModerateImmunity - " + (int)(peopleManager.getFinalReport().
      		percentageOfPopulationContractedDisease_ModerateImmunity() * 100) + " \n");
      writer.write("percentageOfPopulationContractedDisease_GoodImmunity - " + (int)(peopleManager.getFinalReport().
      		percentageOfPopulationContractedDisease_GoodImmunity() * 100) + " \n");
      writer.write("percentageOfPopulationContractedDisease_NaturalImmunity - " + (int)(peopleManager.getFinalReport().
      		percentageOfPopulationContractedDisease_NaturalImmunity() * 100) + " \n");
      writer.write("percentageOfPopulationRecovered_Total - " + (int)(peopleManager.getFinalReport().
      		percentageOfPopulationRecovered_Total() * 100) + " \n");
      writer.write("percentageOfPopulationDied_NoImmunity - " + (int)(peopleManager.getFinalReport().
      		percentageOfPopulationDied_NoImmunity() * 100) + " \n");
      writer.write("percentageOfPopulationDied_MildImmunity - " + (int)(peopleManager.getFinalReport().
      		percentageOfPopulationDied_MildImmunity() * 100) + " \n");
      writer.write("percentageOfPopulationDied_ModerateImmunity - " + (int)(peopleManager.getFinalReport().
      		percentageOfPopulationDied_ModerateImmunity() * 100) + " \n");
      writer.write("percentageOfPopulationDied_GoodImmunity - " + (int)(peopleManager.getFinalReport().
      		percentageOfPopulationDied_GoodImmunity() * 100) + " \n");
      writer.write("percentageOfPopulationDied_NaturalImmunity - " + (int)(peopleManager.getFinalReport().
      		percentageOfPopulationDied_NaturalImmunity() * 100) + " \n");
      writer.write("percentageOfPopulationDied_Total - " + (int)(peopleManager.getFinalReport().
      		percentageOfPopulationDied_Total() * 100) + " \n");
      
      // close
      writer.close();
    } 
    catch (IOException ex) 
    {
      System.out.println("An error occurred.");
      ex.printStackTrace();
    }
	}
	

}
//End class