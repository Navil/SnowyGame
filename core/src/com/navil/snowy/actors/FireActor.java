package com.navil.snowy.actors;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.navil.snowy.SnowyGame;

public class FireActor extends MyActor{
	public FireActor(){
		super(new Texture(Gdx.files.internal("flame.png")));
		setPosition(new Random().nextInt(SnowyGame.WIDTH-getTexture().getWidth()),SnowyGame.HEIGHT);
    }
	
	@Override
	public void act(float delta){
		super.act(delta);	
		updatePosition();
	}

}
