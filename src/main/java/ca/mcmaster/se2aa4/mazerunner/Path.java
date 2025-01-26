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

        StringBuilder sequenceOfMoves = new StringBuilder();

        return sequenceOfMoves.toString();

    }

    /* while (entryRow != exitRow && entryColumn != exitColumn) {  // while not at the exit 




        }


        return sequenceOfMoves.toString();
    } */
    public String factorizedPath(String sequenceOfMoves) {

        int forward_count = 1;
        int left_count = 1;
        int right_count = 1;

        for (int i = 0; i < sequenceOfMoves.length(); i++) {

            if (sequenceOfMoves.charAt(i) == 'F' && sequenceOfMoves.charAt(i + 1) == 'F') {
                forward_count++;
            } else if (sequenceOfMoves.charAt(i) == 'L' && sequenceOfMoves.charAt(i + 1) == 'L') {
                left_count++;
            } else if (sequenceOfMoves.charAt(i) == 'R' && sequenceOfMoves.charAt(i + 1) == 'R') {
                right_count++;
            }
        }

        return forward_count + "F" + left_count + "L" + right_count + "R";

    }

    public Boolean validatePath(String pathToValidate) {

        for (int i = 0; i < pathToValidate.length(); i++) {
            if (pathToValidate.charAt(i) != 'F' && pathToValidate.charAt(i) != 'L' && pathToValidate.charAt(i) != 'R' && pathToValidate.charAt(i) != ' ') {
                return false;
            }

            if (pathToValidate.charAt(i) == ' ') {
                continue; // skip spaces
            }

            // changeDirection(pathToValidate.charAt(i));

        }

        return true;
    }
}
