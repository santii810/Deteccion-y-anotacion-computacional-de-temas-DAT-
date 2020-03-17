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
    @XmlValue
    private String form;

    @XmlAttribute
    private int id;
    @XmlAttribute
    private String xPosTag;

    @XmlTransient
    private String depRel;
    @XmlTransient
    private String lemma;
    @XmlTransient
    private int head;
    @XmlTransient
    private String deps;
    @XmlTransient
    private String misc;
}
