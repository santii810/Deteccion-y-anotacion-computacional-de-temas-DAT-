import org.apache.log4j.Logger;
import parser.Parser;
import reader.FileReader;
import reader.Reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    final static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            String filename = "ame06_a01.txt";
            Reader reader = new FileReader(new File(filename));
            Parser parser = new Parser();
            ReadAndParseManager readAndParseManager = new ReadAndParseManager(reader, parser);
            readAndParseManager.parseToFile();


        } catch (FileNotFoundException e) {
            logger.error("File not found " + e);
        } catch (IOException e) {
            logger.error(e);
        }


    }
}
