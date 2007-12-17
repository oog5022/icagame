package towerdefense;

public class FrostProjectile extends BaseProjectile
{
	public FrostProjectile(TowerDefense game, Mob pointer, BaseTower p)
	{
		super(game, pointer, p);
		
		setImage("/images/FrostProjectile.png", 5, 5);
		slowSpeed(10, 1.2, parent);
	}
	
	public void slowSpeed(int start, double slowMultiplier, BaseTower p)
	{	
		double tempSpeed = start;
		for(int i =0; i <= p.powerlevel;i++)
		{
			tempSpeed /= slowMultiplier; //speed -20% every level
		}
		target.setSpeed(target.getSpeed() * tempSpeed);
		
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
}
