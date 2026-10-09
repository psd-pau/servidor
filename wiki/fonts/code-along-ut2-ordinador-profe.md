---
lang: ca-ES
---

# UT2 — Projecte de l'ordinador del professor i comparació de versions

## Procedència, ubicació i abast de la revisió

**Font aportada per David Pons el 2026-10-09:** carpeta `materials-anteriors/0613-desenvolupament-web-entorn-servidor/UT2/unitat_2_ordinador_profe/`. Després de la primera comparació, el professor confirma que és posterior a `unitat_2_classe2526`, amb les referències del 2 i del 14 d'octubre, i **la designa com a font canònica**. La cronologia s'incorpora per aquesta confirmació directa, no pel número de versió de Boot.

La [[code-along-ut2-2627|proposta per al curs 2026–2027]] deriva d'aquesta font i recupera millores de la còpia antiga. El projecte actual és a `materials/0613-desenvolupament-web-entorn-servidor/UT2/unitat2_2627/`; els apartats de comparació següents descriuen els originals, que es conserven sense corregir.

**Concreció de nivell per a UT2, 2026-10-09:** el professor demana una [[code-along-ut2-2627-simplificat|versió simplificada centrada en entitats i repositoris]], amb serveis i controladors mínims. `unitat2_2627_simplificat` és la proposta docent per a UT2; `unitat2_2627` es conserva íntegre com a versió ampliada per preparar UT3 i la integració final. La canonicitat de la font històrica no canvia.

L'arrel Maven real és `unitat_2_ordinador_profe/unitat2/unitat2/`, on hi ha el [[../../materials-anteriors/0613-desenvolupament-web-entorn-servidor/UT2/unitat_2_ordinador_profe/unitat2/unitat2/pom.xml|`pom.xml`]]. A la carpeta superior `unitat2/` hi ha configuració d'IntelliJ (`.idea/`); el projecte també conserva `target/` amb classes compilades i recursos. S'han inventariat els originals sense modificar-los. La presència de classes compilades no acredita que les rutes i les consultes del codi font actual funcionin.

La comparació pren com a referència `unitat_2_classe2526/`, el [[code-along-ut2-2526|projecte històric utilitzat per redactar els apunts]]. S'han comparat totes les classes Java, els POM, els recursos, les peticions HTTP i els fitxers de suport. Per comparar les classes s'han normalitzat els salts de línia i el canvi de paquet `cat.paucasesnoves.unitat_2` → `cat.paucasesnoves.unitat2` en còpies temporals, fora dels materials. Les diferències de comentaris i format s'han separat dels canvis de comportament.

**Abast de la comparació original:** lectura estàtica; no s'ha compilat ni arrencat cap de les dues fonts històriques. A la primera revisió no s'havien localitzat `java` ni `mvn` al `PATH` i els materials es van conservar intactes per indicació del professor. Per preparar la proposta actual s'han localitzat JDK i Maven fora del `PATH`, i s'ha compilat i provat la còpia derivada; la seva validació consta a la fitxa específica. Els apunts teòrics i els originals continuen intactes.

## Inventari del projecte aportat

L'arrel Maven conté **26 fitxers de projecte**, excloent `target/`, `.idea/` i `.git/`: 14 classes/interfícies/enumeracions Java de producció, una classe de prova Java, dos recursos, dos fitxers HTTP i set fitxers de suport. La còpia de classe conté 24 fitxers amb el mateix criteri.

Els camins de codi següents són relatius a `src/main/java/cat/paucasesnoves/unitat2/`.

| Bloc | Fitxers i contingut |
|---|---|
| Arrencada | `Unitat2Application.java`; Spring Boot 3.5.6 i Java 25 al POM. |
| Model de llibres | `domain/entity/Book.java`, `domain/enums/Genre.java`; identificador `Long` amb `IDENTITY`, restriccions de columna i taula, gènere desat com a text i preu `@Transient`. |
| Persistència de llibres | `repository/BookRepository.java`; CRUD heretat, consultes derivades, paginació amb retorn `Page<Book>`, dues consultes JPQL i una consulta nativa. |
| Servei i API de llibres | `service/BookService.java`, `controller/BookController.java`; cinc operacions CRUD i vuit rutes de consulta addicionals. |
| Model d'universitat | `domain/entity/Teacher.java`, `Course.java`, `Student.java`; mateix mapatge de relacions i mateixes restriccions que a la còpia de classe. |
| Persistència i API d'universitat | `repository/TeacherRepository.java`, `CourseRepository.java`, `StudentRepository.java`, `service/UniversityService.java`, `controller/UniversityController.java`; tres operacions de creació i dues consultes. |
| Configuració i dades | `src/main/resources/application.properties` i `data.sql`; 12 propietats, H2 en memòria i 21 llibres inicials. |
| Comprovacions | `src/test/java/.../Unitat2ApplicationTests.java`, només `contextLoads()`; `src/test/http/book_read.http`, deu peticions GET, i `book_write.http`, dues POST, dues PUT i dues DELETE. |
| Suport | `pom.xml`, `mvnw`, `mvnw.cmd`, `.mvn/wrapper/maven-wrapper.properties`, `.gitignore`, `.gitattributes` i `HELP.md`. |

