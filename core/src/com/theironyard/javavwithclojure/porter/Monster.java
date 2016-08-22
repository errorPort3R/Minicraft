package com.theironyard.javavwithclojure.porter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by jeffryporter on 6/3/16.
 */
public class Monster
{
    float x;
    float y;
    float attackTimer;
    Sound attackSound;
    TextureRegion tile;

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public void create()
    {

    }


    public void setAttackTimer(float attackTimer) {
        this.attackTimer = attackTimer;
    }

    public Sound getAttackSound()
    {
        return attackSound;
    }

    public float getAttackTimer() {
        return attackTimer;
    }

    public void moveCharacter(PlayerCharacter player) {

    }

    public void startLocation(boolean run)
    {

    }
    public TextureRegion animationTile(float time)
    {
        return tile = new TextureRegion();
    }



}
