package model.xml;

import lombok.*;

import javax.xml.bind.annotation.*;

@Setter
@Getter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "word")
@ToString
public class Word {
    @XmlAttribute
    private int id;
    @XmlAttribute
    private String form;
    @XmlAttribute
    private String lemma;
    @XmlAttribute
    private String xPosTag;
    @XmlAttribute
    private int head;
    @XmlAttribute
    private String depRel;
    @XmlAttribute
    private String deps;
    @XmlAttribute
    private String misc;
}
