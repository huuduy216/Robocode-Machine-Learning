import robocode.BattleResults;
import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;
public class BattleRunner
{
	private RobocodeEngine engine;
	private BattlefieldSpecification battlefield;
	private BattleObserver battleObserver;
	
	public BattleRunner()
	{
		// Disable log messages from Robocode
	    RobocodeEngine.setLogMessagesEnabled(false);

	    // Create the RobocodeEngine
	    //   RobocodeEngine engine = new RobocodeEngine(); // Run from current working directory
	    this.engine = new RobocodeEngine(new java.io.File("C:/Robocode")); // Run from C:/Robocode
	    this.battleObserver = new BattleObserver();
	    // Add our own battle listener to the RobocodeEngine 
	    engine.addBattleListener(this.battleObserver);

	    // Show the Robocode battle view
	    engine.setVisible(false);
	}
	
	/**
	 * Run battle between the robot and the practice robot(name is in from RobocodeMachineLearning.PRACTICE_ROBOT_NAME)
	 * int round is the number of round to run. String robotName is the name of the robot(should already be compiled)
	**/
	public double run(int round, String robotName)
	{
		BattleResults[] results;
		// Setup the battle specification
	    this.battlefield = new BattlefieldSpecification(800, 600); // 800x600
	    RobotSpecification[] robots = engine.getLocalRepository(RobocodeMachineLearning.PRACTICE_ROBOT_NAME+","+"learner."+robotName+"*");

	    BattleSpecification battleSpec = new BattleSpecification(round, this.battlefield, robots);

	    // Run our specified battle and let it run till it is over
	    engine.runBattle(battleSpec, true); // waits till the battle finishes
	    results = this.battleObserver.getBattleResults();
	    
	    double score =0;
	    double totalScore = (results[0].getScore()+results[1].getScore());
	    if(results[0].getTeamLeaderName().equals(RobotGenerator.PACKAGE+"."+robotName+"*"))
	    {
	    	score = results[0].getScore()/totalScore;
	    }
	    else
	    {
	    	score = results[1].getScore()/totalScore;
	    }
	    return score;
	}
	
}
