import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.scene.shape.Path;

public class RobotGenerator
{
	public String getRobotName()
	{
		return robotName;
	}

	final static String PATH = new String("C:\\robocode\\robots\\learner");
	static String PACKAGE = new String("learner");
	String JAR = new String("C:\\robocode\\libs\\robocode.jar;");

	private String robotName;
	private int generation, version;
	private String code;
	private Trait[] traits;

	public RobotGenerator(int generation, int version)
	{
		this.generation = generation;
		this.version = version;
		this.robotName = "learner" + generation + "_" + version;
	}
	
	public RobotGenerator()
	{
		
	}
	public void setRobotName(String name)
	{
		this.robotName = name;
	}
	
	public Trait[] getTraits()
	{
		return this.traits;
	}
	public static RobotGenerator[] generateTormentPopulation(int size)
	{
		RobotGenerator result[] = new RobotGenerator[size];
		for(int i = 0; i < size; i++)
		{
			result[i] = new RobotGenerator();
			result[i].generateCodeRandomly();
		}
		return result;
	}
	
	
	final static String ROBOT_SCAN_FUNCTIONS[] = 
		{
			"getEnergy()",
			"getHeading()",
			"getWidth()",
			"getX()",
			"getY()",
			"getDistanceRemaining()",
			"getGunHeadingRadians()",
			"getGunTurnRemainingRadians()",
			"getHeadingRadians()",
			"e.getBearingRadians()",
			"e.getEnergy()",
			"e.getDistance()",
			"e.getHeadingRadians()",
			"getRadarHeadingRadians()",
			"getRadarTurnRemainingRadians()",	
		};
	
	final static String ROBOT_WALL_HIT_FUNCTIONS[] = 
		{
			"getEnergy()",
			"getHeading()",
			"getWidth()",
			"getX()",
			"getY()",
			"getDistanceRemaining()",
			"getGunHeadingRadians()",
			"getGunTurnRemainingRadians()",
			"getHeadingRadians()",
			"getRadarHeadingRadians()",
			"e.getBearingRadians()",
			"getRadarTurnRemainingRadians()",	
		};
	
	final static String MATH_FUNCTIONS[] = 
		{
			"Math.random()",
			"Math.random()* 2 - 500",
			"Math.PI",
		};
	final static String MATH_EXPRESSION[] = 
		{
			" + ",
			" - ",
			" * ",
			" / ",
			" % ",
		};
	
	public void generateCodeRandomly()
	{
		this.traits = new Trait[6];
		
		for(int i = 0; i < 5; i++)
		{
			traits[i] = new Trait();
			traits[i].setSubTrait(0, ROBOT_SCAN_FUNCTIONS[(int)(Math.random()*ROBOT_SCAN_FUNCTIONS.length)]);
			traits[i].setSubTrait(1, MATH_EXPRESSION[(int)(Math.random()*MATH_EXPRESSION.length)]);
			traits[i].setSubTrait(2, ROBOT_SCAN_FUNCTIONS[(int)(Math.random()*ROBOT_SCAN_FUNCTIONS.length)]);
			traits[i].setSubTrait(3, MATH_EXPRESSION[(int)(Math.random()*MATH_EXPRESSION.length)]);
			traits[i].setSubTrait(4, String.valueOf(Math.random()*500));
			traits[i].setSubTrait(5, MATH_EXPRESSION[(int)(Math.random()*MATH_EXPRESSION.length)]);
			traits[i].setSubTrait(6, MATH_FUNCTIONS[(int)(Math.random()*MATH_FUNCTIONS.length)]);
		}
		
		traits[5] = new Trait();
		traits[5].setSubTrait(0, ROBOT_WALL_HIT_FUNCTIONS[(int)(Math.random()*ROBOT_WALL_HIT_FUNCTIONS.length)]);
		traits[5].setSubTrait(1, MATH_EXPRESSION[(int)(Math.random()*MATH_EXPRESSION.length)]);
		traits[5].setSubTrait(2, ROBOT_WALL_HIT_FUNCTIONS[(int)(Math.random()*ROBOT_WALL_HIT_FUNCTIONS.length)]);
		traits[5].setSubTrait(3, MATH_EXPRESSION[(int)(Math.random()*MATH_EXPRESSION.length)]);
		traits[5].setSubTrait(4, String.valueOf(Math.random()*500));
		traits[5].setSubTrait(5, MATH_EXPRESSION[(int)(Math.random()*MATH_EXPRESSION.length)]);
		traits[5].setSubTrait(6, MATH_FUNCTIONS[(int)(Math.random()*MATH_FUNCTIONS.length)]);
		
		setCode();
	}

	private void setCode()
	{
		this.code =
				"package "+PACKAGE+";" +
						"\nimport robocode.*;" +
						"\nimport java.awt.Color;\n" +
						"\n" +		
						"\npublic class " + robotName + " extends AdvancedRobot {" +
						"\n" +
						
						"\n	public void run() {" +
						"\n" +
						"\nsetAdjustGunForRobotTurn(true);" +
						"\n" +
						"\n		setColors(Color.red,Color.red,Color.red);" +	
						"\n		while(true) {" +
						"\n			turnGunRight(Double.POSITIVE_INFINITY);" +
						"\n		}" +
						"\n" +	
						"\n	}" +
						
						"\n	public void onScannedRobot(ScannedRobotEvent e) {" +
						"\n" +
						
						"\n		setAhead(" + this.traits[0].toString() + ");" +
						"\n" +
						
						"\n		setTurnRight("+ this.traits[1].toString() +");"  +
						"\n" +
						
						"\n		setTurnGunRight("+ this.traits[2].toString() +");"  +
						"\n" +
						
						"\n		setTurnRadarRight("+ this.traits[3].toString() +");"  +
						"\n" +
						
						"\n		setFire("+ this.traits[4].toString() +");"  +
						//"\n}" +
						"\n" +
						"\n	}" +
						"\n" +	
						
						"\npublic void onHitWall(HitWallEvent e) {" +
						"\n		back(20);" +
						"\n		setAhead("+ this.traits[5].toString() +");"  +
						"\n	}" +
						"\n}"
					;
	}
	
	//Generate code directly from given trait and set the robot's trait to the given traits
	public void generateCodeFromTrait(Trait[] traits)
	{
		this.traits = traits;
		setCode();
	}
	
	public void writeCode()
	{
		try
		{
			File file = new File(PATH);
			if(!file.exists())
			{
				file.mkdir();
			}
			
			file = new File(PATH + "\\" + robotName + ".java");
			if(!file.exists())
			{
				file.createNewFile();
			}
			
			FileWriter fstream = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(code);
			out.close();
		} 
		catch (Exception e)
		{
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	public void compileCode()
	{
		try 
		{
			runCommand("javac -cp " + JAR + " " + PATH + "\\" + robotName + ".java");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static void runCommand(String command) throws Exception
	{
		Process process = Runtime.getRuntime().exec(command);
		printSystemMessage(command + " stdout:", process.getInputStream());
		printSystemMessage(command + " stderr:", process.getErrorStream());
		process.waitFor();
		if(process.exitValue() != 0)
		{
			System.out.println(command + "exited with value " + process.exitValue());
			System.exit(0);
		}
	}
	
	private static void printSystemMessage(String name, InputStream ins) throws Exception 
	{
		String line = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(ins));
		while((line = in.readLine()) != null)
		{
			System.out.println(name + " " + line);
		}
	}
}
