package com.bluetoothgames.gameplay;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.GridView;

import com.bluetoothgames.MainActivity;
import com.bluetoothgames.adapters.BoardGameAdapter;
import com.bluetoothgames.bluetooth.BluetoothFragment;
import com.bluetoothgames.boardViews.Position;

import java.util.ArrayList;

/**
 * Created by Karen on 5/5/2016.
 */
public abstract class GamePlay {
    protected Player player;
    protected Board board;
    private Player player_one = new Player(GameConstants.PlayerContsants.FIRST_PLAYER, true, GameConstants.BoardConstants.X);
    private Player player_two = new Player(GameConstants.PlayerContsants.SECOND_PLAER, false, GameConstants.BoardConstants.Y);
    protected boolean isFinished;
    protected boolean ready;
    protected Context context;
    protected BoardGameAdapter boardGameAdapter;
    protected GridView gridView;

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public Player getPlayer() {
        return player;
    }

    public Player setPlayer(int which_player) {
        switch (which_player) {
            case GameConstants.PlayerContsants.FIRST_PLAYER:
                player = player_one;
                break;
            case GameConstants.PlayerContsants.SECOND_PLAER:
                player = player_two;
                break;
        }
        return player;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }



    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Position positionFromString(String str) {
        String[] parts = str.split(",", 2);
        String part1 = parts[0];
        String part2 = parts[1];
        int x = Integer.parseInt(part1);
        int y = Integer.parseInt(part2);
        Position p = new Position(x, y);
        return p;
    }


    public void exit(){
        Intent i=new Intent(context, MainActivity.class);
        context.startActivity(i);
    }

    public abstract void play(String position);

    public abstract void startNewGame();

    public abstract void rivalMoved(String position);

    public abstract void setVisibitlity(int visibitlity);

    public abstract void analyzeGame(Position position);

    public abstract void finishGame(ArrayList<Position> positions);

    public abstract Player getWinner();

    public abstract void  onViewCreated(View view, @Nullable Bundle savedInstanceState,final BluetoothFragment bluetoothFragment);

    public abstract void setupBoard(final BluetoothFragment bluetoothFragment);
}
