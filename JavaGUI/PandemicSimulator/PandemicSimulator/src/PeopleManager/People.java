/**
 * Program Name: ProjectSimulatedGUI.java 
 * Purpose:: this is a modified class to create a people object that will be used in the
 *         PandemicSimulatorMain application.
 *         
 * 
 * @author			Soohwan Kim
 * @author			Seolan Jin
 * @author			Junyeong Jo
 * @author			M Sadat Rahman
 * @version		    1.0
 * @since           Jul 18, 2022
 */        


package PeopleManager;

import java.awt.*;

/**
 * People class. Represents a person
 */
public class People
{
    // Important class members 
	private int xCoord;     						// coordinate x value
	private int yCoord;    						  // coordinate y value
	private int diameter;   						// diameter of the circle presentation
	private boolean _isAlive;   				// whether the person is alive
	private boolean _isInfected;    		// whether the person is infected 
	private Color color;    						// color representing the person
	private int immunityStatus; 				// immunity status of the person
	private int infectedTime; 					// cycle counter
	private int numberOfTimesInfected; 	// times infected
	
	private int xIncrement;						 	// position increment value on the x axis
	private int yIncrement; 						// position increment value on the y axis
	

    // constructor
    /**
     * Creates a people object with the position of the person, diameterm color, and immunity values
     * @param x coordinate value of x axis
     * @param y coordinate value of y axis
     * @param diameter diameter of the circle
     * @param color color of the person object
     * @param immunity immunity status
     */
	public People(int x, int y, int diameter, Color color, int immunity)
	{
		//assign parameters
		this.xCoord = x;
		this.yCoord = y;
		this.diameter = diameter;
		this.immunityStatus = immunity;
		// everyone starts at 0, regardless of infection or immunity status
		this.infectedTime = 0;
		this.color = color;
		this._isAlive = true;

		boolean loopFlag = true;
		do
		{
			this.xIncrement = (int)(Math.random()*10 - 4);	// The value of each can range from -5 to +5 pixels.
			this.yIncrement = (int)(Math.random()*10 - 4);		
			if(!(this.xIncrement == 0) &&
					!(this.yIncrement == 0))
			{
				loopFlag = false;
			}
		} while(loopFlag); //end loop
		
	}//end constructor
	
    /**
     * creates a people object with random position within the specified width and height
     * @param diameter diameter of the circle
     * @param color color of the person object
     * @param widthValue width of the screen
     * @param heightValue height of the screen
     * @param immunity immunity status
     */
	public People(int diameter, Color color, int  widthValue, int heightValue, int immunity)
	{
        this(0, 0, diameter, color, immunity);
		do
		{
			//generate a random value using widthValue
			int xCoord = (int)(Math.random() * widthValue);
			int yCoord = (int)(Math.random() * heightValue);

			// if the ball is in range, break out of loop 
			if (!(new People(xCoord, yCoord, diameter, color, immunity)
					.checkCollision(0, 0, widthValue, heightValue)))
			{
                this.setxCoord(xCoord);
                this.setyCoord(yCoord);
				return; // NOTE: instead of breaking out of the loop to end the contructor,
						//  we manually invoke return 
			}
		} while(true); //end loop
	}//end random constructor

/* https://manytools.org/hacker-tools/ascii-banner/
 __    __     ______     ______   __  __     ______     _____     ______    
/\ "-./  \   /\  ___\   /\__  _\ /\ \_\ \   /\  __ \   /\  __-.  /\  ___\   
\ \ \-./\ \  \ \  __\   \/_/\ \/ \ \  __ \  \ \ \/\ \  \ \ \/\ \ \ \___  \  
 \ \_\ \ \_\  \ \_____\    \ \_\  \ \_\ \_\  \ \_____\  \ \____-  \/\_____\ 
  \/_/  \/_/   \/_____/     \/_/   \/_/\/_/   \/_____/   \/____/   \/_____/ 
                                                                            
*/

    /**
     * Move the object calculating the increments
     */
	public void move()
	{
        // move only if alive
		if(isAlive())
        {
            setxCoord(this.xCoord + this.xIncrement);
            setyCoord(this.yCoord + this.yIncrement);

            // also increase the infected time value
            if (getIsInfected() || getInfectedTime() > 0)
            {
                infectedTime++;
            }    
        }
	}

    /**
     * check the collision of the object within the bounds
     * @param minWidth minimum x value
     * @param minHeight minimum y value
     * @param maxWidth maximum x value
     * @param maxHeight maximum y value
     * @return boolean value; true - collided, false - no collision
     */
	public boolean checkCollision(int minWidth, int minHeight, int maxWidth, int maxHeight)
	{
		if (xCoord < minWidth + this.diameter ||
				yCoord < minHeight + this.diameter||
				xCoord > maxWidth - this.diameter ||
				yCoord > maxHeight - this.diameter)
			return true;
		return false;
	}
    /**
     * check collision with other person
     * @param otherPeople another person object
     * @return boolean value; true - collided, false - no collision
     */
	public boolean checkCollision(People otherPeople)
	{
		int dx = this.xCoord - otherPeople.xCoord;
		int dy = this.yCoord - otherPeople.yCoord;
		float distance = (float)Math.sqrt(dx * dx + dy * dy);

		if(distance <= this.diameter /2f + otherPeople.getDiameter()/2f)
			return true;

		return false;
	}

