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
	private static final String sellTower = "Sell Tower";
	private static final String exitMenuItem = "Exit";
	private static final String[] menu = {rocketTowerMenuItem, spacerMenuItem, sellTower, exitMenuItem};
	
	private int level;
	private int time;
	private int lifes;
	private int cash;
	private boolean inMenu;
	
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
		time = 10;

		buildMenu();
		buildEnvironment();
		
		// mc = new MapController(this, level);
		
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
					
					if( cash < rt.getCashValue() )
					{
						return;
					}
					
					rt.setPosition(player.getX(), player.getY());
					addGameItem(rt);
					addMoney( - rt.getCashValue() );
					
				}
			}
		}
		else if( label.equals(sellTower) )
		{
			GameItem tower = findItemAt(player.getX(), player.getY(), 1, 1);
			
			if( tower instanceof BaseTower ) //check whether its a tower or not.
			{
				addMoney( ((int) Math.floor( ((BaseTower)tower).getCashValue() * .75) ) );
				deleteGameItem(tower);
			}
		}
		else if ( label.equals(exitMenuItem) )
	    {
	        exitGame();
	    } 
	}
	
	public boolean getInMenu()
	{
		return inMenu;
	}
	
		public void setMenu()
	{
		if((findItemAt(player.getX(), player.getY(), 1, 1)) instanceof BaseTower)
		{
			if(inMenu == false)
			{
				nieuwMenu();
				inMenu = true;
			}
			else
			{
				GameItem tower = findItemAt(player.getX(), player.getY(), 1, 1);
				db.setItemValue("TowerTyp", ((BaseTower)tower).getTowertyp() + " " );
				db.setItemValue("Power", Integer.toString(((BaseTower)tower).getPowerlevel()) + " ");
				db.setItemValue("Firerate", Integer.toString(((BaseTower)tower).getFireratelevel()) + " ");
				db.setItemValue("Distance", Integer.toString(((BaseTower)tower).getDistancelevel()) + " ");
			}
		}
	}
	
	public int getLevel()
	{
		return level;
	}
	
	public void setPoints(int p)
	{
		// sd.setItemValue("Score", "" + p);
	}
	
	public void addMoney(int amount)
	{
		cash += amount;
		db.setItemValue("Cash", "$ " + cash + " ");
	}
	
	public int getMoney()
	{
		return cash;
	}
	
	public void nieuwMenu()
	{
		GameItem tower = findItemAt(player.getX(), player.getY(), 1, 1);
		
		db = new GameDashboard();
		db.setForegroundColor(255,255,255);
		db.setBackgroundColor(0,0,0);
		db.setLineColor(255, 0, 0, false);
		db.setSize(240,30);
		db.setPosition(0, 260);
		db.addItem("TowerTyp", ((BaseTower)tower).getTowertyp() + " " );
		db.addItem("Power", Integer.toString(((BaseTower)tower).getPowerlevel()) + " ");
		db.addItem("Firerate", Integer.toString(((BaseTower)tower).getFireratelevel()) + " ");
		db.addItem("Distance", Integer.toString(((BaseTower)tower).getDistancelevel()) + " ");
		addGameDashboard(db);
	}
	
	public void buildMenu()
	{
		inMenu = false;
		
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
	}
	
	public void decLife()
	{
		lifes--;
		db.setItemValue("HP", Integer.toString(lifes) + " ");
	}
	
	public void resetTime()
	{
		time = 20;
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
