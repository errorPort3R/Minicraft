package com.theironyard.javavwithclojure.porter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    Zombie zombie;
    int windowHeight;
    int windowWidth;
    int treeX, treeY;
    boolean firstRun = true;
    float x, y, xv, yv;
    float time;
    Animation walkUp;
    Animation walkDown;
    Animation walkLeft;
    Animation walkRight;



    static final int WIDTH = 16;
    static final int HEIGHT = 16;
    static final float MAX_VELOCITY = 100f;
    static final float DECLERATION_RATE = 0.5f;
    static final float SPEED_MULTIPLIER = 2.0f;
    static final float SCALE_MULTIPLIER = 3f;

    public void create () {

        tiles = new Texture("tiles.png");
        tinyGrid = TextureRegion.split(tiles, WIDTH / 2, HEIGHT / 2);
        grid = TextureRegion.split(tiles, WIDTH, HEIGHT);
        down = grid[6][0];
        up = grid[6][1];
        stand = grid[6][2];
        right = grid[6][3];
        tree = tinyGrid[1][0];
        tree.setRegionWidth(WIDTH);
        tree.setRegionHeight(HEIGHT);
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

        windowHeight = Gdx.graphics.getHeight();
        windowWidth = Gdx.graphics.getWidth();

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
        if (Math.abs(velocity) < 5)
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

}
