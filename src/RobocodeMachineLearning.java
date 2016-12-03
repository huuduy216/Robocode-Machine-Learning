
public class RobocodeMachineLearning
{
	public static String PRACTICE_ROBOT_NAME = "abc.Shadow 3.84";
	public static int POPULATION_SIZE = 50;
	public static double bestScore = 0;
	public static RobotGenerator bestRobot = null;
	public static void main(String args[])
	{
		BattleRunner runner = new BattleRunner();

		RobotGenerator robots[] = new RobotGenerator[POPULATION_SIZE];
		for (int i = 0; i < 10000; i++)
		{
			System.out.println("Generation " + i);
			System.out.println("Setting up robots...");

			for (int j = 0; j < POPULATION_SIZE; j++)
			{

				// If generation 0
				if (i == 0) // start cross over robots to evolve them
				{
					robots[j] = new RobotGenerator(i, j);
					robots[j].generateCodeRandomly();
				} else
				{
					robots[j] = new RobotGenerator(i, j);
					robots[j].generateCodeRandomly();
				}
				robots[j].writeCode();
				robots[j].compileCode();
			}
			if (i != 0)
			{
				System.out.println("Evolving...");
				robots = GeneticAlgorithm.evolveRobot(robots);
				for (int j = 0; j < POPULATION_SIZE; j++)
				{
					robots[j].writeCode();
					robots[j].compileCode();
				}
			}

			System.out.println("Running simulation...");
			double score[] = new double[POPULATION_SIZE];
			for (int j = 0; j < POPULATION_SIZE; j++)
			{
				if (runner.equals(null))
				{
					runner = new BattleRunner();
				}

				System.out.print(
						"\rTesting bot " + (j + 1) + "/" + POPULATION_SIZE);
				score[j] = runner.run(20, robots[j].getRobotName());
				if (bestScore == 0)
				{
					bestRobot = robots[j];
					bestScore = score[j];
				} else
				{
					if (bestScore < score[j])
					{
						bestRobot = robots[j];
						bestScore = score[j];
					}
				}
			}
			robots[0] = bestRobot;
			System.out.println("\nBest so far: " + bestRobot.getRobotName()
					+ " with score of " + bestScore);
		}
	}
}
