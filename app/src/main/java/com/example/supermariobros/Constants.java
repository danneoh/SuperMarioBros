package com.example.supermariobros;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Constants {
    public static final int FPS = 30;
    public static final int SCREEN_HEIGHT = Resources.getSystem().getDisplayMetrics().widthPixels;
    public static final int SCREEN_WIDTH = Resources.getSystem().getDisplayMetrics().heightPixels;

    public static Context CURRENT_CONTEXT;

    //Sprites for Level
    public static Bitmap[] level = new Bitmap[3];

    //Sprites for Blocks
    public static Bitmap blockSprite;
    public static Bitmap[] itemBlock = new Bitmap[1];
    public static Bitmap[] hitBlock = new Bitmap[1];
    public static Bitmap[] afterBlock = new Bitmap[1];
    public static Bitmap[] brickBlock = new Bitmap[1];

    //Sprites for Coins
    public static Bitmap coinSprite;
    public static Bitmap[] coinSpin = new Bitmap[4];

    //Sprites for Mushroom
    public static Bitmap[] mushroomRun = new Bitmap[1];

    //Sprites for Star
    public static Bitmap starSprite;
    public static Bitmap[] starRun = new Bitmap[4];

    //Sprites for Goomba
    public static Bitmap goomSprite;
    public static Bitmap[] goomWalk = new Bitmap[2];
    public static Bitmap[] goomSquish = new Bitmap[1];

    //Sprites for Plant
    public static Bitmap plantSprite;
    public static Bitmap[] plantWalk = new Bitmap[2];

    //Sprites for Pipe
    public static Bitmap[] pipeIdle = new Bitmap[1];

    //Sprites for Mario
    public static Bitmap marioSprite;
    public static Bitmap[] marioIdle = new Bitmap[1];
    public static Bitmap[] marioIdleLeft = new Bitmap[1];
    public static Bitmap[] marioWalk = new Bitmap[4];
    public static Bitmap[] marioWalkLeft = new Bitmap[4];
    public static Bitmap[] marioHit = new Bitmap[1];
    public static Bitmap[] marioJump = new Bitmap[1];
    public static Bitmap[] marioJumpLeft = new Bitmap[1];

    //Sprites for SuperMario
    public static Bitmap[] superIdle = new Bitmap[1];
    public static Bitmap[] superIdleLeft = new Bitmap[1];
    public static Bitmap[] superWalk = new Bitmap[4];
    public static Bitmap[] superWalkLeft = new Bitmap[4];
    public static Bitmap[] superHit = new Bitmap[1];
    public static Bitmap[] superHitLeft = new Bitmap[1];
    public static Bitmap[] superJump = new Bitmap[1];
    public static Bitmap[] superJumpLeft = new Bitmap[1];

    //Sprites for Invincible Mario
    public static Bitmap inMario;
    public static Bitmap[] inIdle = new Bitmap[3];
    public static Bitmap[] inIdleLeft = new Bitmap[3];
    public static Bitmap[] inWalk = new Bitmap[12];
    public static Bitmap[] inWalkLeft = new Bitmap[12];
    public static Bitmap[] inHit = new Bitmap[3];
    public static Bitmap[] inHitLeft = new Bitmap[3];
    public static Bitmap[] inJump = new Bitmap[3];
    public static Bitmap[] inJumpLeft = new Bitmap[3];

    public static Bitmap superInMario;
    public static Bitmap[] superInIdle = new Bitmap[3];
    public static Bitmap[] superInIdleLeft = new Bitmap[3];
    public static Bitmap[] superInWalk = new Bitmap[12];
    public static Bitmap[] superInWalkLeft = new Bitmap[12];
    public static Bitmap[] superInHit = new Bitmap[3];
    public static Bitmap[] superInHitLeft = new Bitmap[3];
    public static Bitmap[] superInJump = new Bitmap[3];
    public static Bitmap[] superInJumpLeft = new Bitmap[3];




}// Class Constants

/* EOF */