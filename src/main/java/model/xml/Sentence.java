package model.xml;

import lombok.*;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "sentence")
public class Sentence {
    @XmlAttribute(name = "text")
    private String text;
    @XmlElement
    private String pivot;
    @XmlElement
    private String theme;
    @XmlElement
    private List<Word> words = new ArrayList<>();

}
