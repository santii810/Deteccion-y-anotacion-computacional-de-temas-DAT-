package reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class FolderReader implements Reader {
    private File folder;
    private Queue<Reader> fileReaders = new LinkedList<>();
    private Reader currentFile;

    public FolderReader(File folder) throws FileNotFoundException, NullPointerException {
        this.folder = folder;
        if (folder.listFiles().length == 0)
            throw new FileNotFoundException("Selected folder is empty");
        for (File file : folder.listFiles()) {
            if (file.isFile())
                fileReaders.add(new FileReader(file));
            else {
                fileReaders.add(new FolderReader(file));
            }
        }
        this.currentFile = fileReaders.poll();
    }

    public boolean hasMoreContent() {
        return currentFile.hasMoreContent() || !fileReaders.isEmpty();
    }

    public FileFragment read() throws IOException {
        if (!currentFile.hasMoreContent()) {
            currentFile = fileReaders.poll();
        }
        FileFragment fragment = currentFile.read();

        fragment.setFilename(folder.getName() + "/" + fragment.getFilename());
        return fragment;
    }
}
