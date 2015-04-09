package com.navil.snowy.actors;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.navil.snowy.SnowyGame;
import com.navil.snowy.util.Assets;

public class FireActor extends MyActor{
	public FireActor(){
		super(Assets.getInstance().fire);	
		setScale(0.8f+random.nextFloat()*0.4f);
		setPosition(random.nextInt(SnowyGame.WIDTH-getTexture().getWidth()*2)+getWidth(),SnowyGame.HEIGHT+100);
    }
	
	@Override
	public void act(float delta){
		super.act(delta);	
		updatePosition();
	}

}
