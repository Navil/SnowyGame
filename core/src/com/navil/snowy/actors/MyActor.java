package com.navil.snowy.actors;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MyActor extends Actor {
	private Body body;
	private Texture texture;
	public static Random random = new Random();
	public MyActor(float x, float y, float width, float height) {
		setBounds(x, y, width, height);
	}

	public MyActor(Texture texture) {
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

	public void updatePosition() {
		if (texture != null)
			super.setPosition(getBody().getPosition().x
					- getTexture().getWidth()*getScaleX() / 2, getBody().getPosition().y
					- getTexture().getHeight()*getScaleY() / 2);
	}

	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		setBounds(x, y, getTexture().getWidth(), getTexture().getHeight());
	}

	@Override
	public void draw(Batch batch, float alpha) {
		if (texture != null) {
			if (getRotation() != 1)
				batch.draw(texture, this.getX(), getY(), this.getOriginX(),
						this.getOriginY(), this.getWidth(), this.getHeight(),
						this.getScaleX(), this.getScaleY(), this.getRotation(),
						0, 0, texture.getWidth(), texture.getHeight(), false,
						false);
			else
				// batch.draw(texture, getX(), getY(), getX(), getOriginY(),
				// getWidth(), getHeight(), getScaleX(), getScaleY(),
				// getRotation(), (int)getX(), (int)getY(), (int)getWidth(),
				// (int)getHeight(), false,false);
				batch.draw(texture, getX(), getY(), getWidth(), getHeight());
		}

		// super.draw(batch, alpha);
		// setPosition(body.getPosition().x-texture.getWidth()/2,0);
	}
}
