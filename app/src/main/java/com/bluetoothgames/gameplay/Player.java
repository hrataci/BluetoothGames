package com.bluetoothgames.gameplay;

/**
 * Created by Karen on 5/5/2016.
 */
public class Player {
    private int id;
    private boolean isPlayerTurn;
    private int playerSide;
    private boolean isPlayerWins;


    public Player(int id, boolean isPlayerTurn, int playerSide) {
        this.id = id;
        this.isPlayerTurn = isPlayerTurn;
        this.playerSide = playerSide;
    }

    public Player() {
    }

    public boolean isPlayerWins() {
        return isPlayerWins;
    }

    public void setPlayerWins(boolean playerWins) {
        isPlayerWins = playerWins;
    }
    public void changePlayerTurn(){
        isPlayerTurn=!isPlayerTurn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }

    public void setPlayerTurn(boolean isPlayerTurn) {
        this.isPlayerTurn = isPlayerTurn;
    }

    public int getPlayerSide() {
        return playerSide;
    }

    public void setPlayerSide(int playerSide) {
        this.playerSide = playerSide;
    }
}
