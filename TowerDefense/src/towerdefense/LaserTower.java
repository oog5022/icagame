package towerdefense;

import phonegame.GameItem;

public class LaserTower extends BaseTower
{
	public LaserTower(TowerDefense game)
	{
		super(game);
		
		cashvalue = 100;   					//change-able $100 seems good value..
		towerType = "LaserTower";
		
		setImage("/images/LaserTower.png", 20, 20); 
	}
	
	public void fire()
	{
		BaseProjectile projectile = new LaserProjectile(mygame, target, this);
		projectile.setPosition(this.getX() + (this.getFrameWidth() / 2) - (projectile.getFrameWidth() / 2), this.getY() + (this.getFrameHeight() / 2) - (projectile.getFrameHeight() / 2));
		projectile.setDamage( damage );
		
		mygame.addGameItem(projectile);
	}
	
	public void incFireRateLevel()
	{
		int cashreq = 0;
		if( cashreq <= mygame.getMoney() )
		{
			mygame.addMoney( - cashreq );
			fireratelevel++;
			firerate = (int) Math.floor( 15 - .15 * fireratelevel );
			
			if(firerate < 15)
				firerate = 5;
		}
	}
	
	public void incDistanceLevel()
	{
		int cashreq = 0;
		if( cashreq <= mygame.getMoney() )
		{
			mygame.addMoney( - cashreq );
			distancelevel++;
			maxdist = (double) ( 500 + .5 * distancelevel );
		}
	}
	
	public void incPowerLevel()
	{
		int cashreq = 0;
		if( cashreq <= mygame.getMoney() )
		{
			mygame.addMoney( - cashreq );
			powerlevel++;
			damage = (int) Math.floor( 1 + 1 * powerlevel );
		}
	}
}
