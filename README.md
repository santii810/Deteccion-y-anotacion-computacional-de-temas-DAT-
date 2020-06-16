# Deteccion-y-anotacion-computacional-de-temas-DAT (Detection and anotation of themes)
###  DAT: Detecion and computational anotation of Systemic-Functional *‘themes’* on English language corpus


[Readme en Español](https://github.com/santii810/Deteccion-y-anotacion-computacional-de-temas-DAT-/blob/master/README_es.md)
______
As a contribution to a project on the connection between textual genre and thematic realization in English written 
from empirical linguistics, this study describes the computational procedure used in the automatic detection and XML 
annotation of *'themes'* in *AmE06 (Baker 2009)*, a multi-genre corpus, analyzed syntactically, of a million 
words of contemporary American English (2006). ‘Theme’ is a fundamental concept of Systemic-Functional Linguistics 
(Halliday and Matthiessen 2004) that identifies, as a sentence design factor, the initial segment of each sentence.


This procedement references analysis, design and implementation of a service which, starting from a plane text input, 
use computing algorithms to identify the syntactic structures. It generates an structurate output using 
Extensible Markup Language (XML) .


### Requirements
* Java  version 8 or latest
* Internet connection

### How to use

#### Using Java/Maven
Import [DAT file](https://github.com/santii810/Deteccion-y-anotacion-computacional-de-temas-DAT-/releases/download/v1.0/dat-1.0.jar) *pom.xml* in your proyect  
Or feel free to install it in your computer using ```mvn clean install``` 
```        
<dependency>
    <groupId>com.uvigo.sgomez</groupId>
    <artifactId>dat</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

Once this is done u can declarate **MainHelper** and call his methos ```proccess()``` to execute the analysis.  
This repository has  an [use example](https://github.com/santii810/Deteccion-y-anotacion-computacional-de-temas-DAT-/blob/master/ExternalUseExample/src)




#### Empleo sin Maven
To execute it outside Maven [download executable jar](https://github.com/santii810/Deteccion-y-anotacion-computacional-de-temas-DAT-/releases/download/v1.0/dat-1.0-exec.jar) and execute it since the command line  
``` java -jar  dat-1.0-exec.jar filename ```