El POM declara les mateixes dependències que la còpia de classe: Spring Web, Spring Data JPA, H2 amb scope `runtime`, DevTools amb `runtime` i `optional`, i Spring Boot Test amb scope `test`. El wrapper continua usant Maven 3.9.11. `HELP.md` és ajuda generada amb enllaços de Spring Initializr; no és un dossier docent nou.

## Diferències rellevants

| Aspecte | `unitat_2_classe2526` | `unitat_2_ordinador_profe` | Implicació |
|---|---|---|---|
| Spring Boot | 3.5.7 | 3.5.6 | La còpia del professor té una revisió de Boot inferior; això no determina per si sol l'ordre de desenvolupament del codi. |
| Identitat | Artefacte i nom `unitat_2`; paquet `cat.paucasesnoves.unitat_2` | Artefacte i nom `unitat2`; paquet `cat.paucasesnoves.unitat2` | Canvien imports, camins i nom de l'aplicació, sense canviar el domini. |
| `Book.id` | `long`, amb getters/setters del mateix tipus | `Long`, amb getters/setters del mateix tipus | La còpia del professor pot representar l'identificador encara no assignat amb `null`. Es manté `IDENTITY`. |
| Columnes per defecte | `@Column` sense paràmetres a `publishedDate`, `credits`, `email` i `department` | Sense aquesta anotació als quatre camps | No canvia el mapatge per defecte. Les anotacions amb restriccions es conserven. |
| Identificadors d'universitat | `setId()` a `Teacher`, `Course` i `Student` | Sense aquests tres setters; es conserven els getters | JPA accedeix als camps; no és necessari afegir setters perquè persisteixi les entitats. Canvia la interfície pública del model. |
| Injecció | `@Autowired` als constructors dels dos serveis i dels dos controladors | Constructors sense `@Autowired` | Es manté la injecció per l'únic constructor de cada component. |
| API de llibres | Cinc operacions CRUD; les consultes específiques només són al servei | Tretze operacions: CRUD i vuit consultes | El projecte del professor permet seguir les cerques des de la petició HTTP fins al repositori. |
| Paginació d'autor | `List<Book> findByAuthor(String, Pageable)` | `Page<Book> findByAuthor(String, Pageable)` i `.getContent()` al servei | Introdueix `Page`, però l'endpoint retorna una llista i no publica les metadades de paginació. |
| Consulta per any | `findBooksByPublishedInYear()` | `findBooksPublishedInYear()` | Canvi de nom coordinat amb el servei; la JPQL és equivalent. |
| Consulta nativa | `BOOK` i `LIKE '%:author%'` | `books` i `LIKE %:name%` | Es treu el paràmetre del literal, però apareix una discrepància entre `books` i la taula `BOOK`. |
| Cerca d'estudiants | `findByFullNameContainingIgnoreCase()` | `findByFullNameContaining()` | Es perd la declaració explícita d'ignorar majúscules/minúscules. Cap controlador o servei invoca aquesta cerca. |
| API d'universitat | Base `/api/university`; cerca `/courses/teacher/name/{name}` | Base `/university`; cerca `/courses/teacher/{name}` | Mateixes cinc operacions amb camins diferents. |
| Peticions de demostració | `src/main/resources/test-api.http`, amb tot el contingut comentat | Dos fitxers a `src/test/http/` amb 16 peticions sense comentar | Més suport per comprovar el code along, encara sense assercions automatitzades. |
| Comentaris | Explicacions breus i anotacions de classe | Comentaris més desenvolupats a model, repositoris i capes | Més suport al professor, amb algunes imprecisions que cal revisar. |

`Unitat2Application`, `Genre` i `Unitat2ApplicationTests` són iguals després de normalitzar el paquet. Els altres dotze fitxers Java tenen diferències: algunes són només comentaris, ordre de membres, noms locals o format. `CourseRepository` i `TeacherRepository` conserven els mateixos mètodes; `UniversityService` manté el mateix comportament. `BookService` conserva el CRUD i les mateixes cerques, amb els ajusts de paginació i nom del mètode per any.

