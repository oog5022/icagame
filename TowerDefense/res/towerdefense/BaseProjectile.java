package towerdefense;

import phonegame.*;

public class BaseProjectile extends MoveableGameItem implements IStepListener
{
	private TowerDefense mygame;
	private MoveableGameItem target;
	
	public BaseProjectile(TowerDefense game, MoveableGameItem pointer)
	{
		super();
		
		mygame = game;
		target = pointer;
		
		setImage("/res/images/appel.png", 15, 15);
		setSpeed(5);
		startMoving();
		
		mygame.addStepListener(this);
	}
	
	public void stepAction(int stepnr)
	{
		if(stepnr % 2 == 0) // Mover
		{
			moveTowardsAPoint(target.getX(), target.getY());
		}
	}
	
	public void collisionOccured(GameItem collidedItem)
	{
		if(collidedItem instanceof Player)
		{
			mygame.deleteGameItem(this);
		}
	}
}
