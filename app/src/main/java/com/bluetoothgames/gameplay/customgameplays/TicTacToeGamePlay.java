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
import com.bluetoothgames.gameplay.Block;
import com.bluetoothgames.gameplay.Board;
import com.bluetoothgames.gameplay.GameConstants;
import com.bluetoothgames.gameplay.GamePlay;
import com.bluetoothgames.gameplay.Player;
import com.bluetoothgames.gameplay.customboards.TicTacToeBoard;

import java.util.ArrayList;

import static com.bluetoothgames.gameplay.GameConstants.*;

/**
 * Created by Karen on 5/10/2016.
 */
public class TicTacToeGamePlay extends GamePlay {
    private int first_side_id;
    private int second_side_id;
    private int movecount;
    private Button startGame;
    private Button exitGame;
    private TextView textView;

    private int you_win=0;
    private int rival_win=0;
    private int no_one_win=0;

    public TicTacToeGamePlay(Context context,Board board) {
        this.first_side_id = R.drawable.x;
        this.second_side_id = R.drawable.o;
        this.context=context;
        setReady(false);
        movecount = 0;
        this.board = board;
        boardGameAdapter=new BoardGameAdapter(context,this.board,R.id.square_background,R.id.piece);
     //   board.clearBoard();

    }

    @Override
    public void play(String position) {

        Position p = positionFromString(position);

        for (Block b : board.getBlocks()) {
            if (b.getPosition() == null) {
                Log.e("b.pos=", "null");
            } else

                Log.e("b.pos=", b.getPosition().toString());
        }

        if (player.isPlayerTurn() && !isFinished) {
            player.changePlayerTurn();
            movecount++;
            Block b = board.getBlockAtPosition(p);
            if(!b.isEmpty()){
                player.changePlayerTurn();
                return;
            }
            int in_position = board.refactorFromPosition(p);
            switch (player.getPlayerSide()) {
                case BoardConstants.X:
                    b = addX(b);
                    break;
                case BoardConstants.Y:
                    b = addY(b);
                    break;
            }
            board.getBlocks().set(in_position, b);
            if (movecount > 2) {
                analyzeGame(p);
            }
            boardGameAdapter.notifyDataSetChanged();
        }


    }

    @Override
    public void startNewGame() {
        setFinished(false);
        setReady(true);
        exitGame.setVisibility(View.VISIBLE);

        movecount=0;
        setBoard(new TicTacToeBoard());
        boardGameAdapter = new BoardGameAdapter(context,getBoard(),R.id.square_background,R.id.piece);
        gridView.setAdapter(boardGameAdapter);
        gridView.setNumColumns(getBoard().getNumCell());
        boardGameAdapter.notifyDataSetChanged();

    }

    @Override
    public void rivalMoved(String position) {
        Position p = positionFromString(position);
        int in_position = board.refactorFromPosition(p);

        if (!isFinished) {
            player.changePlayerTurn();
            movecount++;
            Block b = board.getBlockAtPosition(p);
            Log.e("b_p=", position + " refactoted as" + p.toString());
            switch (player.getPlayerSide()) {
                case BoardConstants.X:
                    b = addY(b);

                    break;
                case BoardConstants.Y:
                    b = addX(b);
                    break;
            }
            board.getBlocks().set(in_position, b);
        }
        if (movecount > 2) {
            analyzeGame(p);
        }
       // player.changePlayerTurn();
        boardGameAdapter.notifyDataSetChanged();

    }

    @Override
    public void setVisibitlity(int visibitlity) {
        startGame.setVisibility(visibitlity);
        exitGame.setVisibility(visibitlity);

    }

