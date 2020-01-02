package reader;

import java.io.IOException;

public interface Reader {
    static final int MAX_SIZE = 5500;
    boolean hasMoreContent();
    FileFragment read() throws IOException;

}
