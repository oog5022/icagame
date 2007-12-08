package towerdefense;

import phonegame.*;

public class RocketTower extends BaseTower
{
	public RocketTower(TowerDefense game)
	{
		super(game);
		
        // Memory Leak:
        // mygame.setTimer(firerate, 0, this);
	}
}
