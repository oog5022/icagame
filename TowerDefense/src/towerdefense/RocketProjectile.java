package towerdefense;

public class RocketProjectile extends BaseProjectile
{
	public RocketProjectile(TowerDefense game, Mob pointer, BaseTower p)
	{
		super(game, pointer, p);
		
		setImage("/images/RocketProjectile.png", 5, 5);
		setDamage(10, 1.5, parent);
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
