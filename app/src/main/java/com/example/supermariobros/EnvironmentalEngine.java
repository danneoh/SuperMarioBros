package com.example.supermariobros;


import android.content.Context;
import android.graphics.Rect;

import java.util.Random;

public class EnvironmentalEngine extends Thread {

    private static World gameWorld;
    private MainActivity active;
    private Random r = new Random();

    int floor = Constants.SCREEN_HEIGHT - (3 * (Constants.SCREEN_HEIGHT/12));
    int gravity = 10;

    public EnvironmentalEngine(Context context, World game){
        gameWorld = game;
        active = (MainActivity) context;
    }

    /* Updating Methods */
    private void setPipeFloor(){
        if(Rect.intersects(gameWorld.getMario().getRect(), gameWorld.getPipe().getHitBox())){
            gameWorld.getMario().setFloor(gameWorld.getItemBLock(gameWorld.getMario().getPosInd()).getRect().top);
            gameWorld.getMario().setJumpHeight();
            gameWorld.getMario().getCurrentAnimation().play();}
        if(Rect.intersects(gameWorld.getMario().getRect(), gameWorld.getPipe().getRect())){
                gameWorld.getMario().move(-20,0);
            }
    }

    private void setFloor(){
        gameWorld.getMario().setPosInd(gameWorld.getMario().getRelPos() / ( Constants.SCREEN_WIDTH / 20));

        if(gameWorld.getGoomba() != null){
            gameWorld.getGoomba().setPosInd(gameWorld.getGoomba().getRelPos() / ( Constants.SCREEN_WIDTH / 20));
        }//if

        if(gameWorld.getPlant() != null){
            gameWorld.getPlant().setPosInd(gameWorld.getPlant().getRelPos() / ( Constants.SCREEN_WIDTH / 20));
        }//if

        //Mario Floor
        if((!gameWorld.getItemBLock(gameWorld.getMario().getPosInd()).getRect().isEmpty()) && (gameWorld.getMario().getRect().bottom <= gameWorld.getItemBLock(gameWorld.getMario().getPosInd()).getRect().top)){
            gameWorld.getMario().setFloor(gameWorld.getItemBLock(gameWorld.getMario().getPosInd()).getRect().top);
            gameWorld.getMario().setJumpHeight();
        }else if((!gameWorld.getFloor(gameWorld.getMario().getPosInd()).getRect().isEmpty())){
            gameWorld.getMario().setFloor(gameWorld.getFloor(gameWorld.getMario().getPosInd()).getRect().top);

            if((!gameWorld.getItemBLock(gameWorld.getMario().getPosInd()).getRect().isEmpty()) && (gameWorld.getMario().getRect().top >= gameWorld.getItemBLock(gameWorld.getMario().getPosInd()).getRect().bottom)){
                gameWorld.getMario().setJumpHeight(gameWorld.getItemBLock(gameWorld.getMario().getPosInd()).getRect().bottom);
            }else {
                gameWorld.getMario().setJumpHeight();
            }// if/else
        }else{
            gameWorld.getMario().setFloor(Constants.SCREEN_HEIGHT);
        }// if/elif/else

        //Goomba Floor
        if(!gameWorld.getGoomba().getRect().isEmpty()) {
            if ((!gameWorld.getItemBLock(gameWorld.getGoomba().getPosInd()).getRect().isEmpty()) && (gameWorld.getGoomba().getRect().bottom <= gameWorld.getItemBLock(gameWorld.getGoomba().getPosInd()).getRect().top)) {
                gameWorld.getGoomba().setFloor(gameWorld.getItemBLock(gameWorld.getGoomba().getPosInd()).getRect().bottom);
            } else if ((!gameWorld.getFloor(gameWorld.getGoomba().getPosInd()).getRect().isEmpty())) {
                gameWorld.getGoomba().setFloor(gameWorld.getFloor(gameWorld.getGoomba().getPosInd()).getRect().top);
            } else {
                gameWorld.getGoomba().setFloor(Constants.SCREEN_HEIGHT);
            }// if/elif/else
        }//if/**/

    }//SetFloor

    private void Gravity(){
        if (gameWorld.getMario().getRect().bottom < gameWorld.getMario().getFloor()) {
            gameWorld.getMario().move(0, gravity);

            if(gameWorld.getMario().isFalling() && (gameWorld.getMario().getRect().bottom >= gameWorld.getMario().getFloor())){
                gameWorld.getMario().setCurrentAnimation(gameWorld.getMario().getIdle());
                gameWorld.getMario().setFalling(false);
            }//if
        }//if

        if (gameWorld.getMario().isJumping()) {
            gameWorld.getMario().setCurrentAnimation(gameWorld.getMario().getJump());
            gameWorld.getMario().getCurrentAnimation().play();
            gameWorld.getMario().move(0, -200);
            if (gameWorld.getMario().getRect().bottom <= gameWorld.getMario().getJumpHeight()) {
                gameWorld.getMario().setFalling(true);
                gameWorld.getMario().setJumping(false);
            }//if
        }//if
    }//Gravity

