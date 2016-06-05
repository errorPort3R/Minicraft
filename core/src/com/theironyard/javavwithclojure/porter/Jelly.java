package com.theironyard.javavwithclojure.porter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by jeffryporter on 6/2/16.
 */
public class Jelly extends Monster
{
    private Texture tiles;
    private TextureRegion[][] grid;
    private TextureRegion down;
    private TextureRegion up;
    private TextureRegion left;
    private TextureRegion right;
    private int windowHeight;
    private int windowWidth;
    private int pathDirectionX;
    private int pathDirectionY;
    private float pathDuration;
    private boolean hasTarget = false;
    private boolean hasPath = false;
    private Sound attackSound;


    public static final float SPEED_MULTIPLIER = 1.5f;
    public static final float MAX_PATH_DURATION = 2.0f;


    private float x, y, xv, yv;
    private Animation walkUp;
    private Animation walkDown;
    private Animation walkLeft;
    private Animation walkRight;
    private Animation stand;

    public void create ()
    {
        tiles = new Texture("tiles.png");
        grid = TextureRegion.split(tiles, MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
        down = grid[7][4];
        up = grid[7][5];
        left = grid[7][6];
        right = new TextureRegion(left);
        right.flip(true, false);
        walkUp = new Animation(0.15f, up, down);
        walkDown = new Animation(.15f, up, down);
        walkLeft = new Animation(.15f, left, down);
        walkRight = new Animation(.15f, right, down);

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
            zImg = walkUp.getKeyFrame(time, true);;
        }
        return zImg;
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


        //wrap around code
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
        if (Math.abs(velocity) < MyGdxGame.STOP_THRESHHOLD)
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

    public void startLocation(boolean run)
    {
        if (run)
        {
            x = (int) (Math.random() * 10000 % windowWidth);
            y = (int) (Math.random() * 10000 % windowHeight);
        }
    }

    public void canISeeAGoodGuy(PlayerCharacter player)
    {
        if (Math.abs(player.getX() - x)<MyGdxGame.AGGRO_RANGE && (Math.abs(player.getY() - y)<MyGdxGame.AGGRO_RANGE))
        {
            hasTarget = true;
        }
        else
        {
            hasTarget = false;
        }
        if (((Math.abs((x-player.getX())))<2) && !((Math.abs((y-player.getY()))) < 2))
        {
            x = player.getX();
        }
        if (!((Math.abs((x-player.getX())))<2) && ((Math.abs((y-player.getY()))) < 2))
        {
            y = player.getY();
        }
    }

    @Override
    public Sound getAttackSound()
    {
        return attackSound = Gdx.audio.newSound(Gdx.files.internal("jellyattack.mp3"));
    }
}
