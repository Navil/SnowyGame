package com.navil.snowy;

import com.badlogic.gdx.Game;
import com.navil.snowy.screens.Splash;
import com.navil.snowy.util.IGoogleServices;

public class SnowyGame extends Game {
	
	public static final int WIDTH = 1280, HEIGHT = 720;
	public static float snowyMovespeed = 15;
	public static final int gravity = 20;
	public static final float fireInterval = 1;
	public static final int scorePerFlame = 10;
	public static float invincibleTimer = 3;
	public static final int numLifes = 1;
	public static final boolean advertisement = false;
	
	public static IGoogleServices actionResolver;


	public SnowyGame(IGoogleServices ar) {
		SnowyGame.actionResolver = ar;
	}

	@Override
	public void create() {
		setScreen(new Splash());
	}
	@Override
	public void dispose(){
		super.dispose();
	}
	
}
