package towerdefense;

import phonegame.*;

public class MapController implements IAlarmListener
{
	private boolean active;
	private TowerDefense mygame;

	private int spawningMobs;
	private int startx;
	private int starty;
	
	public MapController(TowerDefense game)
	{
		active = true;
		mygame = game;
		spawningMobs = 1;
		
		startx = 0;
		starty = 20;
		
		mygame.setTimer(30, 0, this);
	}
	
	public void alarm(int id)
	{
		if(id == 0) // spawn control
		{
			spawnMob();
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
