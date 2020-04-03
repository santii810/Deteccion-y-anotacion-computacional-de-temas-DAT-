package model.xml;

import lombok.*;

import javax.xml.bind.annotation.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Unit {

    @XmlAttribute(name = "ref")
    private String ref;

    @XmlElement(name = "pivot")
    private Pivot pivot;
    @XmlElement(name = "theme")
    private Theme theme;


    public void setPivot(Word word) {
        this.pivot = new Pivot(word);
    }

    public List<Word> getTheme() {
        return theme==null? new Theme().getWords(): theme.getWords();
    }

    public void setTheme(List<Word> words) {
        if (this.theme == null)
            theme = new Theme(words);
        else
            this.theme.setWords(words);
    }
}
