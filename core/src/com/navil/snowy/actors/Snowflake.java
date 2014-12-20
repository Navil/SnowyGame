package com.navil.snowy.actors;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.navil.snowy.SnowyGame;

public class Snowflake extends MyActor {

	private float speedY;
	private float speedX;
	
	public Snowflake() {
		super(new Texture(Gdx.files.internal("snowflake_small.png")));
		speedY = (float)Math.random()*4+1;
		speedX = (float)Math.random()/2;
		if(((int)(Math.random()*100+1) & 1) == 0)
			speedX = speedX * -1;
		this.setScale((float)(Math.random()*0.5+0.5f));
		//this.setSize((float)Math.random()*getTexture().getWidth(), (float)Math.random()*getTexture().getHeight());
		this.setRotation((float) (Math.random()*360));
		this.setPosition(new Random().nextInt(SnowyGame.WIDTH-getTexture().getWidth()),SnowyGame.HEIGHT+100);
		this.setBounds(getX(),getY(),getWidth(),getHeight());
	}

	public void act(float delta) {
		/*
		 * Do any stuff with velocity like Euler integration etc here
		 */

		this.moveBy(speedX, -speedY);
		if(this.getY() < -10){
			this.remove();
		}
	}
}
