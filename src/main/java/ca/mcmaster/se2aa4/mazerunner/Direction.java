package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Direction {

    private static final Logger logger = LogManager.getLogger();

    private char direction; 

    public Direction(char direction) {
        this.direction = direction;
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public void turnRight() {
        switch (direction) {
            case 'F':
                direction = 'R';
                break;
            case 'R':
                direction = 'L';
                break;
            case 'L':
                direction = 'F';
                break;
        }
    }

    public void turnLeft() {
        switch (direction) {
            case 'F':
                direction = 'L';
                break;
            case 'R':
                direction = 'F';
                break;
            case 'L':
                direction = 'R';
                break;
        }
    }

}