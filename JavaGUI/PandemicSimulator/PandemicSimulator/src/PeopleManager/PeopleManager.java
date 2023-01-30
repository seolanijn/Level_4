 /**
  * Program Name:	PeopleManger.java
  * Purpose:		Manage the entire array of people, 
  * 				their movement, collision detection, 
  * 				and report generation
  * 
  * @author			Soohwan Kim
  * @author			Seolan Jin
  * @author			Junyeong Jo
  * @author			M Sadat Rahman
  * @version		1.0
  * @since   		Jul 24, 2022
 */

package PeopleManager;

import java.awt.Color;

/**
 * A factory class which manages all the people created.
 */
public class PeopleManager {
	
    // important variables to hold information about 
    // how and where to create objects
	private int width;                      // width of the screen
	private int height;                     // height of the screen
	private int numberOfPeople_Total;       // total number of people
	private int diameter;                   // diameter of every person
	
    // number of people of different immunity levels
    // not needed as the immunity status can change. Also it can be calculated.
	// private int numberOfPeopleWith_NoImmunity;
	// private int numberOfPeopleWith_MildImmunity;
	// private int numberOfPeopleWith_ModerateImmunity;
	// private int numberOfPeopleWith_GoodImmunity;
	// private int numberOfPeopleWith_NaturalImmunity;
	
    /* Immunity status */
	private final int IMMUNITY_STATUS_NO_IMMUNITY = 1;
	private final int IMMUNITY_STATUS_MILD_IMMUNITY = 2;
	private final int IMMUNITY_STATUS_MODERATE_IMMUNITY = 3;
	private final int IMMUNITY_STATUS_GOOD_IMMUNITY = 4;
	private final int IMMUNITY_STATUS_NATURAL_IMMUNITY = 5;
	
    /* Colors for different levels of immunity, infection status, and life condition */
	private final Color COLOR_INFECTED = Color.RED;
	private final Color COLOR_NO_IMMUNITY = Color.BLUE;
	private final Color COLOR_MILD_IMMUNITY = Color.CYAN;
	private final Color COLOR_MODERATE_IMMUNITY = Color.YELLOW;
	private final Color COLOR_GOOD_IMMUNITY = Color.MAGENTA;
	private final Color COLOR_NATURAL_IMMUNITY = Color.GREEN;
	private final Color COLOR_DEAD = Color.BLACK;
	
    /* Probability of people of different immunity status contracting disease */
	private final float PROBABILITY_OF_INFECTION_NO_IMMUNITY = 0.8f;
	private final float PROBABILITY_OF_INFECTION_MILD_IMMUNITY = 0.6f;
	private final float PROBABILITY_OF_INFECTION_MODERATE_IMMUNITY = 0.3f;
	private final float PROBABILITY_OF_INFECTION_GOOD_IMMUNITY = 0.1f;
	private final float PROBABILITY_OF_INFECTION_NATURAL_IMMUNITY = 0.4f;
	
    /* Probability of people of different immunity status dying after disease contraction */
	private final float PROBABILITY_OF_DEATH_NO_IMMUNITY = 0.1f;
	private final float PROBABILITY_OF_DEATH_MILD_IMMUNITY = 0.07f;
	private final float PROBABILITY_OF_DEATH_MODERATE_IMMUNITY = 0.03f;
	private final float PROBABILITY_OF_DEATH_GOOD_IMMUNITY = 0.01f;
	private final float PROBABILITY_OF_DEATH_NATURAL_IMMUNITY = 0.03f;

    /* Final report */
    private FinalReport fReport;
		
    /* People Array holding people information */
	private People[] people;
	
