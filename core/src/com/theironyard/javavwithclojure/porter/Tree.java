package com.theironyard.javavwithclojure.porter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by jeffryporter on 6/3/16.
 */
public class Tree
{
    Texture tiles;
    TextureRegion[][] tinyGrid;
    TextureRegion tree;
    int treeX, treeY;
    int windowHeight;
    int windowWidth;


    public void create ()
    {
        tiles = new Texture("tiles.png");
        tinyGrid = TextureRegion.split(tiles, MyGdxGame.WIDTH/2, MyGdxGame.HEIGHT/2);
        tree = tinyGrid[1][0];
        tree.setRegionWidth(MyGdxGame.WIDTH);
        tree.setRegionHeight(MyGdxGame.HEIGHT);
    }

    public void plantTree()
    {
        windowHeight = Gdx.graphics.getHeight();
        windowWidth = Gdx.graphics.getWidth();
        treeX = (int)(Math.random()*10000%(windowWidth-(MyGdxGame.WIDTH*MyGdxGame.SCALE_MULTIPLIER)));
        treeY = (int)(Math.random()*10000%(windowHeight-(MyGdxGame.HEIGHT*MyGdxGame.SCALE_MULTIPLIER)));
    }

    public TextureRegion getTreeTexture(float time) {
        return tree;
    }

    public int getX()
    {
        return treeX;
    }

    public int getY()
    {
        return treeY;
    }
}
