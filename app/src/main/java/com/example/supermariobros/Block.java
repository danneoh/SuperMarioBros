package com.example.supermariobros;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Block extends Entity {
    private boolean breakable;
    boolean breakBlock = false;
    private boolean isItem;
    private Rect hitBox;
    private Animation item;

    /* Constructors */
    public Block(){//creates empty block
        super();
        hitBox = new Rect(0, 0, 0, 0);
    }

    public Block(int posX, int posY) {
        super(posX, posY, Constants.SCREEN_WIDTH/20, Constants.SCREEN_HEIGHT/12);
        hitBox = new Rect(posX, posY + Constants.SCREEN_HEIGHT/12, posX + Constants.SCREEN_WIDTH/20, posY + Constants.SCREEN_HEIGHT/12 + 5);

        setIdle(Constants.brickBlock, 2);
        setMove1(Constants.hitBlock, 2);
        setMove2(Constants.afterBlock, 2);
        setItem(Constants.itemBlock, 2);

        setCurrentAnimation(getIdle());
        isItem(false);
        setBreakable(false);

    }

    public void setBreakable(boolean breakable){
        this.breakable = breakable;
    }

    public void setBreakBlock(boolean breakBlock){
        this.breakBlock = breakBlock;
    }

    public void isItem(boolean isItem){
        this.isItem = isItem;
    }

    public void setItem(Bitmap[] sprites, double aniTime){
        item = new Animation(sprites, aniTime);
    }//setItem

    public boolean isBreakable(){
        return breakable;
    }
    public boolean getBreakBlock(){
        return breakBlock;
    }

    public Rect getHitBox(){
        return hitBox;
    }

    public boolean isItem(){
        return isItem;
    }

    public Animation getItem(){
        return item;
    }//getItem

    public void empyHitBox(){
        hitBox = new Rect(0, 0, 0, 0);
    }

    @Override
    public void move(int x, int y) {
        super.move(x, y);

        if(!getRect().isEmpty()) {
            hitBox.offset(x, y);
        }//if
    }

    @Override
    public void setEmpty(){
        super.setEmpty();

        hitBox = new Rect(0, 0, 0, 0);
    }

    /*  */
}//class Blocks