package com.example.supermariobros;

public class piranhaPlant extends Enemy
{
    int plantHeight;
    int plantFloor;
    private boolean movingUp = true;
    public piranhaPlant(int posX, int posY)
    {
        super(posX, posY, (Constants.SCREEN_WIDTH / 20), (Constants.SCREEN_HEIGHT / 12));
        setHitBox(posX - getWidth(),posY - 20,posX + 2*getWidth(), posY);
        setMove1(Constants.plantWalk, 0.5f);
        setPiranhaHeight();
        setPlantFloor();
        setCurrentAnimation(getMove1());
    }

    private void setPiranhaHeight(){
        plantHeight = getRect().bottom - (getHeight());
    }

    private void setPlantFloor(){
        plantFloor = getRect().bottom;
    }

    public int getPiranhaFloor(){
        return plantFloor;
    }

    public int getPlantHeight(){
        return plantHeight;
    }

    public boolean isMovingUp(){
        return movingUp;
    }

    public void setMovingUp(boolean movingUp){
        this.movingUp = movingUp;
    }
}