package reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
        while (hasMoreContent() && builder.toString().length() < (MAX_SIZE - 500)) {
            builder.append(fileScanner.nextLine());
            builder.append(" ");
        }
        return new FileFragment(file.getName(), builder.toString().replace("-", ""), !hasMoreContent());
    }

    public String getFilename() {
        return file.getName();
    }
}
