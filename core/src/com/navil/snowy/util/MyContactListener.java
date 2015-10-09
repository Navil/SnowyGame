package com.navil.snowy.util;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
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
		//Gdx.app.error("Touching are", a.getUserData()+" and " +b.getUserData());
		if(b.getUserData().toString().equals("fire")){
			if(a.getUserData().toString().equals("botLine"))
				gamescreen.setRemoveBody();
			else if(a.getUserData().toString().equals("snowy"))
				gamescreen.snowyHit(b);	
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
