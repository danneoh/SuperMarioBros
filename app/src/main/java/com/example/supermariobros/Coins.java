package com.example.supermariobros;

public class Coins extends Items {

    public Coins(){
        super();
    }
    public Coins(int posX, int posY){
        super(posX, posY, Constants.SCREEN_WIDTH/20, Constants.SCREEN_HEIGHT/12);

        setMove1(Constants.coinSpin, 0.5);
        setCurrentAnimation(getMove1());
    }
}
