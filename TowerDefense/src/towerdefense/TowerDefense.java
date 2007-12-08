//Gemaakt Door Rick
package towerdefense;

import phonegame.*;

public class TowerDefense extends GameEngine implements IMenuListener, IAlarmListener, IStepListener
{
    private Player player;

	private GameDashboard db;
	private MapController mc;
	
	private static final String rocketTowerMenuItem = "Build: Rocket Tower";
	private static final String spacerMenuItem = "---";
	private static final String removeTower = "Verwijder Toren";
	private static final String exitMenuItem = "Exit";
	private static final String[] menu = {rocketTowerMenuItem, spacerMenuItem, removeTower, exitMenuItem};
	
	private int level;
	private int time;
	private int lifes;
	private int cash;
	
	public TowerDefense()
	{
		super();

		setBounds(0, 0, 240, 260);
		setBackgroundColor(0, 0, 0);
		
		makeMenu(menu, this);
		
	    player = new Player(this);
	    this.addPlayer(player);
		
		level = 1;
		lifes = 20;
		cash = 100;
		resetTime();
		
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
		
		mc = new MapController(this, level);
		
		addStepListener(this);
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
					BaseTower rt = new RocketTower(this);
					rt.setPosition(player.getX(), player.getY());
					rt.lockTarget(player);
					addGameItem(rt);
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
		else if ( label.equals(exitMenuItem) )
	    {
	        exitGame();
	    } 
	}
	
	public void setPoints(int p)
	{
		// sd.setItemValue("Score", "" + p);
	}
	
	public void decLife()
	{
		lifes--;
		db.setItemValue("HP", Integer.toString(lifes) + " ");
	}
	
	public void resetTime()
	{
		time = 30;
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
	    	"/images/grass.png", // 1 - HEX: 1
	        "/images/path.png", // 2 - HEX: 2
	        "/images/wall.png", // 3 - HEX: 4
	        "/images/end.png" // 4 - HEX: 8
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
			time--;
			
			db.setItemValue("Time", Integer.toString(time) );
			
			setTimer(10, 0, this);
		}
	}
	
	public void stepAction(int stepnr)
	{
		// Check Lifes (game ends at, lifes == 0)
		if(lifes == 0)
		{
			db.deleteItem("LVL");
			db.deleteItem("HP");
			db.deleteItem("Cash");
			db.deleteItem("Time");
			
			db.setBackgroundColor(255, 0, 0);
			db.setForegroundColor(0, 0, 0);
			db.setFont(true, false, false);
			db.addItem("You've Lost The Game", "Yet, made it till level " + level);
			stopGame();
		}
		
		else if(time == 0) // Respawn
		{
			resetTime();
			incLevel();
			mc = new MapController(this, level);
		}
	}
}
