package com.theironyard.javavwithclojure.porter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by jeffryporter on 6/2/16.
 */
public class Jelly
{
    private Texture tiles;
    private TextureRegion[][] grid;
    private TextureRegion down;
    private TextureRegion up;
    private int windowHeight;
    private int windowWidth;
    private int pathDirectionX;
    private int pathDirectionY;
    private float pathDuration;
    private boolean hasTarget = false;
    private boolean hasPath = false;

    public static final float SPEED_MULTIPLIER = 1.5f;
    public static final float MAX_PATH_DURATION = 2.0f;
    public static final float DETECT_DISTANCE = 150f;

    private float x, y, xv, yv;
    private Animation moveTile;

    public void create ()
    {
        tiles = new Texture("tiles.png");
        grid = TextureRegion.split(tiles, MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
        down = grid[7][4];
        up = grid[7][5];


        moveTile = new Animation(0.15f, up, down);

    }

    public TextureRegion animationTile(float time)
    {
        TextureRegion jImg;
        return jImg = moveTile.getKeyFrame(time, true);
    }

    public void moveCharacter(PlayerCharacter player)
    {
        choosePath(player);

        if (pathDirectionY == 0)
        {
            yv = MyGdxGame.MAX_VELOCITY;
            if (hasTarget)
            {
                yv = MyGdxGame.MAX_VELOCITY*SPEED_MULTIPLIER;
            }
        }
        else if (pathDirectionY == 1)
        {
            yv = -MyGdxGame.MAX_VELOCITY;
            if (hasTarget)
            {
                yv = -MyGdxGame.MAX_VELOCITY*SPEED_MULTIPLIER;
            }
        }

        if (pathDirectionX == 0)
        {
            xv = MyGdxGame.MAX_VELOCITY;
            if (hasTarget)
            {
                xv = MyGdxGame.MAX_VELOCITY*SPEED_MULTIPLIER;
            }
        }
        else if (pathDirectionX == 1)
        {
            xv = -MyGdxGame.MAX_VELOCITY;
            if (hasTarget)
            {
                xv = -MyGdxGame.MAX_VELOCITY*SPEED_MULTIPLIER;
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

    public void choosePath(PlayerCharacter player)
    {
        canISeeAGoodGuy(player);

        if (hasTarget)
        {
            if (x>player.getX())
            {
                pathDirectionX = 1;
            }
            else if(x<player.getX())
            {
                pathDirectionX = 0;
            }

            if (y>player.getY())
            {
                pathDirectionY = 1;
            }
            else if(y<player.getY())
            {
                pathDirectionY = 0;
            }
        }
        else if (!hasPath)
        {
            pathDirectionX = (int)(Math.random()*1000%3);
            pathDirectionY = (int)(Math.random()*1000%3);
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
    public void canISeeAGoodGuy(PlayerCharacter player)
    {
        if (Math.abs(player.getX() - x)<DETECT_DISTANCE && (Math.abs(player.getY() - y)<DETECT_DISTANCE))
        {
            hasTarget = true;
        }
        else
        {
            hasTarget = false;
        }
    }
}
