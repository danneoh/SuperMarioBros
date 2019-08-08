package com.example.supermariobros;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Animation {
    private Bitmap[] sprites;//sprites of animation sprite
    private int index;// index of frame
    private boolean playing = false;// used to check if animation of object is currently running
    private double startTime;// used to check time at start of animation
    private double timePerSprite;// time length of animation

    public Animation(Bitmap[] sprites, double aniTime){// Constructor
        setSprites(sprites);
        timePerSprite = (aniTime/(double)sprites.length) * 1000;
        startTime = System.currentTimeMillis();
    }

    public void setSprites(Bitmap[] sprites){
        this.sprites = sprites;
    }

    public boolean isPlaying(){//checks if object is to be animated
        return playing;
    }// isPlaying()

    public void play(){//starts animation of object
        index = 0;
        startTime = System.currentTimeMillis();
        playing = true;
    }// play()

    public void stop(){//stops animation of object
        playing = false;
    }// stop()

    private void update(){// updates frame of animation
        double deltaTime = System.currentTimeMillis() - startTime;
        if(playing && (deltaTime > timePerSprite)/**/){
            index++;
            index = index % sprites.length;
            startTime = System.currentTimeMillis();
        }// end if
    }// update()

    public void draw(Canvas canvas, Rect dest){
        System.out.println(timePerSprite);
        if(playing){
            canvas.drawBitmap(sprites[index], null, dest, new Paint());
            update();
        }
    }

}// class Animation

/* EOF */