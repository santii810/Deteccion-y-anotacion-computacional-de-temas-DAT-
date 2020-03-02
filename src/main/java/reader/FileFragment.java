package reader;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class FileFragment {
    private String filename;
    private String text;
    private boolean isEndOfFile;

    public boolean isNullOrEmpty() {
        if (text.length() == 0) return true;
        if (text.trim().isEmpty()) return true;
        if (text.replace("\"", "").isEmpty()) return true;

        return false;
    }
}
