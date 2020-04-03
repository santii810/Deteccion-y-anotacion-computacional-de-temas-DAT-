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
public class Theme {
    @XmlElement(name = "word")
    List<Word> words;
}
