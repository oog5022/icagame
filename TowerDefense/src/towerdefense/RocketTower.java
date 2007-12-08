package towerdefense;

import phonegame.*;

public class RocketTower extends BaseTower
{
	public RocketTower(TowerDefense game)
	{
		super(game);
		
		setImage("/images/RocketTower.png", 20, 20);
        // Memory Leak:
        // mygame.setTimer(firerate, 0, this);
	}
}
