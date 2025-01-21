package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Path {

    private static final Logger logger = LogManager.getLogger();

    private String sequenceOfMoves;
    private Maze maze;

    public Path(String sequenceOfMoves) {
        this.sequenceOfMoves = sequenceOfMoves;
    }

    /* public String getSequenceOfMoves() {
        return sequenceOfMoves;
    }

    public String findSequenceOfMoves() { 
        // to implement
    }

    public String factorizedPath() { 
        // to implement
    }


*/
} 
// keep track of the sequence of moves made to reach the exit of the maze and returns the path in either format