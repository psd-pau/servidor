# Code along d'U2 del curs 2025–2026

## Procedència i estat

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

- A `BookRepository.searchTop5ByAuthor`, la cadena SQL `author like '%:author%'` inclou el nom del paràmetre dins un literal. Cal corregir l'enllaç del paràmetre o emprar `Pageable` abans de presentar-la com una consulta funcional. La variant `findByAuthor(author, PageRequest.of(0, 5))` ja és al servei.
- `Teacher`, `Course` i `Student` tenen relacions bidireccionals i el controlador retorna les entitats directament. Cal comprovar la serialització JSON, la càrrega lazy i la forma de la resposta; els DTO poden separar el model persistent de la resposta web.
- `UniversityService.getStudentsInCourse()` accedeix a una col·lecció relacionada després de `findById()`. Cal verificar-ne el comportament transaccional i de càrrega abans d'emprar aquest endpoint com a demostració fiable.
- Els mètodes de consulta específica de `BookService` no són accessibles per HTTP en aquesta còpia. Els apunts han de distingir els mètodes implementats de les rutes realment disponibles.
- `BookService.update()` retorna `null` si no troba l'identificador, i el preu `@Transient` es genera aleatòriament en lectures. Convindrà explicar o revisar la gestió d'absències i el caràcter només demostratiu del preu.
- `data.sql` només inicialitza llibres. Les relacions d'universitat no tenen dades de mostra ni proves de comportament; el projecte tampoc inclou proves de repositori, servei o HTTP més enllà de `contextLoads()`.

Cap d'aquests punts no s'ha corregit al projecte històric. No s'ha executat ni compilat el projecte i no es presenten respostes HTTP com a verificades.

## Destinació

La [[../moduls/0613-desenvolupament-web-entorn-servidor/unitats/ut2-acces-dades|fitxa d'U2]] i el [[../moduls/0613-desenvolupament-web-entorn-servidor/desenvolupament|desenvolupament operatiu]] l'utilitzen com a antecedent. El [[../../materials/0613-desenvolupament-web-entorn-servidor/UT2/acces-a-dades-amb-spring.md|primer esborrany teòric]] ja viu a `materials/0613-desenvolupament-web-entorn-servidor/UT2/`; el DOCX i el PDF encara no existeixen. El projecte històric continua sent un antecedent, no un material vigent.

Per contrastar els conceptes tècnics de l'esborrany s'han consultat la [referència de consultes de Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html), la [inicialització SQL de Spring Boot](https://docs.spring.io/spring-boot/how-to/data-initialization.html) i la [referència de `@ManyToMany` de Jakarta Persistence](https://jakarta.ee/specifications/persistence/3.2/apidocs/jakarta.persistence/jakarta/persistence/manytomany). Les decisions de redacció i els exemples continuen basats en el codi local; aquests enllaços no acrediten l'execució del projecte històric.
