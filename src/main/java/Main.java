import lombok.extern.slf4j.Slf4j;
import mapper.ObjectMapper;
import parser.Parser;
import reader.FileReader;
import reader.Reader;
import reader.*;
import utils.Utils;

import javax.xml.bind.JAXBException;
import java.io.*;

@Slf4j
public class Main {

    static long startTime;
    static long newFileTime;

    public static void main(String[] args) {
        startTime = System.currentTimeMillis();
        newFileTime = System.currentTimeMillis();
        try {
//            String filename = "src/test/resources/AmE06_files";
            String filename = "src/test/resources/AmE06_files/AmE06_F/ame06_f19.txt";
            log.info("Iniciando análisis de fichero: " + filename + "\n\n");

            File file = new File(filename);
            Reader reader;
            if (file.isFile()) {
                reader = new FileReader(file);
            } else {
                reader = new FolderReader(file);
            }
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
        log.info("\n\nTotal time: " + Utils.getElapsedTime(startTime, System.currentTimeMillis()));


    }
}
