# Code along d'U2 del curs 2025–2026

## Procedència i estat

**Estat concretat pel professor, 2026-10-09:** aquesta còpia és anterior a [[code-along-ut2-ordinador-profe|`unitat_2_ordinador_profe`]], que passa a ser la font històrica canònica. Les referències de dates aportades són el 2 i el 14 d'octubre. La [[code-along-ut2-2627|proposta actual `unitat2_2627`]] recupera d'aquí Boot 3.5.7, la cerca d'estudiants amb `IgnoreCase` i el fitxer HTTP senzill, dins la fusió demanada pel professor. Aquesta font es conserva intacta com a antecedent.

**Font aportada per David Pons el 2026-10-08:** [[../../materials-anteriors/0613-desenvolupament-web-entorn-servidor/UT2/unitat_2_classe2526.zip|`unitat_2_classe2526.zip`]], desat a `materials-anteriors/0613-desenvolupament-web-entorn-servidor/UT2/`. El professor l'identifica com el code along que va emprar el curs passat. Se n'ha extret el [[../../materials-anteriors/0613-desenvolupament-web-entorn-servidor/UT2/unitat_2_classe2526/pom.xml|projecte font]] a `unitat_2_classe2526/`; el ZIP es conserva íntegre. L'extracció omet `.idea/` i `target/`, que són configuració local de l'IDE i resultats de compilació presents al ZIP.

La revisió següent és una lectura estàtica del codi. El POM declara Java 25, Spring Boot 3.5.7, Spring Web, Spring Data JPA i H2. L'aplicació usa H2 en memòria, genera l'esquema amb `spring.jpa.hibernate.ddl-auto=create`, activa la consola H2 i carrega dades de llibres des de `data.sql` després de crear les taules. Aquestes són característiques de la còpia històrica, no decisions tancades per al curs 2026–2027.

## Inventari funcional

Els camins següents són relatius a `unitat_2_classe2526/src/main/java/cat/paucasesnoves/unitat_2/`.

| Bloc | Fitxers i contingut observat |
|---|---|
| Arrencada i configuració | `Unitat2Application.java`, `pom.xml`, `src/main/resources/application.properties` i `data.sql`; H2, JPA/Hibernate i 21 insercions de llibres. |
| Llibres: model | `domain/entity/Book.java` i `domain/enums/Genre.java`; `@Entity`, identificador generat, columnes i restriccions, `@Enumerated(EnumType.STRING)` i preu calculat `@Transient`. |
| Llibres: persistència | `repository/BookRepository.java`; `JpaRepository`, consultes derivades, `Pageable`, dues consultes JPQL amb `@Query` i una consulta SQL nativa. |
| Llibres: ús web | `service/BookService.java` i `controller/BookController.java`; CRUD a `/books`. El servei també prepara cerques, recompte i paginació, però el controlador històric no n'exposa rutes. |
| Universitat: model | `Teacher`, `Course` i `Student`; relacions professor–cursos `@OneToMany`/`@ManyToOne` i cursos–estudiants `@ManyToMany` amb taula `ENROLLMENT`. |
| Universitat: persistència i ús web | `TeacherRepository`, `CourseRepository`, `StudentRepository`, `UniversityService` i `UniversityController`; creació de les tres entitats, cerca de cursos per nom del professor i obtenció dels estudiants d'un curs a `/api/university`. |
| Comprovació inclosa | `src/test/.../Unitat2ApplicationTests.java` només conté `contextLoads()`; `src/main/resources/test-api.http` conté peticions de mostra de `/books` comentades. |

## Progressió suggerida per als nous materials

**Proposta de preparació, no seqüència de sessions confirmada:** (1) connexió H2, `application.properties` i dades inicials; (2) `Book` i el pas d'objecte Java a taula; (3) `JpaRepository` i CRUD observables a `/books`; (4) consultes derivades, JPQL i paginació, explicant que els mètodes de cerca del servei encara no tenen ruta HTTP; (5) `Teacher`, `Course`, `Student` i propietat de les relacions; (6) consulta que travessa una relació i publicació controlada de dades. A cada bloc es pot aturar la programació per relacionar el codi amb el comportament SQL i HTTP, seguint l'[[../orientacio-docent|orientació docent]].

**Traçabilitat docent orientativa:** H2 i configuració il·lustren `RA6.a` i `RA6.b`; entitats, dades inicials i repositoris, `RA6.c` i `RA6.e`; respostes del controlador, `RA6.d`; creació, actualització i eliminació de llibres, `RA6.f`. `RA6.g` demana proves i documentació observables: la prova de context i les peticions comentades del projecte són un punt de partida, però no acrediten per si soles aquest criteri. La formulació dels CA procedeix de `raw/sources/2526_Servidor-master/00_general/RA.md`, apartat «RA6 — Desenvolupa aplicacions web amb accés a bases de dades»; l'assignació d'U2 i les 40 hores procedeixen de `raw/sources/IFC33C - Desenvolupament web en entorn servidor.xlsx`, fulls `UT2`, `6-Distribució temporal` i `2-ANCORATGE CURRICULAR`.

