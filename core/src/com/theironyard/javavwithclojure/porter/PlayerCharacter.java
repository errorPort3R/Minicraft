package com.theironyard.javavwithclojure.porter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

/**
 * Created by jeffryporter on 6/2/16.
 */
public class PlayerCharacter
{
    private Texture tiles;
    private TextureRegion[][] grid;
    private TextureRegion[][] tinyGrid;
    private TextureRegion down;
    private TextureRegion up;
    private TextureRegion right;
    private TextureRegion left;
    private TextureRegion stand;
    private TextureRegion gotHit;
    private TextureRegion upReversed;
    private TextureRegion downReversed;
    private TextureRegion standReversed;
    private int windowHeight;
    private int windowWidth;
    private float x, y, xv, yv;
    private Animation walkUp;
    private Animation walkDown;
    private Animation walkLeft;
    private Animation walkRight;

    private int score;
    private int health;
    private boolean damage;
    private boolean isAlive;

    private static int CYCLES = 1000;


    public void create () {

        tiles = new Texture("tiles.png");
        tinyGrid = TextureRegion.split(tiles, MyGdxGame.WIDTH / 2, MyGdxGame.HEIGHT / 2);
        grid = TextureRegion.split(tiles, MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
        down = grid[6][0];
        up = grid[6][1];
        stand = grid[6][2];
        right = grid[6][3];
        gotHit = tinyGrid[1][5];
        gotHit.setRegionHeight(MyGdxGame.HEIGHT);
        gotHit.setRegionWidth(MyGdxGame.WIDTH);
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
        damage = false;
        isAlive = true;
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
            health++;
            Apple.getEatAppleSound().play(1.0f);
        }
    }

    public void checkForDamage(Monster monster, float time)
    {
        damage = false;

        if ((Math.abs(monster.getX()-x)<MyGdxGame.PROXIMITY_TOUCHING) &&
                (Math.abs(monster.getY()-y)<MyGdxGame.PROXIMITY_TOUCHING))
        {
            if (time-monster.getAttackTimer()>=1)
            {
                monster.setAttackTimer(time);
                health--;
                monster.getAttackSound().play(1.0f);
                damage = true;
            }
        }
        if (health<=0)
        {
            isAlive = false;
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

    public int getHealth()
    {
        return health;
    }

    public TextureRegion getHitTile()
    {
        return gotHit;
    }
    public boolean getDamageStatus()
    {
        return damage;
    }

    public boolean isAlive()
    {
        return isAlive;
    }

}
