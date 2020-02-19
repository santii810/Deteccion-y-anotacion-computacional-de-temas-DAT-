package unitTest

import org.json.JSONObject
import parser.ParserResponse

import parser.Parser
import spock.lang.Specification
import utils.TestConstraints

class ParserTest extends Specification {
    def "Response has 200 OK"() {
        when:
        Parser Parser = new Parser()
        ParserResponse responseModel = Parser.send(text)
        then:
        responseModel.getHttpStatus() == 200

        where:
        text << ["hola", "texto con espacios", "testoÑ"]
    }

    def "Response body is complete"() {
        when:
        Parser Parser = new Parser()
        ParserResponse responseModel = Parser.send("hola")
        JSONObject jsonObject = new JSONObject(responseModel.getBody())
        then:
        jsonObject.get("model") != null
        jsonObject.get("acknowledgements") != null
        jsonObject.get("result") != null
    }

    def "Analizer rules in english mode"() {
        when:
        Parser Parser = new Parser()
        ParserResponse responseModel = Parser.send("hola")
        then:
        new JSONObject(responseModel.getBody()).get("model") == TestConstraints.DEFAULT_PARSER_LANGUAGE
    }


    //TODO crear test para verificar que devuelve todos los campos necesarios (TODAS LAS COLUMNAS)


    def "Test too long"(){
        when:
        Parser Parser = new Parser()
        ParserResponse responseModel = Parser.send(TestConstraints.TOO_LONG_TEXT)
        then:
        responseModel.getHttpStatus() == 414
    }
}
