package com.example.supermariobros;

import android.graphics.Canvas;
import android.graphics.Rect;

public class World {
    private Mario mario;
    private Goomba goomba;
    boolean checkGoomba = true;
    private piranhaPlant plant;
    private boolean checkPlant = true;
    private Pipe pipe;
    private boolean checkPipe = true;
    private Block[] floor;
    private Block[] item;
    private Coins[] coin;
    private Items[] pow;
    private boolean[] checkPow;
    private int blockWidth = Constants.SCREEN_WIDTH/20;
    private int currentLevel;
    private int levelWidth;
    private Rect frame = new Rect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

    private boolean nextLevel = false;
    private boolean resetLevel = false;
    private boolean endGame = false;

    /* Temp var */
    boolean check = true;
    boolean what = true;
    int time = 0;
    // Items mushroom;

    /*----------*/

    /* Constructors */
    public World(){//sets up level 0 automatically
        mario = new Mario(0, (Constants.SCREEN_HEIGHT - (6 * (Constants.SCREEN_HEIGHT/12))));
        mario.setSuperMario(true);
        mario.setInvMario(true);
        mario.updateSprites();
        mario.getCurrentAnimation().play();
        currentLevel = 0;
        levelSetup(0);

    }

    public World(int n){
        mario = new Mario(0, (Constants.SCREEN_HEIGHT - (6 * (Constants.SCREEN_HEIGHT/12))));
        mario.setSuperMario(false);
        mario.setInvMario(false);
        mario.updateSprites();
        mario.getCurrentAnimation().play();
        currentLevel = n;
        levelSetup(n);
    }

    public World(int n, Mario mario){
        this.mario = new Mario(0, (Constants.SCREEN_HEIGHT - (6 * (Constants.SCREEN_HEIGHT/12))), mario);
        mario.getCurrentAnimation().play();
        currentLevel = n;
        levelSetup(n);
    }

