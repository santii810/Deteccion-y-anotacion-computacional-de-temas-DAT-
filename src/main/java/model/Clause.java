package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "clause")
public class Clause {
    @XmlAttribute
    private String text;
    @XmlElement
    private List<Word> words;
}
