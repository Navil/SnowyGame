package com.navil.snowy.actors;

import com.navil.snowy.SnowyGame;
import com.navil.snowy.util.Assets;

public class Snowflake extends MyActor {

	private float speedY;
	private float speedX;
	public Snowflake() {
		super(Assets.getInstance().snowflake);
		speedY = (float)Math.random()*4+1;
		speedX = (float)Math.random()/2;
		if(((int)(Math.random()*100+1) & 1) == 0)
			speedX = speedX * -1;
		this.setScale((float)(Math.random()*0.5+0.5f));
		//this.setSize((float)Math.random()*getTexture().getWidth(), (float)Math.random()*getTexture().getHeight());
		this.setRotation((float) (Math.random()*360));
		this.setPosition(random.nextInt(SnowyGame.WIDTH),SnowyGame.HEIGHT+100);
		this.setBounds(getX(),getY(),getWidth(),getHeight());
	}

	public void act(float delta) {
		this.moveBy(speedX, -speedY);
		if(this.getY() < -10){
			this.remove();
		}
	}
}