## Punts a revisar abans de redactar els apunts

### Inventari de mapatge sense relacions per a la fitxa de consulta

**Revisió del 2026-10-09 per ampliar l'apartat 5 dels apunts:** s'han llegit les quatre classes de `domain/entity/`. Aquest inventari exclou les anotacions de relació per indicació del professor; no modifica el bloc de relacions dels materials.

| Entitat | Anotacions i opcions sense relacions observades |
|---|---|
| `Book` | `@Entity`; `@Table(name = "BOOK", uniqueConstraints = ...)`; `@UniqueConstraint(columnNames = {"title", "author"})`; `@Id`; `@GeneratedValue(strategy = GenerationType.IDENTITY)`; `@Column(nullable = false, length = 150)` a `title`, longitud `100` a `author`, longitud `13` i `unique = true` a `isbn`; `@Column` sense paràmetres a `publishedDate`; `@Enumerated(EnumType.STRING)`; `@Transient` a `currentPrice`. |
| `Course` | `@Entity`; `@Table(name = "COURSE")`; `@Id`; `@GeneratedValue` sense paràmetres; `@Column(nullable = false, unique = true)` a `name`; `@Column` sense paràmetres a `credits`. |
| `Student` | `@Entity`; `@Table(name = "STUDENT")`; `@Id`; `@GeneratedValue` sense paràmetres; `@Column(nullable = false)` a `fullName`; `@Column` sense paràmetres a `email`. |
| `Teacher` | `@Entity`; `@Table(name = "TEACHER")`; `@Id`; `@GeneratedValue` sense paràmetres; `@Column(nullable = false)` a `fullName`; `@Column` sense paràmetres a `department`. |

Les tres declaracions de `@GeneratedValue` sense paràmetres seleccionen `AUTO`, no `IDENTITY` explícit. Els apunts restitueixen l'anotació `@Column` sense paràmetres de `Book.publishedDate`, omesa al fragment inicial. S'han documentat també com a ampliacions, sense atribuir-les al projecte, les opcions `schema`, `catalog`, `indexes`, el nom de restricció, les altres opcions habituals de columna, les estratègies `SEQUENCE`, `TABLE` i `UUID`, els generadors configurables i `@Basic`, `@Lob` i `@Version`.

### Punts funcionals de la còpia històrica

- A `BookRepository.searchTop5ByAuthor`, la cadena SQL `author like '%:author%'` inclou el nom del paràmetre dins un literal. Cal corregir l'enllaç del paràmetre o emprar `Pageable` abans de presentar-la com una consulta funcional. La variant `findByAuthor(author, PageRequest.of(0, 5))` ja és al servei.
- `Teacher`, `Course` i `Student` tenen relacions bidireccionals i el controlador retorna les entitats directament. Cal comprovar la serialització JSON, la càrrega lazy i la forma de la resposta; els DTO poden separar el model persistent de la resposta web.
- `UniversityService.getStudentsInCourse()` accedeix a una col·lecció relacionada després de `findById()`. Cal verificar-ne el comportament transaccional i de càrrega abans d'emprar aquest endpoint com a demostració fiable.
- Els mètodes de consulta específica de `BookService` no són accessibles per HTTP en aquesta còpia. Els apunts han de distingir els mètodes implementats de les rutes realment disponibles.
- `BookService.update()` retorna `null` si no troba l'identificador, i el preu `@Transient` es genera aleatòriament en lectures. Convindrà explicar o revisar la gestió d'absències i el caràcter només demostratiu del preu.
- `data.sql` només inicialitza llibres. Les relacions d'universitat no tenen dades de mostra ni proves de comportament; el projecte tampoc inclou proves de repositori, servei o HTTP més enllà de `contextLoads()`.

Cap d'aquests punts no s'ha corregit al projecte històric. No s'ha executat ni compilat el projecte i no es presenten respostes HTTP com a verificades.

## Destinació

**Comparació de versions, 2026-10-09:** s'ha incorporat el [[code-along-ut2-ordinador-profe|projecte `unitat_2_ordinador_profe` i la comparació completa]]. La còpia del professor exposa vuit consultes addicionals de llibres, retorna `Page<Book>` al repositori, usa `Long` a `Book.id` i aporta dos fitxers de peticions HTTP. Manté el mateix model relacional i les dades, però té Boot 3.5.6 davant 3.5.7, canvia els camins d'universitat, perd `IgnoreCase` a la cerca d'estudiants i introdueix `books` a la consulta nativa quan la taula és `BOOK`. El professor l'ha designada canònica; la proposta actual combina les millores de les dues fonts i resol aquestes discrepàncies. Aquesta còpia de classe es conserva com a antecedent impartit i els apunts teòrics no s'han modificat en preparar el projecte nou.

