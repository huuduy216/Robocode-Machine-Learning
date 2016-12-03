import robocode.BattleResults;
import robocode.control.events.BattleAdaptor;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.BattleErrorEvent;
import robocode.control.events.BattleMessageEvent;

public class BattleObserver extends BattleAdaptor
{
	private BattleResults[] battleResults;
	// Called when the battle is completed successfully with battle results
	public void onBattleCompleted(BattleCompletedEvent e)
	{
		this.battleResults = e.getIndexedResults();
	}
	
	public BattleResults[] getBattleResults()
	{
		return this.battleResults;
	}
	
	// Called when the game sends out an error message during the battle
	public void onBattleError(BattleErrorEvent e)
	{
		System.out.println("Err> " + e.getError());
		System.exit(0);
	}
}