    /**
     * check collision of this object within bounds with another object
     * @param minWidth minimum x value
     * @param minHeight minimum y value
     * @param maxWidth maximum x value
     * @param maxHeight maximum y value
     * @param otherPeople another person object
     * @return boolean value; true - collided, false - no collision
     */
	public boolean checkCollision (int minWidth, int minHeight, int maxWidth, int maxHeight, People otherPeople)
	{
		return checkCollision(minWidth, minHeight, maxWidth, maxHeight) || checkCollision(otherPeople);
	}
	/**
     * kill and immobilize this person
     * @param color color of the person to set upon death
     */
	public void kill(Color color)
	{
		this._isAlive = false;
		this.xIncrement = 0;
		this.yIncrement = 0;
		this.color = color;
	}



/* https://manytools.org/hacker-tools/ascii-banner/
 ______     ______     ______   ______   ______     ______    
/\  ___\   /\  ___\   /\__  _\ /\__  _\ /\  ___\   /\  == \   
\ \ \__ \  \ \  __\   \/_/\ \/ \/_/\ \/ \ \  __\   \ \  __<   
 \ \_____\  \ \_____\    \ \_\    \ \_\  \ \_____\  \ \_\ \_\ 
  \/_____/   \/_____/     \/_/     \/_/   \/_____/   \/_/ /_/ 
                                                              
 ______     ______     ______   ______   ______     ______    
/\  ___\   /\  ___\   /\__  _\ /\__  _\ /\  ___\   /\  == \   
\ \___  \  \ \  __\   \/_/\ \/ \/_/\ \/ \ \  __\   \ \  __<   
 \/\_____\  \ \_____\    \ \_\    \ \_\  \ \_____\  \ \_\ \_\ 
  \/_____/   \/_____/     \/_/     \/_/   \/_____/   \/_/ /_/ 
                                                              
*/
	//getters and setters
    /**
     * get the variable value
     * @return the value
     */
	public Color getColor()
	{
		return color;
	}

    /**
     * sets the variable value
     */
	public void setColor(Color color)
	{
		this.color = color;
	}

    /**
     * get the variable value
     * @return the value
     */
	public int getxCoord()
	{
		return xCoord;
	}
    /**
     * get the variable value
     * @return the value
     */
	public int getyCoord()
	{
		return yCoord;
	}

    /**
     * get the variable value
     * @return the value
     */
	public int getDiameter()
	{
		return diameter;
	}
	
    /**
     * sets the variable value
     */
	public void setDiameter(int newDiameter)
	{
		this.diameter = newDiameter;
	}
	
    /**
     * sets the variable value
     */
	public void setxCoord(int xCoord)
	{
		this.xCoord = xCoord;
	}

    /**
     * sets the variable value
     */
	public void setyCoord(int yCoord)
	{
		this.yCoord = yCoord;
	}
  
    /**
     * get the variable value
     * @return the value
     */
	public int getxIncrement()
	{
		return xIncrement;
	}

    /**
     * sets the variable value
     */
	public void setxIncrement(int xIncrement)
	{
		this.xIncrement = xIncrement;
	}

    /**
     * get the variable value
     * @return the value
     */
	public int getyIncrement()
	{
		return yIncrement;
	}

    /**
     * sets the variable value
     */
	public void setyIncrement(int yIncrement)
	{
		this.yIncrement = yIncrement;
	}

    /**
     * get the variable value
     * @return the value
     */
	public boolean isAlive() {
		return _isAlive;
	}

    /**
     * sets the variable value
     */
	public void isAlive(boolean _isAlive) {
		this._isAlive = _isAlive;
	}

    /**
     * get the variable value
     * @return the value
     */
	public boolean getIsInfected() {
		return _isInfected;
	}

    /**
     * sets the variable value
     */
	public void setIsInfected(boolean _isInfected) {
		// if the person was not infected but is getting infected now, 
		// increase the number of times infected
		/*if ((!this.getIsInfected()) && _isInfected)
			this.numberOfTimesInfected++;*/
		
		this._isInfected = _isInfected;
	}

    /**
     * get the variable value
     * @return the value
     */
	public int getImmunityStatus() {
		return immunityStatus;
	}

    /**
     * sets the variable value
     */
	public void setImmunityStatus(int immunityStatus) {
		this.immunityStatus = immunityStatus;
	}

    /**
     * get the variable value
     * @return the value
     */
	public int getInfectedTime() {
		return infectedTime;
	}

	public void setInfectedTime(int infectedTime) {
		this.infectedTime = infectedTime;
	}
	
	/**
	 * Gets the numberOfTimesInfected of this object  
	 * @return the numberOfTimesInfected
	 */
	
	public int getNumberOfTimesInfected() {
		return numberOfTimesInfected;
	}

	/**
	 * Sets the numberOfTimesInfected of this object
	 * @param numberOfTimesInfected - the value to set
	 */
	
	public void setNumberOfTimesInfected(int numberOfTimesInfected) {
		this.numberOfTimesInfected = numberOfTimesInfected;
	}


}
//end class