package towerdefense;

public class LaserTower extends BaseTower
{
	public LaserTower(TowerDefense game)
	{
		super(game);
		
		cashvalue = 100;   					//change-able $100 seems good value..
		towerType = "LaserTower";
		
		setImage("/images/LaserTower.png", 20, 20); 
	}
	
	protected void fire()			//make line towars mob. like a vector
	{
		
	}
}
