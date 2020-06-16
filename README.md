# Deteccion-y-anotacion-computacional-de-temas-DAT-
###  DAT: Detección y anotación computacional de *‘temas’* sistémico-funcionales en corpus de lengua inglesa



Como contribución a un proyecto sobre conexión entre género textual y realización temática en inglés escrito desde la lingüística empírica, este estudio describe el procedimiento computacional empleado en la detección automática y anotación XML de *‘temas’* en AmE06 (Baker 2009), un corpus multi-género, analizado sintácticamente, de un millón de palabras de inglés americano contemporáneo (2006). ‘Tema’ es un concepto fundamental de la Lingüística Sistémico-Funcional (Halliday y Matthiessen 2004) que identi􀂦ca, como factor de diseño oracional, el segmento inicial de cada oración.

El proceso computacional mencionado hace referencia al análisis, diseño e implementación de un servicio que, partiendo de una entrada de texto plano en inglés y mediante un conjunto de algoritmos a desarrollar, identifique los *‘Temas’* en dicho texto, los procese y genere una salida estructurada empleando el lenguaje de marcado XML.


### Requerimientos
* Java 8
* Conexión a internet

### Modo de empleo

#### Usando Java/Maven
Debes importar al  [fichero](https://github.com/santii810/Deteccion-y-anotacion-computacional-de-temas-DAT-/releases/download/v1.0/dat-1.0.jar) *pom.xml* de tu proyecto la dependencia del parser 
```        
<dependency>
    <groupId>com.uvigo.sgomez</groupId>
    <artifactId>dat</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```
Puedes importar el jar subido a este repositorio o instalarlo en tu equipo usando  ```mvn clean install```

Una vez añadido al proyecto bastará con declarar la clase **MainHelper** y llamar a su metodo ```proccess()``` para que se ejecute el análisis.  
El repositorio cuenta con un [ejemplo de uso](https://github.com/santii810/Deteccion-y-anotacion-computacional-de-temas-DAT-/blob/master/ExternalUseExample/src)




#### Empleo sin Maven
Para su ejecución sin maven se debe [descargar el ejecutable](https://github.com/santii810/Deteccion-y-anotacion-computacional-de-temas-DAT-/releases/download/v1.0/dat-1.0-exec.jar) y lanzarlo desde una terminal  
``` java -jar  dat-1.0-exec.jar filename ```
