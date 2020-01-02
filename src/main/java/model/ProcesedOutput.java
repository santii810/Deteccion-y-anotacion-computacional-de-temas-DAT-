package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import parser.ParserResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "output")
public class ProcesedOutput {
    @XmlElement
    private List<ParserResponse> parserResponses = new ArrayList<>();

    @XmlElement
    private Set<String> acknowledgements = new HashSet<>();
    @XmlElement
    private List<Clause> clauses;

}
