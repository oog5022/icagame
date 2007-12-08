package towerdefense;

import phonegame.*;

public class BaseTower extends GameItem implements IAlarmListener
{
	private TowerDefense mygame;
	private int firerate;
	private boolean isActive;
	private MoveableGameItem target;
	private BaseProjectile projectile;
	
	// Upgrades
	private int distancelevel;
	private int fireratelevel;
	private int powerlevel;
	
	public BaseTower(TowerDefense game)
	{
		super();
		
		mygame = game;
		firerate = 25;
		isActive = true;
		
        setImage("/images/wall.png", 20, 20);
        
        // DEBUG: fire
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
