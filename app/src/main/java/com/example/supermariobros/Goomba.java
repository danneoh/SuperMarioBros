package com.example.supermariobros;

public class Goomba extends Enemy {
    private boolean moveLeft = true;

    public Goomba( int posX, int posY){
        super(posX, posY, Constants.SCREEN_WIDTH/20, Constants.SCREEN_HEIGHT/12);

        setHitBox(posX + getWidth()/4,posY - 10,posX + getWidth() - getWidth()/4, posY);
        setMove1(Constants.goomWalk, 0.5);
        setIdle(Constants.goomSquish, 2);
        setCurrentAnimation(getMove1());
    }

    public void setMoveLeft(boolean moveLeft){
        this.moveLeft = moveLeft;
    }

    public boolean isMoveLeft(){
        return moveLeft;
    }


}// class Goomba

/* EOF */