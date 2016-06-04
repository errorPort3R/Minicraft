package com.theironyard.javavwithclojure.porter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by jeffryporter on 6/3/16.
 */
public class Apple
{
    private Texture tiles;
    private TextureRegion[][] tinyGrid;
    private TextureRegion apple;
    private int x, y;
    private int windowHeight;
    private int windowWidth;
    private static Sound eatApple = Gdx.audio.newSound(Gdx.files.internal("eatapple.mp3"));


    public void create ()
    {
        tiles = new Texture("tiles.png");
        tinyGrid = TextureRegion.split(tiles, MyGdxGame.WIDTH/2, MyGdxGame.HEIGHT/2);
        apple = tinyGrid[0][5];
    }

    public void locateNewApple()
    {
            windowHeight = Gdx.graphics.getHeight();
            windowWidth = Gdx.graphics.getWidth();
            x = (int) (Math.random() * 10000 % (windowWidth - (MyGdxGame.WIDTH * MyGdxGame.SCALE_MULTIPLIER)));
            y = (int) (Math.random() * 10000 % (windowHeight - (MyGdxGame.HEIGHT * MyGdxGame.SCALE_MULTIPLIER)));
    }

    public TextureRegion getAppleTexture()
    {
        return apple;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public static Sound getEatAppleSound()
    {
        return eatApple;
    }
}
