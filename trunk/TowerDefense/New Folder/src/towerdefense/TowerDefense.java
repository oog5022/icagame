package towerdefense;

import phonegame.*;

public class TowerDefense extends GameEngine implements IMenuListener, IAlarmListener
{
    private Player player;

	private GameDashboard db;
	
	private static final String rocketTowerMenuItem = "Build: Rocket Tower";
	private static final String spacerMenuItem = "---";
	private static final String exitMenuItem = "Exit";
	private static final String[] menu = {rocketTowerMenuItem, spacerMenuItem, exitMenuItem};
	
	private int level;
	private int time;
	
	public TowerDefense()
	{
		super();

		setBounds(0, 0, 240, 260);
		setBackgroundColor(0, 0, 0);
		
		db = new GameDashboard();
		db.setForegroundColor(255,255,255);
		db.setBackgroundColor(0,0,0);
		db.setSize(240,30);
		db.setPosition(0, 260);
		db.addItem("Score", "0");
		db.addItem("Level", "0");
		db.addItem("Cash", "$ 0");
		db.addItem("Time", "00:00");
		addGameDashboard(db);
		
		makeMenu(menu, this);
		
	    player = new Player(this);
	    this.addPlayer(player);
		
		buildEnvironment();
		
		level = 1;
		time = 0;
		
		setTimer(10, 0, this);
		startGame();
	}
	
	public void menuAction(String label)
	{
		// TODO
	}
	
	public void setPoints(int p)
	{
		// sd.setItemValue("Score", "" + p);
	}
	
	public void incLevel()
	{
		level++;
		db.setItemValue("Level", "" + level);
	}
	
	private void buildEnvironment()
	{   // get path to tile images
	    String[] imagePaths =
	    {
	    	"/res/images/grass.png", // 1
	        "/res/images/path.png", // 2
	        "/res/images/wall.png" // 3
	    };
	    // create map
	    byte[][] map = 
	    {
		        {1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 2},
		        {2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		        {2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		        {2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		        {2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 1, 1, 1},
		        {1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1}
	    };
	    
	    // add map
	    this.setTileImages(imagePaths, 20, 20);
	    this.addEnvironmentMap(map, 0, 0);	
	}
	
	public void alarm(int id)
	{
		if(id == 0)
		{
			time++;
			
			int seconds = time % 60;
			int minutes = time - seconds;
			
			String outseconds = "";
			if( seconds < 10 )
				outseconds += "0";
			outseconds += seconds;
			
			String outminutes = "";
			if( minutes < 10 )
				outminutes += "0";
			outminutes += minutes;
			
			db.setItemValue("Time", outminutes + ":" + outseconds );
			
			setTimer(10, 0, this);
		}
	}
}
