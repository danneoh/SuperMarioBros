package com.example.supermariobros;

import android.content.ComponentName;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

public class MainActivity extends AppCompatActivity {
    Matrix reflect = new Matrix();

    int blockWidth;
    int blockHeight;

    int goomWidth;
    int goomHeight;

    int pipeHeight;
    int pipeWidth;

    int plantWidth;
    int plantHeight;

    int coinWidth;
    int coinHeight;

    int starWidth;
    int starHeight;

    int marioWidth;
    int marioHeight;

    Button rightButton;
    Button leftButton;
    Button jumpButton;

    TextView scoreView;
    TextView lifeView;
    TextView levelView;

    public TextView getScoreView(){
        return scoreView;
    }

    public TextView getLifeView(){
        return lifeView;
    }

    public TextView getLevelView(){
        return levelView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(SCREEN_ORIENTATION_LANDSCAPE);
        scoreView = findViewById(R.id.points);
        scoreView.setText("Points: " + Integer.toString(Camera.getWorld().getMario().getPoints()));
        lifeView = findViewById(R.id.life);
        lifeView.setText("Lives: " + Integer.toString(Camera.getWorld().getMario().getLives()));
        levelView = findViewById(R.id.level);
        levelView.setText("Level: "+ Integer.toString((Camera.getWorld().getCurrentLevel())));
        reflect.preScale(-1, 1);

        Constants.CURRENT_CONTEXT = this;

        rightButton = findViewById(R.id.rightButton);
        rightButton.setOnTouchListener(new View.OnTouchListener() {
            private Handler mHandler;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    if(mHandler != null) return true;
                    mHandler = new Handler();
                    mHandler.postDelayed(mAction, 50);
                    return true;
                }
                else if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    if(mHandler == null) return true;
                    mHandler.removeCallbacks(mAction);
                    //Camera.getMario().getMove1().stop();
                    if(!Camera.getWorld().getMario().isFalling() && !Camera.getWorld().isEndGame()) {
                        Camera.getWorld().getMario().getCurrentAnimation().stop();
                        Camera.getWorld().getMario().setCurrentAnimation(Camera.getWorld().getMario().getIdle());
                        Camera.getWorld().getMario().getCurrentAnimation().play();
                    }
                    mHandler = null;
                    return false;
                }
                return false;
            }

