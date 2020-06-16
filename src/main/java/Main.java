import lombok.extern.slf4j.Slf4j;
import mapper.ObjectMapper;
import parser.Parser;
import processor.Processor;
import reader.FileReader;
import reader.Reader;
import reader.*;
import utils.Utils;

import javax.xml.bind.JAXBException;
import java.io.*;

@Slf4j
public class Main {



    public static void main(String[] args) {
        log.info("Iniciando proceso");

        try {
            String filename;
//            filename = "src/test/resources/AmE06_files/AmE06_F";
            filename = args[0];
            log.info("Iniciando análisis de fichero: " + filename + "\n\n");
            File file = new File(filename);
            Reader reader;
            if (file.isFile()) {
                reader = new FileReader(file);
            } else {
                reader = new FolderReader(file);
            }
            MainHelper readAndParseManager = new MainHelper(reader, new Parser(), new ObjectMapper(), new Processor());
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
