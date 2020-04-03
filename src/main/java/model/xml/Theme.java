package model.xml;

import lombok.*;
import utils.Utils;

import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)

public class Theme {
    @XmlElement(name = "word")
    List<Word> words;
    @XmlAttribute(name = "scheme")
    String scheme;

    public Theme(List<Word> words) {
        this.words = words;
        this.scheme = this.words.stream().map(Word::getDepRel)
                .filter(i -> Utils.depRelPermitidos.stream().anyMatch(j -> i.startsWith(j)))
                .collect(Collectors.joining("_"));
//        this.scheme = this.words.stream().map(Word::getDepRel).collect(Collectors.joining("_"));

    }

}
