package com.bluetoothgames.gameplay.gameplayres;

import android.util.Log;

import com.bluetoothgames.boardViews.Position;

import java.util.ArrayList;

/**
 * Created by Karen on 5/20/2016.
 */
public class Ship {
    private ArrayList<Position> position;
    private int res_id;
    private int number_of_decks;

    public Ship(int number_of_decks) {
        this.number_of_decks = number_of_decks;
        position = new ArrayList<Position>();
    }

    public boolean isSheepSeted() {
        return position.size() == number_of_decks;
    }

    public ArrayList<Position> posiblePositions() {
        ArrayList<Position> pos_position = new ArrayList<Position>();
        switch (number_of_decks) {
            case 1:
                break;
            case 2:
                if (position.isEmpty()) {
                    break;
                } else {
                    pos_position = countPosition(position.get(0));
                }
                break;
            case 3:
                if (position.isEmpty()) {
                    Log.e("pos_pos",pos_position.toString());
                    break;
                } else {
                    Log.e("pos_pos","not empty");
                    pos_position = countPosition(position.get(position.size()-1));
                    if (position.size() > 1) {
                        Log.e("pos_pos","size >1");
                        Position tmp_p1 = position.get(0);
                        Position tmp_p2 = position.get(1);
                        if (tmp_p1.getX() == tmp_p2.getX()) {
                            Log.e("pos_pos","x=x");
                            pos_position = new ArrayList<>();
                            if (tmp_p1.getY() - tmp_p2.getY() > 0) {
                                if(tmp_p1.incrementY().isValidPosition()){
                                    pos_position.add(tmp_p1.incrementY());
                                    Log.e("tmp_p1.incrementY()",tmp_p1.incrementY().toString());
                                }

                                if(tmp_p2.decrementY().isValidPosition()) {
                                    pos_position.add(tmp_p2.decrementY());
                                    Log.e("tmp_p2.decrementY()",tmp_p2.decrementY().toString());
                                }
                            } else {
                                if(tmp_p2.incrementY().isValidPosition()){
                                pos_position.add(tmp_p2.incrementY());
                                    Log.e("tmp_p2.incrementY()",tmp_p2.incrementY().toString());
                                }
                                if (tmp_p1.decrementY().isValidPosition()){
                                pos_position.add(tmp_p1.decrementY());
                                    Log.e("tmp_p1.decrementY()",tmp_p1.decrementY().toString());
                                }
                            }
                        } else if (tmp_p1.getY() == tmp_p2.getY()) {
                            Log.e("pos_pos","y=y");
                            pos_position = new ArrayList<>();
                            if (tmp_p1.getX() - tmp_p2.getX() > 0) {
                                if(tmp_p1.incrementX().isValidPosition()){
                                    Log.e("tmp_p1.incrementX()",tmp_p1.incrementX().toString());
                                pos_position.add(tmp_p1.incrementX());}

                                if(tmp_p2.decrementX().isValidPosition()){
                                    Log.e("tmp_p2.decrementX()",tmp_p2.decrementX().toString());
                                pos_position.add(tmp_p2.decrementX());}
                            } else {
                                if(tmp_p2.incrementX().isValidPosition()){
                                    Log.e("tmp_p2.incrementX()",tmp_p2.incrementX().toString());
                                pos_position.add(tmp_p2.incrementX());}
                                if(tmp_p1.decrementX().isValidPosition()){
                                    Log.e("tmp_p1.decrementX()",tmp_p1.decrementX().toString());
                                pos_position.add(tmp_p1.decrementX());}
                            }
                        }
                    }
                }
                break;
        }
        return pos_position;
    }

    private ArrayList<Position> countPosition(Position position) {
        ArrayList<Position> tmp_pos = new ArrayList<Position>();
        Position p = position.decrementX();
        if (p.isValidPosition())
            tmp_pos.add(p);
        p = position.decrementY();
        if (p.isValidPosition())
            tmp_pos.add(p);
        p = position.incrementX();
        if (p.isValidPosition())
            tmp_pos.add(p);
        p = position.incrementY();
        if (p.isValidPosition())
            tmp_pos.add(p);
        return tmp_pos;
    }

    public void addPosition(Position p) {
        position.add(p);
    }

    public ArrayList<Position> getPosition() {
        return position;
    }

    public void setPosition(ArrayList<Position> position) {
        this.position = position;
    }

    public int getNumber_of_decks() {
        return number_of_decks;
    }

    public void setNumber_of_decks(int number_of_decks) {
        this.number_of_decks = number_of_decks;
    }

    public int getRes_id() {
        return res_id;
    }

    public void setRes_id(int res_id) {
        this.res_id = res_id;
    }
    //private int number;
}