    private void levelSetup(int n){
        levelWidth = (n+1) * Constants.SCREEN_WIDTH;

        floor = new Block[(n+1)*20];
        item = new Block[(n+1)*20];
        coin = new Coins[(n+1) * 20];
        pow = new Items[(n+1)*20];
        checkPow = new boolean[(n+1)*20];

        switch (n){
            case 0:
                goomba = new Goomba(Constants.SCREEN_WIDTH - (2 * (Constants.SCREEN_WIDTH/20)), (Constants.SCREEN_HEIGHT -(4 * (Constants.SCREEN_HEIGHT/12))));
                plant = new piranhaPlant(-530+Constants.SCREEN_WIDTH - (2 * (Constants.SCREEN_WIDTH/20)), (Constants.SCREEN_HEIGHT -(9 * (Constants.SCREEN_HEIGHT/24))));
                pipe = new Pipe(-600+(Constants.SCREEN_WIDTH - (2 * (Constants.SCREEN_WIDTH/20))), (Constants.SCREEN_HEIGHT -(9 * (Constants.SCREEN_HEIGHT/24))));

                for(int c = 0; c < floor.length; c++) {
                    floor[c] = new Block(c * blockWidth, (Constants.SCREEN_HEIGHT - (3 * (Constants.SCREEN_HEIGHT / 12))));
                }//for

                for(int i = 0; i < item.length; i++){
                    checkPow[i] = true;

                    if(i < 8 || i > 10) {
                        item[i] = new Block();
                        coin[i] = new Coins();
                    }else{
                        item[i] = new Block(i * blockWidth, (Constants.SCREEN_HEIGHT - (6 * (Constants.SCREEN_HEIGHT / 12))));
                        coin[i] = new Coins(i * blockWidth, (Constants.SCREEN_HEIGHT - (7 * (Constants.SCREEN_HEIGHT / 12))));
                        if(9 == i){
                            item[i].setCurrentAnimation(item[i].getItem());
                            item[i].isItem(true);
                        }else{
                            item[i].setBreakable(true);
                        }// if/else
                    }// if/else
                }//for

                break;
            case 1:
                goomba = new Goomba(Constants.SCREEN_WIDTH - (2 * (Constants.SCREEN_WIDTH/20)), (Constants.SCREEN_HEIGHT -(4 * (Constants.SCREEN_HEIGHT/12))));
                goomba.getCurrentAnimation().play();
                plant = new piranhaPlant(-130+Constants.SCREEN_WIDTH - (2 * (Constants.SCREEN_WIDTH/20)), (Constants.SCREEN_HEIGHT -(9 * (Constants.SCREEN_HEIGHT/24))));
                plant.getCurrentAnimation().play();
                pipe = new Pipe(-200+(Constants.SCREEN_WIDTH - (2 * (Constants.SCREEN_WIDTH/20))), (Constants.SCREEN_HEIGHT -(9 * (Constants.SCREEN_HEIGHT/24))));

                for(int c = 0; c < floor.length; c++){
                    checkPow[c] = true;

                    //Setting up Floor
                    if((17 == c) || (24 < c && c < 27)){
                        floor[c] = new Block();
                    }else{
                        floor[c] = new Block(c * blockWidth, (Constants.SCREEN_HEIGHT - (3 * (Constants.SCREEN_HEIGHT / 12))));
                    }// if/else

                    //Setting up Air Blocks
                    if((14 < c && c < 18) || (22 < c && c < 27)){
                        item[c] = new Block(c * blockWidth, (Constants.SCREEN_HEIGHT - (6 * (Constants.SCREEN_HEIGHT / 12))));

                        if(16 == c){
                            item[c].setCurrentAnimation(item[c].getItem());
                            item[c].isItem(true);

                        }else{
                            item[c].setBreakable(true);
                        }// if/else

                    }else{
                        item[c] = new Block();
                    }// if/else

                    //Setting up Coins Position
                    if(15 < c && c < 19){
                        coin[c] = new Coins(c * blockWidth, (Constants.SCREEN_HEIGHT - (7 * (Constants.SCREEN_HEIGHT / 12))));
                    }else if(28 <= c && c < 35){
                        coin[c] = new Coins(c * blockWidth, (Constants.SCREEN_HEIGHT - (4 * (Constants.SCREEN_HEIGHT / 12))));
                    }else{
                        coin[c] = new Coins();
                    }// if/elif/else
                }//for

                break;
            case 2:

                goomba = new Goomba(Constants.SCREEN_WIDTH - (2 * (Constants.SCREEN_WIDTH/20)), (Constants.SCREEN_HEIGHT -(4 * (Constants.SCREEN_HEIGHT/12))));
                goomba.getCurrentAnimation().play();
                plant = new piranhaPlant(2070+Constants.SCREEN_WIDTH - (2 * (Constants.SCREEN_WIDTH/20)), (Constants.SCREEN_HEIGHT -(9 * (Constants.SCREEN_HEIGHT/24))));
                plant.getCurrentAnimation().play();
                pipe = new Pipe(-800+(Constants.SCREEN_WIDTH - (2 * (Constants.SCREEN_WIDTH/20))), (Constants.SCREEN_HEIGHT -(9 * (Constants.SCREEN_HEIGHT/24))));
                //pipe.getCurrentAnimation().play();

                for(int c = 0; c < floor.length; c++){
                    checkPow[c] = true;

                    //Setting up Floor
                    if( (25 < c && c < 30)){
                        floor[c] = new Block();
                    }else if(30 < c && c < 50){
                        floor[c] = new Block(c * blockWidth, (Constants.SCREEN_HEIGHT - (4 * (Constants.SCREEN_HEIGHT / 12))));
                    }else{
                        floor[c] = new Block(c * blockWidth, (Constants.SCREEN_HEIGHT - (3 * (Constants.SCREEN_HEIGHT / 12))));
                    }// if/else

                    //Setting up Air Blocks
                    if((11 < c && c < 15) || (22 < c && c < 27)) {
                        item[c] = new Block(c * blockWidth, (Constants.SCREEN_HEIGHT - (6 * (Constants.SCREEN_HEIGHT / 12))));
                    }else if((26 < c && c < 34) || (40 < c && c < 44)) {
                        item[c] = new Block((c * blockWidth), (Constants.SCREEN_HEIGHT - (8 * (Constants.SCREEN_HEIGHT / 12))));

                    }else{
                        item[c] = new Block();
                    }// if/else

                    if((13 == c) || (42 == c)){
                        item[c].setCurrentAnimation(item[c].getItem());
                        item[c].isItem(true);

                    }else{
                        item[c].setBreakable(true);
                    }// if/else

                    //Setting up Coins Position
                    if((11 < c && c < 15) || (22 < c && c < 27)){
                        coin[c] = new Coins(c * blockWidth, (Constants.SCREEN_HEIGHT - (7 * (Constants.SCREEN_HEIGHT / 12))));
                    }else if((28 <= c && c < 34) || (40 < c && c < 44)){
                        coin[c] = new Coins(c * blockWidth, (Constants.SCREEN_HEIGHT - (9 * (Constants.SCREEN_HEIGHT / 12))));
                    }else{
                        coin[c] = new Coins();
                    }// if/elif/else
                }//for

                break;
        }//switch

    }//levelSetup

