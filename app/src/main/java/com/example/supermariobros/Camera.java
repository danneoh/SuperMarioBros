package com.example.supermariobros;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;


public class Camera extends View {


    static World world;
    EnvironmentalEngine game;
    MainActivity active;
    Context context;

    Thread camView;
    //SurfaceHolder holder;
    //Canvas cam;

    public Camera(Context context, AttributeSet attributeSet){//Constructor
        super(context, attributeSet);
        this.context = context;
        active = (MainActivity) context;

        world = new World();
        game = new EnvironmentalEngine(context, world);

        camView = new Thread(game);
        camView.start();/**/


    }

    public static World getWorld(){
        return world;
    }

    @Override
    protected void onDraw(Canvas cam){
        //cam.drawARGB(255, 150, 150, 10);
        world.draw(cam);

        if(world.isNextLevel()){
            world = new World(world.getCurrentLevel()+1, world.getMario());
            game = new EnvironmentalEngine(context, world);
            camView = new Thread(game);
            camView.start();
            active.getLevelView().setText("Level: " + Integer.toString(world.getCurrentLevel()));
        }//if

        if(world.isLevelReset()){
            world = new World(world.getCurrentLevel(), world.getMario());
            game = new EnvironmentalEngine(context, world);
            camView = new Thread(game);
            camView.start();
            active.getLevelView().setText("Level: " + Integer.toString(world.getCurrentLevel()));
        }//if /**/

        invalidate();

    }// Draw


    // 20x12

}// class Camera

/* EOF */