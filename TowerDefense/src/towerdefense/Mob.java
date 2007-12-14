package towerdefense;

import phonegame.*;

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
		
		setHP(10, 1.5, mygame.getLevel());
		
		// Speedhack much?
		setSpeed(2);
		startMoving();
	}
	
	public void setHP(double basehp, double inc, int level)
	{
		double tempHp = 1;
		
		for(int i = 0; i < level; i++)
		{
			tempHp *= inc;
		}
		
		tempHp *= basehp;
		
		health = (int) Math.floor(tempHp);
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
		/*
		if(object instanceof BaseProjectile)
		{
		}
		else
		*/
		if(object instanceof RocketProjectile)
		{
			System.out.println( health + " : " + ((BaseProjectile)object).getDamage() );
			
			addDamage( ((BaseProjectile)object).getDamage() );
			
			if(health == 0)
			{
				((BaseTower)((BaseProjectile)object).getParent()).lockTarget(null);
				active = false;
			
				mygame.addMoney( (int) Math.floor( 15 + mygame.getLevel() * 3) );
				mygame.deleteGameItem(this);
			}
			
			mygame.deleteGameItem(object);
		}
	}
	
	private void addDamage(int damage)
	{
		health -= damage;
		
		if(health < 0)
			health = 0;
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
