package towerdefense;

import phonegame.*;

public class Player extends GamePlayer
{
	private TowerDefense mygame;
	
	public Player(TowerDefense game)
	{
		super();
		
		mygame = game;
		
        setImage("/images/player.png", 20, 20);
        setPosition(40, 40);
	}
	
	public void pressedButtonA()
	{
		// 1
	}
	
	public void pressedButtonB()
	{
		// 3
	}
	
	public void pressedButtonC()
	{
		// 7
	}
	
	public void pressedButtonD()
	{
		// 9
	}
	
	public void moveDown()
	{
        if(getY() + getFrameHeight() < mygame.getMaxY() )
        {	
            movePlayer(getX(), getY() + getFrameHeight());
        }
	}
	
	public void moveUp()
	{
        if(getY() - getFrameHeight() >= mygame.getMinY() )
        {	
            movePlayer(getX(), getY() - getFrameHeight());
        }
	}
	
	public void moveLeft()
	{
        if(getX() - getFrameWidth() >= mygame.getMinX() )
        {	
            movePlayer(getX() - getFrameWidth(), getY());
        }
	}
	
	public void moveRight()
	{
        if(getX() + getFrameWidth() < mygame.getMaxX() )
        {	
            movePlayer(getX() + getFrameWidth(), getY());
        }
	}
	
	public void fire()
	{
	}
	
	public void animate()
	{
	}
}
