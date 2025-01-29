package ca.mcmaster.se2aa4.mazerunner;

public class Direction {

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
                moveForward();
                return currentDirection;
            case " ": 
                return currentDirection;
            default:
                throw new Exception("Not a valid direction.");
        }
        
    }

    public Directions turnRight() {
        switch (currentDirection) {
            case NORTH:
                currentDirection = Directions.EAST;
                break;
            case EAST:
                currentDirection = Directions.SOUTH;
                break;
            case SOUTH:
                currentDirection = Directions.WEST;
                break;
            case WEST:
                currentDirection = Directions.NORTH;
                break;
        }

        return currentDirection;
    }

    public Directions turnLeft() {
        switch (currentDirection) {
            case NORTH:
                currentDirection = Directions.WEST;
                break;
            case WEST:
                currentDirection = Directions.SOUTH;
                break;
            case SOUTH:
                currentDirection = Directions.EAST;
                break;
            case EAST:
                currentDirection = Directions.NORTH;
                break;
        }

        return currentDirection;

    }

    public Position moveForward() {
        switch (currentDirection) {
            case NORTH:
                this.row -= 1;
                break;
            case EAST:
                this.column += 1;
                break;
            case SOUTH:
                this.row += 1;
                break;
            case WEST:
                this.column -= 1;
                break;
        }
        
        return new Position(this.row, this.column); 

    }


}

