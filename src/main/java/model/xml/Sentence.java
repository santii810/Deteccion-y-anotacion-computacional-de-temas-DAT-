package model.xml;

import lombok.*;
import model.Sentence;

import javax.xml.bind.annotation.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "sentence")
public class SentenceXml {
    @XmlAttribute(name = "text")
    private String text;
    @XmlElement
    private String pivot;
    @XmlElement
    private String theme;
    //TODO eliminar elemento para que no se imprima
    @XmlElement
    private List<String> words;

    public SentenceXml(Sentence sentence) {
        this.text = sentence.getText();
        this.words = sentence.getWords();
    }
}
