import lombok.extern.slf4j.Slf4j;
import mapper.ObjectMapper;
import parser.Parser;
import reader.FileReader;
import reader.Reader;

import javax.xml.bind.JAXBException;
import java.io.*;

@Slf4j
public class Main {


    public static void main(String[] args) {
        try {
            String filename = "src/test/resources/ame06_a01.txt";
            log.info("Ininicando análisis de fichero: " + filename);
            Reader reader = new FileReader(new File(filename));
            Parser parser = new Parser();
            ObjectMapper mapper = new ObjectMapper();
            MainHelper readAndParseManager = new MainHelper(reader, parser, mapper);
            readAndParseManager.process();


        } catch (FileNotFoundException e) {
            log.error("File not found " + e);
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (JAXBException e) {
            e.printStackTrace();
        }


    }
}
