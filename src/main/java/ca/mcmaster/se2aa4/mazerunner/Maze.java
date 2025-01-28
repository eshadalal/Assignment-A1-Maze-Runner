package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Maze {

    private static final Logger logger = LogManager.getLogger();

    private List<String> mazeToSolve = new ArrayList<>();
    private int entryRow; 
    private int entryColumn = 0;
    private int exitRow; 
    private int exitColumn;

    public int getEntryRow() {
        return entryRow;
    } 

    public int getExitRow() {
        return exitRow;
    }

    public int getEntryColumn() {
        return entryColumn;
    }

    public int getExitColumn() {
        return exitColumn;
    }

    public Maze(String mazeFile) throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(mazeFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                mazeToSolve.add(line);
            }
        }
        findEntry();
        findExit();

    }

    public int getRows() {
        return mazeToSolve.size();
    }

    public int getColumns() {
        return mazeToSolve.get(0).length();
    }

    public void findEntry() { 
        for (int row = 0; row < getRows(); row++) { 
            if (mazeToSolve.get(row).charAt(0) == ' ') { // first column of maze
                entryRow = row; 
                entryColumn = 0; 
                break;
            }
        }

        System.out.println("Entry position: (" + entryRow + ", " + entryColumn + ")");

    } 

    public void findExit() { 
        for (int row = 0; row < getRows(); row++) { 
            if (mazeToSolve.get(row).charAt(getColumns() - 1) == ' ') { // last column of maze
                exitRow = row; 
                exitColumn = getColumns() - 1;
                break;
            }   
        }
        
        System.out.println("Exit position: (" + exitRow + ", " + exitColumn + ")");

    }

    public Boolean validateMove(int row, int column) {
        if (row < 0 || row >= mazeToSolve.size() || column < 0 || column >= mazeToSolve.get(0).length()) {
            return false;
        }

        return mazeToSolve.get(row).charAt(column) != '#';
    }

}