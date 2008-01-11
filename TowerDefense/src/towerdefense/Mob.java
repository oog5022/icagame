/**
 * Scripting Door Rick Meegens & Michiel Dondorp
 * ICA Arnhem IC1C
 */
package towerdefense;

import phonegame.*;

public class Mob extends MoveableGameItem
{
	private TowerDefense mygame;
	private boolean active;
	private int health;
	private int type;
	double mobReward;
	private boolean frosted;
	public Mob(TowerDefense game, int mobtype)
	{
		type = mobtype;
		mygame = game;
		active = true;
		frosted = false;
		switch(mobtype)
		{
		case 0:										// normal mob
			setImage("/images/Mob1.png", 20, 20);	// image Mob1
			setHP(10, 1.5, mygame.getLevel());		// startHP = 10
			setSpeed(2);							// speed = 2
			startMoving();
		break;
		case 1:										// speed mob (motor racer)
			setImage("/images/Mob2.png", 20, 20);	// image Mob2
			setHP(7, 1.5, mygame.getLevel());		// startHP = 7
			setSpeed(4);							// speed = 4 (2*normal)
			startMoving();
		break;		
		case 2:										// heavy mob (tank)
			setImage("/images/Mob3.png", 20, 20);	// image Mob3
			setHP(20, 1.5, mygame.getLevel());		// startHP = 20 (2*normal)
			setSpeed(1);							// speed = 1
			startMoving();
		break;
		
		}		
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
	
	public void animate()
	{}
	
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

		if( temp != 180 && mygame.findTilesAt(getX() + getFrameWidth(), getY(), 1, 1) == 2 ){
			if(frosted == true){
				dir = 0;
				this.setFrame(4);
			}else{
				dir = 0;
				this.setFrame(0);
			}
		}
			
		else if ( temp != 90 && mygame.findTilesAt(getX(), getY() + getFrameHeight(), 1, 1) == 2 )
		{
			if(frosted == true){
				dir = 270;
				this.setFrame(5);
			}else{
				dir = 270;
				this.setFrame(1);
			}
		}
		else if ( temp != 0 && mygame.findTilesAt(getX() - getFrameWidth(), getY(), 1, 1) == 2 )
		{
			if(frosted == true){
				dir = 180;
				this.setFrame(6);
			}else{
				dir = 180;
				this.setFrame(2);
			}
		}
		else if ( temp != 270 && mygame.findTilesAt(getX(), getY() - getFrameHeight(), 1, 1) == 2 )
		{
			if(frosted == true){
				dir = 90;
				this.setFrame(7);
			}else{
				dir = 90;
				this.setFrame(3);
			}
		}
		
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
		if(object instanceof RocketProjectile)
		{
			addDamage( ((BaseProjectile)object).getDamage(), (BaseTower)((BaseProjectile)object).getParent() );
			
			if(health == 0)
			{
				((BaseTower)((BaseProjectile)object).getParent()).lockTarget(null);
				active = false;
				addMoney();
				Explosion exp = new Explosion(mygame);
				exp.setPosition(getX(), getY());
				mygame.deleteGameItem(this);
				mygame.addGameItem(exp);
				mygame.deleteGameItem(exp);
				
			}
			
			mygame.deleteGameItem(object);
		}
		else if(object instanceof LaserProjectile)
		{
			addDamage( ((BaseProjectile)object).getDamage(), (BaseTower)((BaseProjectile)object).getParent() );

			if(health == 0)
			{
				((BaseTower)((BaseProjectile)object).getParent()).lockTarget(null);
				active = false;
				addMoney();
				Explosion exp = new Explosion(mygame);
				exp.setPosition(getX(), getY());
				mygame.deleteGameItem(object);
				mygame.deleteGameItem(this);
				mygame.addGameItem(exp);
				mygame.deleteGameItem(exp);
			}
		}
		else if(object instanceof FrostProjectile)
		{	
			double dmglvl = ((BaseTower)((BaseProjectile)object).getParent()).powerlevel;
			mygame.deleteGameItem(object);
			double mobSpeed = getSpeed();
			float slowMultiplier = 1.1f;  		//speed -10% every level
			for(int i =0; i <= dmglvl;i++)
			{
				mobSpeed /= slowMultiplier;
			}
			if(mobSpeed <= 0.25)
				{ mobSpeed = 0.25;}
			
			mobSpeed = Math.ceil(mobSpeed);
			
			setSpeed(mobSpeed);
			if(frosted == false){frosted = true;checkDir();	}
		}
	}
	
	
	private void addMoney()
	{	
		switch (type)
		{
		case 0:				//Normal mob 2*
			mobReward = 2;
		break;
		case 1:				//Race mob 2.5*
			mobReward = 2.5;
		break;
		case 2:				//Tank mob 3*
			mobReward = 3;
		break;				
		}
		mygame.addMoney( (int) Math.floor( 15 + mygame.getLevel() * mobReward));
	}
	
	private void addDamage(int damage, BaseTower bt)
	{
		bt.addDamageDone(damage);
		
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
