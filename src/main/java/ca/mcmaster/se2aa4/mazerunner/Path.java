package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Path {

    private static final Logger logger = LogManager.getLogger();

    private String path;
    private Maze maze;

    public Path(Maze maze) {
        this.maze = maze;
    }

    public String getPath() {
        return this.path;
    }

    public String findPath() {

        int entryColumn = maze.getEntryColumn();
        int entryRow = maze.getEntryRow(); 
        int exitColumn = maze.getExitColumn(); 
        int exitRow = maze.getExitRow();

        System.out.println("Entry position: (" + entryRow + ", " + entryColumn + ")");
        System.out.println("Exit position: (" + exitRow + ", " + exitColumn + ")");

        StringBuilder sequenceOfMoves = new StringBuilder();

        if (entryRow == exitRow) {  // if entry and exit are in same row then it is a straight path
            int numberOfMoves = exitColumn - entryColumn;
            for (int i = 0; i < numberOfMoves; i++) {
                sequenceOfMoves.append('F');  // simply move forward
            }
        }

        // extend to more complex maze paths

        return sequenceOfMoves.toString();
    }

}
    /* public String factorizedPath() { 
        // to implement
    }


*/
