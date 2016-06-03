package com.theironyard.javavwithclojure.porter;

/**
 * Created by jeffryporter on 6/3/16.
 */
public class Monster
{


    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    float x;
    float y;

    public void setAttackTimer(float attackTimer) {
        this.attackTimer = attackTimer;
    }

    float attackTimer;

    public float getAttackTimer() {
        return attackTimer;
    }

}
