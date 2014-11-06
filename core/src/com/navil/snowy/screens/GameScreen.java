package com.navil.snowy.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.navil.snowy.GameStage;
import com.navil.snowy.SnowyGame;
import com.navil.snowy.actors.BottomLine;
import com.navil.snowy.actors.FireActor;
import com.navil.snowy.actors.SnowyActor;
import com.navil.snowy.util.Assets;
import com.navil.snowy.util.MyContactListener;
import com.navil.snowy.util.ScoreHelper;

public class GameScreen implements Screen {

	private SnowyActor snowyActor;
	public Timer invincibleTimer = new Timer();

	private float timeSinceLastFire = 0;
	private Label scoreLabel;

	public static World world;

	private boolean removeFire = false;
	private boolean gameOver = false;
	
	private int numLifes = SnowyGame.numLifes;

	private Array<FireActor> flames = new Array<FireActor>();
	// private Map<Body,Actor> flames = new HashMap<Body,Actor>();

	private GameStage stage;
	public boolean moveLeft, moveRight;
	private int score = 0;

	@Override
	public void show() {
		world = new World(new Vector2(0, -20), true);
		stage = new GameStage(world, this);
		Gdx.input.setInputProcessor(stage);
		

		snowyActor = new SnowyActor();
		snowyActor.setBody(createBody(snowyActor, BodyType.StaticBody, 1), "snowy");
		world.setContactListener(new MyContactListener(this));

		
		stage.addActor(snowyActor);

		BottomLine botLine = new BottomLine();
		botLine.setBody(createBody(botLine, BodyType.StaticBody, 0), "botLine");
		
		scoreLabel = new Label("Score: 1234567",Assets.getInstance().getSkin(),"normaltext",Color.BLACK);
		scoreLabel.setText("Score: "+score);
		scoreLabel.setPosition(SnowyGame.WIDTH-scoreLabel.getWidth(),SnowyGame.HEIGHT-scoreLabel.getHeight());
		
		stage.addActor(scoreLabel);
		stage.addActor(botLine);

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		moveSnowy();
		
		timeSinceLastFire += delta;
		if (timeSinceLastFire >= SnowyGame.fireInterval) {

			timeSinceLastFire = 0;
			if (!gameOver){
				createFlame();
				
			}
		}
		if (removeFire && !gameOver) {
			FireActor temp = flames.removeIndex(0);
			// temp.clear();
			temp.setVisible(false);
			temp.getBody().setActive(false);
			world.destroyBody(temp.getBody());
			removeFire = false;
			//Gdx.app.error("NumFlames", "" + flames.size);
			score +=SnowyGame.scorePerFlame;
			scoreLabel.setText("Score: "+score);
		}
		stage.draw();
		stage.act(delta);
		// Fixed timestep
		// System.out.println("SnowyX:"+snowySprite.getX());

		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	private Body createBody(Actor actor, BodyType staticbody, float density) {

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = staticbody;
		bodyDef.position.set(actor.getX(), actor.getY());

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(actor.getWidth() / 2, actor.getHeight() / 2);

		Body body = world.createBody(bodyDef);
		body.createFixture(shape, density);
		body.setGravityScale(10);
		shape.dispose();
		return body;
	}

	private void createFlame() {

		FireActor fireActor = new FireActor();
		fireActor.setBody(createBody(fireActor, BodyType.DynamicBody, 100), "fire");
		stage.addActor(fireActor);
		flames.add(fireActor);

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

	private void moveSnowy() {
		if (moveLeft && !moveRight && snowyActor.getX() - SnowyGame.snowyMovespeed >= 0)
			snowyActor.moveBodyLeft();
		// snowyActor.moveBy(-SnowyGame.snowyMovespeed, 0);
		if (moveRight && !moveLeft && snowyActor.getX() <= SnowyGame.WIDTH - snowyActor.getWidth() - SnowyGame.snowyMovespeed)
			snowyActor.moveBodyRight();
	}

	public synchronized void setRemoveBody() {
		removeFire = true;
	}

	public void snowyHit(){
		//Gdx.app.error("Invincible: ",""+snowyActor.isInvincible());
		if(snowyActor.isInvincible()){
			return;
		}
		
		snowyActor.setInvincible(true);
		invincibleTimer.scheduleTask(new Task(){
		    @Override
		    public void run() {
		        snowyActor.setInvincible(false);
		    }
		}, SnowyGame.invincibleTimer);
		numLifes --;
		if(numLifes == 0)
			setGameOver();
		
		//Gdx.app.error("Lifes", ""+numLifes);
	}
	
	public void setGameOver() {
		gameOver = true;
		if(ScoreHelper.loadLocalScore()<score)
			ScoreHelper.saveLocalScore(score);
		showLose();
	}
	
	private void showLose() {
		removeFire = false;
		
		//remove all bodies and sprites
		for (int i = 0; i < flames.size; i++) {
			FireActor fa = flames.get(i);
			fa.setVisible(false);
			fa.getBody().setActive(false);
			world.destroyBody(fa.getBody());
		}
		snowyActor.setVisible(false);
		snowyActor.getBody().setActive(false);
		world.destroyBody(snowyActor.getBody());
		scoreLabel.setVisible(false);
		flames.clear();
		
		//save score
		
		//show the interface
		final Label youLost = new Label("Snowy melted!",Assets.getInstance().getSkin(),"menutitle",Color.RED);
		//final TextField youLost = new TextField("Snowy melted!",skin);
		youLost.setPosition(SnowyGame.WIDTH/2-youLost.getWidth()/2, SnowyGame.HEIGHT-youLost.getHeight());
		
		scoreLabel = new Label("Score: "+score,Assets.getInstance().getSkin(),"normaltext",Color.BLACK);
		scoreLabel.setX(Gdx.graphics.getWidth()/2-scoreLabel.getWidth()/2);
		scoreLabel.setY(youLost.getY()-scoreLabel.getHeight()-50);
		
		Label highScore= new Label("Highscore: "+ScoreHelper.loadLocalScore(),Assets.getInstance().getSkin(),"normaltext",Color.BLACK);
		highScore.setX(Gdx.graphics.getWidth()/2-highScore.getWidth()/2);
		highScore.setY(scoreLabel.getY()-scoreLabel.getHeight());
		
		final TextButton playAgain = new TextButton("Play Again", Assets.getInstance().getSkin(), "default");
        playAgain.setPosition(Gdx.graphics.getWidth() /2 - playAgain.getWidth()/2, Gdx.graphics.getHeight()/2 - playAgain.getHeight());
        playAgain.addListener(new ClickListener(){
            @Override 
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){ 
                SnowyGame.actionResolver.showOrLoadInterstital();
            	((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen());
                return true;
            }
        });
        
        final TextButton upload = new TextButton("Upload", Assets.getInstance().getSkin(), "default");
        upload.setWidth(playAgain.getWidth());
        upload.setPosition(Gdx.graphics.getWidth() /2 - upload.getWidth()/2, playAgain.getY()-playAgain.getHeight()-10);
        upload.addListener(new ClickListener(){
            @Override 
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){ 
                ((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen());
                
                return true;
            }
        });
        
        
        stage.addActor(scoreLabel);
        stage.addActor(highScore);
        stage.addActor(youLost);
        stage.addActor(playAgain);
        stage.addActor(upload);
        Gdx.input.setInputProcessor(stage);
       
	}
	public boolean isGameOver() {
		return gameOver;
	}

}
