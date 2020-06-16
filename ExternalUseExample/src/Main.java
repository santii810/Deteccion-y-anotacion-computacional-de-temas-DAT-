import reader.FileReader;
import reader.Reader;

import javax.xml.bind.JAXBException;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException, JAXBException {
        Reader fileReader = new FileReader(new File("./Hello.txt"));
        MainHelper mh = new MainHelper(fileReader);
        mh.process();
    }
}
