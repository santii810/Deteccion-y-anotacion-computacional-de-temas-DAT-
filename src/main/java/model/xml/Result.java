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
public class Result {

    @XmlAttribute(name = "ref")
    private String ref;

    @XmlElement(name = "pivot")
    private Word pivot;
    @XmlElementWrapper(name = "theme")
    @XmlElement(name = "word")
    private List<Word> theme;


}
