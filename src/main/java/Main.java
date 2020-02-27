import parser.Parser;
import mapper.ObjectMapper;
import reader.FileReader;
import reader.Reader;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

//    final static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            String filename = "src/test/resources/ame06_a01.txt";
            Reader reader = new FileReader(new File(filename));
            Parser parser = new Parser();
            ObjectMapper mapper = new ObjectMapper();
            MainHelper readAndParseManager = new MainHelper(reader, parser, mapper);
            readAndParseManager.process();


        } catch (FileNotFoundException e) {
//            logger.error("File not found " + e);
            e.printStackTrace();
        } catch (IOException e) {
//            logger.error(e);
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }


    }
}
