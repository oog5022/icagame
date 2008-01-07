/**
 * Scripting Door Rick Meegens & Michiel Dondorp
 * ICA Arnhem IC1C
 */
package towerdefense;

import phonegame.*;

public class FrostTower extends BaseTower
{
	public FrostTower(TowerDefense game)
	{
		super(game);
		
		cashvalue = 200;
		towerType = "Frost Tower";
		
		setImage("/images/FrostTower.png", 20, 20);
	}
	
	protected void fire()
	{
		BaseProjectile projectile = new FrostProjectile(mygame, target, this);
		projectile.setSpeed( 10 );
		projectile.setPosition(this.getX() + (this.getFrameWidth() / 2) - (projectile.getFrameWidth() / 2), this.getY() + (this.getFrameHeight() / 2) - (projectile.getFrameHeight() / 2));
		
		mygame.addGameItem(projectile);
	}
	
	public void incFireRateLevel()
	{
		int cashreq = 0;
		if( cashreq < mygame.getMoney() )
		{
			mygame.addMoney( - cashreq );
			fireratelevel++;
			firerate = (int) Math.floor( 35 - .15 * fireratelevel );
		}
	}
	
	public void incDistanceLevel()
	{
		int cashreq = 0;
		if( cashreq < mygame.getMoney() )
		{
			mygame.addMoney( - cashreq );
			distancelevel++;
			maxdist = (double) ( 50 + .5 * distancelevel );
		}
	}
	
	public void incPowerLevel()
	{
		int cashreq = 0;
		if( cashreq < mygame.getMoney() )
		{
			mygame.addMoney( - cashreq );
			powerlevel++;
			damage = (int) Math.floor( 25 + 10 * powerlevel );
		}
	}
}
