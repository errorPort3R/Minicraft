package com.theironyard.javavwithclojure.porter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by jeffryporter on 6/3/16.
 */
public class Terrain
{
    private int speedModifier;
    Texture tile;
    TextureRegion[][] grid;
    TextureRegion tilepiece;
    int x, y;
    int windowHeight;
    int windowWidth;

    public int getY() {
        return y;
    }

    public int getSpeedModifier() {
        return speedModifier;
    }

    public TextureRegion[][] getGrid() {
        return grid;
    }

    public int getX() {
        return x;
    }


}
