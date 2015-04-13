package com.navil.snowy.screens;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.navil.snowy.AndroidCamera;
import com.navil.snowy.GameStage;
import com.navil.snowy.SnowyGame;
import com.navil.snowy.actors.BottomLine;
import com.navil.snowy.actors.FireActor;
import com.navil.snowy.actors.SnowyActor;
import com.navil.snowy.util.Assets;
import com.navil.snowy.util.GoogleActions;
import com.navil.snowy.util.MyContactListener;
import com.navil.snowy.util.ScoreHelper;

public class GameScreen implements Screen {

	private SnowyActor snowyActor;
	public Timer invincibleTimer = new Timer();

	private float timeSinceLastFire = 0;
	private final float transparency = 0.7f;
	private Label scoreLabel;
	private TextButton upload;

	public static World world;

	private boolean removeFire = false;
	private boolean gameOver = false;

	private boolean prePhase;
	private Label intro;

	private int numLifes = SnowyGame.numLifes;

	private CopyOnWriteArrayList<FireActor> flames;
	// private Map<Body,Actor> flames = new HashMap<Body,Actor>();

	private GameStage stage;
	public boolean moveLeft, moveRight;
	private int score = 0;
	private Task intervalDecrease;

	@Override
	public void show() {
		flames = new CopyOnWriteArrayList<FireActor>();
		// Gdx.app.error("show", "called");
		world = new World(new Vector2(0, -20), true);
		stage = new GameStage(world, this);
		Gdx.input.setInputProcessor(stage);

		SnowyGame.fireInterval = SnowyGame.minFireInterval;
		stage.addActor(new Image(Assets.getInstance().vulcano));
		snowyActor = new SnowyActor();
		snowyActor.setBody(createBody(snowyActor, BodyType.StaticBody, 1),
				"snowy");
		world.setContactListener(new MyContactListener(this));

		stage.addActor(snowyActor);

		BottomLine botLine = new BottomLine();
		botLine.setBody(createBody(botLine, BodyType.StaticBody, 0), "botLine");

		scoreLabel = new Label("Score: 1234567",
				Assets.getInstance().getSkin(), "normaltext", Color.WHITE);
		scoreLabel.setText("Score: " + score);
		scoreLabel.setPosition(SnowyGame.WIDTH - scoreLabel.getWidth(),
				SnowyGame.HEIGHT - scoreLabel.getHeight());
		scoreLabel.setZIndex(20);
		stage.addActor(scoreLabel);
		stage.addActor(botLine);

		intro = new Label(
				"Tab the screen on one of the two halfs to move\ntowards that direction and dodge the flames.",
				Assets.getInstance().getSkin(), "normaltext", Color.GRAY);
		intro.setAlignment(Align.center);
		intro.setX(SnowyGame.WIDTH / 2 - intro.getWidth() / 2);
		intro.setY(SnowyGame.HEIGHT / 2 - intro.getHeight() / 2);

		stage.addActor(intro);

		intervalDecrease = new Task() {
			@Override
			public void run() {
				decreaseInterval();
			}
		};
		prePhase = true;
	}

	protected void decreaseInterval() {
		if (SnowyGame.fireInterval > SnowyGame.maxFireInterval) {
			if (score % 10 == 0) {
				SnowyGame.fireInterval -= 0.05f;
				// Gdx.app.error("FireInt", SnowyGame.fireInterval+"");
			}
			if (SnowyGame.fireInterval < SnowyGame.maxFireInterval) {
				SnowyGame.fireInterval = SnowyGame.maxFireInterval;
				intervalDecrease.cancel();
			}
		}
	}

	@Override
	public void render(float delta) {
		// Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (prePhase) {
			stage.draw();
			stage.act(delta);
			return;
		}
		timeSinceLastFire += delta;
		if (removeFire) {
			FireActor temp = flames.remove(0);
			// temp.clear();
			temp.setVisible(false);
			temp.getBody().setActive(false);
			world.destroyBody(temp.getBody());
			removeFire = false;
			// Gdx.app.error("NumFlames", "" + flames.size);
			score += SnowyGame.scorePerFlame;
			scoreLabel.setText("Score: " + score);

		}

