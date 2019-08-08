package com.example.supermariobros;
import android.graphics.Bitmap;

public class Mario extends Entity {
    private int marioHeight = Constants.SCREEN_HEIGHT/12;
    private int superHeight = Constants.SCREEN_HEIGHT/6;
    //  private int relPos;
    private int lives;
    private int points;
    private int jumpHeight;
    private float inTimer;
    private boolean superMario;
    private boolean invMario;
    private boolean faceRight;
    private boolean jumping;
    private Animation idleLeft;
    private Animation damage;
    private Animation jump;
    private Animation jumpLeft;

    //Constructors
    public Mario(int posX, int posY){
        super(posX, posY, Constants.SCREEN_WIDTH/20, Constants.SCREEN_HEIGHT/12);

        faceRight(true);
        setSuperMario(false);
        setInvMario(false);
        setLives(3);
        setPoints(0);
        setRelPos(posX + (Constants.SCREEN_WIDTH/20)/2);
        setFloor(posY + (Constants.SCREEN_HEIGHT/12));
        setJumpHeight();
        setIdle(Constants.marioIdle, 2);
        setIdleLeft(Constants.marioIdleLeft, 2);
        setMove1(Constants.marioWalk, 0.25);
        setMove2(Constants.marioWalkLeft, 0.25);
        setDamage(Constants.marioHit, 2);
        setJump(Constants.marioJump, 2);
        setJumpLeft(Constants.marioJumpLeft, 2);

        setCurrentAnimation(getIdle());
    }

    public Mario(int posX, int posY, int lives){
        super(posX, posY, Constants.SCREEN_WIDTH/20, Constants.SCREEN_HEIGHT/12);

        if(lives > 0) {
            setLives(lives);
        }else{
            setLives(1);
        }// if/else

        setPoints(0);
        faceRight(true);
        setSuperMario(false);
        setInvMario(false);
        setRelPos(posX + (Constants.SCREEN_WIDTH/20)/2);
        setFloor(posY + (Constants.SCREEN_HEIGHT/12));
        setJumpHeight();
        setIdle(Constants.marioIdle, 2);
        setIdleLeft(Constants.marioIdleLeft, 2);
        setMove1(Constants.marioWalk, 0.25);
        setMove2(Constants.marioWalkLeft, 0.25);
        setDamage(Constants.marioHit, 2);
        setJump(Constants.marioJump, 2);
        setJumpLeft(Constants.marioJumpLeft, 2);

        setCurrentAnimation(getIdle());
    }

    public Mario(int posX, int posY, Mario mario){
        super(posX, posY, Constants.SCREEN_WIDTH/20, mario.getHeight());

        setLives(mario.getLives());
        setPoints(mario.getPoints());
        faceRight(true);
        setSuperMario(mario.isSuperMario());
        setInvMario(mario.isInvMario());
        setRelPos(posX + (Constants.SCREEN_WIDTH/20)/2);
        setFloor(posY + (Constants.SCREEN_HEIGHT/12));
        setJumpHeight();
        setIdle(mario.getIdle());
        setIdleLeft(mario.getRigtIdle());
        setMove1(mario.getMove1());
        setMove2(mario.getMove2());
        setDamage(mario.getDamage());
        setJump(mario.getJumpRight());
        setJumpLeft(mario.getJumpLeft());

        setCurrentAnimation(getIdle());
    }


    /* Private Methods */

    //Setters
  /*  private void setRelPos(int pos){
        relPos = pos;
    }/**/

    private void setDamage(Bitmap[] sprites, double aniTime){
        damage = new Animation(sprites, aniTime);
    }//setMiniHurt

    private void setDamage(Animation damge){
        this.damage = damge;
    }//copy setDamge

    private void setIdleLeft(Bitmap[] sprites, double aniTime){
        idleLeft = new Animation(sprites, aniTime);
    }//setIdleLeft

    private void setIdleLeft(Animation idleLeft){
        this.idleLeft = idleLeft;
    }//copy setIdleLeft

    private void setJump(Bitmap[] sprites, double aniTime){
        jump = new Animation(sprites, aniTime);
    }//setJump

    private void setJump(Animation jump){
        this.jump = jump;
    }//copy setJump

    private void setJumpLeft(Bitmap[] sprites, double aniTime){
        jumpLeft = new Animation(sprites, aniTime);
    }//setJumpLeft

    private void setJumpLeft(Animation jumpLeft){
        this.jumpLeft = jumpLeft;
    }//copy setJumpLeft

    public void setJumpHeight(){
        jumpHeight = getFloor() - (4 * (Constants.SCREEN_HEIGHT/12));
    }

    public void setJumpHeight(int jumpHeight){
        this.jumpHeight = jumpHeight;
    }

    public void setSuperMario(boolean superMario){
        this.superMario = superMario;
    }// setSuperMario

