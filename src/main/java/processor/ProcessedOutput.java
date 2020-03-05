package processor;

import lombok.*;
import model.xml.SentenceXml;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "output")
public class ProcessedOutput {
    @XmlElementWrapper(name = "sentences")
    @XmlElement(name = "text")
    List<SentenceXml> sentences = new ArrayList<>();
    @XmlAttribute(name = "ref")
    String ref;
}
