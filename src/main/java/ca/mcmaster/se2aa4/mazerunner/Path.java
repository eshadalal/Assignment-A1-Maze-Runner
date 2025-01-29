package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Path {

    private static final Logger logger = LogManager.getLogger();

    private Maze mazeToSolve;
    private Position currentPosition;
    private Direction currentDirection;

    public Path(Maze mazeToSolve) {
        this.mazeToSolve = mazeToSolve;
        this.currentPosition = new Position(mazeToSolve.getEntryRow(), mazeToSolve.getEntryColumn());
        this.currentDirection = new Direction(mazeToSolve.getEntryRow(), mazeToSolve.getEntryColumn(), Direction.Directions.EAST);
    }

    public String factorizedPath(List<Move> moves) {
        StringBuilder factorizedPath = new StringBuilder();
        int count = 1; 
        
        for (int i = 0; i < moves.size() - 1; i++) {

            if (moves.get(i).getMove() == moves.get(i + 1).getMove()) {
                count++; // count consecutive identical moves
            } else { 
                if (count > 1) { // only append count if there is more than one consecutive move
                    factorizedPath.append(count);
                }

                factorizedPath.append(moves.get(i).getMove()); 
                count = 1; // reset count for next move
            }
        }

        if (count > 1) {
            factorizedPath.append(count);
        }

        factorizedPath.append(moves.get(moves.size() - 1).getMove()); // get last move

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
                    return false; // if just a digit with no moves after it 
                }

                if (pathToValidate.charAt(i + 1) != 'F' && pathToValidate.charAt(i + 1) != 'L' && pathToValidate.charAt(i + 1) != 'R') {
                    return false; // invalid move
                }

                // add the move count times to the path 
                for (int j = 0; j < Character.getNumericValue(pathToValidate.charAt(i)); j++) { 
                    factorizedToCanonical.append(pathToValidate.charAt(i + 1));
                }

                i++; // skip the next move

            } else { // if not a digit, append the move 
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
                    return false; 
            }

            currentPosition = currentPosition.getNextPosition(currentDirection.getCurrentDirection());

            if (!mazeToSolve.validateMove(currentPosition.getRow(), currentPosition.getColumn())) {
                return false; 
            }
        }
        
        logger.info("Final Position: (" + currentPosition.getRow() + ", " + currentPosition.getColumn() + ")");
        return currentPosition.getRow() == mazeToSolve.getExitRow() && currentPosition.getColumn() == mazeToSolve.getExitColumn(); // returns true if its at the exit
    }
}
