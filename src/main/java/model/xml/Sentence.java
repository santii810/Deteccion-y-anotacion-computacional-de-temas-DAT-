package model.xml;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
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
}