    public void setInvMario(boolean invMario){
        if(invMario){
            setInTimer(System.currentTimeMillis());
        }//if

        this.invMario = invMario;
    }// setInvMario

    private void setPoints(int points){
        this.points = points;
    }

    /* End Private Methods */

    public boolean isJumping(){
        return jumping;
    }

    public void setJumping(boolean jumping){
        this.jumping = jumping;
    }

    /* Public Methods */

    //Setter Methods
    public void faceRight(boolean face){
        faceRight = face;
    }//faceRight

    public void setLives(int lives){
        this.lives = lives;
    }// setLives

    public void setInTimer(float inTimer){
        this.inTimer = inTimer;
    }

    //Getter methods
   /* public int getRelPos(){
        return relPos;
    }/**/

    public Animation getDamage(){
        return damage;
    }// getMiniHurt

    public int getLives(){
        return lives;
    }// getLives

    public boolean isSuperMario(){
        return superMario;
    }// isSuperMario

    public boolean isInvMario(){
        return invMario;
    }// isInvMario

    public boolean isRightFace(){
        return faceRight;
    }//isRightFace

    public int getPoints(){
        return points;
    }

    public Animation getJump(){
        if(isRightFace()){
            return jump;
        }else{
            return jumpLeft;
        }// if/else
    }

    public int getJumpHeight(){
        return jumpHeight;
    }

    public Animation getRigtIdle(){
        return super.getIdle();
    }

    public Animation getIdleLeft(){
        return idleLeft;
    }

    public Animation getJumpRight(){
        return jump;
    }

    public Animation getJumpLeft(){
        return jumpLeft;
    }

    public float getInTimer(){
        return inTimer;
    }

    @Override
    public Animation getIdle(){
        if(isRightFace()){
            return super.getIdle();
        }else{
            return idleLeft;
        }// if/else
    }//getIdle

    //Action&Events
    public void addPoints(int points){
        this.points += points;
    }

    public void addLives(int lives){
        this.lives += lives;
    }

   /* public void moveRelPos(int x){
        addRelPos(x);
    }/
    @Override
    public void move(int x, int y){
        super.move(x, y);
        moveRelPos(x);
    }/**/

    public void updateSprites(){
        if(isInvMario()){
            turnInv();

        }else if(isSuperMario()){
            turnSuper();
        }else{
            turnMini();
        }// if/elif/else

    }//updateSprites

    private void turnInv(){
        if(isSuperMario()){
            setRect(getPosX(), getPosY() - marioHeight, getWidth(), superHeight);
            setIdle(Constants.superInIdle, 2);
            setIdleLeft(Constants.superInIdleLeft, 2);
            setMove1(Constants.superInWalk, 0.25);
            setMove2(Constants.superInWalkLeft, 0.25);
            setDamage(Constants.superHit, 0.25);
            setJump(Constants.superJump, 2);
            setJumpLeft(Constants.superJumpLeft, 2);

            setCurrentAnimation(getIdle());
            getCurrentAnimation().play();
        }else{
            setRect(getPosX(), getPosY() + marioHeight, getWidth(), Constants.SCREEN_HEIGHT/12);
            setIdle(Constants.inIdle, 2);
            setIdleLeft(Constants.inIdleLeft, 2);
            setMove1(Constants.inWalk, 0.25);
            setMove2(Constants.inWalkLeft, 0.25);
            setDamage(Constants.marioHit, 2);
            setJump(Constants.marioJump, 2);
            setJumpLeft(Constants.marioJumpLeft, 2);

            setCurrentAnimation(getIdle());
            getCurrentAnimation().play();
        }
    }//turnInv

    private void turnSuper(){
        //     setSuperMario(true);
        setRect(getPosX(), getPosY() - marioHeight, getWidth(), superHeight);
        setIdle(Constants.superIdle, 2);
        setIdleLeft(Constants.superIdleLeft, 2);
        setMove1(Constants.superWalk, 0.25);
        setMove2(Constants.superWalkLeft, 0.25);
        setDamage(Constants.superHit, 0.25);
        setJump(Constants.superJump, 2);
        setJumpLeft(Constants.superJumpLeft, 2);

        setCurrentAnimation(getIdle());
        getCurrentAnimation().play();
    }//turnSuper

    private void turnMini(){
        //       setSuperMario(false);
        setRect(getPosX(), getPosY() + marioHeight, getWidth(), Constants.SCREEN_HEIGHT/12);
        setIdle(Constants.marioIdle, 2);
        setIdleLeft(Constants.marioIdleLeft, 2);
        setMove1(Constants.marioWalk, 0.25);
        setMove2(Constants.marioWalkLeft, 0.25);
        setDamage(Constants.marioHit, 2);
        setJump(Constants.marioJump, 2);
        setJumpLeft(Constants.marioJumpLeft, 2);

        setCurrentAnimation(getIdle());
        getCurrentAnimation().play();

    }//turnMini

    /* End Public Methods */
}// class Mario

/* EOF */