    public void draw(Canvas cam){
        for(int c = 0; c < floor.length; c++){

            //Prints Floor
            if(Rect.intersects(floor[c].getRect(), frame) && (!floor[c].getRect().isEmpty())) {
                if(!floor[c].getCurrentAnimation().isPlaying()) {
                    floor[c].getCurrentAnimation().play();
                }//if
                floor[c].getCurrentAnimation().draw(cam, floor[c].getRect());
            }else if(!floor[c].getRect().isEmpty()){
                floor[c].getCurrentAnimation().stop();
            }// if/elif

            //Prints Block in air
            if(Rect.intersects(item[c].getRect(), frame) && (!item[c].getRect().isEmpty())) {
                if(!item[c].getCurrentAnimation().isPlaying()) {
                    item[c].getCurrentAnimation().play();
                }//if
                item[c].getCurrentAnimation().draw(cam, item[c].getRect());
            }else if(!item[c].getRect().isEmpty()){
                item[c].getCurrentAnimation().stop();
            }// if/elif

            //print Coins
            if(Rect.intersects(coin[c].getRect(), frame) && (!coin[c].getRect().isEmpty())) {
                if(!coin[c].getCurrentAnimation().isPlaying()) {
                    coin[c].getCurrentAnimation().play();
                }//if
                coin[c].getCurrentAnimation().draw(cam, coin[c].getRect());
            }else if(!coin[c].getRect().isEmpty()){
                coin[c].getCurrentAnimation().stop();
            }// if/elif

            //Printing Power Item
            if(pow[c] != null) {
                if (!Rect.intersects(pow[c].getRect(), frame)) {
                    checkPow[c] = true;
                    pow[c].getCurrentAnimation().stop();
                } else {
                    if (checkPow[c]) {
                        pow[c].getCurrentAnimation().play();
                        checkPow[c] = false;
                    }//if
                    pow[c].getCurrentAnimation().draw(cam, pow[c].getRect());
                }// if/else
            }//if

        }//for

        //Printing Goomba
        if(goomba != null) {
            if (!Rect.intersects(goomba.getRect(), frame)) {
                checkGoomba = true;
                goomba.getCurrentAnimation().stop();
            } else {
                if (checkGoomba) {
                    goomba.getCurrentAnimation().play();
                    checkGoomba = false;
                }//if
                goomba.getCurrentAnimation().draw(cam, goomba.getRect());
            }// if/else
        }//if

        //Printing plant
        if(plant != null){
            if(!Rect.intersects(plant.getRect(), frame)){
                checkPlant = true;
                plant.getCurrentAnimation().stop();
            }else{
                if(checkPlant){
                    plant.getCurrentAnimation().play();
                    checkPlant = false;
                }//if
                plant.getCurrentAnimation().draw(cam, plant.getRect());
            }// if/else
        }//if

        //Printing Pipe
        if(pipe != null){
            if (!Rect.intersects(pipe.getRect(), frame)) {
                checkPipe = true;
                pipe.getCurrentAnimation().stop();
            } else {
                if (checkPipe) {
                    pipe.getCurrentAnimation().play();
                    checkPipe = false;
                }//if
                pipe.getCurrentAnimation().draw(cam, pipe.getRect());
            }// if/else
        }//if

        mario.getCurrentAnimation().draw(cam, mario.getRect());
    }

    public void setNextLevel(boolean nextLevel){
        this.nextLevel = nextLevel;
    }

    public void setResetLevel(boolean resetLevel){
        this.resetLevel = resetLevel;
    }

    public void setEndGame(boolean endGame){
        this.endGame = endGame;
    }

    public void setPow(int index, int power, int posX, int posY){
        switch (power){
            case 0:
                pow[index] = new Mushroom(posX, posY);
                break;
            case 1:
                pow[index] = new Star(posX, posY);
                break;
        }//switch
    }//setPow

    public Mario getMario(){
        return mario;
    }//getMario

    public Goomba getGoomba(){
        return goomba;
    }//getGoomba

    public piranhaPlant getPlant(){
        return plant;
    }//getPlant

    public Pipe getPipe(){
        return pipe;
    }//Pipe

    public int getCurrentLevel(){
        return currentLevel;
    }//getCurrentLevel

    public int getLevelWidth(){
        return levelWidth;
    }

    public boolean isNextLevel(){
        return nextLevel;
    }

    public boolean isLevelReset(){
        return resetLevel;
    }

    public boolean isEndGame(){
        return endGame;
    }

    public int getLevelLength(){
        return floor.length;
    }

    public Block getFloor(int index){
        return floor[index];
    }//getFloor

    public Block getItemBLock(int index){
        return item[index];
    }//get

    public Coins getCoin(int index){
        return coin[index];
    }

    public Items getPow(int index){
        return pow[index];
    }

    public void shiftWorld(int shift){


        for(int s = 0; s < floor.length; s++){
            floor[s].move(shift, 0);
            item[s].move(shift, 0);
            coin[s].move(shift,0);

            if(pow[s] != null){
                pow[s].move(shift, 0);
            }//if
        }//for

        if(goomba != null){
            goomba.move(shift, 0);
        }//if

        if(plant != null){
            plant.move(shift, 0);
        }//if
        if(pipe != null){
            pipe.move(shift, 0);
        }

    }//shiftWorld

}// class World

/* EOF */