    private void CoinBehaviour(){
        if(!gameWorld.getCoin(gameWorld.getMario().getPosInd()).getRect().isEmpty()
                && Rect.intersects(gameWorld.getCoin(gameWorld.getMario().getPosInd()).getRect(),gameWorld.getMario().getRect())){

            gameWorld.getCoin(gameWorld.getMario().getPosInd()).setEmpty();
            gameWorld.getMario().addPoints(200);
            UpdateView();
        }//if
    }//CoinBehaviour

    private void BlockBehaviour(){
        if(!gameWorld.getItemBLock(gameWorld.getMario().getPosInd()).getRect().isEmpty()
                && (gameWorld.getItemBLock(gameWorld.getMario().getPosInd()).getBreakBlock() || gameWorld.getMario().isJumping())) {

            if (!gameWorld.getItemBLock(gameWorld.getMario().getPosInd()).getHitBox().isEmpty()
                    && Rect.intersects(gameWorld.getItemBLock(gameWorld.getMario().getPosInd()).getHitBox(), gameWorld.getMario().getRect())) {

                if (gameWorld.getMario().isSuperMario() && gameWorld.getItemBLock(gameWorld.getMario().getPosInd()).isBreakable()) {


                    gameWorld.getItemBLock(gameWorld.getMario().getPosInd()).setEmpty();
                    gameWorld.getMario().addPoints(10);
                    UpdateView();
                } else if (gameWorld.getItemBLock(gameWorld.getMario().getPosInd()).isItem()) {
                    gameWorld.getItemBLock(gameWorld.getMario().getPosInd()).empyHitBox();
                    gameWorld.getItemBLock(gameWorld.getMario().getPosInd()).setCurrentAnimation(gameWorld.getItemBLock(gameWorld.getMario().getPosInd()).getMove2());
                    //Setting power Item
                    gameWorld.setPow(gameWorld.getMario().getPosInd(), r.nextInt(2),
                            gameWorld.getItemBLock(gameWorld.getMario().getPosInd()).getPosX(),
                            gameWorld.getItemBLock(gameWorld.getMario().getPosInd()).getPosY()  -  gameWorld.getItemBLock(gameWorld.getMario().getPosInd()).getHeight()/**/);
                    //------------------
                }
            }
        }//if
    }//BlockBehaviour

    private void PiranhaBehaviour(){

        if(Rect.intersects(gameWorld.getPlant().getHitBox(), gameWorld.getMario().getRect())){
            gameWorld.getPlant().move(0, 100);
        }//if
        else if(Rect.intersects(gameWorld.getPlant().getRect(), gameWorld.getMario().getRect())){
            if(gameWorld.getMario().isInvMario()){
                gameWorld.getPlant().setEmpty();
                gameWorld.getMario().addPoints(10);
                UpdateView();
            }else if(gameWorld.getMario().isSuperMario()){
                gameWorld.getMario().move(-200, 0);
                gameWorld.getMario().setSuperMario(false);
                gameWorld.getMario().updateSprites();
            }else{
                if(gameWorld.getMario().getLives() > 1){
                    gameWorld.getMario().move(-20, 0);
                    gameWorld.getMario().addLives(-1);
                    UpdateView();
                }else{
                    gameWorld.getMario().setCurrentAnimation(gameWorld.getMario().getDamage());
                    gameWorld.getMario().getCurrentAnimation().play();
                    gameWorld.setEndGame(true);
                }// if/else

            }// if/elif/else
        }// if/elif
    }//PiranhaBehaviour

    private void GoombaBehaviour() {
        if(!gameWorld.getGoomba().getRect().isEmpty()) {
            if (gameWorld.getGoomba().getPosInd() <= 5) {
                gameWorld.getGoomba().setMoveLeft(false);
            } else if (gameWorld.getGoomba().getPosInd() >= gameWorld.getLevelLength() - 5) {
                gameWorld.getGoomba().setMoveLeft(true);
            }// if/elif
        }

        if(gameWorld.getGoomba().isMoveLeft()){
            gameWorld.getGoomba().move(-1, 0);
        }else{
            gameWorld.getGoomba().move(1, 0);
        }// if/elif

        if(Rect.intersects(gameWorld.getGoomba().getHitBox(), gameWorld.getMario().getRect())){
            gameWorld.getGoomba().setEmpty();
            gameWorld.getMario().addPoints(10);
            UpdateView();
        }else if(Rect.intersects(gameWorld.getGoomba().getRect(), gameWorld.getMario().getRect())){
            gameWorld.getGoomba().setMoveLeft(false);
            if(gameWorld.getMario().isInvMario()){
                gameWorld.getGoomba().setEmpty();
                gameWorld.getMario().addPoints(10);
                UpdateView();
            }else if(gameWorld.getMario().isSuperMario()){
                gameWorld.getMario().move(-20, 0);
                gameWorld.getMario().setSuperMario(false);
                gameWorld.getMario().updateSprites();
            }else{
                if(gameWorld.getMario().getLives() > 1){
                    gameWorld.getMario().move(-20, 0);
                    gameWorld.getMario().addLives(-1);
                    UpdateView();
                }else{
                    gameWorld.getMario().setCurrentAnimation(gameWorld.getMario().getDamage());
                    gameWorld.getMario().getCurrentAnimation().play();
                    gameWorld.setEndGame(true);
                }// if/else

            }// if/elif/else
        }// if/elif
    }//GoombaBehaviour

