package reader;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Slf4j
public class FileReader implements Reader {
    private File file;
    private Scanner fileScanner;

    public FileReader(File file) throws FileNotFoundException {
        this.file = file;
        this.fileScanner = new Scanner(file);
    }

    public boolean hasMoreContent() {
        return fileScanner.hasNextLine();
    }

    public FileFragment read() {
        StringBuilder builder = new StringBuilder();
        while (hasMoreContent() && builder.toString().length() < (MAX_SIZE - 1000)) {
            builder.append(fileScanner.nextLine());
            builder.append(" ");
        }
        String toret = builder.toString().replace("-", "");
        FileFragment fragment = new FileFragment(file.getName(), toret, !hasMoreContent());

        log.debug("Retornando FileFragment : " + fragment.getFilename() + " - " + fragment.getText().substring(0, Math.min(100, fragment.getText().length())));
        return fragment;
    }

    public String getFilename() {
        return file.getName();
    }
}
