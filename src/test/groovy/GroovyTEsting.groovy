import spock.lang.Specification

class GroovyTEsting extends Specification {
    def "Test de santi"() {
        when:
        String a = "hola/b/jj.txt"
        File file = new File(a);
        a = a.replace(file.getName(), "")
        then:
        true
    }
}
