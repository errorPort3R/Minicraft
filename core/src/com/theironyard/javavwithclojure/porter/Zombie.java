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
public class Zombie
{
    private Texture tiles;
    private TextureRegion[][] grid;
    private TextureRegion down;
    private TextureRegion up;
    private TextureRegion right;
    private TextureRegion left;
    private TextureRegion stand;
    private TextureRegion upReversed;
    private TextureRegion downReversed;
    private TextureRegion standReversed;
    private int windowHeight;
    private int windowWidth;
    private int pathDirection;
    private float pathDuration;
    private boolean hasTarget = false;
    private boolean hasPath = false;

    public static final float SPEED_MULTIPLIER = 1.5f;
    public static final float MAX_PATH_DURATION = 2.0f;

    float x, y, xv, yv;
    float time;
    Animation walkUp;
    Animation walkDown;
    Animation walkLeft;
    Animation walkRight;

    public void create ()
    {
        tiles = new Texture("tiles.png");
        grid = TextureRegion.split(tiles, MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
        down = grid[6][4];
        up = grid[6][5];
        stand = grid[6][6];
        right = grid[6][7];
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
        TextureRegion zImg;
        if (xv<0)
        {
            zImg = walkLeft.getKeyFrame(time, true);
        }
        else if (xv>0)
        {
            zImg = walkRight.getKeyFrame(time, true);
        }
        else if (yv<0)
        {
            zImg = walkDown.getKeyFrame(time, true);
        }
        else if (yv>0)
        {
            zImg = walkUp.getKeyFrame(time, true);
        }
        else
        {
            zImg = stand;
        }
        return zImg;
    }

    public void moveCharacter()
    {
        choosePath();



        if (pathDirection == 0)
        {
            yv = MyGdxGame.MAX_VELOCITY;
            if (hasTarget)
            {
                yv = MyGdxGame.MAX_VELOCITY*SPEED_MULTIPLIER;
            }
        }
        else if (pathDirection == 1)
        {
            yv = -MyGdxGame.MAX_VELOCITY;
            if (hasTarget)
            {
                yv = -MyGdxGame.MAX_VELOCITY*MyGdxGame.SPEED_MULTIPLIER;
            }
        }

        if (pathDirection == 2)
        {
            xv = MyGdxGame.MAX_VELOCITY;
            if (hasTarget)
            {
                xv = MyGdxGame.MAX_VELOCITY*MyGdxGame.SPEED_MULTIPLIER;
            }
        }
        else if (pathDirection == 3)
        {
            xv = -MyGdxGame.MAX_VELOCITY;
            if (hasTarget)
            {
                xv = -MyGdxGame.MAX_VELOCITY*MyGdxGame.SPEED_MULTIPLIER;
            }
        }

        float delta = Gdx.graphics.getDeltaTime();
        y+= yv * delta;
        x+= xv * delta;
        yv = decelerate(yv);
        xv = decelerate(xv);
        pathDuration -= delta;
        if (pathDuration <=0)
        {
            hasPath = false;
        }

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

    }

    public float decelerate(float velocity)
    {
        velocity *= MyGdxGame.DECLERATION_RATE;
        if (Math.abs(velocity) < 1)
        {
            velocity = 0;
        }
        return velocity;
    }

    public void choosePath()
    {
        if (!hasPath)
        {
            pathDirection = (int)(Math.random()*1000%5);
            pathDuration = (float) (Math.random() * 1000 % MAX_PATH_DURATION);
            hasPath = true;
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
    public void startLocation(boolean run) {
        if (run)
        {
            x = (int) (Math.random() * 10000 % windowWidth);
            y = (int) (Math.random() * 10000 % windowHeight);
        }
    }
}
