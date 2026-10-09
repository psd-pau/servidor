---
lang: ca-ES
author: David Pons
date: 2026-10-09
status: Proposta de code along centrat en UT2
---

# UT2 — Entitats i repositoris: versió simplificada

Projecte de preparació del code along de **David Pons**, CIFP Pau Casesnoves, **IFC33C**, curs 2026–2027. És la versió proposada per treballar **persistència a la UT2**. El [projecte complet](../unitat2_2627/README.md) es conserva per preparar ampliacions de serveis web a UT3 i la integració final. Cap dels dos es registra encara com a projecte impartit.

## Què es treballa ara

El centre és `domain/entity/` i `repository/`: mapatge objecte–taula, identificadors, restriccions, enumeracions, camps no persistents, CRUD, consultes derivades, JPQL, SQL natiu, paginació i relacions. Els controladors permeten observar aquestes operacions per HTTP; els serveis mantenen la separació per capes ja introduïda.

Es conserven **tots els repositoris sense canvis** respecte del projecte complet, inclosos `Page<Book>`, la consulta nativa corregida i `IgnoreCase`. Es conserva també el mapatge JPA de les quatre entitats, amb totes les relacions, cascada i `orphanRemoval`. De `Book` només s'han retirat les anotacions de Bean Validation i Jackson de la versió ampliada: els camps i les anotacions JPA no canvien. `Genre` i els 21 llibres de `data.sql` es mantenen.

La versió té les 14 classes/interfícies/enumeracions de producció dels exemples originals: aplicació, quatre entitats, enumeració, quatre repositoris, dos serveis i dos controladors. No hi ha DTO, mapper, gestor propi d'excepcions, validació de peticions ni selecció explícita de codis HTTP.

## Execució

Cal **JDK 25**. Obre aquesta carpeta com a projecte Maven; usa Spring Boot **3.5.7** i el wrapper Maven **3.9.11**.

```bash
./mvnw verify
./mvnw spring-boot:run
```

A Windows: `mvnw.cmd verify` i `mvnw.cmd spring-boot:run`. El JAR és `target/unitat2_2627_simplificat-0.0.1-SNAPSHOT.jar`.

Arrenca una de les dues versions a `http://localhost:8080`. La consola H2 és a `/h2-console`: URL JDBC `jdbc:h2:mem:testdb`, usuari `sa` i contrasenya buida. Cada arrencada recrea l'esquema i carrega els **21 llibres originals**; universitat comença sense registres. Atura i torna a iniciar per restaurar les dades abans de repetir demostracions.

El POM conserva Web, JPA, H2, DevTools i Test, i retira el starter de validació. `spring.jpa.open-in-view=false` continua configurat: les col·leccions lazy es llegeixen dins `UniversityService`, que té una única anotació `@Transactional` per poder consultar i modificar les relacions. És suport a la persistència.

## Controladors i serveis senzills

`BookController` rep paràmetres o un `Book` i crida el servei. Retorna el llibre, una llista o el recompte. La paginació retorna només els llibres de la pàgina; `Page` i els seus totals es poden examinar al repositori i als tests. `BookService` fa el CRUD, crida les consultes i simula `currentPrice` per mostrar `@Transient`.

`UniversityController` retorna **IDs en crear** professor, estudiant i curs, i **llistes de noms en consultar**. Les dues operacions d'assignació permeten observar el costat propietari: `Course.teacher` desa la clau forana i `Course.students` desa les matrícules a `ENROLLMENT`. Els getters de les relacions inverses es conserven a les entitats; les proves els comproven amb una lectura nova des de la BD.

La publicació d'IDs i noms evita serialitzar recursivament el graf professor → cursos → professor i estudiants → cursos → estudiants. Són valors simples, sense DTO ni configuració especial de Jackson. El disseny del contracte de l'API, les respostes estructurades i la seva evolució es treballaran a UT3.

Les absències de llibres mantenen el comportament senzill dels originals (`null`); les restriccions i altres errors conserven el tractament per defecte de Spring. No s'han reproduït els contractes 400/404/409 del projecte complet. Els cossos d'actualització dels exemples inclouen tots els camps editables.

## Ordre del code along i fitxers de prova

Pauta de preparació; no és un registre de sessions impartides.

