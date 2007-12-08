package towerdefense;

import phonegame.*;

public class Mob extends MoveableGameItem implements IAlarmListener
{
	private TowerDefense mygame;
	
	public Mob(TowerDefense game)
	{
		mygame = game;
		setImage("/images/Mob1.png");
		
		mygame.setTimer(1, 0, this);
		
		setSpeed(2);
		startMoving();
	}
	
	public void alarm(int id)
	{
		if(id == 0) // walk control
		{			
			if( getX() >= 0 && getX() < 220 && getY() >= 20 && getY() < 40 )
			{
				this.setDirection(0);
				mygame.setTimer(1, 0, this);
			}
			else if( getX() == 220 )
			{
				this.setDirection(270);
				mygame.setTimer(1, 0, this);
			}
			if( findTilesAt(getX(), getY(), 1, 1) == 1 ) // check tile
			{
			this.setDirection(90); // Right
			}
			else ( findTilesAt(getX(), getY(), 1, 1) == 1 ) // check tile
			{
			this.setDirection(270); // Left
			}
		}
	}
	
	public void outsideWorld()
	{
		setPosition(0, 20);
	}
}
