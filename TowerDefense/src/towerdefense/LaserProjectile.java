package towerdefense;

import javax.microedition.lcdui.Graphics;

public class LaserProjectile extends BaseProjectile
{
	public LaserProjectile(TowerDefense game, Mob pointer, BaseTower p)
	{
		super(game, pointer, p);
		
		setDamage(20, 1.5, parent);
		setSpeed(1);
	}
	
	public void setDamage(int start, double mod, BaseTower p)
	{
		double tempDmg = start;
		
		for(int i = 0; i <= p.powerlevel; i++) 
		{ 
			tempDmg *= mod;
		}
		
		damage = (int) Math.floor( tempDmg );
	}
	
	public void stepAction(int stepnr)
	{
		if(stepnr % 5 == 0)
		{
			int dx = (parent.getX() + parent.getFrameWidth() / 2) - (target.getX() + target.getFrameWidth() / 2);
			int dy = (parent.getY() + parent.getFrameHeight() / 2) - (target.getY() + target.getFrameHeight() / 2);
			double distance = Math.sqrt(dx * dx + dy * dy);
			
	    	if( parent.getDistance() > distance )
	    	{
	    		mygame.deleteGameItem(this);
	    		return;
	    	}

	    	target.collisionOccured(this);
		}
		
		if(target.isActive() == false)
		{
			mygame.deleteGameItem(this);
			return;
		}

		// moveTowardsAPoint(target.getX(), target.getY());
	}
	
	public void paint(Graphics g, int offsetX, int offsetY)
	{
        if ( isVisible() )
        {
        	g.setClip(0,0,mygame.getMaxX(),mygame.getMaxY());
            g.setColor(0xFF0000);
            g.drawLine(parent.getX() + parent.getFrameWidth()/2, parent.getY() + parent.getFrameHeight()/2, target.getX() + target.getFrameWidth() / 2, target.getY() + target.getFrameHeight() / 2);
        }
	}
}
