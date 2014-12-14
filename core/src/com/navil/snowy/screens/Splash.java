package com.navil.snowy.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.navil.snowy.util.Assets;

public class Splash implements Screen{

	private Texture texture;
	private Image splashImage;
	private Stage stage = new Stage();
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0,1); //sets clear color to black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clear the batch
        stage.act(); //update all actors
        stage.draw(); //draw all actors on the Stage.getBatch()
        
        if(Assets.getInstance().update() && splashImage.getActions().size==0){ // check if all files are loaded
            
            if(Assets.getInstance().getSkin() == null){
            	Assets.getInstance().setMenuSkin(); // uses files to create menuSkin    	
            }
            ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());
        }
        
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		texture  = new Texture(Gdx.files.internal("logo720.jpg"));
		splashImage = new Image(texture);
		stage.addActor(splashImage);
		splashImage.addAction(Actions.sequence(Actions.alpha(0),Actions.fadeIn(0.0f),Actions.delay(0.5f)));
		//fadeInt(2.0f)
		if(Assets.getInstance().getSkin() == null){
			Assets.getInstance().queueLoading(); 
		}
		
	}


	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		texture.dispose();
		stage.dispose();
	}

}
