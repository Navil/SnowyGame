package com.navil.snowy.actors;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.navil.snowy.SnowyGame;
import com.navil.snowy.screens.GameScreen;

public class FireActor extends MyActor{
	public FireActor(){
		super(new Texture(Gdx.files.internal("flame.png")));
		setPosition(new Random().nextInt(SnowyGame.WIDTH-getTexture().getWidth()),SnowyGame.HEIGHT+100);
    }
	
	@Override
	public void act(float delta){
		super.act(delta);
		updatePosition();
	}

}