### Rutes de llibres que afegeix la còpia del professor

Totes són GET sota `/books`, a més de les dues lectures i les tres escriptures CRUD compartides.

| Ruta addicional | Comportament del servei |
|---|---|
| `/isbn/{isbn}` | Cerca per ISBN. |
| `/author/{author}` | Llibres d'un autor exacte. |
| `/author/{author}/top5` | Primera pàgina de cinc llibres de l'autor, retornada com a llista. |
| `/search/title?keyword=...` | Títols que contenen el fragment. |
| `/genre/{genre}` | Gènere amb ordre de data descendent. |
| `/year/{year}` | Any de publicació mitjançant JPQL. |
| `/count?author=...&genre=...` | Recompte per autor i gènere mitjançant JPQL. |
| `/author/{author}/nativeTop5` | Cerca parcial d'autor amb SQL natiu i límit de cinc; pendent de corregir i verificar. |

### Configuració i dades: coincidència exacta amb el suport separat

Els dos recursos del projecte del professor són **idèntics byte a byte** als de [[dades-exemple-classe-ut2|`dades exemple classe/`]], inclosos comentaris, codificació i salts de línia. S'ha verificat amb comparació del contingut i SHA-256:

| Fitxer | SHA-256 compartit |
|---|---|
| `application.properties` | `8c3dae1e04f92f44552f4911654d2508c44c1ddd1d0d3f56f5a44e473b4d8963` |
| `data.sql` | `06f7cccdeef8028271099ad374e7235b96b4f8e89e0f4254fe9e3513e6f3e36d` |

Respecte de la còpia de classe, les 12 claus i tots els valors funcionals són iguals llevat de `spring.application.name`. El SQL conté els mateixos 21 registres i comentaris, amb diferències de caixa `book`/`BOOK` i salts de línia. Per tant, es conserva el funcionament explicat als apunts: H2 en memòria, esquema recreat amb `create` i dades carregades després de JPA amb `defer-datasource-initialization=true`.

La coincidència vincula documentalment el suport separat amb la còpia del professor; no demostra per si sola quin projecte es va modificar més tard.

## Punts pendents de la còpia del professor

### Consulta nativa i comentaris SQL

La consulta activa és:

```java
@Query(value = "SELECT * FROM books WHERE author LIKE %:name% LIMIT 5", nativeQuery = true)
List<Book> searchTop5ByAuthor(@Param("name") String name);
```

`Book` declara `@Table(name = "BOOK")` i `data.sql` insereix a `BOOK`. **`books` i `BOOK` no són el mateix nom de taula:** el plural afegeix una lletra, independentment de les majúscules. El SQL natiu no rep la traducció dels noms d'entitat pròpia de JPQL. És una incoherència objectiva del codi i caldrà corregir-la abans de donar la ruta com a funcional. Els comentaris SQL del repositori també empren `books`.

La notació `%:name%` no s'ha de marcar com a error només perquè no sigui SQL executable literalment: Spring Data JPA processa expressions `LIKE` amb comodins al voltant del paràmetre. El [manual de Spring Data JPA 3.5](https://docs.spring.io/spring-data/jpa/reference/3.5/jpa/query-methods.html#jpa.query-methods.at-query) explica aquest tractament; el [parser `StringQuery` de la branca 3.5](https://github.com/spring-projects/spring-data-jpa/blob/3.5.x/spring-data-jpa/src/main/java/org/springframework/data/jpa/repository/query/StringQuery.java) processa aquestes expressions i omet paràmetres dins literals entre cometes. Això distingeix la notació de la còpia del professor de `'%:author%'` a la còpia de classe. El contrast del mecanisme no substitueix una prova d'execució de la consulta concreta.

### Petició PUT amb només l'ISBN

`book_write.http` inclou un `PUT /books/2` amb aquest únic camp:

```json
{
  "isbn": "9781617294945"
}
```

El comentari associa l'error a un ISBN ja existent, però `BookService.update()` copia també `title`, `author`, `publishedDate` i `genre` del cos rebut. Els camps omesos queden a `null` i `title` i `author` són obligatoris. A més, l'ISBN ja pertany a *Spring in Action*. Aquesta petició barreja absència de camps obligatoris i unicitat: no és una demostració aïllada de l'ISBN duplicat ni una actualització parcial implementada pel servei. Deixar l'ISBN buit, com suggereix el comentari, tampoc representa conservar el valor anterior.

### Paginació i dades de demostració

