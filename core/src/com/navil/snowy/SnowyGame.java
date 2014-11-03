package com.navil.snowy;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.navil.snowy.screens.Splash;
import com.navil.snowy.util.ActionResolver;

public class SnowyGame extends Game {
	
	public static final int WIDTH = 1280, HEIGHT = 720;
	public static float snowyMovespeed = 15;
	public static final int gravity = 20;
	public static final float fireInterval = 1;
	public static final int scorePerFlame = 10;
	public static float invincibleTimer = 3;
	public static final int numLifes = 1;
	
	public static ActionResolver actionResolver;

	public SnowyGame(ActionResolver ar) {
		SnowyGame.actionResolver = ar;
	}

	@Override
	public void create() {
		setScreen(new Splash());
	}
	@Override
	public void dispose(){
		actionResolver.showOrLoadInterstital();
		super.dispose();
	}
	
}
