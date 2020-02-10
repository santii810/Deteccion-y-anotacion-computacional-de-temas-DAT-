package writer;

import model.xml.ProcesedOutput;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.File;

public class Marshaller {
    public static final String DEFAULT_OUTPUT_FOLDER = "./out/parsedFiles/";

    private static void createStructure(String filename) {
        File file = new File(DEFAULT_OUTPUT_FOLDER);
        if (!file.exists()) file.mkdirs();

        String directoryName = filename.replace(new File(filename).getName(), "");
        File directory = new File(DEFAULT_OUTPUT_FOLDER + "/" + directoryName);
        if (!directory.exists()) directory.mkdirs();
    }

    public static void marshall(ProcesedOutput procesedOutput, String filename) throws JAXBException {
        createStructure(filename);

        JAXBContext context = JAXBContext.newInstance(procesedOutput.getClass());
        javax.xml.bind.Marshaller mar = context.createMarshaller();
        mar.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        mar.marshal(procesedOutput, new File(DEFAULT_OUTPUT_FOLDER + filename));
    }
}
