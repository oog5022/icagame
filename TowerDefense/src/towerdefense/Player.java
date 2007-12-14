package towerdefense;

import phonegame.*;

public class Player extends GamePlayer
{
	private TowerDefense mygame;
	public String name;
	
	public Player(TowerDefense game)
	{
		super();
		
		mygame = game;
		name = "pl";
		
        setImage("/images/player.png", 20, 20);
        setPosition(40, 40);
	}
	
	public void pressedButtonA()
	{
		if( mygame.getInMenu())
		{
			GameItem tower = mygame.findItemAt(getX(), getY(), 1, 1);
			{
				((BaseTower)tower).incPowerLevel();
				mygame.updateMenu(0);
			}			
		}
		
	}
	
	public void pressedButtonB()
	{
		if( mygame.getInMenu())
		{
			GameItem tower = mygame.findItemAt(getX(), getY(), 1, 1);
			{
				((BaseTower)tower).incFireRateLevel();
				mygame.updateMenu(1);
			}			
		}
	}
	
	public void pressedButtonC()
	{
		if( mygame.getInMenu())
		{
			GameItem tower = mygame.findItemAt(getX(), getY(), 1, 1);
			{
				((BaseTower)tower).incDistanceLevel();
				mygame.updateMenu(2);
			}			
		}
	}
	
	public void pressedButtonD()
	{
		// 9
	}
	
	public void moveDown()
	{
		if( mygame.getInMenu() )
			mygame.buildMenu();
	
        if(getY() + getFrameHeight() < mygame.getMaxY() )
        {	
            movePlayer(getX(), getY() + getFrameHeight());
        }
	}
	
	public void moveUp()
	{
		if( mygame.getInMenu() )
			mygame.buildMenu();
	
        if(getY() - getFrameHeight() >= mygame.getMinY() )
        {	
            movePlayer(getX(), getY() - getFrameHeight());
        }
	}
	
	public void moveLeft()
	{
		if( mygame.getInMenu() )
			mygame.buildMenu();
	
        if(getX() - getFrameWidth() >= mygame.getMinX() )
        {	
            movePlayer(getX() - getFrameWidth(), getY());
        }
	}
	
	public void moveRight()
	{
		if( mygame.getInMenu() )
			mygame.buildMenu();
	
        if(getX() + getFrameWidth() < mygame.getMaxX() )
        {	
            movePlayer(getX() + getFrameWidth(), getY());
        }
	}
	
	public void fire()
	{
		mygame.setMenu();
	}
	
	public void animate()
	{
	}
}
