package towerdefense;

import phonegame.*;

public abstract class BaseTower extends GameItem implements IAlarmListener
{
	protected TowerDefense mygame;
	protected boolean isActive;
	protected MoveableGameItem target;
	protected BaseProjectile projectile;
	
	// Upgrades
	protected int distancelevel;
	protected int fireratelevel;
	protected int powerlevel;
	
	protected int firerate;
	
	public BaseTower(TowerDefense game)
	{
		super();
		
		mygame = game;
		isActive = true;
		
		firerate = 25;
        setImage("/images/wall.png", 20, 20);
        
        mygame.setTimer(firerate, 0, this);
	}
	
	protected void fire()
	{
		BaseProjectile projectile = new BaseProjectile(mygame, target);
		projectile.setPosition(this.getX(), this.getY());
		
		mygame.addGameItem(projectile);
	}
	
	protected void lockTarget(MoveableGameItem pointer)
	{
		target = pointer;
	}
	
	public void setActive(boolean active)
	{
		isActive = active;
	}
	
	public boolean getActive()
	{
		return isActive;
	}
	
	public void animate()
	{}
	
	public void alarm(int id)
	{
		if(id == 0) // Fire
		{
			if(isActive)
			{
				fire();
				mygame.setTimer(firerate, 0, this);
			}
		}
	}
}
