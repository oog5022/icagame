package towerdefense;

import phonegame.*;

public abstract class BaseProjectile extends MoveableGameItem implements IStepListener
{
	private TowerDefense mygame;
	private Mob target;
	private BaseTower parent;
	private int dmg;
	
	public BaseProjectile(TowerDefense game, Mob pointer, BaseTower p)
	{
		super();
		
		mygame = game;
		target = pointer;
		parent = p;
		
		setSpeed(5);
		startMoving();
		//setDmg();
		mygame.addStepListener(this);
	}
	
	/* public void setDmg()
	{
	if(parent instanceof RocketTower)
		{
		//lvl 1 dmg RocketTower = 10 // incredement by 50%
		//lvl 2 dmg RocketTower = 15
		//lvl 3 dmg RocketTower = 22.5 (int = 23)
		for(int i = 0; i <= parent.powerlevel;i++) 
			{ 
			dmg = (int)Math.floor((10 * 1.5));
			}
		System.out.println( dmg + " damage dealt by rocker! (Testing + expanding)");
		}

	}
	*/

	
	public BaseTower getParent()
	{
		return parent;
	}
	
	public void stepAction(int stepnr)
	{
		moveTowardsAPoint(target.getX(), target.getY());
	}
	
	public void collisionOccured(GameItem collidedItem)
	{ }
	
}
