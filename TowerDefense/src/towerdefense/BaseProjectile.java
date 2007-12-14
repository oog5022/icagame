package towerdefense;

import phonegame.*;

public abstract class BaseProjectile extends MoveableGameItem implements IStepListener
{
	protected TowerDefense mygame;
	protected Mob target;
	protected BaseTower parent;
	protected int damage;
	
	public BaseProjectile(TowerDefense game, Mob pointer, BaseTower p)
	{
		super();
		
		mygame = game;
		target = pointer;
		parent = p;
		damage = 0;
		
		setSpeed(5);
		startMoving();
		
		mygame.addStepListener(this);
	}
	
	public BaseTower getParent()
	{
		return parent;
	}
	
	public int getDamage()
	{
		return damage;
	}
	
	protected void setDamage(int dmg)
	{
		damage = dmg;
	}
	
	public void stepAction(int stepnr)
	{
		if(target.isActive() == false)
			mygame.deleteGameItem(this);

		moveTowardsAPoint(target.getX(), target.getY());
	}
	
	public void collisionOccured(GameItem collidedItem)
	{ }
}