    // constructor
    /**
     * Creates a PeopleManager object and instantiates the variables with the values 
     * @param width The width of the screen expected
     * @param height The width of the screen expected
     * @param numberOfPeople The total number of the people
     * @param diameterOfCircle The diameter of every circle representing a person to be drawn
     */
	public PeopleManager(int width, int height, int numberOfPeople,
			int diameterOfCircle) 
	{
		this.width = width;
		this.height = height;
		this.numberOfPeople_Total = numberOfPeople;
		this.diameter = diameterOfCircle;
				
		people = new People[numberOfPeople];

        fReport = new FinalReport();
		
//		Populate the array. Assume that no one is vaccinated or has any immunity
		try {
			setNumberOfPeopleWithImmunity(numberOfPeople_Total, 0, 0, 0, 0);
		} catch (Exception e) {
			System.out.println("Exception thrown in PeopleManager.PeopleManager() [constructor]");	
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Populate the people array with the numbers of different immunity status provided
	 * @param numberOfPeopleWith_NoImmunity Number of people with no immunity; un vaccinated people
	 * @param numberOfPeopleWith_MildImmunity Number of people with mild immunity; vaccinated- 1 shot
	 * @param numberOfPeopleWith_ModerateImmunity Number of people with moderate immunity; vaccinated- 2 shots
	 * @param numberOfPeopleWith_GoodImmunity Number of people with good immunity; vaccinated- 3 shots
	 * @param numberOfPeopleWith_NaturalImmunity Number of people with natural immunity; naturally grown immunity
	 * @throws Exception thrown when the numbers do not add up to the total number of people
	 */
	public void setNumberOfPeopleWithImmunity (int numberOfPeopleWith_NoImmunity,
			int numberOfPeopleWith_MildImmunity, int numberOfPeopleWith_ModerateImmunity,
			int numberOfPeopleWith_GoodImmunity, int numberOfPeopleWith_NaturalImmunity) throws Exception
	{
		// sanity check
		if (!(numberOfPeopleWith_NoImmunity + 
				numberOfPeopleWith_MildImmunity +
				numberOfPeopleWith_ModerateImmunity +
				numberOfPeopleWith_GoodImmunity + 
				numberOfPeopleWith_NaturalImmunity
					== this.numberOfPeople_Total))
			throw new Exception("Numbers do not add up");

		// no longer needed
		// this.numberOfPeopleWith_NoImmunity = numberOfPeopleWith_NoImmunity;
		// this.numberOfPeopleWith_MildImmunity = numberOfPeopleWith_MildImmunity;
		// this.numberOfPeopleWith_ModerateImmunity = numberOfPeopleWith_ModerateImmunity;
		// this.numberOfPeopleWith_GoodImmunity = numberOfPeopleWith_GoodImmunity;
		// this.numberOfPeopleWith_NaturalImmunity = numberOfPeopleWith_NaturalImmunity;
		
		
		int peopleGenerated = 0;
		for (int i = peopleGenerated; i < peopleGenerated + numberOfPeopleWith_NoImmunity; i++)
		{
			people[i] = new People(this.diameter, COLOR_NO_IMMUNITY, 
					this.width, this.height, IMMUNITY_STATUS_NO_IMMUNITY);
		}
		peopleGenerated += numberOfPeopleWith_NoImmunity;
		for (int i = peopleGenerated; i < peopleGenerated + numberOfPeopleWith_MildImmunity; i++)
		{
			people[i] = new People(this.diameter, COLOR_MILD_IMMUNITY, 
					this.width, this.height, IMMUNITY_STATUS_MILD_IMMUNITY);
		}
		peopleGenerated += numberOfPeopleWith_MildImmunity;
		for (int i = peopleGenerated; i < peopleGenerated + numberOfPeopleWith_ModerateImmunity; i++)
		{
			people[i] = new People(this.diameter, COLOR_MODERATE_IMMUNITY, 
					this.width, this.height, IMMUNITY_STATUS_MODERATE_IMMUNITY);
		}
		peopleGenerated += numberOfPeopleWith_ModerateImmunity;
		for ( int i = peopleGenerated; i < peopleGenerated + numberOfPeopleWith_GoodImmunity; i++)
		{
			people[i] = new People(this.diameter, COLOR_GOOD_IMMUNITY, 
					this.width, this.height, IMMUNITY_STATUS_GOOD_IMMUNITY);
		}
		peopleGenerated += numberOfPeopleWith_GoodImmunity;
		for (int i = peopleGenerated; i < peopleGenerated + numberOfPeopleWith_NaturalImmunity; i++)
		{
			people[i] = new People(this.diameter, COLOR_NATURAL_IMMUNITY, 
					this.width, this.height, IMMUNITY_STATUS_NATURAL_IMMUNITY);
			people[i].setNumberOfTimesInfected(1);
		}
		
		
		// manually infect a random person. poor guy
		int idx = (int)(Math.random() * numberOfPeople_Total -1);
		people[idx].setIsInfected(true);
		people[idx].setColor(COLOR_INFECTED);	
	} // end setNumberOfPeopleWithImmunity
	
	
	
	public void movePeople()
	{
		// perform person-by-person case first
		for (int i = 0; i < numberOfPeople_Total; i++)
		{
			people[i].move();
			
			// check collision with screen bounds
			if (people[i].checkCollision(0, 0, this.width, this.height))
			{
                // no longer needed. Randomly selecting x and y increments 
                // overwrites these values anyway
				// people[i].setxIncrement(people[i].getxIncrement() * -1);
				// people[i].setyIncrement(people[i].getyIncrement() * -1);
				
				int firstBallnewxIncrement = (int) (Math.random() * 11 - 5);
				int firstBallnewyIncrement = (int) (Math.random() * 11 - 5);

				// this will prevent balls from "getting stuck" on the borders.
				people[i].setxIncrement(firstBallnewxIncrement);
				people[i].setyIncrement(firstBallnewyIncrement);

			}
			
			
			/*
			  	Probability of Death From Infection.
				You also need to determine if any infected dots live or die. ONLY AFTER an infected object has
				stopped being infectious (150 cycles from when it got infected), there is a probablility that the
				infected dot will die, depending on its immunity status.
			 */
			if ( people[i].getInfectedTime() >= 150 )
			{
                // reset infected time
                people[i].setInfectedTime(0);

                // actions upon death
				if(probablyDied(people[i].getImmunityStatus()))
				{
					people[i].kill(COLOR_DEAD);
				}

                // Did not die
				else /* (people[i].isInfected()) */
				{
					people[i].setIsInfected(false);
					people[i].setNumberOfTimesInfected(people[i].getNumberOfTimesInfected()+1);
					
                    // if unvaccinated people recover, they grow natural immunity
					if(people[i].getImmunityStatus() == IMMUNITY_STATUS_NO_IMMUNITY 
                        || people[i].getImmunityStatus() == IMMUNITY_STATUS_MILD_IMMUNITY)
					{
						people[i].setImmunityStatus(IMMUNITY_STATUS_NATURAL_IMMUNITY);
					}
					
					// change color of the person
					switch(people[i].getImmunityStatus())
					{
						// case IMMUNITY_STATUS_NO_IMMUNITY: // peculiar case. the case will never hit. so commented out.
						// 	people[i].setColor(COLOR_NO_IMMUNITY);
						// 	break;
						// case IMMUNITY_STATUS_MILD_IMMUNITY:
						// 	people[i].setColor(COLOR_MILD_IMMUNITY);
						// 	break;
						case IMMUNITY_STATUS_MODERATE_IMMUNITY:
							people[i].setColor(COLOR_MODERATE_IMMUNITY);
							break;
						case IMMUNITY_STATUS_GOOD_IMMUNITY:
							people[i].setColor(COLOR_GOOD_IMMUNITY);
							break;
						case IMMUNITY_STATUS_NATURAL_IMMUNITY:
							people[i].setColor(COLOR_NATURAL_IMMUNITY);
							break;
					}
				} // recovery activities
			}
		}
		
		// check for collision, and spread disease...
		for(int i = 0; i < numberOfPeople_Total; i++)
		{
            // ignore dead bodies
			if(!people[i].isAlive())
			{
				continue;
			}
			
			
			// interaction with other people
			for (int j = i + 1; j < numberOfPeople_Total; j++)
			{

                // ignore dead people
                if (!people[j].isAlive())
                {
                    continue;
                }

				// check collision with another person
				if(people[i].checkCollision(people[j]))
				{ // collision happened
					
					// randomly change direction
					people[i].setxIncrement((int)(Math.random()* 10 - 5));
					people[i].setyIncrement((int)(Math.random()* 10 - 5));
					people[j].setxIncrement((int)(Math.random()* 10 - 5));
					people[j].setyIncrement((int)(Math.random()* 10 - 5));
					
					// any collided person is infected
					// if both are infected
					if (people[i].getIsInfected() && people[j].getIsInfected())
					{
						continue;
					}
					int uninfectedPersonIdx;
					if (people[i].getIsInfected())
					{
						uninfectedPersonIdx = j;
					}
					else if (people[j].getIsInfected())
					{
						uninfectedPersonIdx = i;
					}
					//no one is infected
					else
					{
						continue;
					}
					
                    // infect the other person
					if(probablyGotInfected(people[uninfectedPersonIdx].getImmunityStatus()))
					{
						people[uninfectedPersonIdx].setColor(COLOR_INFECTED);
						people[uninfectedPersonIdx].setIsInfected(true);
						people[uninfectedPersonIdx].setNumberOfTimesInfected(people[uninfectedPersonIdx].getNumberOfTimesInfected()+1);
						
                    }
					
					// DO NOT TRY TO INCREASE THE CYCLE COUNTER OF EACH PERSON HERE.
					// THE People.move() handles the cycle counter already
					
				}
			}
		}
	} // end movePeople()
	
	/**
     * calculates whether the person with a certain immunity status can get infected or not
     * @param immunityStatus the immunity status of the person
     * @return whether the person is infected or not; true - infected, false - uninfected
     */
	private boolean probablyGotInfected(int immunityStatus) 
	{
		float randomProbability = (float)Math.random();
		boolean result = false;
		
		if (immunityStatus == IMMUNITY_STATUS_NO_IMMUNITY) {
			if (randomProbability < PROBABILITY_OF_INFECTION_NO_IMMUNITY) {
				result = true;
			}
			else {
				result = false;
			}
		}
		if (immunityStatus == IMMUNITY_STATUS_MILD_IMMUNITY) {
			if (randomProbability < PROBABILITY_OF_INFECTION_MILD_IMMUNITY) {
				result = true;
			}
			else {
				result = false;
			}
		}
		if (immunityStatus == IMMUNITY_STATUS_MODERATE_IMMUNITY) {
			if (randomProbability < PROBABILITY_OF_INFECTION_MODERATE_IMMUNITY) {
				result = true;
			}
			else {
				result = false;
			}
		}
		if (immunityStatus == IMMUNITY_STATUS_GOOD_IMMUNITY) {
			if (randomProbability < PROBABILITY_OF_INFECTION_GOOD_IMMUNITY) {
				result = true;
			}
			else {
				result = false;
			}
		}
		if (immunityStatus == IMMUNITY_STATUS_NATURAL_IMMUNITY) {
			if (randomProbability < PROBABILITY_OF_INFECTION_NATURAL_IMMUNITY) {
				result = true;
			}
			else {
				result = false;
			}
		}
		return result;
	}
	
	/**
     * calculates whether the person with a certain immunity status dies
     * @param immunityStatus the immunity status of the person
     * @return whether the person is dead; true - dead, false - alive
     */
	private boolean probablyDied(int immunityStatus)
	{
		float randomProbability = (float)Math.random();
		
		return  ((immunityStatus == IMMUNITY_STATUS_NO_IMMUNITY && randomProbability < PROBABILITY_OF_DEATH_NO_IMMUNITY)
				|| (immunityStatus == IMMUNITY_STATUS_MILD_IMMUNITY && randomProbability < PROBABILITY_OF_DEATH_MILD_IMMUNITY)
				|| (immunityStatus == IMMUNITY_STATUS_MODERATE_IMMUNITY && randomProbability < PROBABILITY_OF_DEATH_MODERATE_IMMUNITY)
				|| (immunityStatus == IMMUNITY_STATUS_GOOD_IMMUNITY && randomProbability < PROBABILITY_OF_DEATH_GOOD_IMMUNITY)
				|| (immunityStatus == IMMUNITY_STATUS_NATURAL_IMMUNITY && randomProbability < PROBABILITY_OF_DEATH_NATURAL_IMMUNITY));

	}



/* https://manytools.org/hacker-tools/ascii-banner/
 ███████████   ██████████   █████████   █████                                 
░░███░░░░░███ ░░███░░░░░█  ███░░░░░███ ░░███                                  
 ░███    ░███  ░███  █ ░  ░███    ░███  ░███                                  
 ░██████████   ░██████    ░███████████  ░███                                  
 ░███░░░░░███  ░███░░█    ░███░░░░░███  ░███                                  
 ░███    ░███  ░███ ░   █ ░███    ░███  ░███      █                           
 █████   █████ ██████████ █████   █████ ███████████                           
░░░░░   ░░░░░ ░░░░░░░░░░ ░░░░░   ░░░░░ ░░░░░░░░░░░                            
 ███████████ █████ ██████   ██████ ██████████                                 
░█░░░███░░░█░░███ ░░██████ ██████ ░░███░░░░░█                                 
░   ░███  ░  ░███  ░███░█████░███  ░███  █ ░                                  
    ░███     ░███  ░███░░███ ░███  ░██████                                    
    ░███     ░███  ░███ ░░░  ░███  ░███░░█                                    
    ░███     ░███  ░███      ░███  ░███ ░   █                                 
    █████    █████ █████     █████ ██████████                                 
   ░░░░░    ░░░░░ ░░░░░     ░░░░░ ░░░░░░░░░░                                  
 ███████████   ██████████ ███████████     ███████    ███████████   ███████████
░░███░░░░░███ ░░███░░░░░█░░███░░░░░███  ███░░░░░███ ░░███░░░░░███ ░█░░░███░░░█
 ░███    ░███  ░███  █ ░  ░███    ░███ ███     ░░███ ░███    ░███ ░   ░███  ░ 
 ░██████████   ░██████    ░██████████ ░███      ░███ ░██████████      ░███    
 ░███░░░░░███  ░███░░█    ░███░░░░░░  ░███      ░███ ░███░░░░░███     ░███    
 ░███    ░███  ░███ ░   █ ░███        ░░███     ███  ░███    ░███     ░███    
 █████   █████ ██████████ █████        ░░░███████░   █████   █████    █████   
░░░░░   ░░░░░ ░░░░░░░░░░ ░░░░░           ░░░░░░░    ░░░░░   ░░░░░    ░░░░░    
                                                                           
 */
	/*
	 * Real-time reports
	 * 
	 * Does he mean real time report?
	 * Or Does he want the aggregate result in real-time updates?
	 * 
	 * ## I assumed the former one ##
     * 
		1) Number of infected persons.
		2) Number of non-vaccinated persons infected.
		3) Number of one-shot-vaccinated people infected.
		4) Number of two-shot-vaccinated people infected.
		5) Number of three-shot-vaccinated people infected.
		6) Number of naturally immune people who have been re-infected.
		7) Number of infected people who have recovered.
		8) Number of infected people who have died.
	 */
	/**
     * Real time report of total number of infected persons
     * @return the number of infected people
     */
	public int report_NumberOfTotalInfectedPersons()
	{
		int infected = 0;
		for(int i = 0; i < numberOfPeople_Total; i++)
			if(people[i].getIsInfected())
				infected++;
		return infected;
	}
    /**
     * Real time report of total number of infected persons with no immunity
     * @return the number of infected people
     */
	public int report_NumberOfNoImmunePersonsInfected()
	{
		int infected = 0;
		for(int i = 0; i < numberOfPeople_Total; i++)
			if(people[i].getImmunityStatus() == IMMUNITY_STATUS_NO_IMMUNITY && people[i].getIsInfected())
				infected++;
		return infected;
	}
    /**
     * Real time report of total number of infected persons with mild immunity
     * @return the number of infected people
     */
	public int report_NumberOfMildImmunePersonsInfected()
	{
		int infected = 0;
		for(int i = 0; i < numberOfPeople_Total; i++)
			if(people[i].getImmunityStatus() == IMMUNITY_STATUS_MILD_IMMUNITY && people[i].getIsInfected())
				infected++;
		return infected;
	}
    /**
     * Real time report of total number of infected persons with moderate immunity
     * @return the number of infected people
     */
	public int report_NumberOfModerateImmunePersonsInfected()
	{
		int infected = 0;
		for(int i = 0; i < numberOfPeople_Total; i++)
			if(people[i].getImmunityStatus() == IMMUNITY_STATUS_MODERATE_IMMUNITY && people[i].getIsInfected())
				infected++;
		return infected;
	}
    /**
     * Real time report of total number of infected persons with good immunity
     * @return the number of infected people
     */
	public int report_NumberOfGoodImmunePersonsInfected()
	{
		int infected = 0;
		for(int i = 0; i < numberOfPeople_Total; i++)
			if(people[i].getImmunityStatus() == IMMUNITY_STATUS_GOOD_IMMUNITY && people[i].getIsInfected())
				infected++;
		return infected;
	}
    /**
     * Real time report of total number of infected persons with natural immunity
     * @return the number of infected people
     */
	public int report_NumberOfNaturalImmunePersonsInfected()
	{
		int infected = 0;
		for(int i = 0; i < numberOfPeople_Total; i++)
			if(people[i].getImmunityStatus() == IMMUNITY_STATUS_NATURAL_IMMUNITY && people[i].getIsInfected())
				infected++;
		return infected;
	}
    /**
     * Real time report of total number of recovered persons
     * @return the number of recovered people
     */
	public int report_NumberOfRecoveredPeople()
	{
		int recovered = 0;
		for(int i = 0; i < numberOfPeople_Total; i++)
			if(people[i].getNumberOfTimesInfected() > 0 && !(people[i].getIsInfected()))
				recovered++;
		return recovered;
	}
    /**
     * Real time report of total number of dead persons
     * @return the number of dead people
     */
	public int report_NumberOfDeadPeople()
	{
		int death = 0;
		for(int i = 0; i < numberOfPeople_Total; i++)
			if(!people[i].isAlive())
				death++;
		return death;
	}
	
	

/* https://manytools.org/hacker-tools/ascii-banner/
   █████████  ██████████ ███████████ ███████████ ██████████ ███████████  
  ███░░░░░███░░███░░░░░█░█░░░███░░░█░█░░░███░░░█░░███░░░░░█░░███░░░░░███ 
 ███     ░░░  ░███  █ ░ ░   ░███  ░ ░   ░███  ░  ░███  █ ░  ░███    ░███ 
░███          ░██████       ░███        ░███     ░██████    ░██████████  
░███    █████ ░███░░█       ░███        ░███     ░███░░█    ░███░░░░░███ 
░░███  ░░███  ░███ ░   █    ░███        ░███     ░███ ░   █ ░███    ░███ 
 ░░█████████  ██████████    █████       █████    ██████████ █████   █████
  ░░░░░░░░░  ░░░░░░░░░░    ░░░░░       ░░░░░    ░░░░░░░░░░ ░░░░░   ░░░░░ 
  █████████  ██████████ ███████████ ███████████ ██████████ ███████████   
 ███░░░░░███░░███░░░░░█░█░░░███░░░█░█░░░███░░░█░░███░░░░░█░░███░░░░░███  
░███    ░░░  ░███  █ ░ ░   ░███  ░ ░   ░███  ░  ░███  █ ░  ░███    ░███  
░░█████████  ░██████       ░███        ░███     ░██████    ░██████████   
 ░░░░░░░░███ ░███░░█       ░███        ░███     ░███░░█    ░███░░░░░███  
 ███    ░███ ░███ ░   █    ░███        ░███     ░███ ░   █ ░███    ░███  
░░█████████  ██████████    █████       █████    ██████████ █████   █████ 
 ░░░░░░░░░  ░░░░░░░░░░    ░░░░░       ░░░░░    ░░░░░░░░░░ ░░░░░   ░░░░░  
                                                                         
            
 */
	
    /**
	 * Gets the width of this object  
	 * @return the width
	 */
	
	public int getWidth() {
		return width;
	}

	/**
	 * Gets the height of this object  
	 * @return the height
	 */
	
	public int getHeight() {
		return height;
	}

	/**
	 * Gets the numberOfPeople_Total of this object  
	 * @return the numberOfPeople_Total
	 */
	
	public int getNumberOfPeople_Total() {
		return numberOfPeople_Total;
	}

	/**
	 * Gets the diameter of this object  
	 * @return the diameter
	 */
	
	public int getDiameter() {
		return diameter;
	}

	/**
	 * Gets the numberOfPeopleWith_NoImmunity of this object  
	 * @return the numberOfPeopleWith_NoImmunity
	 */
	
	public int getNumberOfPeopleWith_NoImmunity() {
		int total = 0;
        for (int i = 0; i < people.length; i++)
        {
            if (people[i].getImmunityStatus() == IMMUNITY_STATUS_NO_IMMUNITY)
            {
                total++;
            }
        }

        return total;
	}

	/**
	 * Gets the numberOfPeopleWith_MildImmunity of this object  
	 * @return the numberOfPeopleWith_MildImmunity
	 */
	
	public int getNumberOfPeopleWith_MildImmunity() {
        int total = 0;
        for (int i = 0; i < people.length; i++)
        {
            if (people[i].getImmunityStatus() == IMMUNITY_STATUS_MILD_IMMUNITY)
            {
                total++;
            }
        }

        return total;
    }

	/**
	 * Gets the numberOfPeopleWith_ModerateImmunity of this object  
	 * @return the numberOfPeopleWith_ModerateImmunity
	 */
	
	public int getNumberOfPeopleWith_ModerateImmunity() {
        int total = 0;
        for (int i = 0; i < people.length; i++)
        {
            if (people[i].getImmunityStatus() == IMMUNITY_STATUS_MODERATE_IMMUNITY)
            {
                total++;
            }
        }

        return total;
	}

	/**
	 * Gets the numberOfPeopleWith_GoodImmunity of this object  
	 * @return the numberOfPeopleWith_GoodImmunity
	 */
	
	public int getNumberOfPeopleWith_GoodImmunity() {
        int total = 0;
        for (int i = 0; i < people.length; i++)
        {
            if (people[i].getImmunityStatus() == IMMUNITY_STATUS_GOOD_IMMUNITY)
            {
                total++;
            }
        }

        return total;
	}

	/**
	 * Gets the numberOfPeopleWith_NaturalImmunity of this object  
	 * @return the numberOfPeopleWith_NaturalImmunity
	 */
	
	public int getNumberOfPeopleWith_NaturalImmunity() {
        int total = 0;
        for (int i = 0; i < people.length; i++)
        {
            if (people[i].getImmunityStatus() == IMMUNITY_STATUS_NATURAL_IMMUNITY)
            {
                total++;
            }
        }

        return total;
	}

	/**
	 * Gets the people of this object  
	 * @return the people
	 */
	
	public People[] getPeople() {
		return people;
	}
	
	/**
	 * Gets the final report of this object  
	 * @return the final Report
	 */
		public FinalReport getFinalReport() {
		return fReport;
	}
	




/* https://manytools.org/hacker-tools/ascii-banner/
 ███████████ █████ ██████   █████   █████████   █████                         
░░███░░░░░░█░░███ ░░██████ ░░███   ███░░░░░███ ░░███                          
 ░███   █ ░  ░███  ░███░███ ░███  ░███    ░███  ░███                          
 ░███████    ░███  ░███░░███░███  ░███████████  ░███                          
 ░███░░░█    ░███  ░███ ░░██████  ░███░░░░░███  ░███                          
 ░███  ░     ░███  ░███  ░░█████  ░███    ░███  ░███      █                   
 █████       █████ █████  ░░█████ █████   █████ ███████████                   
░░░░░       ░░░░░ ░░░░░    ░░░░░ ░░░░░   ░░░░░ ░░░░░░░░░░░                    
 ███████████   ██████████ ███████████     ███████    ███████████   ███████████
░░███░░░░░███ ░░███░░░░░█░░███░░░░░███  ███░░░░░███ ░░███░░░░░███ ░█░░░███░░░█
 ░███    ░███  ░███  █ ░  ░███    ░███ ███     ░░███ ░███    ░███ ░   ░███  ░ 
 ░██████████   ░██████    ░██████████ ░███      ░███ ░██████████      ░███    
 ░███░░░░░███  ░███░░█    ░███░░░░░░  ░███      ░███ ░███░░░░░███     ░███    
 ░███    ░███  ░███ ░   █ ░███        ░░███     ███  ░███    ░███     ░███    
 █████   █████ ██████████ █████        ░░░███████░   █████   █████    █████   
░░░░░   ░░░░░ ░░░░░░░░░░ ░░░░░           ░░░░░░░    ░░░░░   ░░░░░    ░░░░░    
                                                                              
                                                                              
 */


 /**
  * Inner class to calculate final report
  */
	public class FinalReport 
	{
        /**
         * Calculates percentage of people infected
         * @return a float value in range [0, 1]
         */
        public float percentageOfPopulationContractedDisease_Total()
        {
            int total = 0;
            for (int i = 0; i < people.length; i++)
            {
                if(people[i].getNumberOfTimesInfected() > 0)
                {
                    total++;
                }
            }

            return (float)total/people.length;
        }

         /**
         * Calculates percentage of people infected - no immunity, unvaccinated
         * @return a float value in range [0, 1]
         */
        public float percentageOfPopulationContractedDisease_NoImmunity()
        {
            int total = 0;
            for (int i = 0; i < people.length; i++)
            {
                if((people[i].getImmunityStatus() == IMMUNITY_STATUS_NO_IMMUNITY && people[i].getIsInfected())
                        || people[i].getImmunityStatus() == IMMUNITY_STATUS_NATURAL_IMMUNITY)
                {
                    total++;
                }
            }

            return (float)total/people.length;

        }

        /**
         * Calculates percentage of people infected - mild immunity, vaccinated - 1 shots
         * @return a float value in range [0, 1]
         */
        public float percentageOfPopulationContractedDisease_MildImmunity()
        {
            int total = 0;
            for (int i = 0; i < people.length; i++)
            {
                if(people[i].getImmunityStatus() == IMMUNITY_STATUS_MILD_IMMUNITY && people[i].getNumberOfTimesInfected() > 0)
                {
                    total++;
                }
            }

            return (float)total/people.length;
        }

        /**
         * Calculates percentage of people infected - moderate immunity, vaccinated - 2 shots
         * @return a float value in range [0, 1]
         */
        public float percentageOfPopulationContractedDisease_ModerateImmunity()
        {
            int total = 0;
            for (int i = 0; i < people.length; i++)
            {
                if(people[i].getImmunityStatus() == IMMUNITY_STATUS_MODERATE_IMMUNITY && people[i].getNumberOfTimesInfected() > 0)
                {
                    total++;
                }
            }

            return (float)total/people.length;
        }

        /**
         * Calculates percentage of people infected - good immunity, vaccinated - 3 shots
         * @return a float value in range [0, 1]
         */
        public float percentageOfPopulationContractedDisease_GoodImmunity()
        {
            int total = 0;
            for (int i = 0; i < people.length; i++)
            {
                if(people[i].getImmunityStatus() == IMMUNITY_STATUS_GOOD_IMMUNITY && people[i].getNumberOfTimesInfected() > 0)
                {
                    total++;
                }
            }

            return (float)total/people.length;
        }

        /**
         * Calculates percentage of people infected - natural immunity
         * @return a float value in range [0, 1]
         */
        public float percentageOfPopulationContractedDisease_NaturalImmunity()
        {
            int total = 0;
            for (int i = 0; i < people.length; i++)
            {
                if(people[i].getImmunityStatus() == IMMUNITY_STATUS_NATURAL_IMMUNITY && people[i].getNumberOfTimesInfected() > 0)
                {
                    total++;
                }
            }

            return (float)total/people.length;
        }

        /**
         * Calculates percentage of people recovered at least once
         * @return a float value in range [0, 1]
         */
        public float percentageOfPopulationRecovered_Total()
        {
            int total = 0;
            for (int i = 0; i < people.length; i++)
            {
                if((people[i].getNumberOfTimesInfected() == 1 && !people[i].getIsInfected())
                        || people[i].getNumberOfTimesInfected() > 1)
                {
                    total++;
                }
            }

            return (float)total/people.length;
        }

        /* 
         * Death rate
         */

        /**
         * Calculates percentage of people died - no immunity, unvaccinated
         * @return a float value in range [0, 1]
         */
        public float percentageOfPopulationDied_NoImmunity()
        {
            int death = 0;
            int persons = 0;
            for (int i = 0; i < people.length; i++)
            {
                if(people[i].getImmunityStatus() == IMMUNITY_STATUS_NO_IMMUNITY)
                {
                    persons++;
                    if (!people[i].isAlive())
                    {
                        death++;
                    }
                }
            }

            return (float)death/persons;
        }

        /**
         * Calculates percentage of people died - mild immunity, vaccinated - 1 shots
         * @return a float value in range [0, 1]
         */
        public float percentageOfPopulationDied_MildImmunity()
        {
            int death = 0;
            int persons = 0;
            for (int i = 0; i < people.length; i++)
            {
                if(people[i].getImmunityStatus() == IMMUNITY_STATUS_MILD_IMMUNITY)
                {
                    persons++;
                    if (!people[i].isAlive())
                    {
                        death++;
                    }
                }
            }

            return (float)death/persons;
        }

        /**
         * Calculates percentage of people died - moderate immunity, vaccinated - 2 shots
         * @return a float value in range [0, 1]
         */
        public float percentageOfPopulationDied_ModerateImmunity()
        {
            int death = 0;
            int persons = 0;
            for (int i = 0; i < people.length; i++)
            {
                if(people[i].getImmunityStatus() == IMMUNITY_STATUS_MODERATE_IMMUNITY)
                {
                    persons++;
                    if (!people[i].isAlive())
                    {
                        death++;
                    }
                }
            }

            return (float)death/persons;
        }

        /**
         * Calculates percentage of people died - good immunity, vaccinated - 3 shots
         * @return a float value in range [0, 1]
         */
        public float percentageOfPopulationDied_GoodImmunity()
        {
            int death = 0;
            int persons = 0;
            for (int i = 0; i < people.length; i++)
            {
                if(people[i].getImmunityStatus() == IMMUNITY_STATUS_GOOD_IMMUNITY)
                {
                    persons++;
                    if (!people[i].isAlive())
                    {
                        death++;
                    }
                }
            }

            return (float)death/persons;
        }

         /**
         * Calculates percentage of people died - natural immunity
         * @return a float value in range [0, 1]
         */
        public float percentageOfPopulationDied_NaturalImmunity()
        {
            int death = 0;
            int persons = 0;
            for (int i = 0; i < people.length; i++)
            {
                if(people[i].getImmunityStatus() == IMMUNITY_STATUS_NATURAL_IMMUNITY)
                {
                    persons++;
                    if (!people[i].isAlive())
                    {
                        death++;
                    }
                }
            }

            return (float)death/persons;
        }

        /**
         * Calculates percentage of total people died
         * @return a float value in range [0, 1]
         */
        public float percentageOfPopulationDied_Total()
        {
            return (float)report_NumberOfDeadPeople()/people.length;
        }
	} // end class FinalReport
}
// end of class
