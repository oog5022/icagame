//Gemaakt Door Rick
package towerdefense;

import phonegame.*;

public class TowerDefense extends GameEngine implements IMenuListener, IAlarmListener, IStepListener
{
    private Player player;

	private GameDashboard db;
	private MapController mc;
	
	private int highscore;
	
	private static final String rocketTowerMenuItem = "Build: Rocket Tower";
	private static final String laserTowerMenuItem = "Build: Laser Tower";
	private static final String frostTowerMenuItem = "Build: Frost Tower";
	private static final String spacerMenuItem = "---";
	private static final String sellTowerMenuItem = "Sell Tower";
	private static final String restartGameMenuItem = "Restart Game";
	private static final String exitMenuItem = "Exit";
	private static final String[] menu = {rocketTowerMenuItem, laserTowerMenuItem, frostTowerMenuItem, spacerMenuItem, sellTowerMenuItem, restartGameMenuItem, exitMenuItem};
	
	private int level;
	private int time;
	private int lifes;
	private int cash;
	private boolean inMenu;
	private boolean reset;
	
	public TowerDefense()
	{
		super();
		
		highscore = 0;

		setBounds(0, 0, 240, 260);
		setBackgroundColor(0, 0, 0);
		
		makeMenu(menu, this);
		
		buildEnvironment();
		resetGame();
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
		else if( label.equals(laserTowerMenuItem) )
		{
			if( findTilesAt(player.getX(), player.getY(), 1, 1) == 1 ) // Building on Grass tile?
			{
				if( (findItemAt(player.getX(), player.getY(), 1, 1) instanceof BaseTower) == false ) // Not building on another tower?
				{
					BaseTower rt = new LaserTower(this);
					
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
		else if( label.equals(frostTowerMenuItem) )
		{
			if( findTilesAt(player.getX(), player.getY(), 1, 1) == 1 ) // Building on Grass tile?
			{
				if( (findItemAt(player.getX(), player.getY(), 1, 1) instanceof BaseTower) == false ) // Not building on another tower?
				{
					BaseTower rt = new FrostTower(this);
					
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
		else if( label.equals(sellTowerMenuItem) )
		{
			GameItem tower = findItemAt(player.getX(), player.getY(), 1, 1);
			
			if( tower instanceof BaseTower ) //check whether its a tower or not.
			{
				addMoney( ((int) Math.floor( ((BaseTower)tower).getCashValue() * .75) ) );
				deleteGameItem(tower);
			}
		}
		else if ( label.equals(restartGameMenuItem) )
		{
			reset = true;
		}
		else if ( label.equals(exitMenuItem) )
	    {
	        exitGame();
	    } 
	}
	
	public void resetGame()
	{
		stopGame();
		
		deleteAllGameItems();
		removeStepListener(this);
		resetGameTime();
		
	    player = new Player(this);
	    this.addPlayer(player);
		
		level = 0;
		lifes = 20;
		cash = 1000000;
		time = 10;
		reset = false;
		
		buildMenu();
		startGame();
		
		addStepListener(this);
		setTimer(10, 0, this);
	}
	
	public boolean getInMenu()
	{
		return inMenu;
	}
	
	public int getLevel()
	{
		return level;
	}
	
	public void addMoney(int amount)
	{
		cash += amount;
		db.setItemText("Cash", Integer.toString(cash) );
	}
	
	public int getMoney()
	{
		return cash;
	}
	
	public void nieuwMenu()
	{
		GameItem tower = findItemAt(player.getX(), player.getY(), 1, 1);
		if(tower instanceof BaseTower)
		{
			db = new GameDashboard();
			db.setForegroundColor(255,255,255);
			db.setBackgroundColor(0,0,0);
			db.setLineColor(255, 0, 0, false);
			db.setSize(240,30);
			db.setPosition(0, 260);
			db.addTextItem("Tower Type", ((BaseTower)tower).getTowertyp(), 1, 1);
			db.addTextItem("Power", "Power: " + Integer.toString(((BaseTower)tower).getPowerlevel()), 100, 1);
			db.addTextItem("Firerate", "Firerate: " + Integer.toString(((BaseTower)tower).getFireratelevel()), 100, 9);
			db.addTextItem("Distance", "Distance: " + Integer.toString(((BaseTower)tower).getDistancelevel()), 100, 18);
			db.addTextItem("Damage", "Damage: " + Integer.toString(((BaseTower)tower).getDamageDone()), 150, 1);
			addGameDashboard(db);
			inMenu = true;
		}
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

		db.addImageItem("HPLBL", "/images/health.png", 2, 3);
		db.addImageItem("CashLBL", "/images/dollar.png", 1, 11);
		db.addTextItem("HP", Integer.toString(lifes), 11, 1);
		db.addTextItem("Cash", Integer.toString(cash), 11, 9);
		db.addTextItem("Time", "Time: " + Integer.toString(time), 75, 1);
		db.addTextItem("LVL", "  Lvl: " + Integer.toString(level), 75, 11 );

		addGameDashboard(db);
	}
	
	public void updateMenu(int flag)
	{
		GameItem tower = findItemAt(player.getX(), player.getY(), 1, 1);
		switch(flag)
		{
			case 0: db.setItemText("Power", "Power: " + Integer.toString(((BaseTower)tower).getPowerlevel()) ); break;
			case 1: db.setItemText("Firerate", "Firerate: " + Integer.toString(((BaseTower)tower).getFireratelevel()) ); break;
			case 2: db.setItemText("Distance", "Distance: " + Integer.toString(((BaseTower)tower).getDistancelevel()) ); break;
			default:
				db.setItemText("Power", "Power: " + Integer.toString(((BaseTower)tower).getPowerlevel()) );
				db.setItemText("Firerate", "Firerate: " + Integer.toString(((BaseTower)tower).getFireratelevel()) );
				db.setItemText("Distance", "Distance: " + Integer.toString(((BaseTower)tower).getDistancelevel()) );
		}		
	}
	
	public void decLife()
	{
		lifes--;
		db.setItemText("HP", Integer.toString(lifes));
	}
	
	public void resetTime()
	{
		time = 20;
	}
	
	public boolean getResetAsk()
	{
		return reset;
	}
	
	public void incLevel()
	{
		level++;
		db.setItemText("LVL", "  Lvl: " + Integer.toString(level));
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
			
			db.setItemText("Time", "Time: " + Integer.toString(time) );
			
			setTimer(10, 0, this);
		}
	}
	
	public int getTime()
	{
		return time;
	}
	
	public void stepAction(int stepnr)
	{
		// Check Lifes (game ends at, lifes == 0)
		if(lifes == 0)
		{
			if( highscore < level)
				highscore = level;
			
			db = new GameDashboard();
			db.setBackgroundColor(255, 0, 0);
			db.setForegroundColor(0, 0, 0);
			db.setLineColor(255, 0, 0, false);
			db.setSize(240,30);
			db.setPosition(0, 260);
			db.setFont(true, false, false);
			db.addTextItem("LOSE", "You've lost the game: Yet, made it till level " + level, 20, 6);
			db.addTextItem("HIGHSCORE", "New Highscore!", 82, 16);
			addGameDashboard(db);

			// deleteAllGameItems();
			pauseApp();
		}
		else if(time == 0) // Respawn
		{
			resetTime();
			incLevel();
			mc = new MapController(this, level);
		}
	}
}
