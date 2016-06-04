package com.theironyard.javavwithclojure.porter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by jeffryporter on 6/3/16.
 */
public class Water extends Terrain
{
    public static Texture tiles;
    public static TextureRegion[][] grid;
    public static TextureRegion topEdge;
    public static TextureRegion bottomEdge;
    public static TextureRegion leftEdge;
    public static TextureRegion middle;
    public static TextureRegion middle2;
    public static TextureRegion topLeftEdge;
    public static TextureRegion bottomLeftEdge;
    public static TextureRegion rightEdge;
    public static TextureRegion topRightEdge;
    public static TextureRegion bottomRightEdge;



    public static void create() {
        Texture tiles = new Texture("tiles.png");
        TextureRegion[][] grid = TextureRegion.split(tiles, MyGdxGame.WIDTH / 2, MyGdxGame.HEIGHT / 2);
        TextureRegion topEdge = grid[6][2];
        TextureRegion bottomEdge = grid[8][2];
        TextureRegion leftEdge = grid[7][0];
        TextureRegion middle = grid[7][1];
        TextureRegion middle2 = grid[7][2];
        TextureRegion topLeftEdge = grid[6][0];
        TextureRegion bottomLeftEdge = grid[8][0];
        TextureRegion rightEdge = grid[7][5];
        TextureRegion topRightEdge = grid[6][5];
        TextureRegion bottomRightEdge = grid[8][5];
    }



}
