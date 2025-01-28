package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Path {

    private static final Logger logger = LogManager.getLogger();

    private String path;
    private Maze mazeToSolve;
    private Position currentPosition;
    private Direction currentDirection;

    public Path(Maze mazeToSolve) {
        this.mazeToSolve = mazeToSolve;
        this.currentPosition = new Position(mazeToSolve.getEntryRow(), mazeToSolve.getEntryColumn());
        this.currentDirection = new Direction(mazeToSolve.getEntryRow(), mazeToSolve.getEntryColumn(), Direction.Directions.EAST);
    }

    public String getPath() {
        return this.path;
    }

    public String findPath() throws Exception {

        int exitColumn = mazeToSolve.getExitColumn();
        int exitRow = mazeToSolve.getExitRow();

        StringBuilder sequenceOfMoves = new StringBuilder();

        while (currentPosition.getRow() != exitRow || currentPosition.getColumn() != exitColumn) {   

            logger.info("Current Position: (" + currentPosition.getRow() + ", " + currentPosition.getColumn() + ")");

            currentDirection.turnRight(); // attempt to turn right
            Position nextPosition = currentPosition.getNextPosition(currentDirection.getCurrentDirection());

            if (mazeToSolve.validateMove(nextPosition.getRow(), nextPosition.getColumn())) {
                currentDirection.moveForward();
                sequenceOfMoves.append("R");
                currentPosition = nextPosition;

            } else { 
                
                nextPosition = currentPosition.getNextPosition(currentDirection.getCurrentDirection());

                if (mazeToSolve.validateMove(nextPosition.getRow(), nextPosition.getColumn())) { 
                    currentDirection.moveForward(); // otherwise try moving forward
                    sequenceOfMoves.append("F");
                    currentPosition = nextPosition; 

                } else { 
                    currentDirection.turnLeft(); // if all else fails, turn left
                    nextPosition = currentPosition.getNextPosition(currentDirection.getCurrentDirection());
                    if (mazeToSolve.validateMove(nextPosition.getRow(), nextPosition.getColumn())) {
                        currentDirection.moveForward();
                        sequenceOfMoves.append("L");
                        currentPosition = nextPosition;
                }
            }
        
            }
        

            if (currentPosition.getRow() == exitRow && currentPosition.getColumn() == exitColumn) {
                logger.debug("Exit reached at: (" + exitRow + ", " + exitColumn + ")");
                break; 
            }
        }

        return sequenceOfMoves.toString();
    } 

    public String factorizedPath(String sequenceOfMoves) {

        StringBuilder factorizedPath = new StringBuilder();

        int count = 1; // count consecutive identical moves

        for (int i = 0; i < sequenceOfMoves.length() - 1; i++) {

            if (sequenceOfMoves.charAt(i) == ' ') {
                continue; 
            }

            if (sequenceOfMoves.charAt(i) == sequenceOfMoves.charAt(i+1)) {
                count++;
            } else { 

                if (count > 1) { // only append count if there is more than one consecutive move
                    factorizedPath.append(count);
                }
                
                factorizedPath.append(sequenceOfMoves.charAt(i)); // Append the move
                count = 1; // Reset count for the next sequence of moves
            }
        }
        
        
        if (count > 1) {
            factorizedPath.append(count);
        }

        factorizedPath.append(sequenceOfMoves.charAt(sequenceOfMoves.length() - 1)); 
        
        return factorizedPath.toString();

    }

    public Boolean validatePath(String pathToValidate) throws Exception {

        currentPosition = new Position(mazeToSolve.getEntryRow(), mazeToSolve.getEntryColumn());
        currentDirection = new Direction(mazeToSolve.getEntryRow(), mazeToSolve.getEntryColumn(), Direction.Directions.EAST);
        
        pathToValidate = pathToValidate.replaceAll(" ", "");

        StringBuilder factorizedToCanonical = new StringBuilder();

        for (int i = 0; i < pathToValidate.length(); i++) {
            
            if (Character.isDigit(pathToValidate.charAt(i))) {
                int count = Character.getNumericValue(pathToValidate.charAt(i));
                char prevMove = factorizedToCanonical.charAt(factorizedToCanonical.length() - 1);
                
                for (int j = 0; j < count - 1; j++) {
                    factorizedToCanonical.append(prevMove);
                }

            } else {
                factorizedToCanonical.append(pathToValidate.charAt(i)); 
            }
        }

        logger.info("Path to validate: " + factorizedToCanonical.toString());

        for (int i = 0; i < factorizedToCanonical.length(); i++) {
            if (factorizedToCanonical.charAt(i) != 'F' && factorizedToCanonical.charAt(i) != 'L' && factorizedToCanonical.charAt(i) != 'R') {
                return false; 
            }

            switch (factorizedToCanonical.charAt(i)) {
            case 'F':
                currentDirection.moveForward();
                break;
            case 'L':
                currentDirection.turnLeft();
                break;
            case 'R':
                currentDirection.turnRight();
                break;
            default:
                return false; 
            } 

            currentPosition = currentPosition.getNextPosition(currentDirection.getCurrentDirection());

            if (!mazeToSolve.validateMove(currentPosition.getRow(), currentPosition.getColumn())) {
                return false; 
            }
        }

        if (currentPosition.getRow() == mazeToSolve.getExitRow() && currentPosition.getColumn() == mazeToSolve.getExitColumn()) {
            return true; // Path reached the exit
        } else {
            return false; // Path did not reach the exit
        }
    }


}

