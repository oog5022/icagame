package towerdefense;

import phonegame.*;
import phonegame.utils.*;

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
		int r = Tools.random(6);
		int randMob = 0;
			switch(r)
			{
			case 0:	
				case 1:	
				case 2:	
				case 3:						randMob = 0;break; //normal mob 2/3 chance
			case 4:							randMob = 1;break; //motor mob 1/6 chance
			case 5:							randMob = 2;break; //tank mob 1/6 chance
			}	
		Mob mob = new Mob(mygame, randMob);
		mob.setPosition(startx, starty);
		mygame.addGameItem(mob);
		
		spawningMobs--;
	}
}
