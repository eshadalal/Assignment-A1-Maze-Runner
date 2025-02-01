package ca.mcmaster.se2aa4.mazerunner;

public class Position {

    private int row;
    private int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public Position getNextPosition(Direction.Directions direction) throws Exception { // change position based on direction
        switch (direction) {
            case NORTH:
                return new Position(this.row - 1, this.column);
            case EAST:
                return new Position(this.row, this.column + 1);
            case SOUTH:
                return new Position(this.row + 1, this.column);
            case WEST:
                return new Position(this.row, this.column - 1);
            default:
                throw new Exception("Not a valid direction.");
            }
        }


}
