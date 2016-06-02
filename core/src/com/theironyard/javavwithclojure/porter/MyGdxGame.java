package com.theironyard.javavwithclojure.porter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture tiles;
	TextureRegion[][] grid;
	TextureRegion down;
	TextureRegion up;
	TextureRegion right;
	TextureRegion left;
	TextureRegion stand;
	float x, y, xv, yv;

	static final int WIDTH = 16;
	static final int HEIGHT = 16;
	static final float MAX_VELOCITY = 100;
	static final float DECLERATION_RATE = 0.95f;
	static final float SPEED_MULTIPLIER = 1.5f;

	@Override
	public void create () {
		batch = new SpriteBatch();
		tiles = new Texture("tiles.png");
		grid = TextureRegion.split(tiles, WIDTH, HEIGHT);
		down = grid[6][0];
		up = grid[6][1];
		stand = grid[6][2];
		right = grid[6][3];
		left = new TextureRegion(right);
		left.flip(true, false);
	}

	@Override
	public void render () {
		move();

		//time += Gdx.graphics.getDeltaTime();

		TextureRegion img;
		if (xv<0)
		{
			img = left;
		}
		else if (xv>0)
		{
			img = right;
		}
		else if (yv<0)
		{
			img = down;
		}
		else if (yv>0)
		{
			img = up;
		}
		else
		{
			img = stand;
		}

		Gdx.gl.glClearColor(0.1f, 0.1f, 0.8f, 0.7f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, x, y, WIDTH*2, HEIGHT*2);
		batch.end();
	}

	public void move()
	{
		if (Gdx.input.isKeyPressed(Input.Keys.UP))
		{
			yv = MAX_VELOCITY;
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
			{
				yv = MAX_VELOCITY*SPEED_MULTIPLIER;
			}
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
		{
			yv = -MAX_VELOCITY;
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
			{
				yv = -MAX_VELOCITY*SPEED_MULTIPLIER;
			}
		}

		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
		{
			xv = MAX_VELOCITY;
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
			{
				xv = MAX_VELOCITY*SPEED_MULTIPLIER;
			}
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
		{
			xv = -MAX_VELOCITY;
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
			{
				xv = -MAX_VELOCITY*SPEED_MULTIPLIER;
			}
		}





		float delta = Gdx.graphics.getDeltaTime();
		y+= yv * delta;
		x+= xv * delta;
		yv = decelerate(yv);
		xv = decelerate(xv);

	}

	public float decelerate(float velocity)
	{
		velocity *= DECLERATION_RATE;
		if (Math.abs(velocity) < 1)
		{
			velocity = 0;
		}
		return velocity;
	}
}
