package com.bluetoothgames.gameplay.customgameplays;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.bluetoothgames.R;
import com.bluetoothgames.adapters.BoardGameAdapter;
import com.bluetoothgames.bluetooth.BluetoothFragment;
import com.bluetoothgames.boardViews.Position;
import com.bluetoothgames.gameplay.Board;
import com.bluetoothgames.gameplay.GameConstants;
import com.bluetoothgames.gameplay.GamePlay;
import com.bluetoothgames.gameplay.Player;
import com.bluetoothgames.gameplay.customboards.NavalBattleBoard;
import com.bluetoothgames.gameplay.gameplayres.Ship;

import java.util.ArrayList;

/**
 * Created by Karen on 5/18/2016.
 */
public class NavalBattleGamPlay extends GamePlay {
    private int movecount;
    private Button first;
    private Button second;
    private Button third;
    private Button start;
    private Button exitGame;
    private Button ready_btn;
    private TextView textView;

    private int you_win = 0;
    private int rival_win = 0;
    private int no_one_win = 0;
    private Board rivalBoard;
    private GridView rivalGridView;
    private BoardGameAdapter rivalBoardAdapter;
    private boolean u_prepaired = false;
    private boolean r_prepaired = false;
    private ArrayList<Ship> single_deck_ships;
    private ArrayList<Ship> double_deck_ships;
    private Ship three_deck_ship;
    private int number_of_decks;
    private int hit_number;
    private int rival_hit_number;
    private int count_of_positions = 10;
    private ArrayList<Position> rivalPositions;
    private ArrayList<Position> uPositions;

    public NavalBattleGamPlay(Context context, Board board) {
        this.context = context;
        setReady(false);
        this.board = new NavalBattleBoard();
        rivalBoard = new NavalBattleBoard();
        boardGameAdapter = new BoardGameAdapter(context, this.board, R.id.naval_square_background, R.id.naval_piece);

        rivalBoardAdapter = new BoardGameAdapter(context, this.rivalBoard, R.id.naval_square_background, R.id.naval_piece);

    }

    @Override
    public void play(String position) {
        if (isReady()) {
            if (!u_prepaired) {
                preperForGame(position);
            } else if (u_prepaired && r_prepaired) {
                Position p = positionFromString(position);
                analyzeGame(p);
            }

        }
    }

    private ArrayList<Position> pos_position;

    private void preperForGame(String position) {
        Position p = positionFromString(position);

        if (uPositions.contains(p)) {
            return;
        }
        switch (number_of_decks) {
            case 1:
                if (single_deck_ships.size() < 3) {
                    Ship ship = new Ship(1);
                    ship.addPosition(p);
                    single_deck_ships.add(ship);
                    getBoard().getBlockAtPosition(p).setTop_resId(R.drawable.naval);
                    uPositions.add(p);
                }
                if (single_deck_ships.size() == 3) {
                    first.setEnabled(true);
                    second.setEnabled(true);
                    third.setEnabled(true);
                    pos_position =null;
                }
                break;
            case 2:

                if (double_deck_ships.size() < 2 || !double_deck_ships.get(double_deck_ships.size() - 1).isSheepSeted()) {
                    Ship ship = new Ship(2);
                    ship.addPosition(p);
                    if (double_deck_ships.isEmpty()) {
                        double_deck_ships.add(ship);
                        getBoard().getBlockAtPosition(p).setTop_resId(R.drawable.naval);
                        uPositions.add(p);
                        pos_position = double_deck_ships.get(double_deck_ships.size() - 1).posiblePositions();
                        for (Position pos : pos_position) {
                            getBoard().getBlockAtPosition(pos).setBackground_resId(R.drawable.missed);
                        }
                    } else {
                        if (!double_deck_ships.get(double_deck_ships.size() - 1).isSheepSeted()) {
                            if (double_deck_ships.get(double_deck_ships.size() - 1).posiblePositions().contains(p)) {
                                for (Position pos : pos_position) {
                                    getBoard().getBlockAtPosition(pos).setBackground_resId(R.drawable.sq_naval);
                                }
                                double_deck_ships.get(double_deck_ships.size() - 1).addPosition(p);
                                getBoard().getBlockAtPosition(p).setTop_resId(R.drawable.naval);
                                uPositions.add(p);
                            }
                        } else {
                            double_deck_ships.add(ship);
                            getBoard().getBlockAtPosition(p).setTop_resId(R.drawable.naval);
                            uPositions.add(p);
                            pos_position = double_deck_ships.get(double_deck_ships.size() - 1).posiblePositions();
                            for (Position pos : pos_position) {
                                getBoard().getBlockAtPosition(pos).setBackground_resId(R.drawable.missed);
                            }
                        }

                    }
                }
                if (double_deck_ships.size() == 2 && double_deck_ships.get(double_deck_ships.size() - 1).isSheepSeted()) {
                    first.setEnabled(true);
                    second.setEnabled(true);
                    third.setEnabled(true);
                    pos_position = null;
                }
                break;
            case 3:
                if (!three_deck_ship.isSheepSeted()) {
                    //three_deck_ship.addPosition(p);
                    if (pos_position != null) {
                        if(pos_position.contains(p)){
                            three_deck_ship.addPosition(p);
                        }else{
                            return;
                        }
                        for (Position pos : pos_position) {
                            getBoard().getBlockAtPosition(pos).setBackground_resId(R.drawable.sq_naval);
                        }
                    }else{
                        three_deck_ship.addPosition(p);
                    }
                    pos_position = three_deck_ship.posiblePositions();
                    Log.e("3p", pos_position.toString());
                    for (Position pos : pos_position) {

                        getBoard().getBlockAtPosition(pos).setBackground_resId(R.drawable.missed);
                    }


                        getBoard().getBlockAtPosition(p).setTop_resId(R.drawable.naval);
                        uPositions.add(p);
                    }

                if (three_deck_ship.isSheepSeted()) {
                    first.setEnabled(true);
                    second.setEnabled(true);
                    third.setEnabled(true);
                    if(pos_position!=null){
                    for (Position pos : pos_position) {
                        getBoard().getBlockAtPosition(pos).setBackground_resId(R.drawable.sq_naval);
                    }
                    pos_position = null;
                    }
                }
                break;
            default:
                break;
        }
        if (uPositions.size() == count_of_positions) {
            u_prepaired = true;
         //   Toast.makeText(context, "u prepere :" + "u_p=" + u_prepaired, Toast.LENGTH_SHORT).show();

        }
        Log.e("p=====", double_deck_ships.size() + "");


    }


