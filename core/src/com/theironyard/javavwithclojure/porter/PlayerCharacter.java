package com.theironyard.javavwithclojure.porter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by jeffryporter on 6/2/16.
 */
public class PlayerCharacter
{
    Texture tiles;
    TextureRegion[][] grid;
    TextureRegion[][] tinyGrid;
    TextureRegion down;
    TextureRegion up;
    TextureRegion right;
    TextureRegion left;
    TextureRegion stand;
    TextureRegion upReversed;
    TextureRegion downReversed;
    TextureRegion standReversed;
    TextureRegion tree;
    int windowHeight;
    int windowWidth;
    float x, y, xv, yv;
    Animation walkUp;
    Animation walkDown;
    Animation walkLeft;
    Animation walkRight;

    int score;
    int health;


    public void create () {

        tiles = new Texture("tiles.png");
        tinyGrid = TextureRegion.split(tiles, MyGdxGame.WIDTH / 2, MyGdxGame.HEIGHT / 2);
        grid = TextureRegion.split(tiles, MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
        down = grid[6][0];
        up = grid[6][1];
        stand = grid[6][2];
        right = grid[6][3];
        tree = tinyGrid[1][0];
        tree.setRegionWidth(MyGdxGame.WIDTH);
        tree.setRegionHeight(MyGdxGame.HEIGHT);
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
        score = 0;
        health = 25;
    }

    public TextureRegion animationTile(float time)
    {
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
        return img;
    }

    public void moveCharacter(Apple apple)
    {
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
        {

            if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
            {
                yv = MyGdxGame.MAX_VELOCITY*MyGdxGame.SPEED_MULTIPLIER;
            }
            else
            {
                yv = MyGdxGame.MAX_VELOCITY;
            }
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
        {

            if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
            {
                yv = -MyGdxGame.MAX_VELOCITY*MyGdxGame.SPEED_MULTIPLIER;
            }
            else
            {
                yv = -MyGdxGame.MAX_VELOCITY;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {

            if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
            {
                xv = MyGdxGame.MAX_VELOCITY*MyGdxGame.SPEED_MULTIPLIER;
            }
            else
            {
                xv = MyGdxGame.MAX_VELOCITY;
            }
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {

            if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
            {
                xv = -MyGdxGame.MAX_VELOCITY*MyGdxGame.SPEED_MULTIPLIER;
            }
            else
            {
                xv = -MyGdxGame.MAX_VELOCITY;
            }
        }

        float delta = Gdx.graphics.getDeltaTime();
        y+= yv * delta;
        x+= xv * delta;
        yv = decelerate(yv);
        xv = decelerate(xv);

        windowHeight = Gdx.graphics.getHeight();
        windowWidth = Gdx.graphics.getWidth();

        if (x<(-MyGdxGame.WIDTH*MyGdxGame.SCALE_MULTIPLIER))
        {
            x = windowWidth;
        }
        if (x>(windowWidth))
        {
            x = -(MyGdxGame.WIDTH*MyGdxGame.SCALE_MULTIPLIER);
        }

        if (y<(-MyGdxGame.HEIGHT*MyGdxGame.SCALE_MULTIPLIER))
        {
            y = windowHeight;
        }
        if (y>(windowHeight))
        {
            y = -(MyGdxGame.HEIGHT*MyGdxGame.SCALE_MULTIPLIER);
        }

        if (Math.abs(apple.getX()-x)<MyGdxGame.PROXIMITY_TOUCHING && Math.abs(apple.getY()-y)<MyGdxGame.PROXIMITY_TOUCHING)
        {
            apple.locateNewApple();
            score++;
        }

    }

    public float decelerate(float velocity)
    {
        velocity *= MyGdxGame.DECLERATION_RATE;
        if (Math.abs(velocity) < MyGdxGame.STOP_THRESHHOLD)
        {
            velocity = 0;
        }
        return velocity;
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public int getScore()
    {
        return score;
    }

}
