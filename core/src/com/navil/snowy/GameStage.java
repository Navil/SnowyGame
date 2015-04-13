package com.navil.snowy;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.navil.snowy.screens.GameScreen;

public class GameStage extends Stage implements InputProcessor{

	private Box2DDebugRenderer renderer;
	private OrthographicCamera camera;

	private World world;
	private final float TIME_STEP = 1 / 400f;
	private float accumulator = 0f;

	private GameScreen gameScreen;
	private byte fingersDown = 0;

	public GameStage(World world, GameScreen gameScreen) {
		renderer = new Box2DDebugRenderer();
		this.world = world;
		this.gameScreen = gameScreen;
		setUpCamera();
	}

	public void setUpCamera() {
		camera = new AndroidCamera(SnowyGame.WIDTH, SnowyGame.HEIGHT);
		camera.update();
	}
	
	@Override
	public void draw() {
		super.draw();
		//renderer.render(world, camera.combined);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);

		accumulator += delta;

		while (accumulator >= delta) {
			world.step(TIME_STEP, 6, 2);
			accumulator -= TIME_STEP;
		}
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		gameScreen.setPrePhase(false);
		if (gameScreen.isGameOver())
			return super.touchDown(screenX, screenY, pointer, button);
		else {
			fingersDown++;
			if (screenX > SnowyGame.WIDTH / 2)
				gameScreen.moveRight = true;
			else
				gameScreen.moveLeft = true;
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (gameScreen.isGameOver())
			return super.touchUp(screenX, screenY, pointer, button);
		else {
			fingersDown--;
			if (fingersDown == 0) {
				gameScreen.moveLeft = false;
				gameScreen.moveRight = false;
			}
			if (screenX > SnowyGame.WIDTH / 2)
				gameScreen.moveRight = false;
			else
				gameScreen.moveLeft = false;
		}
		return true;
	}

}
