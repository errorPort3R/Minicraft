package com.theironyard.javavwithclojure.porter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	Texture tiles;
	ArrayList<Monster> monsters;
	PlayerCharacter player;
	Tree tree;
	Apple apple;
	BitmapFont scoreFont;
	BitmapFont healthFont;
	BitmapFont theEndFont;

	TiledMap tiledMap;
	OrthographicCamera camera;
	TiledMapRenderer tiledMapRenderer;



	boolean firstRun = true;
	float time;
	String scoreOutput;
	String healthOutput;
	String theEndOutput;


	static final int WIDTH = 16;
	static final int HEIGHT = 16;
	static final float MAX_VELOCITY = 100f;
	static final float DECLERATION_RATE = 0.5f;
	static final float SPEED_MULTIPLIER = 2f;
	static final float SCALE_MULTIPLIER = 3f;
	static final float STOP_THRESHHOLD = 5f;
	static final float AGGRO_RANGE = 100f;
	static final float PROXIMITY_TOUCHING = 32f;
	static final int NUM_OF_MONSTERS = 20;

	@Override
	public void create () {
		batch = new SpriteBatch();
		tiles = new Texture("tiles.png");
		apple = new Apple();
		apple.create();
		apple.locateNewApple();
		tree = new Tree();
		tree.create();
		monsters = new ArrayList();
		generateMonsters();
		player = new PlayerCharacter();
		player.create();
		scoreFont = new BitmapFont();
		scoreFont.setColor(Color.BLACK);
		healthFont = new BitmapFont();
		theEndFont = new BitmapFont();
		theEndFont.setColor(Color.RED);
		theEndOutput = "You have died!\n   THE END!\n PLAY AGAIN?\n        Y/N";

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		camera.update();
		tiledMap = new TmxMapLoader().load("level1.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap,SCALE_MULTIPLIER);
		Gdx.input.setInputProcessor(this);

	}

	@Override
	public void render () {
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
		{
			Gdx.app.exit();
		}
		if (player.isAlive()) {
			player.moveCharacter(apple);
			player.checkForDamage(monsters, time);
			for(Monster monster : monsters)
			{
				monster.moveCharacter(player);
				monster.startLocation(firstRun);
			}

			if (firstRun)
			{
				tree.plantTree();
			}
			firstRun = false;
			scoreOutput = String.format("SCORE: %d", player.getScore());
			if (player.getHealth() <= (player.getHealth() / 5)) {
				healthFont.setColor(Color.RED);
			} else {
				healthFont.setColor(Color.BLACK);
			}
			healthOutput = String.format("HEALTH: %d", player.getHealth());

//start drawing
			Gdx.gl.glClearColor(0.5f, 0.65f, 0.5f, 0.5f);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		}

		time += Gdx.graphics.getDeltaTime();
		TextureRegion img = player.animationTile(time);
		TextureRegion tImg = tree.getTreeTexture(time);
		TextureRegion aImg = apple.getAppleTexture();
		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
		batch.begin();
		scoreFont.draw(batch, scoreOutput, 10, (Gdx.graphics.getHeight() - 3));
		healthFont.draw(batch, healthOutput, 10, (Gdx.graphics.getHeight() - 15));
		if (player.isAlive())
		{
			if (player.getDamageStatus())
			{
				batch.draw(player.getHitTile(), player.getX(), player.getY(), WIDTH * SCALE_MULTIPLIER, HEIGHT * SCALE_MULTIPLIER);
			}
			batch.draw(img, player.getX(), player.getY(), WIDTH * SCALE_MULTIPLIER, HEIGHT * SCALE_MULTIPLIER);
			batch.draw(img, player.getX(), player.getY(), WIDTH * SCALE_MULTIPLIER, HEIGHT * SCALE_MULTIPLIER);
			for(Monster monster : monsters)
			{
				batch.draw(monster.animationTile(time), monster.getX(), monster.getY(), WIDTH * SCALE_MULTIPLIER, HEIGHT * SCALE_MULTIPLIER);
			}
			batch.draw(tImg, tree.getX(), tree.getY(), WIDTH * SCALE_MULTIPLIER, HEIGHT * SCALE_MULTIPLIER);
			batch.draw(aImg, apple.getX(), apple.getY(), WIDTH, HEIGHT);
		}
		else
		{
			theEndFont.draw(batch, theEndOutput, ((Gdx.graphics.getWidth()/2)-10), (Gdx.graphics.getHeight()/2)-10);

		}
		batch.end();
		if (Gdx.input.isKeyPressed(Input.Keys.Y))
		{
			firstRun = true;
			create();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.N))
		{
			Gdx.app.exit();
		}
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

	public void generateMonsters()
	{
		for (int i = 0; i< NUM_OF_MONSTERS ; i++)
		{
			Monster monster = null;
			int selector = (int)((Math.random()*50)%4);
			switch (selector)
			{
				case 0:
					monster = new Jelly();
					break;
				case 1:
					monster = new Zombie();
					break;
				case 2:
					monster = new Octopus();
					break;
				case 3:
					monster = new Gatorman();
					break;
			}
			monster.create();
			monsters.add(monster);
		}
	}
}
