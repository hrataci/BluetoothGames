package com.bluetoothgames.bluetooth;

import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.GridView;

import android.widget.Toast;

import com.bluetoothgames.R;
import com.bluetoothgames.adapters.BoardGameAdapter;
import com.bluetoothgames.bluetooth.logger.Log;


import com.bluetoothgames.gameplay.GameConstants;
import com.bluetoothgames.gameplay.GamePlay;
import com.bluetoothgames.gameplay.Player;

import java.util.ArrayList;

/**
 * Created by Karen on 5/3/2016.
 */
public class BluetoothFragment extends Fragment {

    private static final String TAG = "BluetoothFragment";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    private GamePlay gamePlay;
    private Player player;
    private Context context;
    private BoardGameAdapter boardGameAdapter;
    private boolean ready;

    private GridView gridView;
    private int layoout_res_id;
    private boolean isConnected = false;

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    /**
     * Name of the connected device
     */
    private String mConnectedDeviceName = null;

    /**
     * Array adapter for the conversation thread
     */
    // private ArrayAdapter<String> mConversationArrayAdapter;

    /**
     * String buffer for outgoing messages
     */
    private StringBuffer mOutStringBuffer;

    /**
     * Local Bluetooth adapter
     */
    private BluetoothAdapter mBluetoothAdapter = null;

    /**
     * Member object for the chat services
     */
    private BluetoothService mChatService = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            FragmentActivity activity = getActivity();
            Toast.makeText(activity, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            activity.finish();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        } else if (mChatService == null) {
            setupBoard();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mChatService != null) {
            mChatService.stop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothService.STATE_NONE) {
                // Start the Bluetooth chat services
                mChatService.start();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layoout_res_id, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        gamePlay.onViewCreated(view, savedInstanceState, this);
     /*   gridView = (GridView) view.findViewById(R.id.grid);
        startGame = (Button) view.findViewById(R.id.game_btn);
        startGame.setVisibility(View.INVISIBLE);
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player = gamePlay.setPlayer(GameConstants.PlayerContsants.FIRST_PLAYER);
                ready=true;
                if(gamePlay.isFinished()){
                    gamePlay.startNewGame();
                    boardGameAdapter = new BoardGameAdapter(context,gamePlay.getBoard());
                    gridView.setAdapter(boardGameAdapter);
                    gridView.setNumColumns(gamePlay.getBoard().getNumCell());
                    sendMessage("New Game");
                }
            }
        });
        exitGame = (Button) view.findViewById(R.id.exit_btn);
        exitGame.setVisibility(View.INVISIBLE);
        exitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        gridView.setAdapter(boardGameAdapter);
        gridView.setNumColumns(gamePlay.getBoard().getNumCell());*/

    }

    /**
     * Set up the UI and background operations for chat.
     */
    private void setupBoard() {


       /* gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (ready&&!gamePlay.isFinished()&&gamePlay.getPlayer().isPlayerTurn()) {
                    gamePlay.play(gamePlay.getBoard().refactorToPostion(position).toString());
                  //  boardGameAdapter.notifyDataSetChanged();
                    if(!gamePlay.getPlayer().isPlayerTurn()) {
                        sendMessage(gamePlay.getBoard().refactorToPostion(position).toString());
                        //boardGameAdapter = new BoardGameAdapter(context,gamePlay.getBoard());
                        boardGameAdapter.notifyDataSetChanged();
                    }
                    }
            }
        });*/
        gamePlay.setupBoard(this);

        // Initialize the BluetoothChatService to perform bluetooth connections
        mChatService = new BluetoothService(getActivity(), mHandler);

        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
    }

    /**
     * Makes this device discoverable.
     */
    private void ensureDiscoverable() {
        if (mBluetoothAdapter.getScanMode() !=
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }

    /**
     * Sends a message.
     *
     * @param message A string of text to send.
     */
    public void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothService.STATE_CONNECTED) {
            Toast.makeText(getActivity(), R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mChatService.write(send);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
            //  mOutEditText.setText(mOutStringBuffer);
        }
    }


    /**
     * Updates the status on the action bar.
     *
     * @param resId a string resource ID
     */
    private void setStatus(int resId) {
        FragmentActivity activity = getActivity();
        if (null == activity) {
            return;
        }
        final ActionBar actionBar = activity.getActionBar();
        if (null == actionBar) {
            return;
        }
        actionBar.setSubtitle(resId);
    }

    /**
     * Updates the status on the action bar.
     *
     * @param subTitle status
     */
    private void setStatus(CharSequence subTitle) {
        FragmentActivity activity = getActivity();
        if (null == activity) {
            return;
        }
        final ActionBar actionBar = activity.getActionBar();
        if (null == actionBar) {
            return;
        }
        actionBar.setSubtitle(subTitle);
    }

