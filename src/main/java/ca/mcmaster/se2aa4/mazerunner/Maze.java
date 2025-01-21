package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Maze {

    private static final Logger logger = LogManager.getLogger();

    private List<String> mazetoSolve = new ArrayList<>();
    private int entryRow; 
    private int entryColumn = 0;
    private int exitRow; 
    private int exitColumn = getColumns() - 1;

    public Maze(String mazeFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(mazeFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                mazetoSolve.add(line);
            }
        }
    }

    public int getRows() {
        return mazetoSolve.size();
    }

    public int getColumns() {
        return mazetoSolve.get(0).length();
    }

    public void findEntry() { 
        for (int row = 0; row < getRows(); row++) { 
            if (mazetoSolve.get(row).charAt(0) == ' ') { // first column of maze
                entryRow = row; 
            }
        }
    } 

    public void findExit() { 
        for (int row = 0; row < getRows(); row++) { 
            if (mazetoSolve.get(row).charAt(getColumns() - 1) == ' ') { // last column of maze
                exitRow = row; 
            }   
        }
    }

}