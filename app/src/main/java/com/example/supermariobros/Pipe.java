package com.example.supermariobros;

import android.graphics.Rect;

public class Pipe extends Enemy{

    public Pipe(int posX, int posY)
    {
        super(posX, posY, 2* (Constants.SCREEN_WIDTH / 20), 3 * (Constants.SCREEN_HEIGHT/24));
        setHitBox(posX,posY - 10,posX + getWidth(), posY);
        setIdle(Constants.pipeIdle, 2);
        setCurrentAnimation(getIdle());
    }

}
