package reader;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class FileFragment {
    private String filename;
    private String text;
    private boolean isEndOfFile;
}