    private void InMarioBehaviour(){
        if(gameWorld.getMario().isInvMario()){
            if((System.currentTimeMillis() - gameWorld.getMario().getInTimer()) >= 5000){
                gameWorld.getMario().setInvMario(false);
                gameWorld.getMario().updateSprites();
            }//if
        }//if
    }//InMarioBehaviour

    public void PowerBehaviour(){
        for(int c = 0; c < gameWorld.getLevelLength(); c++){
            if((gameWorld.getPow(c) != null) && !gameWorld.getPow(c).getRect().isEmpty()){
                if(Rect.intersects(gameWorld.getPow(c).getRect(), gameWorld.getMario().getRect())){

                    if (gameWorld.getPow(c) instanceof Mushroom) {
                        gameWorld.getMario().addPoints(1000);
                        if(gameWorld.getMario().isSuperMario()){
                            gameWorld.getMario().addLives(1);
                        }else{
                            gameWorld.getMario().setSuperMario(true);
                            gameWorld.getMario().updateSprites();
                        }// if/else
                        UpdateView();
                    }else if(gameWorld.getPow(c) instanceof Star){
                        gameWorld.getMario().addPoints(1000);
                        gameWorld.getMario().setInvMario(true);
                        gameWorld.getMario().updateSprites();
                        UpdateView();
                    }// if/elif

                    gameWorld.getPow(c).setEmpty();
                }//if
            }//if
        }//for
    }//PowerBehaviour

    private void UpdateView(){
        active.getScoreView().setText("Points: " + Integer.toString(gameWorld.getMario().getPoints()));
        active.getLifeView().setText("Lives: " + Integer.toString(gameWorld.getMario().getLives()));
    }//UpdateView

    private void CheckEndLevel(){
        if(gameWorld.getMario().getPosInd() >= gameWorld.getLevelLength()- 5){
            if(gameWorld.getCurrentLevel() < 2) {
                gameWorld.setNextLevel(true);
            }else{
                gameWorld.setEndGame(true);
            }// if/else
        }//if
    }//CheckEndLevel

    private void CheckFall(){
        if(gameWorld.getMario().getRect().bottom >= (Constants.SCREEN_HEIGHT - Constants.SCREEN_HEIGHT / 12)){
            gameWorld.getMario().faceRight(true);
            if(gameWorld.getMario().isSuperMario()){
                gameWorld.getMario().setSuperMario(false);
            }//if

            if(gameWorld.getMario().isInvMario()){
                gameWorld.getMario().setInvMario(false);
            }//if

            gameWorld.getMario().updateSprites();

            if(gameWorld.getMario().getLives() > 1) {
                gameWorld.getMario().addLives(-1);
                UpdateView();
                gameWorld.setResetLevel(true);
            }else{
                gameWorld.setEndGame(true);
                gameWorld.getMario().setCurrentAnimation(gameWorld.getMario().getDamage());
                gameWorld.getMario().getCurrentAnimation().play();
            }// if/else
        }//if
    }//CheckFall

    public void run(){
        //boolean onTheFloor = true;
        int time = 0;
        // int jumpHeight = Constants.SCREEN_HEIGHT - (4 * (Constants.SCREEN_HEIGHT/12)) -  gameWorld.getMario().getHeight();
        do{
            try {
                setFloor();
                setPipeFloor();
                InMarioBehaviour();
                Gravity();
                CheckFall();
                PiranhaBehaviour();
                GoombaBehaviour();
                BlockBehaviour();
                CoinBehaviour();
                PowerBehaviour();

                //gameWorld.getPlant().setPiranhaHeight();
                if(gameWorld.getPlant().isMovingUp()){
                    gameWorld.getPlant().move(0,-1);
                    if(gameWorld.getPlant().getRect().bottom <= gameWorld.getPlant().getPlantHeight()){
                        gameWorld.getPlant().setMovingUp(false);
                    }//if
                }//if
                else{
                    gameWorld.getPlant().move(0,1);
                    if(gameWorld.getPlant().getRect().bottom >= gameWorld.getPlant().getPiranhaFloor()){
                        gameWorld.getPlant().setMovingUp(true);
                    }//if
                }//else



                System.out.println("Postion Index: " + gameWorld.getMario().getPosInd());
                System.out.println("Jump Height: " + gameWorld.getMario().getJumpHeight());
                System.out.println("Level WIdth: " + gameWorld.getLevelWidth());
                System.out.println("RelPos: " + gameWorld.getMario().getRelPos());

                CheckEndLevel();

                sleep(25);
            }catch(InterruptedException e){e.printStackTrace();}
        }while(!gameWorld.isNextLevel() && !gameWorld.isEndGame());//do

    }//end run

}