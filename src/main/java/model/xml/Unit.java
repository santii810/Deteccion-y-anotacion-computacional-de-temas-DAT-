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
    @XmlElementWrapper(name = "theme")
    @XmlElement(name = "word")
    private List<Word> theme;


    public void setPivot(Word word) {
        this.pivot = new Pivot(word);
    }
}