    /**
     * The Handler that gets information back from the BluetoothChatService
     */
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            FragmentActivity activity = getActivity();
            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                            setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                            isConnected = true;
                            //    ready=true;
                            gamePlay.setReady(true);
                            //    mConversationArrayAdapter.clear();
                            break;
                        case BluetoothService.STATE_CONNECTING:
                            setStatus(R.string.title_connecting);
                            break;
                        case BluetoothService.STATE_LISTEN:
                        case BluetoothService.STATE_NONE:
                            isConnected = false;
                            gamePlay.setFinished(true);
                            setStatus(R.string.title_not_connected);
                            break;
                    }
                    break;
                case Constants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                  /*  if(!writeMessage.equals(GameConstants.NEW_GAME)) {
                        Toast.makeText(getContext(), gamePlay.getPlayer().getId() + "  pressed  " + writeMessage + " ---" + gamePlay.getBoard().getBlockAtPosition(gamePlay.positionFromString(writeMessage)).getBelongs_side() + "refactorToInt"
                                + gamePlay.getBoard().refactorFromPosition(gamePlay.positionFromString(writeMessage)), Toast.LENGTH_SHORT).show();
                    }else{
                       *//* gamePlay.startNewGame();
                        gridView.setNumColumns(gamePlay.getBoard().getNumCell());
                        gridView.setNumColumns(gamePlay.getBoard().getNumCell());*//*
                    }*/
                    //     mConversationArrayAdapter.add("Me:  " + writeMessage);
                    break;
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    if (!readMessage.equals(GameConstants.NEW_GAME)&&!readMessage.equals(GameConstants.EXIT_GAME)) {
                        //  ready = true;
                        gamePlay.setReady(true);
                        gamePlay.rivalMoved(readMessage);
                        //      boardGameAdapter.notifyDataSetChanged();
                        //  boardGameAdapter = new BoardGameAdapter(context,gamePlay.getBoard());
                    /*Toast.makeText(getContext(), gamePlay.getPlayer().getId() + " he pressed  " + readMessage + "---" + gamePlay.getBoard().getBlockAtPosition(gamePlay.positionFromString(readMessage)).getBelongs_side() + "refactorToInt"
                            + gamePlay.getBoard().refactorFromPosition(gamePlay.positionFromString(readMessage)), Toast.LENGTH_SHORT).show();
            */
                    } else {
                        if (readMessage.equals(GameConstants.EXIT_GAME)) {
                            gamePlay.exit();
                        }

                        gamePlay.startNewGame();
                   /* boardGameAdapter = new BoardGameAdapter(context,gamePlay.getBoard());
                    gridView.setAdapter(boardGameAdapter);
                    gridView.setNumColumns(gamePlay.getBoard().getNumCell());*/
                    }
                    //      mConversationArrayAdapter.add(mConnectedDeviceName + ":  " + readMessage);
                    break;
                case Constants.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
                    if (null != activity) {
                        Toast.makeText(activity, "Connected to "
                                + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Constants.MESSAGE_TOAST:
                    if (null != activity) {
                        Toast.makeText(activity, msg.getData().getString(Constants.TOAST),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_SECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, true);
                }
                break;
            case REQUEST_CONNECT_DEVICE_INSECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, false);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    setupBoard();
                } else {
                    // User did not enable Bluetooth or an error occurred
                    Log.d(TAG, "BT not enabled");
                    Toast.makeText(getActivity(), R.string.bt_not_enabled_leaving,
                            Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
        }
    }

    /**
     * Establish connection with other divice
     *
     * @param data   An {@link Intent} with {@link DeviceListActivity#EXTRA_DEVICE_ADDRESS} extra.
     * @param secure Socket Security type - Secure (true) , Insecure (false)
     */
    private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras()
                .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mChatService.connect(device, secure);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.bluetooth_board, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.secure_connect_scan: {
                // Launch the DeviceListActivity to see devices and do scan
                Intent serverIntent = new Intent(getActivity(), DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
               /* startGame.setVisibility(View.VISIBLE);
                exitGame.setVisibility(View.VISIBLE);*/
                gamePlay.setVisibitlity(View.VISIBLE);
                return true;
            }
            case R.id.insecure_connect_scan: {
                // Launch the DeviceListActivity to see devices and do scan
                Intent serverIntent = new Intent(getActivity(), DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_INSECURE);
                /*startGame.setVisibility(View.VISIBLE);
                exitGame.setVisibility(View.VISIBLE);*/
                gamePlay.setVisibitlity(View.VISIBLE);
                return true;
            }
            case R.id.discoverable: {
                // Ensure this device is discoverable by others
                ensureDiscoverable();
                return true;
            }
        }
        return false;
    }

   /* private Position refactorPostion(int position) {
        int x = 0;
        int y = 0;
        y = 8 - position / 8;
        x = position % 8 + 1;
        return new Position(x, y);
    }

    private int refactorFromPosition(Position position) {
        int result;
        result = (8 - position.getY()) * 8 + position.getX() - 1;
        return result;
    }*/

    public BluetoothFragment(Context context, GamePlay gamePlay, int layoout_res_id) {
        this.context = context;
        this.gamePlay = gamePlay;
        this.layoout_res_id = layoout_res_id;
        player = gamePlay.setPlayer(GameConstants.PlayerContsants.SECOND_PLAER);
        //   boardGameAdapter = new BoardGameAdapter(context,gamePlay.getBoard());
        // ready=false;
        //gamePlay.setReady(false);
    }

}
