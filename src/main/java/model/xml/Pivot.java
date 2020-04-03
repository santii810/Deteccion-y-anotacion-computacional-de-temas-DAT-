package model.xml;

import lombok.*;

import javax.xml.bind.annotation.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Pivot {
    @XmlElement(name = "word")
    private Word word;
}