`PageRequest.of(0, 5)` no especifica ordenació, igual que a la còpia de classe. La consulta nativa tampoc té `ORDER BY`: el nom «top 5» expressa un límit, però no un rànquing definit. A més, cap dels 14 autors inicials té més de tres llibres, de manera que el conjunt inicial no demostra el tall després del cinquè resultat. La variant paginada cerca l'autor exacte; la nativa cerca un fragment. Per comparar-les caldrà explicitar aquesta diferència.

### Problemes que les dues còpies comparteixen

- Les relacions d'universitat són bidireccionals i els controladors retornen entitats directament. No hi ha DTO ni anotacions Jackson per controlar aquestes referències; cal verificar la forma del JSON i la possible recursió.
- `UniversityService.getStudentsInCourse()` conserva l'accés a `Course.students` després de `findById()`, sense una transacció explícita al servei. La càrrega lazy s'ha de provar en el context concret de la petició; no s'ha constatat cap error en execució.
- Les lectures i actualitzacions de llibres absents retornen `null`; no hi ha tractament explícit d'HTTP 404. La consulta d'estudiants d'un curs absent conserva `.orElseThrow()` sense tractament propi.
- `data.sql` només inicialitza llibres; no hi ha dades ni peticions HTTP d'universitat per comprovar les relacions.
- La prova Java continua limitada a `contextLoads()`. Les peticions HTTP són exemples manuals, sense assercions de resultats o codis d'estat. No s'han localitzat informes Surefire de proves a la carpeta aportada.
- El preu transitori continua simulat amb `Random` a `BookService`; el comentari nou de `Book` que parla d'un servei extern descriu una possibilitat, no una integració implementada.

Els fitxers HTTP també contenen separadors `---` entre algunes peticions; caldrà comprovar-ne la compatibilitat amb el client HTTP de l'IDE que s'empri. Les lectures assumeixen les dades inicials i les escriptures les alteren: qualsevol futura comprovació amb recomptes esperats ha de partir d'una arrencada coneguda.

## Decisió sobre la referència canònica

**Decisió de David Pons, 2026-10-09:** emprar `unitat_2_ordinador_profe` com a font canònica i preparar una proposta actual integrant les millors parts de les dues còpies. `unitat_2_classe2526` es conserva com a antecedent anterior i com a code along identificat com a impartit el curs passat. Els fitxers HTTP senzills s'empraran com a primer exemple; els més complexos seran ampliacions.

La còpia del professor és **més completa per explicar i demostrar les consultes**: connecta els mètodes de repositori i servei amb rutes HTTP, aporta peticions de lectura i escriptura, desenvolupa comentaris docents i coincideix exactament amb els recursos de configuració i dades aportats separadament. El retorn `Page<Book>` també ofereix un punt d'explicació addicional. El canvi a `Long` a `Book.id` permet explicar millor l'estat previ a la generació de l'identificador.

**La cronologia queda confirmada pel professor; la font original conserva les discrepàncies inventariades.** Boot 3.5.6 és inferior a 3.5.7, la cerca d'estudiants perd `IgnoreCase` i la consulta nativa canvia un problema per una altra incoherència. La proposta actual recupera Boot 3.5.7 i `IgnoreCase`, corregeix la consulta i els exemples HTTP, i resol la publicació de relacions i els errors; s'han verificat aquestes decisions amb proves.

La [[code-along-ut2-2627|fitxa de la proposta actual]] i el README del projecte detallen les correccions i els resultats de verificació. La proposta usa Java 25 i Boot 3.5.7; es documenta explícitament que UT1 actual usa Boot 4.1.1. No s'ha incorporat una migració major de Boot a aquesta fusió.

## Impacte en els apunts, sense aplicar canvis

La introducció JPA/ORM, les dependències, la configuració i la càrrega de dades continuen sent compatibles amb totes dues còpies. Les anotacions i restriccions explicades també es mantenen: les `@Column` sense paràmetres eliminades no aportaven restriccions noves. Si s'adopta la còpia del professor, caldrà ajustar el fragment de `Book` (`Long` i absència de `@Column` a la data), els imports i camins de projecte, el retorn paginat, el nom de la consulta per any, les rutes de cerca disponibles i els camins d'universitat. La fitxa de consulta d'anotacions es pot conservar com a ampliació docent, diferenciant exemples vigents i anteriors.

**Estat actual:** font històrica canònica confirmada i proposta de projecte d'enguany creada, compilada i provada a `materials/`. Els apunts encara conserven fragments de la primera còpia i requeriran una revisió posterior contra la proposta; no s'ha reescrit el seu contingut en aquesta preparació. No es registra cap nova activitat impartida i els originals es conserven.
