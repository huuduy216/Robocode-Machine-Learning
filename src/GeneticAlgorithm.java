public class GeneticAlgorithm
{

	/* GA parameters */
	private static final double uniformRate = 0.55;
	private static final double mutationRate = 0.015;
	private static final int tournamentSize = 3;
	private static final double FITTEST_BIAS_RATE = 0.75;

	/* Public methods */

	// Evolve a population
	public static RobotGenerator[] evolveRobot(RobotGenerator[] robots)
	{
		// Crossover population
		
		// Loop over the population size and create new individuals with
		// crossover
		for (int i = 1; i < RobocodeMachineLearning.POPULATION_SIZE; i++)
		{
			System.out.print("\rEvolving "+(i+1)+"/"+RobocodeMachineLearning.POPULATION_SIZE);
			RobotGenerator robot1;
			if(Math.random() <= FITTEST_BIAS_RATE)//BIAS  to use fittest robot
			{
				robot1 = RobocodeMachineLearning.bestRobot;
			}
			else
			{
				robot1 = tournamentSelection(robots);	
			}
			RobotGenerator robot2 = tournamentSelection(robots);
			RobotGenerator newRobot = crossover(robot1, robot2);
			robots[i] = newRobot;
		}
		System.out.println(" ");
		// Mutate population
		for (int i = 0; i < RobocodeMachineLearning.POPULATION_SIZE; i++)
		{
			robots[i] = mutate(robots[i]);
		}

		return robots;
	}

	/**
	 * Crossover individuals, use the name of robot2. robot1 should be the better one.
	 * @param robot1
	 * @param robot2
	 * @return a robot that has been breed using both
	 */
	public static RobotGenerator crossover(RobotGenerator robot1, RobotGenerator robot2)
	{
		RobotGenerator newRobot = new RobotGenerator();
		newRobot.setRobotName(robot2.getRobotName());
		Trait[] traits = new Trait[6];
		// Loop through traits
		for (int i = 0; i < 6; i++)
		{
			traits[i] = new Trait();
			// Crossover
			//Loop through subTraits
			for(int j = 0; j < 7; j++)
			{
				if (Math.random() <= uniformRate)
				{
					traits[i].setSubTrait(j, robot1.getTraits()[i].getSubTraits(j));
				} else
				{
					traits[i].setSubTrait(j, robot2.getTraits()[i].getSubTraits(j));
				}
			}
		}
		newRobot.generateCodeFromTrait(traits);
		return newRobot;
	}

	// Mutate sub trait
	private static RobotGenerator mutate(RobotGenerator robot)
	{
		Trait[] traits = robot.getTraits();
		RobotGenerator newRobot = new RobotGenerator();
		newRobot.setRobotName(robot.getRobotName());
		
		// Loop through genes
		for (int i = 0; i < traits.length; i++)
		{
			for(int j = 0; j < 6; j++)
			{
				if (Math.random() <= mutationRate)
				{
					if(j == 0 || j == 2)
					{
						if(i != 5)
						{
							traits[i].setSubTrait(j,  RobotGenerator.ROBOT_SCAN_FUNCTIONS[(int)(Math.random()*RobotGenerator.ROBOT_SCAN_FUNCTIONS.length)]);
						}
						else
						{
							traits[i].setSubTrait(j,  RobotGenerator.ROBOT_WALL_HIT_FUNCTIONS[(int)(Math.random()*RobotGenerator.ROBOT_WALL_HIT_FUNCTIONS.length)]);
						}	
					}
					else if(j == 1 || j == 3 || j == 5)
					{
						traits[i].setSubTrait(j, RobotGenerator.MATH_EXPRESSION[(int)(Math.random()*RobotGenerator.MATH_EXPRESSION.length)]);
					}
					else if(j == 4)
					{
						traits[i].setSubTrait(j, String.valueOf(Math.random()*500));
					}
					else if(j == 6)
					{
						traits[i].setSubTrait(6, RobotGenerator.MATH_FUNCTIONS[(int)(Math.random()*RobotGenerator.MATH_FUNCTIONS.length)]);
					}
				}
			}
			
		}
		newRobot.generateCodeFromTrait(traits);
		return newRobot;
	}

	// Select individuals for crossover
	private static RobotGenerator tournamentSelection(RobotGenerator[] robots)
	{
		// Create a tournament population
		RobotGenerator[] tournament = new RobotGenerator[tournamentSize];
		// For each place in the tournament get a random individual
		for (int i = 0; i < tournamentSize; i++)
		{
			int randomId = (int) (Math.random() * (robots.length));
			tournament[i] = robots[randomId];
		}
		// Get the fittest
		int fittestId = 0;
		double fittestScore = 0;
		BattleRunner runner = new BattleRunner();
		for(int i = 0; i < tournamentSize; i++)
		{
			double temp = runner.run(3, tournament[i].getRobotName());
			if(fittestScore < temp)
			{
				fittestScore = temp;
				fittestId = i;
			}
		}
		return tournament[fittestId];
	}
}