package com.navil.snowy.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.navil.snowy.SnowyGame;
import com.navil.snowy.util.Assets;

public class MainMenu implements Screen {

	private Stage stage = new Stage();


	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
		
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
		final Label label = new Label("Snowy the man!",Assets.getInstance().getSkin(),"menutitle",Color.RED);
		label.setPosition(Gdx.graphics.getWidth()/2-label.getWidth()/2,Gdx.graphics.getHeight()-label.getHeight()-20);
		
		final TextButton startButton = new TextButton("Start Game", Assets.getInstance().getSkin());

        startButton.setWidth(460);
        startButton.setHeight(256);
        startButton.setPosition(Gdx.graphics.getWidth() /4 - startButton.getWidth()/2, Gdx.graphics.getHeight()/2 - startButton.getHeight()/2);
        startButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
                startButton.setText("You clicked the button");
                ((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen());
            }
        });
        
        final TextButton scoreButton = new TextButton("Scoreboard", Assets.getInstance().getSkin());

        scoreButton.setWidth(460);
        scoreButton.setHeight(256);
        scoreButton.setPosition(Gdx.graphics.getWidth() *3/4 - scoreButton.getWidth()/2, Gdx.graphics.getHeight()/2 - scoreButton.getHeight()/2);
        scoreButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
                //((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen());
            	SnowyGame.actionResolver.showScores();
            }
        });
        
        final TextButton exitButton = new TextButton("Exit", Assets.getInstance().getSkin());

        exitButton.setWidth(150);
        exitButton.setHeight(100);
        exitButton.setPosition(Gdx.graphics.getWidth() - 20 - exitButton.getWidth(), 20);
        exitButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
                //((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen());
            	Gdx.app.exit();
            }
        });
		
        final Label copyright = new Label("\u00A9 Thomas Anderl 2014", Assets.getInstance().getSkin(),"normaltext",Color.BLACK);
        copyright.setX(Gdx.graphics.getWidth()/2-copyright.getWidth()/2);
        copyright.setY((scoreButton.getY()-copyright.getHeight())/2);
        
        stage.addActor(label);
		stage.addActor(startButton);
		stage.addActor(scoreButton);
		stage.addActor(exitButton);
		stage.addActor(copyright);
		Gdx.input.setInputProcessor(stage);
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

}
