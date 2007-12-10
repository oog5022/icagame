package towerdefense;

public class RocketProjectile extends BaseProjectile
{
	public RocketProjectile(TowerDefense game, Mob pointer, BaseTower p)
	{
		super(game, pointer, p);
		
		setImage("/images/RocketProjectile.png", 5, 5);
	}
}
