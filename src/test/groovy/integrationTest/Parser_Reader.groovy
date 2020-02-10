package integrationTest

import org.json.JSONArray
import org.json.JSONObject
import parser.Parser
import parser.ParserResponse
import reader.FileFragment
import reader.FileReader
import reader.Reader
import spock.lang.Specification
import utils.TestConstraints

class Parser_Reader extends Specification {

    def "Response has 200 OK"() {
        when:
        FileFragment str = new FileReader(new File(TestConstraints.RESOURCES_URL + a)).read()
        ParserResponse response = new Parser().send(str.getText())
        String result = new JSONObject(response.getBody()).get("result")

        then:
        response.getHttpStatus() == 200
        result.split("\n").length == b

        where:
        a                          | b
        "SimpliestTexts/Text1.txt" | 14
        "SimpliestTexts/Text2.txt" | 15

    }
}
