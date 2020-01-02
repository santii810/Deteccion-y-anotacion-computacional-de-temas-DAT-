package reader;

public class FileFragment {
    private String filename;
    private String text;

    public FileFragment(String filename, String text) {
        this.filename = filename;
        this.text = text;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
