package com.navil.snowy.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.navil.snowy.screens.GameScreen;

public class MyContactListener implements ContactListener{

	private GameScreen gamescreen;

	public MyContactListener(GameScreen gameScreen) {
		this.gamescreen = gameScreen;
	}

	@Override
	public void beginContact(Contact arg0) {
		
		Body a = arg0.getFixtureA().getBody();
		Body b = arg0.getFixtureB().getBody();
		//Gdx.app.error("Touching2", a.getUserData()+" and " +b.getUserData());
		
		if(b.getUserData().toString().contains("fire"))
			gamescreen.setRemoveBody();
			
		if(a.getUserData().equals("snowy")&&b.getUserData().equals("fire")){
			gamescreen.setRemoveBody();
			gamescreen.snowyHit();			
		}
			
	}

	@Override
	public void endContact(Contact arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
		// TODO Auto-generated method stub
		
	}

}
