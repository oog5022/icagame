package towerdefense;

import phonegame.*;

public class RocketTower extends BaseTower
{
	public RocketTower(TowerDefense game)
	{
		super(game);
		setImage("/images/RocketTower.png", 20, 20);
	}
	
	protected void fire()
	{		
		BaseProjectile projectile = new BaseProjectile(mygame, target);
		projectile.setSpeed(1);
		projectile.setPosition(this.getX(), this.getY());
		
		mygame.addGameItem(projectile);
	}
}
