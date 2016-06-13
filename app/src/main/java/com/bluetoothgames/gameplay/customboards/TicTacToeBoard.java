package com.bluetoothgames.gameplay.customboards;

import android.util.Log;

import com.bluetoothgames.R;
import com.bluetoothgames.gameplay.Block;
import com.bluetoothgames.gameplay.Board;

import java.util.ArrayList;

/**
 * Created by Karen on 5/6/2016.
 */
public class TicTacToeBoard extends Board {
    private int boardCellNum=3;


    public TicTacToeBoard(){
        //  setBoardIds(ids);
        setLayoutResID(R.layout.tictactoe_board);
        setNumCell(boardCellNum);
        blocks=new ArrayList<Block>();
        for(int i=0;i<boardCellNum*boardCellNum;i++){

            blocks.add(new Block(R.drawable.sq));
        }
    }

    public int getBoardCellNum() {
        return boardCellNum;
    }

    public void setBoardCellNum(int boardCellNum) {
        this.boardCellNum = boardCellNum;
    }
    public void clearBoard(){
        for(int i=0;i<boardCellNum;i++){
            blocks.get(i).setEmpty(true);
            Log.e("block",blocks.get(i)+""+blocks.get(i).getTop_resId());

        }

    }
}