            Runnable mAction = new Runnable() {
                @Override public void run() {
                    if (!Camera.getWorld().isEndGame()) {
                        Camera.getWorld().getMario().faceRight(true);
                        if (false == Camera.getWorld().getMario().isFalling()) {
                            Camera.getWorld().getMario().getCurrentAnimation().stop();
                            Camera.getWorld().getMario().setCurrentAnimation(Camera.getWorld().getMario().getMove1());
                            Camera.getWorld().getMario().getMove1().play();
                        }

                        if ((Camera.getWorld().getMario().getRect().right + 40) < Constants.SCREEN_WIDTH) {
                            if ((Camera.getWorld().getMario().getRect().left >= Constants.SCREEN_WIDTH / 2)
                                    && ((Camera.getWorld().getMario().getRelPos() + Camera.getWorld().getMario().getWidth()
                                    + Constants.SCREEN_WIDTH / 2) < Camera.getWorld().getLevelWidth())) {

                                Camera.getWorld().shiftWorld(-40);
                                Camera.getWorld().getMario().moveRelPos(40);
                            } else {
                                // Camera.getWorld().getMario().moveRelPos(40);
                                Camera.getWorld().getMario().move(40, 0);
                            }// if/else
                        }
                        mHandler.postDelayed(this, 150);
                    }
                }//if
            };
        });

        leftButton = findViewById(R.id.leftButton);
        leftButton.setOnTouchListener(new View.OnTouchListener() {
            private Handler mHandler;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    if(mHandler != null) return true;
                    mHandler = new Handler();
                    mHandler.postDelayed(mAction, 50);
                    return true;
                }
                else if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    if(mHandler == null) return true;
                    mHandler.removeCallbacks(mAction);
                    //Camera.getMario().getMove1().stop();
                    if(!Camera.getWorld().getMario().isFalling() && !Camera.getWorld().isEndGame()) {
                        Camera.getWorld().getMario().getCurrentAnimation().stop();
                        Camera.getWorld().getMario().setCurrentAnimation(Camera.getWorld().getMario().getIdle());
                        Camera.getWorld().getMario().getCurrentAnimation().play();
                    }
                    mHandler = null;
                    return false;
                }
                return false;
            }

            Runnable mAction = new Runnable() {
                @Override public void run() {
                    if (!Camera.getWorld().isEndGame()) {
                        Camera.getWorld().getMario().faceRight(false);
                        if (false == Camera.getWorld().getMario().isFalling()) {
                            Camera.getWorld().getMario().getCurrentAnimation().stop();
                            Camera.getWorld().getMario().setCurrentAnimation(Camera.getWorld().getMario().getMove2());
                            Camera.getWorld().getMario().getMove2().play();
                        }
                        if ((Camera.getWorld().getMario().getPosX() - 40) > 0) {
                            Camera.getWorld().getMario().move(-40, 0);
                            //Camera.getWorld().getMario().moveRelPos(-40);
                        }
                        mHandler.postDelayed(this, 150);
                    }
                }//if
            };
        });

        jumpButton = findViewById(R.id.jumpButton);
        jumpButton.setOnClickListener(new View.OnClickListener() {
            int floor = Constants.SCREEN_HEIGHT - (3 * (Constants.SCREEN_HEIGHT/12));
            //  int jumpHeight = Constants.SCREEN_HEIGHT - (4 * (Constants.SCREEN_HEIGHT/12)) -  Camera.getWorld().getMario().getHeight();
            @Override
            public void onClick(View v) {
                if (!Camera.getWorld().isEndGame()) {
                    if ((Camera.getWorld().getMario().getRect().top > Camera.getWorld().getMario().getJumpHeight())
                            && (Camera.getWorld().getMario().getRect().bottom >= Camera.getWorld().getMario().getFloor())) {
                        Camera.getWorld().getMario().setJumping(true);
                        Camera.getWorld().getItemBLock(Camera.getWorld().getMario().getPosInd()).setBreakBlock(true);
                    } else if (Camera.getWorld().getMario().isSuperMario()
                            && !Camera.getWorld().getItemBLock(Camera.getWorld().getMario().getPosInd()).getRect().isEmpty()
                            && (Camera.getWorld().getMario().getRect().top >= Camera.getWorld().getItemBLock(Camera.getWorld().getMario().getPosInd()).getRect().bottom)) {

                        Camera.getWorld().getItemBLock(Camera.getWorld().getMario().getPosInd()).setBreakBlock(true);
                    }// if/else
                }//if
            }
        });/**/

        //Level Sprite Setup
        Constants.level[0] = BitmapFactory.decodeResource(getResources(), R.drawable.supermarioback);

        //Block Sprite Setup
        Constants.blockSprite = BitmapFactory.decodeResource(getResources(), R.drawable.brickblocks);
        blockWidth = Constants.blockSprite.getWidth()/4;
        blockHeight = Constants.blockSprite.getHeight();
        Constants.itemBlock[0] = Bitmap.createBitmap(Constants.blockSprite, 0, 0, blockWidth, blockHeight);
        Constants.hitBlock[0] = Bitmap.createBitmap(Constants.blockSprite, blockWidth, 0, blockWidth, blockHeight);
        Constants.afterBlock[0] = Bitmap.createBitmap(Constants.blockSprite, 2* blockWidth, 0, blockWidth, blockHeight);
        Constants.brickBlock[0] = Bitmap.createBitmap(Constants.blockSprite, 3 * blockWidth, 0, blockWidth, blockHeight);

        //Coin Sprite Setup
        Constants.coinSprite = BitmapFactory.decodeResource(getResources(), R.drawable.coinsprite);
        coinWidth = Constants.coinSprite.getWidth()/4;
        coinHeight = Constants.coinSprite.getHeight();
        Constants.coinSpin[0] = Bitmap.createBitmap(Constants.coinSprite, 0, 0, coinWidth, coinHeight);
        Constants.coinSpin[1] = Bitmap.createBitmap(Constants.coinSprite, coinWidth, 0, coinWidth, coinHeight);
        Constants.coinSpin[2] = Bitmap.createBitmap(Constants.coinSprite, 2*coinWidth, 0, coinWidth, coinHeight );
        Constants.coinSpin[3] = Bitmap.createBitmap(Constants.coinSprite, 3*coinWidth, 0, coinWidth, coinHeight);

        //Mushroom Sprite Setup
        Constants.mushroomRun[0] = BitmapFactory.decodeResource(getResources(), R.drawable.mushroomsprite);

        //Star Sprite Setup
        Constants.starSprite = BitmapFactory.decodeResource(getResources(), R.drawable.starsprite);
        starWidth = Constants.starSprite.getWidth()/4;
        starHeight = Constants.starSprite.getHeight();
        Constants.starRun[0] = Bitmap.createBitmap(Constants.starSprite, 0, 0, starWidth, starHeight);
        Constants.starRun[1] = Bitmap.createBitmap(Constants.starSprite, starWidth, 0, starWidth, starHeight);
        Constants.starRun[2] = Bitmap.createBitmap(Constants.starSprite, 2 * starWidth, 0, starWidth, starHeight);
        Constants.starRun[3] = Bitmap.createBitmap(Constants.starSprite, 3 * starWidth, 0, starWidth, starHeight);

        //Goomba Sprite Setup
        Constants.goomSprite = BitmapFactory.decodeResource(getResources(), R.drawable.goom);
        goomWidth = Constants.goomSprite.getWidth()/3;
        goomHeight = Constants.goomSprite.getHeight();
        Constants.goomWalk[0] = Bitmap.createBitmap(Constants.goomSprite, 0, 0, goomWidth, goomHeight);
        Constants.goomWalk[1] = Bitmap.createBitmap(Constants.goomSprite, goomWidth, 0, goomWidth, goomHeight);
        Constants.goomSquish[0] = Bitmap.createBitmap(Constants.goomSprite, 2 * goomWidth, 0, goomWidth, goomHeight);

        //Plant Sprite Setup
        Constants.plantSprite = BitmapFactory.decodeResource(getResources(), R.drawable.plant);
        plantWidth = Constants.plantSprite.getWidth()/2;
        plantHeight = Constants.plantSprite.getHeight();
        Constants.plantWalk[0] = Bitmap.createBitmap(Constants.plantSprite, 0, 0, plantWidth, plantHeight);
        Constants.plantWalk[1] = Bitmap.createBitmap(Constants.plantSprite, plantWidth, 0, plantWidth, plantHeight);

        //Pipe Sprite Setup
        Constants.pipeIdle[0] = BitmapFactory.decodeResource(getResources(), R.drawable.pipe);
        //pipeWidth = Constants.pipe.getWidth();
        //pipeHeight = Constants.pipe.getHeight();
        //Constants.pipeIdle[0] = Bitmap.createBitmap(Constants.pipe, 0, 0, pipeWidth, pipeHeight);

        //Mario Sprite Setup
        Constants.marioSprite = BitmapFactory.decodeResource(getResources(), R.drawable.mario);
        marioWidth = Constants.marioSprite.getWidth()/7;
        marioHeight = Constants.marioSprite.getHeight()/3;
        Constants.marioIdle[0] = Bitmap.createBitmap(Constants.marioSprite, 0, 2 * marioHeight, marioWidth, marioHeight);
        Constants.marioIdleLeft[0] = Bitmap.createBitmap(Constants.marioSprite, 0, 2 * marioHeight, marioWidth, marioHeight, reflect, false);
        Constants.marioWalk[0] = Bitmap.createBitmap(Constants.marioSprite, marioWidth, 2 * marioHeight, marioWidth, marioHeight);
        Constants.marioWalk[1] = Bitmap.createBitmap(Constants.marioSprite, 2 * marioWidth, 2 * marioHeight, marioWidth, marioHeight);
        Constants.marioWalk[2] = Bitmap.createBitmap(Constants.marioSprite, 3 * marioWidth, 2 * marioHeight, marioWidth, marioHeight);
        Constants.marioWalk[3] = Bitmap.createBitmap(Constants.marioSprite, 2 * marioWidth, 2 * marioHeight, marioWidth, marioHeight);
        Constants.marioWalkLeft[0] = Bitmap.createBitmap(Constants.marioSprite, marioWidth, 2 * marioHeight, marioWidth, marioHeight, reflect, false);
        Constants.marioWalkLeft[1] = Bitmap.createBitmap(Constants.marioSprite, 2 * marioWidth, 2 * marioHeight, marioWidth, marioHeight, reflect, false);
        Constants.marioWalkLeft[2] = Bitmap.createBitmap(Constants.marioSprite, 3 * marioWidth, 2 * marioHeight, marioWidth, marioHeight, reflect, false);
        Constants.marioWalkLeft[3] = Bitmap.createBitmap(Constants.marioSprite, 2 * marioWidth, 2 * marioHeight, marioWidth, marioHeight, reflect, false);
        Constants.marioHit[0] = Bitmap.createBitmap(Constants.marioSprite, 6 * marioWidth, 2 * marioHeight, marioWidth, marioHeight);
        Constants.marioJump[0] = Bitmap.createBitmap(Constants.marioSprite, 5 * marioWidth, 2 * marioHeight, marioWidth, marioHeight);
        Constants.marioJumpLeft[0] = Bitmap.createBitmap(Constants.marioSprite, 5 * marioWidth, 2 * marioHeight, marioWidth, marioHeight, reflect, false);



        //SuperMario Sprite Setup
        Constants.superIdle[0] = Bitmap.createBitmap(Constants.marioSprite, 0, 0, marioWidth, 2 * marioHeight);
        Constants.superIdleLeft[0] = Bitmap.createBitmap(Constants.marioSprite, 0, 0, marioWidth, 2 * marioHeight, reflect, false);
        Constants.superWalk[0] = Bitmap.createBitmap(Constants.marioSprite, marioWidth, 0, marioWidth, 2 * marioHeight);
        Constants.superWalk[1] = Bitmap.createBitmap(Constants.marioSprite, 2 * marioWidth, 0, marioWidth, 2 * marioHeight);
        Constants.superWalk[2] = Bitmap.createBitmap(Constants.marioSprite, 3 * marioWidth, 0, marioWidth, 2 * marioHeight);
        Constants.superWalk[3] = Bitmap.createBitmap(Constants.marioSprite, 2 * marioWidth, 0, marioWidth, 2 * marioHeight);
        Constants.superWalkLeft[0] = Bitmap.createBitmap(Constants.marioSprite,  marioWidth, 0, marioWidth, 2 * marioHeight, reflect, false);
        Constants.superWalkLeft[1] = Bitmap.createBitmap(Constants.marioSprite, 2 * marioWidth, 0, marioWidth, 2 * marioHeight, reflect, false);
        Constants.superWalkLeft[2] = Bitmap.createBitmap(Constants.marioSprite, 3 * marioWidth, 0, marioWidth, 2 * marioHeight, reflect, false);
        Constants.superWalkLeft[3] = Bitmap.createBitmap(Constants.marioSprite, 2 * marioWidth, 0, marioWidth, 2 * marioHeight, reflect, false);
        Constants.superHit[0] = Bitmap.createBitmap(Constants.marioSprite, 6 * marioWidth, 0, marioWidth, 2 * marioHeight);
        Constants.superHitLeft[0] = Bitmap.createBitmap(Constants.marioSprite, 6 * marioWidth, 0, marioWidth, 2 * marioHeight, reflect, false);
        Constants.superJump[0] = Bitmap.createBitmap(Constants.marioSprite, 5 * marioWidth, 0, marioWidth, 2 * marioHeight);
        Constants.superJumpLeft[0] = Bitmap.createBitmap(Constants.marioSprite, 5 * marioWidth, 0, marioWidth, 2 * marioHeight, reflect, false);

        //Invincible Mario Setup
        Constants.inMario = BitmapFactory.decodeResource(getResources(), R.drawable.invinsiblesmallmario);
        marioHeight = Constants.inMario.getHeight()/3;
        marioWidth = Constants.inMario.getWidth()/4;
        Constants.inIdle[0] = Bitmap.createBitmap(Constants.inMario, 0, 0, marioWidth, marioHeight);
        Constants.inIdle[1] = Bitmap.createBitmap(Constants.inMario, 0, marioHeight, marioWidth, marioHeight);
        Constants.inIdle[2] = Bitmap.createBitmap(Constants.inMario, 0, 2 * marioHeight, marioWidth, marioHeight);
        Constants.inIdleLeft[0] = Bitmap.createBitmap(Constants.inMario, 0, 0, marioWidth, marioHeight, reflect, false);
        Constants.inIdleLeft[1] = Bitmap.createBitmap(Constants.inMario, 0, marioHeight, marioWidth,  marioHeight, reflect, false);
        Constants.inIdleLeft[2] = Bitmap.createBitmap(Constants.inMario, 0, 2 * marioHeight, marioWidth,  marioHeight, reflect, false);

        Constants.inWalk[0] = Bitmap.createBitmap(Constants.inMario, marioWidth, 0, marioWidth, marioHeight);
        Constants.inWalk[1] = Bitmap.createBitmap(Constants.inMario, 2 * marioWidth, marioHeight, marioWidth, marioHeight);
        Constants.inWalk[2] = Bitmap.createBitmap(Constants.inMario, 3 * marioWidth, 2 * marioHeight, marioWidth, marioHeight);
        Constants.inWalk[3] = Bitmap.createBitmap(Constants.inMario, 2 * marioWidth, 0 * marioHeight, marioWidth, marioHeight);
        Constants.inWalk[4] = Bitmap.createBitmap(Constants.inMario, 1 * marioWidth, 1 * marioHeight, marioWidth, marioHeight);
        Constants.inWalk[5] = Bitmap.createBitmap(Constants.inMario, 2 * marioWidth, 2 * marioHeight, marioWidth, marioHeight);
        Constants.inWalk[6] = Bitmap.createBitmap(Constants.inMario, 3 * marioWidth, 0 * marioHeight, marioWidth, marioHeight);
        Constants.inWalk[7] = Bitmap.createBitmap(Constants.inMario, 2 * marioWidth, 2 * marioHeight, marioWidth, marioHeight);
        Constants.inWalk[8] = Bitmap.createBitmap(Constants.inMario, 1 * marioWidth, 2 * marioHeight, marioWidth, marioHeight);
        Constants.inWalk[9] = Bitmap.createBitmap(Constants.inMario, 2 * marioWidth, 1 * marioHeight, marioWidth, marioHeight);
        Constants.inWalk[10] = Bitmap.createBitmap(Constants.inMario, 3 * marioWidth, 0 * marioHeight, marioWidth, marioHeight);
        Constants.inWalk[11] = Bitmap.createBitmap(Constants.inMario, 2 * marioWidth, 2 * marioHeight, marioWidth, marioHeight);

        Constants.inWalkLeft[0] = Bitmap.createBitmap(Constants.inMario, marioWidth, 0, marioWidth, marioHeight,reflect, false);
        Constants.inWalkLeft[1] = Bitmap.createBitmap(Constants.inMario, 2 * marioWidth, marioHeight, marioWidth, marioHeight, reflect, false);
        Constants.inWalkLeft[2] = Bitmap.createBitmap(Constants.inMario, 3 * marioWidth, 2 * marioHeight, marioWidth, marioHeight, reflect, false);
        Constants.inWalkLeft[3] = Bitmap.createBitmap(Constants.inMario, 2 * marioWidth, 0 * marioHeight, marioWidth, marioHeight, reflect, false);
        Constants.inWalkLeft[4] = Bitmap.createBitmap(Constants.inMario, 1 * marioWidth, 1 * marioHeight, marioWidth, marioHeight, reflect, false);
        Constants.inWalkLeft[5] = Bitmap.createBitmap(Constants.inMario, 2 * marioWidth, 2 * marioHeight, marioWidth, marioHeight, reflect, false);
        Constants.inWalkLeft[6] = Bitmap.createBitmap(Constants.inMario, 3 * marioWidth, 0 * marioHeight, marioWidth, marioHeight, reflect, false);
        Constants.inWalkLeft[7] = Bitmap.createBitmap(Constants.inMario, 2 * marioWidth, 2 * marioHeight, marioWidth, marioHeight, reflect, false);
        Constants.inWalkLeft[8] = Bitmap.createBitmap(Constants.inMario, 1 * marioWidth, 2 * marioHeight, marioWidth, marioHeight, reflect, false);
        Constants.inWalkLeft[9] = Bitmap.createBitmap(Constants.inMario, 2 * marioWidth, 1 * marioHeight, marioWidth, marioHeight, reflect, false);
        Constants.inWalkLeft[10] = Bitmap.createBitmap(Constants.inMario, 3 * marioWidth, 0 * marioHeight, marioWidth, marioHeight, reflect, false);
        Constants.inWalkLeft[11] = Bitmap.createBitmap(Constants.inMario, 2 * marioWidth, 2 * marioHeight, marioWidth, marioHeight, reflect, false);


        Constants.superInMario = BitmapFactory.decodeResource(getResources(), R.drawable.invinsiblebigmario);
        marioHeight = Constants.superInMario.getHeight()/3;
        marioWidth = Constants.superInMario.getWidth()/4;
        Constants.superInIdle[0] = Bitmap.createBitmap(Constants.superInMario, 0, 0, marioWidth, marioHeight);
        Constants.superInIdle[1] = Bitmap.createBitmap(Constants.superInMario, 0, marioHeight, marioWidth, marioHeight);
        Constants.superInIdle[2] = Bitmap.createBitmap(Constants.superInMario, 0, 2 * marioHeight, marioWidth, marioHeight);
        Constants.superInIdleLeft[0] = Bitmap.createBitmap(Constants.superInMario, 0, 0, marioWidth, marioHeight, reflect, false);
        Constants.superInIdleLeft[1] = Bitmap.createBitmap(Constants.superInMario, 0, marioHeight, marioWidth,  marioHeight, reflect, false);
        Constants.superInIdleLeft[2] = Bitmap.createBitmap(Constants.superInMario, 0, 2 * marioHeight, marioWidth,  marioHeight, reflect, false);

        Constants.superInWalk[0] = Bitmap.createBitmap(Constants.superInMario, marioWidth, 0, marioWidth, marioHeight);
        Constants.superInWalk[1] = Bitmap.createBitmap(Constants.superInMario, 2 * marioWidth, marioHeight, marioWidth, marioHeight);
        Constants.superInWalk[2] = Bitmap.createBitmap(Constants.superInMario, 3 * marioWidth, 2 * marioHeight, marioWidth, marioHeight);
        Constants.superInWalk[3] = Bitmap.createBitmap(Constants.superInMario, 2 * marioWidth, 0 * marioHeight, marioWidth, marioHeight);
        Constants.superInWalk[4] = Bitmap.createBitmap(Constants.superInMario, 1 * marioWidth, 1 * marioHeight, marioWidth, marioHeight);
        Constants.superInWalk[5] = Bitmap.createBitmap(Constants.superInMario, 2 * marioWidth, 2 * marioHeight, marioWidth, marioHeight);
        Constants.superInWalk[6] = Bitmap.createBitmap(Constants.superInMario, 3 * marioWidth, 0 * marioHeight, marioWidth, marioHeight);
        Constants.superInWalk[7] = Bitmap.createBitmap(Constants.superInMario, 2 * marioWidth, 2 * marioHeight, marioWidth, marioHeight);
        Constants.superInWalk[8] = Bitmap.createBitmap(Constants.superInMario, 1 * marioWidth, 2 * marioHeight, marioWidth, marioHeight);
        Constants.superInWalk[9] = Bitmap.createBitmap(Constants.superInMario, 2 * marioWidth, 1 * marioHeight, marioWidth, marioHeight);
        Constants.superInWalk[10] = Bitmap.createBitmap(Constants.superInMario, 3 * marioWidth, 0 * marioHeight, marioWidth, marioHeight);
        Constants.superInWalk[11] = Bitmap.createBitmap(Constants.superInMario, 2 * marioWidth, 2 * marioHeight, marioWidth, marioHeight);

        Constants.superInWalkLeft[0] = Bitmap.createBitmap(Constants.superInMario, marioWidth, 0, marioWidth, marioHeight,reflect, false);
        Constants.superInWalkLeft[1] = Bitmap.createBitmap(Constants.superInMario, 2 * marioWidth, marioHeight, marioWidth, marioHeight, reflect, false);
        Constants.superInWalkLeft[2] = Bitmap.createBitmap(Constants.superInMario, 3 * marioWidth, 2 * marioHeight, marioWidth, marioHeight, reflect, false);
        Constants.superInWalkLeft[3] = Bitmap.createBitmap(Constants.superInMario, 2 * marioWidth, 0 * marioHeight, marioWidth, marioHeight, reflect, false);
        Constants.superInWalkLeft[4] = Bitmap.createBitmap(Constants.superInMario, 1 * marioWidth, 1 * marioHeight, marioWidth, marioHeight, reflect, false);
        Constants.superInWalkLeft[5] = Bitmap.createBitmap(Constants.superInMario, 2 * marioWidth, 2 * marioHeight, marioWidth, marioHeight, reflect, false);
        Constants.superInWalkLeft[6] = Bitmap.createBitmap(Constants.superInMario, 3 * marioWidth, 0 * marioHeight, marioWidth, marioHeight, reflect, false);
        Constants.superInWalkLeft[7] = Bitmap.createBitmap(Constants.superInMario, 2 * marioWidth, 2 * marioHeight, marioWidth, marioHeight, reflect, false);
        Constants.superInWalkLeft[8] = Bitmap.createBitmap(Constants.superInMario, 1 * marioWidth, 2 * marioHeight, marioWidth, marioHeight, reflect, false);
        Constants.superInWalkLeft[9] = Bitmap.createBitmap(Constants.superInMario, 2 * marioWidth, 1 * marioHeight, marioWidth, marioHeight, reflect, false);
        Constants.superInWalkLeft[10] = Bitmap.createBitmap(Constants.superInMario, 3 * marioWidth, 0 * marioHeight, marioWidth, marioHeight, reflect, false);
        Constants.superInWalkLeft[11] = Bitmap.createBitmap(Constants.superInMario, 2 * marioWidth, 2 * marioHeight, marioWidth, marioHeight, reflect, false);

        //Constants.CURRENT_CONTEXT = this;

        //cam = new Camera(this, attr );
        //cam.start();
    }

}// class MainActivity

/* EOF */