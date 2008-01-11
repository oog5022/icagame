/**
 * Scripting Door Rick Meegens & Michiel Dondorp
 * ICA Arnhem IC1C
 */
package towerdefense;

public class RocketTower extends BaseTower
{
	public RocketTower(TowerDefense game)
	{
		super(game);
		
		cashvalue = 50;
		towerType = "Rocket Tower";
		
		setImage("/images/RocketTower.png", 20, 20);
	}
	
	public void fire()
	{
		BaseProjectile projectile = new RocketProjectile(mygame, target, this);
		projectile.setSpeed( 10 );
		projectile.setPosition(this.getX() + (this.getFrameWidth() / 2) - (projectile.getFrameWidth() / 2), this.getY() + (this.getFrameHeight() / 2) - (projectile.getFrameHeight() / 2));
		projectile.setDamage( damage );
		
		mygame.addGameItem(projectile);
	}
	
	public void incFireRateLevel()
	{
		int cashreq = fireratelevel*100;
		if( cashreq < mygame.getMoney() )
		{
			mygame.addMoney( - cashreq );
			fireratelevel++;
			firerate = (int) Math.floor( 35 - .15 * fireratelevel );
			
			if(firerate < 15)
				firerate = 15;
		}
	}
	
	public void incDistanceLevel()
	{
		int cashreq = distancelevel*100;
		if( cashreq < mygame.getMoney() )
		{
			mygame.addMoney( - cashreq );
			distancelevel++;
			maxdist = (double) ( 50 + .5 * distancelevel );
		}
	}
	
	public void incPowerLevel()
	{
		int cashreq = powerlevel*100;
		if( cashreq < mygame.getMoney() )
		{
			mygame.addMoney( - cashreq );
			powerlevel++;
			damage = (int) Math.floor( 25 + 10 * powerlevel );
		}
	}
}
