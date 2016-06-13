package com.bluetoothgames;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.bluetoothgames.bluetooth.BluetoothFragment;

import com.bluetoothgames.gameplay.customboards.NavalBattleBoard;
import com.bluetoothgames.gameplay.customboards.TicTacToeBoard;
import com.bluetoothgames.gameplay.customgameplays.NavalBattleGamPlay;
import com.bluetoothgames.gameplay.customgameplays.TicTacToeGamePlay;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        /*TicTacToeBoard board=new TicTacToeBoard();
        TicTacToeGamePlay gamePlay= new TicTacToeGamePlay(this,board);
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            BluetoothFragment fragment = new BluetoothFragment(this,gamePlay,R.layout.tictactoe_layout);
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }*/



        }
    public void ticTacToeOnClick(View v){
        TicTacToeBoard board=new TicTacToeBoard();
        TicTacToeGamePlay gamePlay= new TicTacToeGamePlay(this,board);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            BluetoothFragment fragment = new BluetoothFragment(this,gamePlay,R.layout.tictactoe_layout);
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();


    }
    public void navalOnClick(View v){
        NavalBattleBoard board=new NavalBattleBoard();
        NavalBattleGamPlay gamePlay= new NavalBattleGamPlay(this,board);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            BluetoothFragment fragment = new BluetoothFragment(this,gamePlay,R.layout.navalbattle_layout);
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();

    }

    @Override
    public void onBackPressed() {

    }
}
