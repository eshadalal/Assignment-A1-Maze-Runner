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
            
            Position nextPosition = currentPosition.getNextPosition(currentDirection.getCurrentDirection());
            if (mazeToSolve.validateMove(nextPosition.getRow(), nextPosition.getColumn())) {
                currentDirection.moveForward();
                sequenceOfMoves.append("F");
                currentPosition = nextPosition;
            } else { 
                currentDirection.turnRight();
                nextPosition = currentPosition.getNextPosition(currentDirection.getCurrentDirection());
                if (mazeToSolve.validateMove(nextPosition.getRow(), nextPosition.getColumn())) {
                    currentDirection.moveForward();
                    sequenceOfMoves.append("R");
                    currentPosition = nextPosition;
                } else { 
                    currentDirection.turnLeft();
                    nextPosition = currentPosition.getNextPosition(currentDirection.getCurrentDirection());
                    if (mazeToSolve.validateMove(nextPosition.getRow(), nextPosition.getColumn())) {
                        currentDirection.moveForward();
                        sequenceOfMoves.append("L");
                        currentPosition = nextPosition;
                    }
                }
            }
            
            if (currentPosition.getRow() == exitRow && currentPosition.getColumn() == exitColumn) {
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
                
                factorizedPath.append(sequenceOfMoves.charAt(i)); 
                count = 1; // reset count 
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

        pathToValidate = pathToValidate.replaceAll(" ", ""); // replace spaces

        StringBuilder factorizedToCanonical = new StringBuilder();

        for (int i = 0; i < pathToValidate.length(); i++) {

            if (Character.isDigit(pathToValidate.charAt(i))) {
                if (i + 1 >= pathToValidate.length()) {
                    return false;
                }

                if (pathToValidate.charAt(i + 1) != 'F' && pathToValidate.charAt(i + 1) != 'L' && pathToValidate.charAt(i + 1) != 'R') {
                    return false; 
                }

                int count = Character.getNumericValue(pathToValidate.charAt(i));
                for (int j = 0; j < count; j++) {
                    factorizedToCanonical.append(pathToValidate.charAt(i + 1));
                }

                i++; 
            } else {
                if (pathToValidate.charAt(i) != 'F' && pathToValidate.charAt(i) != 'L' && pathToValidate.charAt(i) != 'R') {
                    return false; 
                }

                factorizedToCanonical.append(pathToValidate.charAt(i));
            }
        }

        logger.info("Path to validate: " + factorizedToCanonical.toString());

        for (int i = 0; i < factorizedToCanonical.length(); i++) {

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
                    return false; // Invalid move
            }

            currentPosition = currentPosition.getNextPosition(currentDirection.getCurrentDirection());

            if (!mazeToSolve.validateMove(currentPosition.getRow(), currentPosition.getColumn())) {
                return false; // Invalid move within the maze
            }
        }

        // Check if the final position matches the maze exit
        return currentPosition.getRow() == mazeToSolve.getExitRow() &&
            currentPosition.getColumn() == mazeToSolve.getExitColumn();
    }

}

