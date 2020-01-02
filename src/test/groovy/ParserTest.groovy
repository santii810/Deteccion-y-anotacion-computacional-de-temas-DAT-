import parser.ParserResponse

import parser.Parser
import spock.lang.Specification

class ParserTest extends Specification {
    def "response ok"() {
        when:
        Parser Parser = new Parser()
        ParserResponse responseModel = Parser.send(text)
        then:
        responseModel.getHttpStatus() == 200

        where:
        text << ["hola", "texto con espacios", "testoÑ"]
    }

    def "response body is complete"() {
        when:
        Parser Parser = new Parser()
        ParserResponse responseModel = Parser.send("hola")
        then:
        responseModel.getBody().get("model") != null
        responseModel.getBody().get("acknowledgements") != null
        responseModel.getBody().get("result") != null
    }

    def "analizer rules in english mode"() {
        when:
        Parser Parser = new Parser()
        ParserResponse responseModel = Parser.send("hola")
        then:
        responseModel.getBody().get("model") == "english-ud-1.2-160523"
    }

}
