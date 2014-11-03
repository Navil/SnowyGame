package com.navil.snowy;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.navil.snowy.screens.GameScreen;
import com.navil.snowy.util.ActionResolver;

public class GameStage extends Stage implements InputProcessor{

	private Box2DDebugRenderer renderer;
	private OrthographicCamera camera;

	private World world;
	private final float TIME_STEP = 1 / 300f;
	private float accumulator = 0f;
	private float timeSinceLastFire = 0;

	private GameScreen gameScreen;
	private byte fingersDown = 0;

	public GameStage(World world, GameScreen gameScreen) {
		renderer = new Box2DDebugRenderer();
		this.world = world;
		this.gameScreen = gameScreen;
		setUpCamera();
	}

	public void setUpCamera() {
		camera = new OrthographicCamera(SnowyGame.WIDTH, SnowyGame.HEIGHT);
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		camera.update();
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

	public void draw() {
		super.draw();
		//renderer.render(world, camera.combined);
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		if (keycode == Keys.LEFT)
			gameScreen.moveLeft = true;
		else if (keycode == Keys.RIGHT)
			gameScreen.moveRight = true;
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.LEFT)
			gameScreen.moveLeft = false;
		else if (keycode == Keys.RIGHT)
			gameScreen.moveRight = false;
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		super.keyTyped(character);
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (gameScreen.isGameOver())
			super.touchDown(screenX, screenY, pointer, button);
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
			super.touchDown(screenX, screenY, pointer, button);
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
