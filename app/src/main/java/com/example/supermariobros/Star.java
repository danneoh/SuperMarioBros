package com.example.supermariobros;

public class Star extends Items {
    public Star(){
        super(0,0,0,0);
    }

    public Star(int posX, int posY){
        super(posX, posY, Constants.SCREEN_WIDTH/20, Constants.SCREEN_HEIGHT/12);

        setMove1(Constants.starRun, 0.5);
        setCurrentAnimation(getMove1());
    }
}
