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
import com.navil.snowy.AndroidCamera;
import com.navil.snowy.SnowyGame;
import com.navil.snowy.util.Assets;
import com.navil.snowy.util.GoogleActions;
import com.navil.snowy.util.ScoreHelper;

public class AfterGameScreen implements Screen {

	private Stage stage = new Stage();
	private TextButton upload;
	private int currentScore;

	public AfterGameScreen(int score) {
		this.currentScore = score;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.25f, 0.25f, 0.6f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().setCamera(new AndroidCamera(SnowyGame.WIDTH, SnowyGame.HEIGHT));

	}

	@Override
	public void show() {
		// show the interface
		Gdx.input.setInputProcessor(stage);
		final Label youLost = new Label("Snowy melted!", Assets.getInstance()
				.getSkin(), "menutitle", Color.RED);
		// final TextField youLost = new TextField("Snowy melted!",skin);
		youLost.setPosition(SnowyGame.WIDTH / 2 - youLost.getWidth() / 2,
				SnowyGame.HEIGHT - youLost.getHeight());

		Label scoreLabel = new Label("Score: " + currentScore, Assets
				.getInstance().getSkin(), "normaltext", Color.BLACK);
		scoreLabel
				.setX(SnowyGame.WIDTH / 2 - scoreLabel.getWidth() / 2);
		scoreLabel.setY(youLost.getY() - scoreLabel.getHeight() - 50);

		Label highScore = new Label("Highscore: "
				+ ScoreHelper.loadLocalScore(), Assets.getInstance().getSkin(),
				"normaltext", Color.BLACK);
		highScore.setX(SnowyGame.WIDTH / 2 - highScore.getWidth() / 2);
		highScore.setY(scoreLabel.getY() - scoreLabel.getHeight());

		final TextButton playAgain = new TextButton("Play Again", Assets
				.getInstance().getSkin(), "default");
		playAgain.setPosition(
				SnowyGame.WIDTH / 2 - playAgain.getWidth() / 2,
				SnowyGame.HEIGHT / 2 - playAgain.getHeight());
		playAgain.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (SnowyGame.advertisement)
					SnowyGame.actionResolver.showOrLoadInterstital();
				((Game) Gdx.app.getApplicationListener())
						.setScreen(new GameScreen());
				return true;
			}
		});

		upload = new TextButton("Upload", Assets.getInstance().getSkin(),
				"default");
		upload.setWidth(playAgain.getWidth());
		upload.setPosition(SnowyGame.WIDTH / 2 - upload.getWidth() / 2,
				playAgain.getY() - playAgain.getHeight() - 10);
		upload.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// ((Game)Gdx.app.getApplicationListener()).setScreen(new
				// GameScreen());
				upload.setDisabled(true);
				SnowyGame.googleAction = GoogleActions.UPLOADSCORE;
				upload.setChecked(false);
				SnowyGame.actionResolver.submitScore(currentScore);
				upload.setDisabled(false);
			}
		});

		final TextButton exitButton = new TextButton("Exit", Assets
				.getInstance().getSkin());

		exitButton.setWidth(150);
		exitButton.setHeight(100);
		exitButton.setPosition(
				SnowyGame.WIDTH - 20 - exitButton.getWidth(), 20);
		exitButton.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// ((Game)Gdx.app.getApplicationListener()).setScreen(new
				// GameScreen());
				Gdx.app.exit();
				return true;
			}
		});

		stage.addActor(scoreLabel);
		stage.addActor(highScore);
		stage.addActor(youLost);
		stage.addActor(playAgain);
		stage.addActor(exitButton);
		stage.addActor(upload);
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