		if (!gameOver && timeSinceLastFire >= SnowyGame.fireInterval) {
			timeSinceLastFire -= SnowyGame.fireInterval;
			createFlame();
		}
		moveSnowy();

		stage.draw();
		if (!gameOver)
			stage.act(delta);

	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().setCamera(
				new AndroidCamera(SnowyGame.WIDTH, SnowyGame.HEIGHT));
	}

	private Body createBody(Actor actor, BodyType staticbody, float density) {

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = staticbody;
		if (actor instanceof SnowyActor)
			bodyDef.position.set(actor.getX(), actor.getY());
		else if (actor instanceof FireActor)
			bodyDef.position.set(actor.getX(),
					actor.getY() - (10 * actor.getScaleY()));
		else
			bodyDef.position.set(
					actor.getX() + (actor.getWidth() * actor.getScaleX()) / 2
							+ 5, actor.getY() - 5);

		Shape shape = new PolygonShape();
		if (actor instanceof SnowyActor) {
			// shape = new CircleShape();
			// shape.setRadius(actor.getWidth()*actor.getScaleX()/2 -12);
			// shape = new PolygonShape();
			Vector2[] vertices = new Vector2[3];
			vertices[0] = new Vector2(-actor.getWidth() / 2 +18,
					-actor.getHeight() / 2 +5); // bottom left
			vertices[1] = new Vector2(7, actor.getHeight() / 2 -3); // top middle
			vertices[2] = new Vector2(actor.getWidth() / 2 -15, // bottom right
					-actor.getHeight() / 2 +5);
			((PolygonShape) shape).set(vertices);
		} else if (actor instanceof FireActor) {
			shape = new CircleShape();
			shape.setRadius(actor.getWidth() * actor.getScaleX() / 2);
		} else
			((PolygonShape) shape).setAsBox(
					(actor.getWidth() * actor.getScaleX() / 2) - 5,
					(actor.getHeight() * actor.getScaleY() / 2) - 10);
		Body body = world.createBody(bodyDef);
		body.createFixture(shape, density);
		shape.dispose();
		return body;
	}

	private void createFlame() {

		FireActor fireActor = new FireActor();
		Body body = createBody(fireActor, BodyType.DynamicBody, 1);
		body.setLinearVelocity(0, -SnowyGame.gravity);
		fireActor.setBody(body, "fire");

		stage.addActor(fireActor);
		flames.add(fireActor);
		fireActor.setZIndex(10);

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
		// remove all bodies and sprites
		for (int i = 0; i < flames.size(); i++) {
			FireActor fa = flames.remove(i);
			fa.setVisible(false);
			fa.getBody().setActive(false);
			world.destroyBody(fa.getBody());
		}
		snowyActor.setVisible(false);
		snowyActor.getBody().setActive(false);
		world.destroyBody(snowyActor.getBody());
		scoreLabel.setVisible(false);
		flames.clear();
		flames = null;
		intervalDecrease.cancel();
		intervalDecrease = null;

	}

	private void moveSnowy() {
		if (gameOver)
			return;
		if (moveLeft && !moveRight
				&& snowyActor.getX() - SnowyGame.snowyMovespeed >= 0)
			snowyActor.moveBodyLeft();
		// snowyActor.moveBy(-SnowyGame.snowyMovespeed, 0);
		if (moveRight
				&& !moveLeft
				&& snowyActor.getX() <= SnowyGame.WIDTH - snowyActor.getWidth()
						- SnowyGame.snowyMovespeed)
			snowyActor.moveBodyRight();
	}

	/**
	 * Wenn eine Flamme etwas beruehrt hat
	 */
	public synchronized void setRemoveBody() {
		removeFire = true;
	}

	/**
	 * Wenn snowy getroffen wurde
	 */
	public void snowyHit(Body flameThatHit) {
		// Gdx.app.error("Invincible: ",""+snowyActor.isInvincible());
		if (snowyActor.isInvincible()) {
			return;
		}
		numLifes--;
		if (numLifes <= 0) {
			gameOver = true;
			showLose();
		} else {
			snowyActor.setInvincible(true);
			invincibleTimer.scheduleTask(new Task() {
				@Override
				public void run() {
					snowyActor.setInvincible(false);
				}
			}, SnowyGame.invincibleTimer);
		}
		// Gdx.app.error("Lifes", ""+numLifes);
	}

	private void showLose() {
		removeFire = false;

		if (ScoreHelper.loadLocalScore() < score)
			ScoreHelper.saveLocalScore(score);

		scoreLabel.setVisible(false);
		// ((Game) Gdx.app.getApplicationListener())
		// .setScreen(new AfterGameScreen(score));^
		// stage.addActor(new Image(Assets.getInstance().vulcano));
		final Label youLost = new Label("Snowy melted!", Assets.getInstance()
				.getSkin(), "othertitle", Color.WHITE);

		youLost.setPosition(SnowyGame.WIDTH / 2 - youLost.getWidth() / 2,
				SnowyGame.HEIGHT - youLost.getHeight());

		youLost.setColor(youLost.getColor().r, youLost.getColor().g,
				youLost.getColor().b, transparency);
		scoreLabel = new Label("Score: " + score, Assets.getInstance()
				.getSkin(), "boldtext", Color.WHITE);
		scoreLabel.setX(SnowyGame.WIDTH / 2 - scoreLabel.getWidth() / 2);
		scoreLabel.setY(youLost.getY() - scoreLabel.getHeight());
		scoreLabel.setColor(0.6f, 1f, 0.2f, 1);

		Label highScore = new Label("Highscore: "
				+ ScoreHelper.loadLocalScore(), Assets.getInstance().getSkin(),
				"boldtext", Color.WHITE);
		highScore.setX(SnowyGame.WIDTH / 2 - highScore.getWidth() / 2);
		highScore.setY(scoreLabel.getY() - scoreLabel.getHeight());
		highScore.setColor(0.6f, 1f, 0.2f, 1);

		final TextButton playAgain = new TextButton("Play Again", Assets
				.getInstance().getSkin(), "default");
		playAgain.setWidth(400);
		playAgain.setPosition(SnowyGame.WIDTH / 2 - playAgain.getWidth() / 2,
				SnowyGame.HEIGHT / 2 - playAgain.getHeight());
		playAgain.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (SnowyGame.advertisement)
					SnowyGame.actionResolver.showOrLoadInterstital();
				((Game) Gdx.app.getApplicationListener())
						.setScreen(new GameScreen());
			}
		});
		Color original = playAgain.getColor();
		playAgain.setColor(original.r, original.g, original.b, transparency);
		upload = new TextButton("Upload", Assets.getInstance().getSkin(),
				"default");
		upload.setWidth(350);
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
				SnowyGame.actionResolver.submitScore(score);
				upload.setDisabled(false);
			}
		});
		upload.setColor(original.r, original.g, original.b, transparency);
		final TextButton exitButton = new TextButton("Exit", Assets
				.getInstance().getSkin());

		exitButton.setWidth(160);
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
		exitButton.setColor(original.r, original.g, original.b, transparency);
		final TextButton menuButton = new TextButton("Menu", Assets
				.getInstance().getSkin());

		menuButton.setWidth(160);
		menuButton.setHeight(100);
		menuButton.setPosition(20, 20);
		menuButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

				((Game) Gdx.app.getApplicationListener())
						.setScreen(new MainMenu());
			}
		});
		menuButton.setColor(original.r, original.g, original.b, transparency);

		stage.addActor(scoreLabel);
		stage.addActor(highScore);
		stage.addActor(youLost);
		stage.addActor(playAgain);
		stage.addActor(exitButton);
		stage.addActor(menuButton);
		stage.addActor(upload);

	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setPrePhase(boolean phase) {
		if (!prePhase)
			return;
		this.prePhase = phase;
		if (!phase) {
			intro.remove();
			Timer.schedule(intervalDecrease, 0, 0.5f);
		}
	}
}
