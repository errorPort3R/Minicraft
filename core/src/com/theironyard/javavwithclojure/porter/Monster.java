package com.theironyard.javavwithclojure.porter;

import com.badlogic.gdx.audio.Sound;

/**
 * Created by jeffryporter on 6/3/16.
 */
public class Monster
{
    float x;
    float y;
    float attackTimer;
    Sound attackSound;


    public float getY() {
        return y;
    }

    public float getX() {
        return x;
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

}
