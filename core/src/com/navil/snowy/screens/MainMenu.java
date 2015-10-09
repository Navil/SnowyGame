package com.navil.snowy.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.navil.snowy.AndroidCamera;
import com.navil.snowy.SnowyGame;
import com.navil.snowy.actors.Snowflake;
import com.navil.snowy.util.Assets;
import com.navil.snowy.util.GoogleActions;

public class MainMenu implements Screen {

	private Stage stage;
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
			sf.setTouchable(Touchable.disabled);
			// snowFlakes.add(sf);
			stage.addActor(sf);
			// Gdx.app.error("NuMFlaked", ""+snowFlakes.size());
		}

	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().setCamera(
				new AndroidCamera(SnowyGame.WIDTH, SnowyGame.HEIGHT));
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		stage = new Stage();
		stage.addActor(new Image(Assets.getInstance().mainmenuBackground));
		
		
		final Label label = new Label("Snowy the man!", Assets.getInstance()
				.getSkin(), "othertitle", Color.WHITE);
		label.setFontScale(0.8f);
		label.setPosition(SnowyGame.WIDTH / 2 - label.getWidth()*label.getFontScaleX() / 2,
				SnowyGame.HEIGHT - label.getHeight() - 20);
		label.setColor(Color.WHITE);
		final TextButton startButton = new TextButton("Start Game", Assets
				.getInstance().getSkin());

		
		startButton.setWidth(450);
		startButton.setHeight(230);
		startButton.setPosition(SnowyGame.WIDTH / 4 - startButton.getWidth()
				/ 2, SnowyGame.HEIGHT / 2 - startButton.getHeight() / 2 +20);
		startButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				SnowyGame.actionResolver.hideToast();
				((Game) Gdx.app.getApplicationListener())
						.setScreen(new GameScreen());
			}
		});

		scoreButton = new TextButton("Scoreboard", Assets.getInstance()
				.getSkin());

		scoreButton.setWidth(450);
		scoreButton.setHeight(230);

		scoreButton.setPosition(
				SnowyGame.WIDTH * 3 / 4 - scoreButton.getWidth() / 2,
				SnowyGame.HEIGHT / 2 - scoreButton.getHeight() / 2 +20);
		scoreButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// ((Game)Gdx.app.getApplicationListener()).setScreen(new
				// GameScreen());
				// scoreButton.setDisabled(true);
				scoreButton.setDisabled(true);
				SnowyGame.googleAction = GoogleActions.OPENSCOREBOARD;
				scoreButton.setChecked(false);
				SnowyGame.actionResolver.showScores();
				scoreButton.setDisabled(false);
				// return true;
			}
		});

		final TextButton exitButton = new TextButton("Exit", Assets
				.getInstance().getSkin());

		exitButton.setWidth(150);
		exitButton.setHeight(100);
		exitButton
				.setPosition(SnowyGame.WIDTH - 20 - exitButton.getWidth(), 20);
		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// ((Game)Gdx.app.getApplicationListener()).setScreen(new
				// GameScreen());
				Gdx.app.exit();
			}
		});

		final TextButton achievementsButton = new TextButton("Achievements",
				Assets.getInstance().getSkin());

		achievementsButton.setWidth(400);
		achievementsButton.setHeight(100);
		achievementsButton.setPosition(SnowyGame.WIDTH * 3 / 4
				- achievementsButton.getWidth() / 2, scoreButton.getY() - 120);
		achievementsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				achievementsButton.setDisabled(true);
				SnowyGame.googleAction = GoogleActions.OPENACHIEVEMENTS;
				achievementsButton.setChecked(false);
				SnowyGame.actionResolver.showAchievements();
				;
				achievementsButton.setDisabled(false);
			}
		});

		final TextButton creditsButton = new TextButton("Credits", Assets
				.getInstance().getSkin());

		creditsButton.setWidth(400);
		creditsButton.setHeight(100);
		creditsButton.setPosition(
				SnowyGame.WIDTH / 4 - creditsButton.getWidth() / 2,
				scoreButton.getY() - 120);

		creditsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				SnowyGame.actionResolver
						.showToast(
								"This game was created and developed by Thomas Anderl with LibGDX.\n\nAssets were deliviered by Markus Bauer.\n\nHope you enjoy :)");
				creditsButton.toggle();
				
			}
		});

//		final Label soundLabel = new Label("Sound: ", Assets.getInstance().getSkin());
//		soundLabel.setPosition(startButton.getX(), 20);
//		soundLabel.setHeight(60);
//		
//		final CheckBox soundBox = new CheckBox("", Assets.getInstance().getSkin());
//		soundBox.setPosition(soundLabel.getX()+soundLabel.getWidth()+5, 20);
//		soundBox.setSize(60, 60);
//		soundBox.addListener(new ClickListener() {
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				//Gdx.app.error("Sound", "clicked");
//			}
//		});
		
		
		stage.addActor(label);
		stage.addActor(startButton);
		stage.addActor(scoreButton);
		stage.addActor(exitButton);
		stage.addActor(achievementsButton);
		stage.addActor(creditsButton);
		//stage.addActor(soundBox);
		//stage.addActor(soundLabel);
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
