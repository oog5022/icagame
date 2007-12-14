package towerdefense;

import phonegame.*;

public class MapController implements IAlarmListener
{
	private boolean active;
	private TowerDefense mygame;

	private int spawningMobs;
	private int startx;
	private int starty;
	
	public MapController(TowerDefense game, int level)
	{
		
		active = true;
		mygame = game;
		spawningMobs = 5;
		
		startx = 0;
		starty = 20;
		
		alarm(0);
	}
	
	public void alarm(int id)
	{
		if(id == 0) // spawn control
		{
			spawnMob();
			mygame.setTimer(10, 0, this);
		}
	}
	
	public void spawnMob()
	{
		if(! active)
			return;
		
		if(spawningMobs == 0)
			return;
		
		Mob mob = new Mob(mygame);
		mob.setPosition(startx, starty);
		mygame.addGameItem(mob);
		
		spawningMobs--;
	}
}
