package com.example.supermariobros;

import android.graphics.Rect;

public abstract class Enemy extends Entity {
    private Rect hitBox;

    public Enemy(int posX, int posY, int width, int height){
        super(posX, posY, width, height);

        setRelPos(posX);
    }

    protected void setHitBox(int left, int top, int right, int bottom){
        hitBox = new Rect(left, top, right, bottom);
    }

    public Rect getHitBox(){
        return hitBox;
    }

    @Override
    public void setEmpty(){
        super.setEmpty();

        hitBox = new Rect(0,0,0,0);
    }

    @Override
    public void move(int x, int y){
        super.move(x, y);

        hitBox.offset(x, y);
    }

}// class Enemy

/* EOF */