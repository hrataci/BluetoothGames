package com.bluetoothgames.gameplay;

import com.bluetoothgames.bluetooth.logger.Log;
import com.bluetoothgames.boardViews.Position;

import java.util.ArrayList;

/**
 * Created by Karen on 5/5/2016.
 */
public abstract class Board {
    private Integer[] boardIds;
    private int numCell;
    protected ArrayList<Block> blocks;
    private int layoutResID;

    public abstract void clearBoard();

    public int getLayoutResID() {
        return layoutResID;
    }

    public void setLayoutResID(int layoutResID) {
        this.layoutResID = layoutResID;
    }


    public ArrayList<Block> getBlocks() {
        return blocks;
    }
    public Block getBlockAtPosition(Position position){
        android.util.Log.e("pos=",position.toString());
        for(Block block:blocks){
            android.util.Log.e("b=",block.getPosition().getX()+"");
            if(block.getPosition().getX()==position.getX()&&block.getPosition().getY()==position.getY()){
                return block;
            }
        }
        return null;

    }
    public void setBlockAtPostion(Position position,Block b){
        for(Block block:blocks){
            if(block.getPosition().equals(position)){
              block=b;
            }
        }
    }

    public void setBlocks(ArrayList<Block> blocks) {
        this.blocks = blocks;
    }

    public int getNumCell() {
        return numCell;
    }

    public void setNumCell(int numCell) {
        this.numCell = numCell;
    }

    public Integer[] getBoardIds() {

        return boardIds;
    }
    public Position refactorToPostion(int position) {
        int x = 0;
        int y = 0;
        y =getNumCell() - position / getNumCell();
        x = position % getNumCell() + 1;
        return new Position(x, y);
    }

    public int refactorFromPosition(Position position) {

        int result;
        result = (getNumCell() - position.getY()) * getNumCell() + position.getX() - 1;
        return result;
    }

    public void setBoardIds(Integer[] boardIds) {
        this.boardIds = boardIds;
    }
}
