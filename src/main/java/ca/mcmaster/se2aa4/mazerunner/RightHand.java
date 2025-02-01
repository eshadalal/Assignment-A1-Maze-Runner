package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RightHand {

    private static final Logger logger = LogManager.getLogger();

    private Maze mazeToSolve;
    private Position currentPosition;
    private Direction currentDirection;

    public RightHand(Maze mazeToSolve) {
        this.mazeToSolve = mazeToSolve;
        this.currentPosition = new Position(mazeToSolve.getEntryRow(), mazeToSolve.getEntryColumn());
        this.currentDirection = new Direction(mazeToSolve.getEntryRow(), mazeToSolve.getEntryColumn(), Direction.Directions.EAST);
    }

    public List<Move> findPath() throws Exception {
        int exitColumn = mazeToSolve.getExitColumn();
        int exitRow = mazeToSolve.getExitRow();
        List<Move> movesMade = new ArrayList<>();

        while (currentPosition.getRow() != exitRow || currentPosition.getColumn() != exitColumn) { // while not at exit
            logger.info("Current Position: (" + currentPosition.getRow() + ", " + currentPosition.getColumn() + ")");
            
            // try turning right first
            currentDirection.changeDirection("R");

            Position nextPosition = currentPosition.getNextPosition(currentDirection.getCurrentDirection());
            logger.info("Next Position: " + nextPosition.getRow() + " " + nextPosition.getColumn());
            
            // if right is valid, move forward
            if (mazeToSolve.validateMove(nextPosition.getRow(), nextPosition.getColumn())) {
                logger.info("turned right, moving forward");
                movesMade.add(new Move('R'));
                movesMade.add(new Move('F'));
                currentPosition = nextPosition; // update position

            } else {
                currentDirection.changeDirection("L"); // come back to original direction

                // if right is invalid, try moving forward
                nextPosition = currentPosition.getNextPosition(currentDirection.getCurrentDirection());
                logger.info("Next Position: " + nextPosition.getRow() + " " + nextPosition.getColumn());
                
                if (mazeToSolve.validateMove(nextPosition.getRow(), nextPosition.getColumn())) {
                    logger.info("Moving forward");
                    movesMade.add(new Move('F'));
                    currentPosition = nextPosition;

                } else {
                    currentDirection.changeDirection("L"); // if forward is invalid, try to turn left and move forward

                    nextPosition = currentPosition.getNextPosition(currentDirection.getCurrentDirection());
                    logger.info("Next Position: " + nextPosition.getRow() + " " + nextPosition.getColumn());

                    if (mazeToSolve.validateMove(nextPosition.getRow(), nextPosition.getColumn())) {
                        logger.info("turned left, moving forward");
                        movesMade.add(new Move('L'));
                        movesMade.add(new Move('F'));
                        currentPosition = nextPosition;

                    } else { // otherwise, turn around - entered dead end
                        
                        currentDirection.changeDirection("R"); // come back to original direction 

                        currentDirection.changeDirection("R"); // turn around
                        currentDirection.changeDirection("R");
                        logger.info("turning around");
                        movesMade.add(new Move('R'));
                        movesMade.add(new Move('R'));
                    }
                }
            }

            if (currentPosition.getRow() == exitRow && currentPosition.getColumn() == exitColumn) { // if exit is reached
                logger.info("Exit reached: (" + currentPosition.getRow() + ", " + currentPosition.getColumn() + ")");
                break; // exit the loop if the exit point is reached
            }
        }

        return movesMade; 
    }
}
