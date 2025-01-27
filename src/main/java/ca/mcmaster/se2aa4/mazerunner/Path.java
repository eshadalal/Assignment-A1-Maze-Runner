package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Path {

    private static final Logger logger = LogManager.getLogger();

    private String path;
    private Maze mazeToSolve;

    public Path(Maze mazeToSolve) {
        this.mazeToSolve = mazeToSolve;
    }

    Position currentPosition = new Position(mazeToSolve.getEntryRow(), mazeToSolve.getEntryColumn());
    Direction currentDirection = new Direction(mazeToSolve.getEntryRow(), mazeToSolve.getEntryColumn(), Direction.Directions.EAST);

    public String getPath() {
        return this.path;
    }

    public String findPath() {

        int entryColumn = mazeToSolve.getEntryColumn();
        int entryRow = mazeToSolve.getEntryRow();
        int exitColumn = mazeToSolve.getExitColumn();
        int exitRow = mazeToSolve.getExitRow();

        StringBuilder sequenceOfMoves = new StringBuilder();

        while (entryRow != exitRow || entryColumn != exitColumn) {   

            currentDirection.turnRight(); // attempt to turn right

            if (mazeToSolve.validateMove(currentPosition.getRow(), currentPosition.getColumn())) { 
                currentDirection.moveForward();
                sequenceOfMoves.append("R");

            } else { 
                currentDirection.moveForward(); // otherwise try moving forward

                if (mazeToSolve.validateMove(currentPosition.getRow(), currentPosition.getColumn())) { 
                    currentDirection.moveForward();
                    sequenceOfMoves.append("F");

                } else { 
                    currentDirection.turnLeft(); // if all else fails, turn left
                    currentDirection.moveForward();
                    sequenceOfMoves.append("L");
                }
            }
        }


        return sequenceOfMoves.toString();
    } 

    public String factorizedPath(String sequenceOfMoves) {

        StringBuilder factorizedPath = new StringBuilder();

        int count = 1; // count consecutive identical moves

        for (int i = 0; i < sequenceOfMoves.length() - 1; i++) {

            if (sequenceOfMoves.charAt(i) == sequenceOfMoves.charAt(i+1)) {
                count++;
            } else { 
                factorizedPath.append(count);
                factorizedPath.append(sequenceOfMoves.charAt(i));
                count = 1; // reset count 
            }
    
        }

        return factorizedPath.toString();

    }

    public Boolean validatePath(String pathToValidate) {

        for (int i = 0; i < pathToValidate.length(); i++) {
            if (pathToValidate.charAt(i) != 'F' && pathToValidate.charAt(i) != 'L' && pathToValidate.charAt(i) != 'R' && pathToValidate.charAt(i) != ' ') {
                return false;
            }

            if (pathToValidate.charAt(i) == ' ') {
                continue; // skip spaces
            }

            switch (pathToValidate.charAt(i)) {
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

            if (!mazeToSolve.validateMove(currentPosition.getRow(), currentPosition.getColumn())) {
                return false; 
            }
        }

        return true;

        }


}

