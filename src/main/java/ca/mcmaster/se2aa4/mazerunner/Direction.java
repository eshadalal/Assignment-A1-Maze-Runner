package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Direction {

    private static final Logger logger = LogManager.getLogger();

    private int row;
    private int column;
    private Directions currentDirection;

    enum Directions {
        NORTH,
        SOUTH,
        EAST,
        WEST
    }

    public Direction(int row, int column, Directions startingDirection) {
        this.row = row;
        this.column = column;
        this.currentDirection = startingDirection;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public Directions getCurrentDirection() {
        return this.currentDirection;
    }

    public void setCurrentDirection(Directions nextDirection) {
        this.currentDirection = nextDirection;
    }

    public Directions changeDirection(String symbol) throws Exception {
        switch (symbol) {
            case "L":
                return turnLeft();
            case "R":
                return turnRight();
            case "F":
                return currentDirection;
            case " ": 
                return currentDirection;
            default:
                throw new Exception("Not a valid direction.");
        }
    }

    private Directions turnRight() throws Exception {
        switch (currentDirection) {
            case NORTH:
                return Directions.EAST;
            case EAST:
                return Directions.SOUTH;
            case SOUTH:
                return Directions.WEST;
            case WEST:
                return Directions.NORTH;
            default:
                throw new Exception("Not a valid direction.");
        }
    }

    private Directions turnLeft() throws Exception {
        switch (currentDirection) {
            case NORTH:
                return Directions.WEST;
            case WEST:
                return Directions.SOUTH;
            case SOUTH:
                return Directions.EAST;
            case EAST:
                return Directions.NORTH;
            default:
                throw new Exception("Not a valid direction.");
        }
    }

}