    private void rivalprepere(Position position) {
        //Toast.makeText(context, "rival prepere :" + "u_p=" + u_prepaired + "r_p=" + r_prepaired + "ready=" + ready + ",isFinished=" + isFinished + "isPlayerTurn=" + player.isPlayerTurn(), Toast.LENGTH_SHORT).show();

        if (rivalPositions.contains(position)) {
            return;
        }
        rivalPositions.add(position);
        if (rivalPositions.size() == count_of_positions) {
            r_prepaired = true;
        }

    }

    @Override
    public void startNewGame() {
        setFinished(false);
        setReady(true);
        board = new NavalBattleBoard();
        rivalBoard = new NavalBattleBoard();
        boardGameAdapter = new BoardGameAdapter(context, this.board, R.id.naval_square_background, R.id.naval_piece);

        rivalBoardAdapter = new BoardGameAdapter(context, this.rivalBoard, R.id.naval_square_background, R.id.naval_piece);
        gridView.setAdapter(boardGameAdapter);
        gridView.setNumColumns(getBoard().getNumCell());
        boardGameAdapter.notifyDataSetChanged();
        rivalGridView.setAdapter(rivalBoardAdapter);
        rivalGridView.setNumColumns(rivalBoard.getNumCell());
        rivalBoardAdapter.notifyDataSetChanged();
        single_deck_ships = new ArrayList<Ship>();
        double_deck_ships = new ArrayList<Ship>();
        three_deck_ship = new Ship(3);
        number_of_decks = -1;
        rivalPositions = new ArrayList<Position>();
        uPositions = new ArrayList<Position>();
        hit_number = 0;
        rival_hit_number = 0;
        u_prepaired = false;
        r_prepaired = false;
        first.setEnabled(true);
        second.setEnabled(true);
        third.setEnabled(true);
        exitGame.setVisibility(View.VISIBLE);
        start.setVisibility(View.INVISIBLE);
        Toast.makeText(context, "Please set decks on board", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void rivalMoved(String position) {
        //Toast.makeText(context, "rival mooved :" + "u_p=" + u_prepaired + "r_p=" + r_prepaired + "ready=" + ready + ",isFinished=" + isFinished + "isPlayerTurn=" + player.isPlayerTurn(), Toast.LENGTH_SHORT).show();

        Position p = positionFromString(position);
        if (!r_prepaired) {
            rivalprepere(p);
            return;
        }
      
        if (u_prepaired && r_prepaired) {
            if (uPositions.contains(p)) {
                rivalHit(p);
                return;
            }
            player.setPlayerTurn(true);

            Toast.makeText(context, "Rival didn't hit, your turn", Toast.LENGTH_SHORT).show();
            board.getBlockAtPosition(p).setTop_resId(R.drawable.missed);
            boardGameAdapter.notifyDataSetChanged();
        }


    }

    public void rivalHit(Position p) {
        Log.e("r", ": Rival Hit");
        board.getBlockAtPosition(p).setTop_resId(R.drawable.boom);
        boardGameAdapter.notifyDataSetChanged();
        rival_hit_number++;
        player.setPlayerTurn(false);
        Toast.makeText(context,"Rival hitted", Toast.LENGTH_SHORT).show();

        if (rival_hit_number == count_of_positions) {
            rival_win++;
            Log.e("f", ": finish");
            finishGame(null);
        }

    }

    @Override
    public void setVisibitlity(int visibitlity) {
        start.setVisibility(visibitlity);
        exitGame.setVisibility(visibitlity);

    }

    @Override
    public void analyzeGame(Position position) {
        if (rivalPositions.contains(position)) {
            rivalBoard.getBlockAtPosition(position).setTop_resId(R.drawable.boom);
            rivalBoardAdapter.notifyDataSetChanged();
            Toast.makeText(context,"you hited,your turn", Toast.LENGTH_SHORT).show();
            hit_number++;
            if (hit_number == count_of_positions) {
                you_win++;
                finishGame(null);
            }
            return;
        }
        Toast.makeText(context,"you missed,rival turn", Toast.LENGTH_SHORT).show();
        rivalBoard.getBlockAtPosition(position).setTop_resId(R.drawable.missed);
        rivalBoardAdapter.notifyDataSetChanged();
        player.setPlayerTurn(false);


    }

    @Override
    public void finishGame(ArrayList<Position> positions) {
        setFinished(true);
        start.setText("New Game");
        start.setVisibility(View.VISIBLE);
        textView.setText("W="+you_win+",L="+rival_win);

    }

    @Override
    public Player getWinner() {
        return null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState, final BluetoothFragment bluetoothFragment) {
        gridView = (GridView) view.findViewById(R.id.your_board);
        rivalGridView = (GridView) view.findViewById(R.id.rival_board);
        first = (Button) view.findViewById(R.id.first);
        Toast.makeText(context, "Please connect to other device", Toast.LENGTH_SHORT).show();
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number_of_decks = 1;
                Toast.makeText(context,"Please put 3 single-deck ship on board",Toast.LENGTH_SHORT).show();
                first.setEnabled(false);
                second.setEnabled(false);
                third.setEnabled(false);
            }
        });
        second = (Button) view.findViewById(R.id.second);
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number_of_decks = 2;
                Toast.makeText(context,"Please put 2 double-deck ship on board",Toast.LENGTH_SHORT).show();
                first.setEnabled(false);
                second.setEnabled(false);
                third.setEnabled(false);
            }
        });
        third = (Button) view.findViewById(R.id.third);
        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number_of_decks = 3;
                Toast.makeText(context,"Please put 1 three-deck ship on board",Toast.LENGTH_SHORT).show();
                first.setEnabled(false);
                second.setEnabled(false);
                third.setEnabled(false);
            }
        });
        start = (Button) view.findViewById(R.id.start);
        textView = (TextView) view.findViewById(R.id.text_id);
        ready_btn = (Button) view.findViewById(R.id.ready);
        ready_btn.setVisibility(View.INVISIBLE);

        first.setEnabled(false);
        second.setEnabled(false);
        third.setEnabled(false);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothFragment.isConnected()) {
                    player = setPlayer(GameConstants.PlayerContsants.FIRST_PLAYER);
                    ready = true;

                    startNewGame();

                    bluetoothFragment.sendMessage(GameConstants.NEW_GAME);
                    start.setVisibility(View.INVISIBLE);

                }
            }
        });
        start.setVisibility(View.INVISIBLE);

        exitGame = (Button) view.findViewById(R.id.exit);
        exitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Exiting Game")
                        .setMessage("Are you sure you want to quit the game?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                bluetoothFragment.sendMessage(GameConstants.EXIT_GAME);
                                exit();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        gridView.setAdapter(boardGameAdapter);
        gridView.setNumColumns(board.getNumCell());
        rivalGridView.setAdapter(rivalBoardAdapter);
        rivalGridView.setNumColumns(rivalBoard.getNumCell());


    }

    @Override
    public void setupBoard(final BluetoothFragment bluetoothFragment) {

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                if (!u_prepaired && number_of_decks != -1 && !isFinished) {
                    if (single_deck_ships.size() == 3 && double_deck_ships.size() == 2 && three_deck_ship.isSheepSeted()) {
                        u_prepaired = true;
                    }

                    preperForGame(getBoard().refactorToPostion(position).toString());
                    boardGameAdapter.notifyDataSetChanged();
                    bluetoothFragment.sendMessage(getBoard().refactorToPostion(position).toString());
                }
            }
        });
        rivalGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 if (bluetoothFragment.isConnected() && ready && u_prepaired && r_prepaired && !isFinished() && getPlayer().isPlayerTurn()) {
                    play(rivalBoard.refactorToPostion(position).toString());
                    //  boardGameAdapter.notifyDataSetChanged();
                    if (u_prepaired && r_prepaired) {
                        bluetoothFragment.sendMessage(rivalBoard.refactorToPostion(position).toString());

                        rivalBoardAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

    }
}
