package com.navil.snowy.actors;

import com.navil.snowy.SnowyGame;
import com.navil.snowy.util.Assets;

public class FireActor extends MyActor{
	public FireActor(){
		super(Assets.getInstance().fire);	
		setScale(0.8f+random.nextFloat()*0.4f);
		setPosition(random.nextInt(SnowyGame.WIDTH-(int)(getTexture().getWidth()*getScaleX())),SnowyGame.HEIGHT+100);
    }
	
	@Override
	public void act(float delta){
		super.act(delta);	
		updatePosition();
	}

}
