package com.bluetoothgames.boardViews;



/**
 * Created by Karen on 4/28/2016.
 */
public class Position {
    private int x;
    private int y;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        return y == position.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position position) {
        this.x=position.getX();
        this.y=position.getY();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Position incrementXY(){

        return new Position(this.getX()+1,this.getY()+1);
    }
    public Position incrementX(){

        return new Position(this.getX()+1,this.getY());
    }
    public Position incrementY(){

        return new Position(this.getX(),this.getY()+1);
    }
    public Position decrementXY(){

        return new Position(this.getX()-1,this.getY()-1);
    }
    public Position decrementX(){

        return new Position(this.getX()-1,this.getY());

    }
    public Position decrementY(){

        return new Position(this.getX(),this.getY()-1);
    }
    public boolean isValidPosition(){

        if(this.x>0&&this.y>0&&this.x<=6&&this.y<=6){
            return true;
        }
        return false;
    }
    @Override
    public String toString() {
        return x+ "," + y;
    }
}
