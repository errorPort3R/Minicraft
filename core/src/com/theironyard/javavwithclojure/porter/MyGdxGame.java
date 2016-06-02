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
	TextureRegion[][] grid;
	TextureRegion down;
	TextureRegion up;
	TextureRegion right;
	TextureRegion left;
	TextureRegion stand;
	TextureRegion upReversed;
	TextureRegion downReversed;
	TextureRegion standReversed;
	float x, y, xv, yv;
	float time;
	Animation walkUp;
	Animation walkDown;
	Animation walkLeft;
	Animation walkRight;


	static final int WIDTH = 16;
	static final int HEIGHT = 16;
	static final float MAX_VELOCITY = 100;
	static final float DECLERATION_RATE = 0.95f;
	static final float SPEED_MULTIPLIER = 1.5f;
	static final float SCALE_MULTIPLIER = 3;

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
		upReversed = new TextureRegion(up);
		upReversed.flip(true, false);
		downReversed = new TextureRegion(down);
		downReversed.flip(true, false);
		standReversed = new TextureRegion(stand);
		standReversed.flip(true, false);
		walkUp = new Animation(0.15f, up, upReversed);
		walkDown = new Animation(.15f, down, downReversed);
		walkLeft = new Animation(.15f, left, standReversed);
		walkRight = new Animation(.15f, right, stand);

	}

	@Override
	public void render () {
		moveCharacter();
		moveZombie();
		moveJelly();


		time += Gdx.graphics.getDeltaTime();

		TextureRegion img;
		if (xv<0)
		{
			img = walkLeft.getKeyFrame(time, true);
		}
		else if (xv>0)
		{
			img = walkRight.getKeyFrame(time, true);
		}
		else if (yv<0)
		{
			img = walkDown.getKeyFrame(time, true);
		}
		else if (yv>0)
		{
			img = walkUp.getKeyFrame(time, true);
		}
		else
		{
			img = stand;
		}

		Gdx.gl.glClearColor(0.5f, 0.65f, 0.5f, 0.5f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, x, y, WIDTH*SCALE_MULTIPLIER, HEIGHT*SCALE_MULTIPLIER);
		batch.end();
	}

	public void moveCharacter()
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

		int windowHeight = Gdx.graphics.getHeight();
		int windowWidth  =  Gdx.graphics.getWidth();

		if (x<(-WIDTH*SCALE_MULTIPLIER))
		{
			x = windowWidth;
		}
		if (x>(windowWidth))
		{
			x = -(WIDTH*SCALE_MULTIPLIER);
		}

		if (y<(-HEIGHT*SCALE_MULTIPLIER))
		{
			y = windowHeight;
		}
		if (y>(windowHeight))
		{
			y = -(HEIGHT*SCALE_MULTIPLIER);
		}

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

	public void moveZombie()
	{

	}

	public void moveJelly()
	{

	}
}
