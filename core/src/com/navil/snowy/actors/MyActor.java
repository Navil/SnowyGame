package com.navil.snowy.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MyActor extends Actor{
	private Body body;
	private Texture texture;
	
	public MyActor(float x, float y,float width, float height){
		setBounds(x,y,width,height);
	}
	
	public MyActor(Texture texture){
		this.texture = texture;
		
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body, String description) {
		
		this.body = body;
		this.body.setUserData(description);
		updatePosition();
	}

	public Texture getTexture() {
		return texture;
	}
	
	public void updatePosition(){
		if(texture != null)
			super.setPosition(getBody().getPosition().x-getTexture().getWidth()/2,getBody().getPosition().y-getTexture().getHeight()/2);
	}
	
	@Override
	public void setPosition(float x, float y){
		super.setPosition(x, y);
		setBounds(x,y,getTexture().getWidth(),getTexture().getHeight());
	}
	
	@Override
    public void draw(Batch batch, float alpha){
		if(texture != null){
			batch.draw(texture,getX(),getY());
		}
		
		//super.draw(batch, alpha);
        //setPosition(body.getPosition().x-texture.getWidth()/2,0);
    }
}