    @Override
    public void analyzeGame(Position p) {
        // Position p=positionFromString(position);
        int belong_side = board.getBlockAtPosition(p).getBelongs_side();
        ArrayList<Position> positions = new ArrayList<Position>();


        int n = board.getNumCell();

        for (int i = 1; i <= n; i++) {
            if (board.getBlockAtPosition(new Position(p.getX(), i)).getBelongs_side() != belong_side)
                break;
            else {
                positions.add(new Position(p.getX(), i));
            }
            if (i == n) {
                finishGame(positions);
                return;
            }
        }
        positions = new ArrayList<Position>();

        for (int i = 1; i <= n; i++) {
            if (board.getBlockAtPosition(new Position(i, p.getY())).getBelongs_side() != belong_side)
                break;
            else {
                positions.add(new Position(i, p.getY()));
            }
            if (i == n) {
                finishGame(positions);
                return;
            }
        }
        positions = new ArrayList<Position>();

        for (int i = 1; i <= n; i++) {
            if (board.getBlockAtPosition(new Position(i, i)).getBelongs_side() != belong_side)
                break;
            else {
                positions.add(new Position(i, i));
            }
            if (i == n) {
                finishGame(positions);
                return;
            }
        }


        positions = new ArrayList<Position>();

        for (int i = 0; i < n; i++) {
            if (board.getBlockAtPosition(new Position(i + 1, n - i)).getBelongs_side() != belong_side)
                break;
            else {
                positions.add(new Position(i+1, n-i));
            }
            if (i == n - 1) {
                finishGame(positions);
                return;
            }
        }
        if (movecount >= board.getNumCell() * board.getNumCell()) {
            isFinished = true;
            no_one_win++;
            textView.setText("W="+you_win+",L="+rival_win+",D="+no_one_win);
            return;
        }

    }

    @Override
    public void finishGame(ArrayList<Position> positions) {
        isFinished=true;
       int winer_id=-1;
        if(positions.size()>2){
            int res=-1;
            switch (board.getBlockAtPosition(positions.get(0)).getBelongs_side()) {
                case BoardConstants.X:
                    res = R.drawable.sq_green;
                    winer_id= PlayerContsants.FIRST_PLAYER;
                    break;
                case BoardConstants.EMPTY:
                    break;
                case BoardConstants.Y:
                    res=R.drawable.sq_red;
                    winer_id= PlayerContsants.SECOND_PLAER;
                    default:
                        break;
            }
            for (Position p:
                 positions) {
                board.getBlockAtPosition(p).setBackground_resId(res);

            }
            setWinner(winer_id);
            textView.setText("W="+you_win+",L="+rival_win+",D="+no_one_win);

        }

    }
    private void setWinner(int id){
        if(player.getId()==id){
            you_win++;
            player.setPlayerWins(true);
        }
        else{
            rival_win++;
            player.setPlayerWins(false);
        }

    }

    private Block addY(Block b) {
        b.setTop_resId(second_side_id);
        b.setBelongs_side(BoardConstants.Y);
        return b;
    }

    private Block addX(Block b) {
        b.setTop_resId(first_side_id);
        b.setBelongs_side(BoardConstants.X);
        return b;
    }

    @Override
    public Player getWinner() {
        return null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState,final BluetoothFragment bluetoothFragment) {
        gridView = (GridView) view.findViewById(R.id.grid);
        startGame = (Button) view.findViewById(R.id.game_btn);
        Toast.makeText(context, "Connect to other device", Toast.LENGTH_SHORT).show();
        startGame.setVisibility(View.INVISIBLE);
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothFragment.isConnected()) {
                    player = setPlayer(GameConstants.PlayerContsants.FIRST_PLAYER);
                    ready = true;
                    startNewGame();
                    bluetoothFragment.sendMessage(GameConstants.NEW_GAME);
                }
            }
        });
        exitGame = (Button) view.findViewById(R.id.exit_btn);
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
        textView= (TextView) view.findViewById(R.id.text_id);
        gridView.setAdapter(boardGameAdapter);
        gridView.setNumColumns(getBoard().getNumCell());

    }

    @Override
    public void setupBoard(final BluetoothFragment bluetoothFragment) {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              //  Toast.makeText(context, "ready="+ready+",isFinished="+isFinished+"isPlayerTurn="+player.isPlayerTurn(), Toast.LENGTH_SHORT).show();

                if (bluetoothFragment.isConnected()&&ready&&!isFinished()&&getPlayer().isPlayerTurn()) {
                    play(getBoard().refactorToPostion(position).toString());
                    //  boardGameAdapter.notifyDataSetChanged();
                    if(!getPlayer().isPlayerTurn()) {
                       bluetoothFragment.sendMessage(getBoard().refactorToPostion(position).toString());
                        //boardGameAdapter = new BoardGameAdapter(context,gamePlay.getBoard());
                        boardGameAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

    }

}
