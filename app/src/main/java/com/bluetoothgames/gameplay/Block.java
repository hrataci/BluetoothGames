package com.bluetoothgames.gameplay;

import com.bluetoothgames.boardViews.Position;

/**
 * Created by Karen on 5/6/2016.
 */
public class Block {
    private int background_resId;
    private int top_resId=-1;
    private  Position position;
    private boolean isEmpty;

    private int belongs_side=GameConstants.BoardConstants.EMPTY;

    public int getBelongs_side() {
        return belongs_side;
    }

    public void setBelongs_side(int belongs_side) {
        this.belongs_side = belongs_side;
        isEmpty=false;

    }

    public Block(int background_resId) {
        this.background_resId = background_resId;
        isEmpty=true;
    }

    public int getBackground_resId() {
        return background_resId;
    }

    public void setBackground_resId(int background_resId) {
        this.background_resId = background_resId;

    }

    public int getTop_resId() {
        return top_resId;
    }

    public void setTop_resId(int top_resId) {
        this.top_resId = top_resId;
        isEmpty=false;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = new Position(position.getX(),position.getY());
    }

    public boolean isEmpty() {

        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
        top_resId=-1;
    }
    public boolean neededClear(){
        if(top_resId==-1)
            return true;
        return false;
    }
}
