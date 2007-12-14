package towerdefense;

import phonegame.*;
import java.lang.Math;

public class Mob extends MoveableGameItem // implements IAlarmListener
{
	private TowerDefense mygame;
	private boolean active;
	private int health;
	
	public Mob(TowerDefense game)
	{
		mygame = game;
		active = true;
		setImage("/images/Mob1.png");
		
		setHP(10, 1.1, mygame.getLevel());
		
		// Speedhack much?
		setSpeed(2);
		startMoving();
	}
	
	public void setHP(double basehp, double inc, int level)
	{
		health = 1;
		
		for(int i = 0; i < level; i++)
		{
			health *= inc;
		}
		
		health += basehp;
	}
	
	/*
	 * DEV-notes:
	 * Compas
	 *     90
	 *      |
	 * 180 -+- 0
	 *      |
	 *     270
	 */
	
	public int checkDir()
	{
		int dir = getDirection();
		int temp = 999;
		
		// Move back
		if(dir == 270)
			setPosition(getX(), ((int) (getY() - getSpeed())));
		else if(dir == 0)
			setPosition(((int) (getX() - getSpeed())), getY());
		else if(dir == 90)
			setPosition(getX(), ((int) (getY() + getSpeed())));
		else if(dir == 180)
			setPosition(((int) (getX() + getSpeed())), getY());
		
		
		temp = dir;

		if( temp != 180 && mygame.findTilesAt(getX() + getFrameWidth(), getY(), 1, 1) == 2 )
			dir = 0;
		else if ( temp != 90 && mygame.findTilesAt(getX(), getY() + getFrameHeight(), 1, 1) == 2 )
			dir = 270;
		else if ( temp != 0 && mygame.findTilesAt(getX() - getFrameWidth(), getY(), 1, 1) == 2 )
			dir = 180;
		else if ( temp != 270 && mygame.findTilesAt(getX(), getY() - getFrameHeight(), 1, 1) == 2 )
			dir = 90;
		
		return dir;
	}
	
	public void collisionOccured(int tilePattern, boolean horizontal, int position)
	{
		if(tilePattern != 2 && tilePattern != 8)
			setDirection(checkDir());	
		else if(tilePattern == 8)
		{
			mygame.decLife();
			mygame.deleteGameItem(this);
		}
	}
	
	public void collisionOccured(GameItem object)
	{
		if(object instanceof BaseProjectile)
		{
			((BaseTower)((BaseProjectile)object).getParent()).lockTarget(null);
			active = false;
			
			mygame.deleteGameItem(this);
			mygame.deleteGameItem(object);
		}
	}
	
	public void setActive(boolean a)
	{
		active = a;
	}
	
	public boolean getActive()
	{
		return active;
	}
	
	public void outsideWorld()
	{
		mygame.deleteGameItem(this);
	}
}
