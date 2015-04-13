package com.navil.snowy;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.navil.snowy.screens.Splash;
import com.navil.snowy.util.Assets;
import com.navil.snowy.util.GoogleActions;
import com.navil.snowy.util.IGoogleServices;

public class SnowyGame extends Game {
	
	public static final int WIDTH = 1280, HEIGHT = 720;
	public static float snowyMovespeed = 15;
	public static final int gravity = 1000;
	public static final float minFireInterval = 1.0f;
	public static float fireInterval = minFireInterval;
	public static final float maxFireInterval = 0.09f;
	public static final int scorePerFlame = 10;
	public static float invincibleTimer = 3;
	public static final int numLifes = 1;
	public static final boolean advertisement = false;
	
	public static GoogleActions googleAction = GoogleActions.DONOTHING;
	
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
		actionResolver.signOut(); //!!!
		Assets.getInstance().dispose();
		super.dispose();
	}
	
}
