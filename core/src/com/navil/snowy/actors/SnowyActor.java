package com.navil.snowy.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.navil.snowy.SnowyGame;

public class SnowyActor extends MyActor{
	
	private boolean invincible = false;
	public SnowyActor(){
		super(new Texture(Gdx.files.internal("snowman.png")));
		setPosition(SnowyGame.WIDTH/2,getTexture().getHeight()/2);
	}
	
	public void moveBodyLeft(){

		getBody().setTransform(getBody().getPosition().x-SnowyGame.snowyMovespeed,getBody().getPosition().y,0);
		updatePosition();
	}
	
	public void moveBodyRight(){
		getBody().setTransform(getBody().getPosition().x+SnowyGame.snowyMovespeed,getBody().getPosition().y,0);
		updatePosition();
	}
	
	public Texture getTexture(){
		return super.getTexture();
	}

	public boolean isInvincible() {
		return invincible;
	}

	public void setInvincible(boolean invincible) {
		this.invincible = invincible;
	}
	
	@Override
    public void draw(Batch batch, float alpha){
		if(super.getTexture() != null){
			batch.draw(super.getTexture(),getX(),getY());
			if(invincible && batch.getColor().a != 0.5f)
				batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 0.5f);
			else if(!invincible && batch.getColor().a != 1)
				batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 1);
		}
		
		//super.draw(batch, alpha);
        //setPosition(body.getPosition().x-texture.getWidth()/2,0);
    }
}
