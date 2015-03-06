package com.navil.snowy.actors;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.navil.snowy.SnowyGame;

public class FireActor extends MyActor{
	public FireActor(){
		super(new Texture(Gdx.files.internal("flame.png")));
		setScale(1.5f);
		setPosition(new Random().nextInt(SnowyGame.WIDTH-getTexture().getWidth()*2)+getWidth(),SnowyGame.HEIGHT-100);
		
    }
	
	@Override
	public void act(float delta){
		super.act(delta);	
		updatePosition();
	}

}