| Bloc | Punt d'explicació i suport |
|---|---|
| Connexió i inicialització | Starter JPA, H2, `application.properties` i `data.sql`; esquema abans de les dades. |
| Primera entitat | `Book` i `Genre`: identificador, columnes, unicitat, enum i `@Transient`. |
| CRUD heretat | `BookRepository` i ús des de servei/controlador; [test-api.http](src/test/http/test-api.http) com a primer exemple, amb cinc peticions. |
| Consultes | Noms derivats, `@Query`, JPQL i SQL natiu; [book_read.http](src/test/http/book_read.http). |
| Modificació i restriccions | `save`, actualització i eliminació amb [book_write.http](src/test/http/book_write.http); restriccions de BD amb [book_constraints.http](src/test/http/book_constraints.http). |
| Paginació | `PageRequest`, ordre i `Page`; [book_pagination.http](src/test/http/book_pagination.http) afegeix dades temporals per tenir sis llibres d'un autor. |
| Relacions | `Teacher`, `Course`, `Student`, claus foranes, taula intermèdia, costat propietari i consultes per relació; [university.http](src/test/http/university.http). |
| Comprovació | [PersistenciaTests.java](src/test/java/cat/paucasesnoves/unitat2/PersistenciaTests.java): lectura després de guardar, restriccions, relacions inverses, cascada i `orphanRemoval`. |

S'han preservat els cinc noms de fitxer HTTP del projecte complet, adaptant-los a aquesta versió; s'hi afegeix `book_constraints.http`. El primer CRUD pressuposa una arrencada nova i el llibre creat té ID 22. Universitat també parteix d'una arrencada nova: professor 1, estudiants 1 i 2 i curs 1, amb seqüències independents. Si ja has creat altres registres, reinicia o usa els IDs que retornen les creacions.

Les dues peticions de restriccions han de fallar i es poden contrastar amb la consola i els tests; es deixa la resposta d'error per defecte. No hi ha scripts del client HTTP per guardar IDs ni casos destinats a ensenyar validació o gestió REST d'errors. El preu simulat varia entre lectures.

## Progressió del mòdul

| Moment | Focus docent concretat pel professor |
|---|---|
| UT2 | Persistència: entitats i repositoris, amb serveis i controladors mínims per fer visibles les operacions. |
| UT3 | Serveis web: DTO, representació externa, ús dels mètodes HTTP i tractament coherent de respostes i errors. El projecte complet és una reserva d'exemples per preparar-ho. |
| Unitat final | Integrar les parts treballades: infraestructura Spring, persistència, serveis web i aplicació/presentació. |

És una decisió de progressió docent, coherent amb l'[orientació del mòdul](../../../../wiki/orientacio-docent.md), no una nova formulació del currículum. La traçabilitat d'UT2 es manté amb `RA6.a` i `RA6.b` (tecnologies i connexió), `RA6.c` i `RA6.e` (recuperació i dades), `RA6.d` (publicació elemental del resultat), `RA6.f` (actualització i eliminació) i `RA6.g` (proves i documentació). Fonts: `raw/sources/2526_Servidor-master/00_general/RA.md`, apartat RA6, i `raw/sources/IFC33C - Desenvolupament web en entorn servidor.xlsx`, full `UT2`.

## Verificació

`mvn verify` ha completat compilació, empaquetament i **10 proves de persistència**, sense fallades ni errors. Usen H2 i repositoris reals; cada prova desfà els seus canvis. Els fitxers HTTP es comproven separadament contra un servidor real, de manera que la transacció dels tests no oculti problemes dels serveis o de càrrega lazy.

S'han executat **40 peticions dels sis fitxers HTTP** contra Tomcat, comprovant també els noms de les relacions i el nombre de llibres a cada pàgina. Les dues peticions de restriccions han fallat com correspon; la resta s'han completat. La consola H2 respon amb 200. S'ha verificat que el projecte complet i els apunts es conserven amb el mateix contingut, i que els repositoris i el mapatge JPA són equivalents.

La [fitxa de la versió simplificada](../../../../wiki/fonts/code-along-ut2-2627-simplificat.md) registra les comprovacions i les diferències. El projecte complet i els [apunts en elaboració](../acces-a-dades-amb-spring.md) es conserven sense canvis de contingut durant aquesta preparació.