**Suport aportat separadament el 2026-10-09:** la [[dades-exemple-classe-ut2|configuració comentada i les dades de demostració de `dades exemple classe/`]] coincideixen amb aquesta còpia històrica, excepte el nom de l'aplicació i detalls de format. La nova fitxa documenta les 12 propietats, els 21 llibres i la codificació dels originals; el POM d'aquest projecte és la referència de la dependència H2.

La [[../moduls/0613-desenvolupament-web-entorn-servidor/unitats/ut2-acces-dades|fitxa d'U2]] i el [[../moduls/0613-desenvolupament-web-entorn-servidor/desenvolupament|desenvolupament operatiu]] l'utilitzen com a antecedent. El [[../../materials/0613-desenvolupament-web-entorn-servidor/UT2/acces-a-dades-amb-spring.md|primer esborrany teòric]] ja viu a `materials/0613-desenvolupament-web-entorn-servidor/UT2/`; el DOCX i el PDF encara no existeixen. El projecte històric continua sent un antecedent, no un material vigent.

Per contrastar els conceptes tècnics de l'esborrany s'han consultat la [referència de consultes de Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html), la [inicialització SQL de Spring Boot](https://docs.spring.io/spring-boot/how-to/data-initialization.html) i la [referència de `@ManyToMany` de Jakarta Persistence](https://jakarta.ee/specifications/persistence/3.2/apidocs/jakarta.persistence/jakarta/persistence/manytomany). Les decisions de redacció i els exemples continuen basats en el codi local; aquests enllaços no acrediten l'execució del projecte històric.

**Ampliació introductòria dels apunts, 2026-10-09:** s'han contrastat el POM històric d'UT2 (Spring Boot 3.5.7) i `materials/0613-desenvolupament-web-entorn-servidor/UT1/unitat1_2627/pom.xml` (Spring Boot 4.1.1): el starter JPA i H2 són les incorporacions relatives a persistència respecte de la UT1. Els fragments dels apunts no fixen versions ni modifiquen el parent de cap projecte. Fonts oficials de contrast: [Jakarta Persistence](https://jakarta.ee/specifications/persistence/3.1/), [introducció a Hibernate ORM](https://docs.hibernate.org/orm/6.6/introduction/html_single/), [Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html), [integració JPA a Spring Boot 3.5](https://docs.spring.io/spring-boot/3.5/reference/data/sql.html), [integració JPA a Spring Boot 4.1](https://docs.spring.io/spring-boot/reference/data/sql.html), [dependències transitives i scopes de Maven](https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html) i [gestió de versions amb el parent de Spring Boot](https://docs.spring.io/spring-boot/3.5/maven-plugin/using.html). La justificació de l'ORM i la primera passa de dependències s'han incorporat al Markdown com a ampliació parcial, sense executar ni modificar els projectes.

**Contrast de l'apartat 5 i la fitxa de consulta, 2026-10-09:** [requisits d'entitat del tutorial Jakarta EE](https://jakarta.ee/learn/docs/jakartaee-tutorial/current/persist/persistence-intro/persistence-intro.html), [especificació Jakarta Persistence 3.1](https://jakarta.ee/specifications/persistence/3.1/jakarta-persistence-spec-3.1), documentació de [`@Table`](https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/table), [`@UniqueConstraint`](https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/uniqueconstraint), [`@Column`](https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/column), [`@Enumerated`](https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/enumerated), [`@Transient`](https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/transient), [`GenerationType`](https://jakarta.ee/specifications/persistence/3.2/apidocs/jakarta.persistence/jakarta/persistence/generationtype), [`@SequenceGenerator`](https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/sequencegenerator), [`@TableGenerator`](https://jakarta.ee/specifications/persistence/3.2/apidocs/jakarta.persistence/jakarta/persistence/tablegenerator), [`@Basic`](https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/basic), [`@Lob`](https://jakarta.ee/specifications/persistence/3.2/apidocs/jakarta.persistence/jakarta/persistence/lob) i [`@Version`](https://jakarta.ee/specifications/persistence/3.2/apidocs/jakarta.persistence/jakarta/persistence/version). Les opcions recollides són compatibles amb l'API 3.1 de referència; no s'han incorporat les noves opcions de DDL de 3.2. La fitxa de consulta és una ampliació docent de Markdown, no una modificació ni una execució del projecte.
