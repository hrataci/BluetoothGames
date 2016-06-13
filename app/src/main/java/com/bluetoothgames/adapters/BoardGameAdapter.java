package com.bluetoothgames.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bluetoothgames.R;
import com.bluetoothgames.bluetooth.logger.Log;
import com.bluetoothgames.boardViews.Position;
import com.bluetoothgames.gameplay.Board;

/**
 * Created by Karen on 5/6/2016.
 */
public class BoardGameAdapter extends BaseAdapter {
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected Board board;
    private int first_id;
    private int second_id;


    public BoardGameAdapter(Context mContext, Board board, int first_id, int second_id) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        this.board = board;
        this.first_id = first_id;
        this.second_id = second_id;

    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public int getCount() {
        return board.getBlocks().size();
    }

    @Override
    public Object getItem(int position) {
        return board.getBlocks().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {  // if it's not recycled, initialize some attributes

            rowView = mInflater.inflate(board.getLayoutResID(), null);
        }
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.square = (ImageView) rowView.findViewById(first_id);
        viewHolder.square.setImageResource(board.getBlocks().get(position).getBackground_resId());
        viewHolder.piece = (ImageView) rowView.findViewById(second_id);
        viewHolder.position = new Position(board.refactorToPostion(position));
        board.getBlocks().get(position).setPosition(viewHolder.position);

        if (position < board.getBlocks().size()) {
            if (!board.getBlocks().get(position).isEmpty()) {
                android.util.Log.e("pos=", position + "");
                viewHolder.piece.setImageResource(board.getBlocks().get(position).getTop_resId());

            }
        } else {
            android.util.Log.e("pos=", position + "");
            ;
        }

        return rowView;
    }

    static class ViewHolder {
        public ImageView square;
        public ImageView piece;
        public Position position;

    }


    /*protected Position refactorToPostion(int position) {
        int x = 0;
        int y = 0;
        y= board.getNumCell() - position / board.getNumCell();
        x = position % board.getNumCell() + 1;
        return new Position(x, y);
    }

    protected int refactorFromPosition(Position position) {

        int result;
        result = (board.getNumCell() - position.getY()) * board.getNumCell() + position.getX() - 1;
        return result;
    }*/
}
