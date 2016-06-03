package com.theironyard.javavwithclojure.porter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture tiles;
	TextureRegion[][] tinyGrid;
	TextureRegion tree;
	Zombie zombie;
	PlayerCharacter player;
	int windowHeight;
	int windowWidth;
	int treeX, treeY;
	boolean firstRun = true;
	float time;

	static final int WIDTH = 16;
	static final int HEIGHT = 16;
	static final float MAX_VELOCITY = 100f;
	static final float DECLERATION_RATE = 0.5f;
	static final float SPEED_MULTIPLIER = 2.0f;
	static final float SCALE_MULTIPLIER = 3f;

	@Override
	public void create () {
		batch = new SpriteBatch();
		tiles = new Texture("tiles.png");
		tinyGrid = TextureRegion.split(tiles, WIDTH/2, HEIGHT/2);
		tree = tinyGrid[1][0];
		tree.setRegionWidth(WIDTH);
		tree.setRegionHeight(HEIGHT);
		zombie = new Zombie();
		zombie.create();
		player = new PlayerCharacter();
		player.create();

	}

	@Override
	public void render () {
		player.moveCharacter();
		zombie.moveCharacter();
		zombie.startLocation(firstRun);
		plantTree();


		time += Gdx.graphics.getDeltaTime();
		TextureRegion zImg = zombie.animationTile(time);
		TextureRegion img = player.animationTile(time);

		Gdx.gl.glClearColor(0.5f, 0.65f, 0.5f, 0.5f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, player.getX(), player.getY(), WIDTH*SCALE_MULTIPLIER, HEIGHT*SCALE_MULTIPLIER);
		batch.draw(zImg, zombie.getX(), zombie.getY(), WIDTH*SCALE_MULTIPLIER, HEIGHT*SCALE_MULTIPLIER);
		batch.draw(tree, treeX, treeY, WIDTH*SCALE_MULTIPLIER, HEIGHT*SCALE_MULTIPLIER);
		batch.end();
	}

	public void plantTree()
	{
		if (firstRun)
		{
			treeX = (int)(Math.random()*10000%(windowWidth-(WIDTH*SCALE_MULTIPLIER)));
			treeY = (int)(Math.random()*10000%(windowHeight-(HEIGHT*SCALE_MULTIPLIER)));
			firstRun = false;
		}
	}
}
