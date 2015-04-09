package com.navil.snowy.screens;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.navil.snowy.AndroidCamera;
import com.navil.snowy.SnowyGame;
import com.navil.snowy.actors.Snowflake;
import com.navil.snowy.util.Assets;
import com.navil.snowy.util.GoogleActions;

public class MainMenu implements Screen {

	private Stage stage = new Stage();
	private TextButton scoreButton;
	private float timeSinceLastFlake = 0;
	private final double flakeInterval = 0.05;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.25f, 0.25f, 0.6f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act();
		stage.draw();
		
		timeSinceLastFlake += delta;
		if (timeSinceLastFlake >= flakeInterval) {

			timeSinceLastFlake = 0;
			Snowflake sf = new Snowflake();
			//snowFlakes.add(sf);
			stage.addActor(sf);
			//Gdx.app.error("NuMFlaked", ""+snowFlakes.size());
		}
		
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().setCamera(new AndroidCamera(SnowyGame.WIDTH, SnowyGame.HEIGHT));
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		final Label label = new Label("Snowy the man!",Assets.getInstance().getSkin(),"menutitle",Color.WHITE);
		label.setPosition(SnowyGame.WIDTH/2-label.getWidth()/2,SnowyGame.HEIGHT-label.getHeight()-20);
		label.setColor(Color.WHITE);
		final TextButton startButton = new TextButton("Start Game", Assets.getInstance().getSkin());

        startButton.setWidth(460);
        startButton.setHeight(256);
        startButton.setPosition(SnowyGame.WIDTH /4 - startButton.getWidth()/2, SnowyGame.HEIGHT/2 - startButton.getHeight()/2);
        startButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
                startButton.setText("You clicked the button");
                ((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen());
            }
        });
        
        scoreButton = new TextButton("Scoreboard", Assets.getInstance().getSkin());

        scoreButton.setWidth(460);
        scoreButton.setHeight(256);
        
        scoreButton.setPosition(SnowyGame.WIDTH *3/4 - scoreButton.getWidth()/2, SnowyGame.HEIGHT/2 - scoreButton.getHeight()/2);
        scoreButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
                //((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen());
            	//scoreButton.setDisabled(true);
            	scoreButton.setDisabled(true);
            	SnowyGame.googleAction = GoogleActions.OPENSCOREBOARD;
            	scoreButton.setChecked(false);
            	SnowyGame.actionResolver.showScores();
            	scoreButton.setDisabled(false);
            	//return true;
            }
        });
        
        final TextButton exitButton = new TextButton("Exit", Assets.getInstance().getSkin());

        exitButton.setWidth(150);
        exitButton.setHeight(100);
        exitButton.setPosition(SnowyGame.WIDTH - 20 - exitButton.getWidth(), 20);
        exitButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
                //((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen());
            	Gdx.app.exit();     	
            }
        });
		
        final Label copyright = new Label("\u00A9 Thomas Anderl 2014", Assets.getInstance().getSkin(),"normaltext",Color.BLACK);
        copyright.setX(SnowyGame.WIDTH/2-copyright.getWidth()/2);
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
