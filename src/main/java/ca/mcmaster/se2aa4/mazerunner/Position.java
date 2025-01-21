package ca.mcmaster.se2aa4.mazerunner;

public class Position {

    private int row; 
    private int column;
    private Direction direction;

    public Position(int row, int column, char startingDirection) {
        this.row = row;
        this.column = column;
        this.direction = new Direction(startingDirection);
    }

    public int getRow() { 
        return row;
    }

    public int getColumn() {
        return column;
    }

    public char getDirection() {
        return direction.getDirection();
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void moveForward() {
        switch (direction.getDirection()) {
            case 'F':
                row--; 
                break;
            case 'R':
                column++; 
                break;
            case 'L':
                column--; 
                break;
        }
    }

    public void turnRight() {
        direction.turnRight(); 
    }

    public void turnLeft() {
        direction.turnLeft();
    }
}

