package towerdefense;

import phonegame.*;

public class BaseProjectile extends MoveableGameItem implements IStepListener
{
	private TowerDefense mygame;
	private MoveableGameItem target;
	private BaseTower parent;
	
	public BaseProjectile(TowerDefense game, MoveableGameItem pointer, BaseTower p)
	{
		super();
		
		mygame = game;
		target = pointer;
		parent = p;
		
		setImage("/images/appel.png", 15, 15);
		setSpeed(5);
		startMoving();
		
		mygame.addStepListener(this);
	}
	
	public BaseTower getParent()
	{
		return parent;
	}
	
	public void stepAction(int stepnr)
	{
		if(stepnr % 5 == 0) // Mover
			moveTowardsAPoint(target.getX(), target.getY());
	}
	
	public void collisionOccured(GameItem collidedItem)
	{ }
}
