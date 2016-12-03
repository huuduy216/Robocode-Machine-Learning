package robocode_project;

import robocode.control.*;
import robocode.control.events.*;
import java.io.*;	//-added, needed for file writing
 
 //
 // Application that demonstrates how to run two sample robots in Robocode using the
 // RobocodeEngine from the robocode.control package.
 //
 // @author Flemming N. Larsen
 //
 public class roboclass {
 
     public static void main(String[] args) /*throws IOException*/ {
 
         // Disable log messages from Robocode
         RobocodeEngine.setLogMessagesEnabled(false);

         // Create the RobocodeEngine
         //   RobocodeEngine engine = new RobocodeEngine(); // Run from current working directory
         RobocodeEngine engine = new RobocodeEngine(new java.io.File("C:/Robocode")); // Run from C:/Robocode
 
         // Add our own battle listener to the RobocodeEngine 
         engine.addBattleListener(new BattleObserver());
 
         // Show the Robocode battle view
         engine.setVisible(true);
 
         // Setup the battle specification
 
         int numberOfRounds = 5;
         BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600); // 800x600
         RobotSpecification[] selectedRobots = engine.getLocalRepository("TestPack1.Test1,sample.PaintingRobot");
 
         BattleSpecification battleSpec = new BattleSpecification(numberOfRounds, battlefield, selectedRobots);
 
         // Run our specified battle and let it run till it is over
         engine.runBattle(battleSpec, true); // waits till the battle finishes
 
         // Cleanup our RobocodeEngine
         engine.close();
 
         // Make sure that the Java VM is shut down properly
         System.exit(0);
     }
 }

 // Our private battle listener for handling the battle event we are interested in.
 class BattleObserver extends BattleAdaptor {
 
     // Called when the battle is completed successfully with battle results
     public void onBattleCompleted(BattleCompletedEvent e) {
         System.out.println("-- Battle has completed --");
         
         //-added
         try{	//writes robot data to a file
        	//runs once for each robot that participated, makes a new object with battle results
        	 for (robocode.BattleResults result : e.getSortedResults()) {
        		 //initializes a new file with the name of the robot in it
        		 File roboRes = new File("C:/aibots/roboStats_" + result.getTeamLeaderName() + ".txt");
        		 roboRes.createNewFile();	//creates a file at the spot
        	 
        		 PrintWriter writer = new PrintWriter(roboRes);
        		 //writes the damage done by the robot in its own line, to the file
        		 writer.println("damage_done=" + result.getBulletDamage());
        		 //can add more things to add here
        		 
        		 writer.close();
        	 
        		 System.out.println("Results have been written to: " + roboRes);
        	 }
         }catch (IOException exc){
        	 //in case of exception
         }
         
         // Print out the sorted results with the robot names, can be deleted
         System.out.println("Battle results:");
         for (robocode.BattleResults result : e.getSortedResults()) {
             System.out.println("  " + result.getTeamLeaderName() + ": " + result.getScore());
         }
     }
 
     // Called when the game sends out an information message during the battle
     public void onBattleMessage(BattleMessageEvent e) {
         System.out.println("Msg> " + e.getMessage());
     }
 
     // Called when the game sends out an error message during the battle
     public void onBattleError(BattleErrorEvent e) {
         System.out.println("Err> " + e.getError());
     }
 }