package com.theironyard.javavwithclojure.porter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	Texture tiles;
	Jelly jelly;
	Zombie zombie;
	PlayerCharacter player;
	Tree tree;
	Apple apple;
	BitmapFont scoreFont;
	BitmapFont healthFont;
	TiledMap tiledMap;
	OrthographicCamera camera;
	TiledMapRenderer tiledMapRenderer;



	boolean firstRun = true;
	float time;
	String scoreOutput;
	String healthOutput;


	static final int WIDTH = 16;
	static final int HEIGHT = 16;
	static final float MAX_VELOCITY = 100f;
	static final float DECLERATION_RATE = 0.5f;
	static final float SPEED_MULTIPLIER = 2f;
	static final float SCALE_MULTIPLIER = 3f;
	static final float STOP_THRESHHOLD = 5f;
	static final float PROXIMITY_TOUCHING = 32f;

	@Override
	public void create () {
		batch = new SpriteBatch();
		tiles = new Texture("tiles.png");
		apple = new Apple();
		apple.create();
		tree = new Tree();
		tree.create();
		jelly = new Jelly();
		jelly.create();
		zombie = new Zombie();
		zombie.create();
		player = new PlayerCharacter();
		player.create();
		scoreFont = new BitmapFont();
		scoreFont.setColor(Color.BLACK);
		healthFont = new BitmapFont();

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();


		//vvvvvv borrowed code from
		camera = new OrthographicCamera();
		camera.setToOrtho(false,w,h);
		camera.update();
		tiledMap = new TmxMapLoader().load("level1.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap,2f);
		Gdx.input.setInputProcessor(this);
		//^^^^^^^ borrowed code
	}

	@Override
	public void render () {
		player.moveCharacter(apple);
		player.checkForDamage(zombie,time);
		player.checkForDamage(jelly,time);
		jelly.moveCharacter(player);
		jelly.startLocation(firstRun);
		zombie.moveCharacter(player);
		zombie.startLocation(firstRun);
		if (firstRun)
		{
			tree.plantTree();
		}
		firstRun = false;
		scoreOutput = String.format("SCORE: %d", player.getScore());
		if (player.getHealth()<=(player.getHealth()/5))
		{
			healthFont.setColor(Color.RED);
		}
		else
		{
			healthFont.setColor(Color.BLACK);
		}
		healthOutput = String.format("HEALTH: %d", player.getHealth());

		time += Gdx.graphics.getDeltaTime();
		TextureRegion jImg = jelly.animationTile(time);
		TextureRegion zImg = zombie.animationTile(time);
		TextureRegion img = player.animationTile(time);
		TextureRegion tImg = tree.getTreeTexture(time);
		TextureRegion aImg = apple.getAppleTexture();

//start drawing
		Gdx.gl.glClearColor(0.5f, 0.65f, 0.5f, 0.5f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//vvvvvvvvvborrowed code
		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
		//^^^^^^^ borrowed code

		batch.begin();
		scoreFont.draw(batch,scoreOutput,10, (Gdx.graphics.getHeight()-3));
		healthFont.draw(batch, healthOutput, 10, (Gdx.graphics.getHeight()-15));
		batch.draw(img, player.getX(), player.getY(), WIDTH*SCALE_MULTIPLIER, HEIGHT*SCALE_MULTIPLIER);
		batch.draw(zImg, zombie.getX(), zombie.getY(), WIDTH*SCALE_MULTIPLIER, HEIGHT*SCALE_MULTIPLIER);
		batch.draw(jImg, jelly.getX(), jelly.getY(), WIDTH*SCALE_MULTIPLIER, HEIGHT*SCALE_MULTIPLIER);
		batch.draw(tImg, tree.getX(), tree.getY(), WIDTH*SCALE_MULTIPLIER, HEIGHT*SCALE_MULTIPLIER);
		batch.draw(aImg, apple.getX(), apple.getY(), WIDTH, HEIGHT);
		batch.end();
	}



	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}


}
