package reader;

import java.io.IOException;

public interface Reader {
    static final int MAX_SIZE = 5000;
    boolean hasMoreContent();
    FileFragment read() throws IOException;
}
