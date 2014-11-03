package com.navil.snowy.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.navil.snowy.SnowyGame;
import com.navil.snowy.screens.GameScreen;

public class InputHandler implements InputProcessor {

	private GameScreen gs;
	private byte fingersDown = 0;
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		if(keycode == Keys.LEFT)
			gs.moveLeft = true;
		else if(keycode == Keys.RIGHT)
			gs.moveRight = true;
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.LEFT)
			gs.moveLeft = false;
		else if	(keycode == Keys.RIGHT)
			gs.moveRight = false;
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {	
		fingersDown++;
		if(screenX > SnowyGame.WIDTH/2)
			gs.moveRight = true;
		else
			gs.moveLeft = true;
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		fingersDown--;
		if(fingersDown == 0){
			gs.moveLeft = false;
			gs.moveRight = false;
		}
		if(screenX > SnowyGame.WIDTH/2)
			gs.moveRight = false;
		else
			gs.moveLeft = false;
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
