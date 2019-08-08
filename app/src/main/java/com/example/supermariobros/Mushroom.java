package com.example.supermariobros;

public class Mushroom extends Items{

    public Mushroom(){
        super(0,0,0,0);
    }

    public Mushroom(int posX, int posY){
        super(posX, posY, Constants.SCREEN_WIDTH/20, Constants.SCREEN_HEIGHT/12);

        setMove1(Constants.mushroomRun, 0.5);
        setCurrentAnimation(getMove1());
    }
}