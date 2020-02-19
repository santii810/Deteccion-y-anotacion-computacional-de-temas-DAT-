package unitTest

import model.Word
import model.xml.SentenceXml
import processor.ProcessedOutput
import spock.lang.Specification
import writer.Marshaller

class MarshallerTest extends Specification {
    def "Writtes XML in correct format"() {
        when:
        ProcessedOutput output = new ProcessedOutput()
        ArrayList<SentenceXml> sentences = new ArrayList<>()
        output.setSentences(sentences)
        SentenceXml sentence = new SentenceXml()
        sentences.push(sentence)
        Word word1 = new Word()
        word1.setId(1)
        word1.setForm("Hola")
        Word word2 = new Word()
        word2.setId(1)
        word2.setForm("qué")
        Word word3 = new Word()
        word3.setId(1)
        word3.setForm("tal")
        sentence.setPivot(word3)
        sentence.setTheme([word1, word2])


        Marshaller.marshall(output, "output.xml")
        then:
        true
    }
}
