package com.bluetoothgames.gameplay.customboards;

import android.util.Log;

import com.bluetoothgames.R;
import com.bluetoothgames.adapters.BoardGameAdapter;
import com.bluetoothgames.boardViews.Position;
import com.bluetoothgames.gameplay.Block;
import com.bluetoothgames.gameplay.Board;

import java.util.ArrayList;

/**
 * Created by Karen on 5/18/2016.
 */
public class NavalBattleBoard extends Board {
    private int boardCellNum = 6;
    // private ArrayList<Block> rival_blocks;

    public NavalBattleBoard() {
        setLayoutResID(R.layout.navalbattle_board);
        setNumCell(boardCellNum);
        blocks = new ArrayList<Block>();
        //    rival_blocks=new ArrayList<Block>();
        for (int i = 0; i < boardCellNum * boardCellNum; i++) {
            //          rival_blocks.add(new Block(R.drawable.sq));
            blocks.add(new Block(R.drawable.sq_naval));
        }


    }
    /*public Block getRivalBlockAtPosition(Position position){
        for(Block block:rival_blocks){
            if(block.getPosition().getX()==position.getX()&&block.getPosition().getY()==position.getY()){
                return block;
            }
        }
        return null;

    }
    public void setRivalBlockAtPostion(Position position,Block b){
        for(Block block:rival_blocks){
            if(block.getPosition().equals(position)){
                block=b;
            }
        }
    }*/

    @Override

    public void clearBoard() {
        for (int i = 0; i < boardCellNum; i++) {
            blocks.get(i).setEmpty(true);
            //  rival_blocks.get(i).setEmpty(true);
            Log.e("block", blocks.get(i) + "" + blocks.get(i).getTop_resId());

        }

    }
}
