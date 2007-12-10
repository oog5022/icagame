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
		BaseProjectile projectile = new RocketProjectile(mygame, target, this);
		projectile.setSpeed(10);
		projectile.setPosition(this.getX() + (this.getFrameWidth() / 2) - (projectile.getFrameWidth() / 2), this.getY() + (this.getFrameHeight() / 2) - (projectile.getFrameHeight() / 2));
		
		mygame.addGameItem(projectile);
	}
}
