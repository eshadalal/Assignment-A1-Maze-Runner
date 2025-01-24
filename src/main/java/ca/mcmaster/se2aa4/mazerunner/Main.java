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

        String displayPath = ""; 

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
            Path generatePath = new Path(maze);
            displayPath = generatePath.findPath();
        
        } catch(Exception e) {
            logger.error("/!\\ An error has occured /!\\");
        }

        logger.info("**** Computing path");
        System.out.println(displayPath);  
        logger.info("** End of MazeRunner");
    }
}
