//Gemaakt Door Rick
package towerdefense;

import phonegame.*;

public class TowerDefense extends GameEngine implements IMenuListener, IAlarmListener
{
    private Player player;

	private GameDashboard db;
	
	private static final String rocketTowerMenuItem = "Build: Rocket Tower";
	private static final String spacerMenuItem = "---";
	private static final String removeTower = "Verwijder Toren";
	private static final String exitMenuItem = "Exit";
	private static final String[] menu = {rocketTowerMenuItem, spacerMenuItem, removeTower, exitMenuItem};
	
	private int level;
	private int time;
	private int lifes;
	private int cash;
	
	private Object[] towerList;
	
	public TowerDefense()
	{
		super();

		setBounds(0, 0, 240, 260);
		setBackgroundColor(0, 0, 0);
		
		makeMenu(menu, this);
		
	    player = new Player(this);
	    this.addPlayer(player);
		
		level = 1;
		time = 0;
		lifes = 20;
		cash = 100;
		
		db = new GameDashboard();
		db.setForegroundColor(255,255,255);
		db.setBackgroundColor(0,0,0);
		db.setLineColor(255, 0, 0, false);
		db.setSize(240,30);
		db.setPosition(0, 260);
		db.addItem("LVL", Integer.toString(level) + " ");
		db.addItem("HP", Integer.toString(lifes) + " ");
		db.addItem("Cash", "$ " + cash + " ");
		db.addItem("Time", "00:00");
		addGameDashboard(db);
		
		buildEnvironment();
		
		setTimer(10, 0, this);
		startGame();
	}
	
	public void menuAction(String label)
	{
		if ( label.equals(rocketTowerMenuItem) )
		{
			if( findTilesAt(player.getX(), player.getY(), 1, 1) == 1 ) // Building on Grass tile?
			{
				if( (findItemAt(player.getX(), player.getY(), 1, 1) instanceof BaseTower) == false ) // Not building on another tower?
				{
					BaseTower bt = new BaseTower(this);
					bt.setPosition(player.getX(), player.getY());
					bt.lockTarget(player);
					addGameItem(bt);
				}
			}
		}
		else if( label.equals(removeTower) )
		{
			GameItem tower = findItemAt(player.getX(), player.getY(), 1, 1);
			
			if( tower instanceof BaseTower ) //check whether its a tower or not.
			{
				deleteGameItem(tower);
			}
		}
	}
	
	public void setPoints(int p)
	{
		// sd.setItemValue("Score", "" + p);
	}
	
	public void incLevel()
	{
		level++;
		db.setItemValue("LVL", Integer.toString(level) + " ");
	}
	
	private void buildEnvironment()
	{   // get path to tile images
	    String[] imagePaths =
	    {
	    	"/images/grass.png", // 1
	        "/images/path.png", // 2
	        "/images/wall.png", // 3
	        "/images/end.png" // 4
	    };
	    // create map
	    byte[][] map = 
	    {
		        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
		        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1},
		        {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 1},
		        {1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1},
		        {1, 2, 1, 2, 2, 2, 2, 2, 1, 2, 1, 2, 1},
		        {1, 2, 1, 2, 1, 1, 1, 2, 1, 2, 1, 2, 1},
		        {1, 2, 1, 2, 1, 4, 2, 2, 1, 2, 1, 2, 1},
		        {1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1},
		        {1, 2, 1, 2, 2, 2, 2, 2, 2, 2, 1, 2, 1},
		        {1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1},
		        {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
		        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
	    };
	    
	    // add map
	    this.setTileImages(imagePaths, 20, 20);
	    this.addEnvironmentMap(map, 0, 0);	
	}
	
	public void alarm(int id)
	{
		if(id == 0) // Game Timer
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
