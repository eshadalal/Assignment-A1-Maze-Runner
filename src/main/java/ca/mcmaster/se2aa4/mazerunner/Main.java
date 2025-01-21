package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");

        Options options = new Options();

        options.addOption("i", true, "maze file to read");
        options.addOption("p", true, "path to validate");

        try {

            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);
            String path = cmd.getOptionValue("p");
            String inputFile = cmd.getOptionValue("i"); 

            logger.info("**** Reading the maze from file " + inputFile);
            BufferedReader mazeFile = new BufferedReader(new FileReader(inputFile));

            String line;
            while ((line = mazeFile.readLine()) != null) {
                for (int idx = 0; idx < line.length(); idx++) {
                    if (line.charAt(idx) == '#') {
                        System.out.print("WALL ");
                    } else if (line.charAt(idx) == ' ') {
                        System.out.print("PASS ");
                    }
                }
                
                System.out.print(System.lineSeparator());
            }

            Maze maze = new Maze(inputFile); 

            if (path != null) { // if path is given by user, then validate it
                Path pathToValidate = new Path(path);
                /* if (maze.validatePath(pathToValidate)) {
                    System.out.println("correct path");
                } else {
                    System.out.println("incorrect path");
                } */
            } else { 
                // maze.solvePath(); 
                // System.out.println(maze.factorizedPath());
            }

        } catch(Exception e) {
            logger.error("/!\\ An error has occured /!\\");
        }

        logger.info("**** Computing path");
        logger.info("PATH NOT COMPUTED");
        logger.info("** End of MazeRunner");
    }
}
