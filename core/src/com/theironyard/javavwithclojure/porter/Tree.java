package com.theironyard.javavwithclojure.porter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by jeffryporter on 6/3/16.
 */
public class Tree extends Terrain
{
    Texture tiles;
    TextureRegion[][] grid;
    TextureRegion tilePiece;
    int windowHeight;
    int windowWidth;


    public void create ()
    {
        tiles = new Texture("tiles.png");
        grid = TextureRegion.split(tiles, MyGdxGame.WIDTH/2, MyGdxGame.HEIGHT/2);
        tilePiece = grid[1][0];
        tilePiece.setRegionWidth(MyGdxGame.WIDTH);
        tilePiece.setRegionHeight(MyGdxGame.HEIGHT);
    }

    public void plantTree()
    {
        windowHeight = Gdx.graphics.getHeight();
        windowWidth = Gdx.graphics.getWidth();
        x = (int)(Math.random()*10000%(windowWidth-(MyGdxGame.WIDTH*MyGdxGame.SCALE_MULTIPLIER)));
        y = (int)(Math.random()*10000%(windowHeight-(MyGdxGame.HEIGHT*MyGdxGame.SCALE_MULTIPLIER)));
    }

    public TextureRegion getTreeTexture(float time) {
        return tilePiece;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }
}
