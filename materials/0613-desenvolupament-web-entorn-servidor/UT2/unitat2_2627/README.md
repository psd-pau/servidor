---
lang: ca-ES
author: David Pons
date: 2026-10-09
status: Proposta de versió definitiva per al code along
---

# UT2 — Accés a dades amb Spring, curs 2026–2027

Projecte de referència preparat per al code along del **CIFP Pau Casesnoves**, grup **IFC33C**. Autoria docent: **David Pons**. És una proposta resolta per preparar les explicacions i la programació progressiva amb l'alumnat; encara no es registra com a codi impartit enguany.

## Procedència i decisions de fusió

El professor ha designat [unitat_2_ordinador_profe](../../../../wiki/fonts/code-along-ut2-ordinador-profe.md) com a **font canònica** i ha confirmat que [unitat_2_classe2526](../../../../wiki/fonts/code-along-ut2-2526.md) és anterior, amb les referències del 2 i del 14 d'octubre. Aquesta còpia nova integra les dues versions i corregeix les discrepàncies; els originals continuen a `materials-anteriors/`.

| Decisió per a la proposta | Justificació |
|---|---|
| Java 25 i Spring Boot 3.5.7 | Java coincideix amb les fonts i UT1; Boot pren la revisió superior present a les dues fonts. La UT1 actual usa Boot 4.1.1: aquesta proposta conserva la branca 3.5 dels exemples de persistència, sense incorporar una migració major a la fusió. |
| Paquet `cat.paucasesnoves.unitat2`, artefacte `unitat2_2627` | Paquet de la font canònica i nom propi per distingir la proposta actual. |
| `Book.id` de tipus `Long` i camps simples sense `@Column` buida | Es conserva la versió canònica; un identificador pendent pot ser `null` i el mapatge per defecte no necessita anotacions sense opcions. |
| Injecció per constructor, sense `@Autowired` redundant | Es conserva la versió canònica, amb un únic constructor per component. |
| Totes les consultes HTTP de llibres | Es conserva l'API ampliada del professor i s'afegeix una resposta explícita de pàgina. |
| `Page<Book>` i `BookPage` | Es conserva el retorn paginat del professor. `top5` continua retornant una llista; `/page` publica contingut i metadades. |
| `findByFullNameContainingIgnoreCase` | Es recupera la cerca de la versió antiga i s'exposa a `/university/students/search`. |
| Rutes d'universitat amb compatibilitat | `/university` és el camí principal; `/api/university` i `/courses/teacher/name/{name}` continuen disponibles com a àlies. |
| SQL natiu sobre `BOOK`, paràmetre vinculat i ordre per ID | S'elimina el plural incorrecte i el paràmetre dins un literal; `CONCAT` construeix el patró. Tant la variant paginada com la nativa ordenen per ID. |
| Configuració en UTF-8 i 21 llibres inicials | Es reutilitza el conjunt original de dades. Els comentaris del `.properties` s'han redactat en català sense els caràcters malmesos de la font. |
| Errors 400, 404 i 409, i transaccions al servei | Les peticions incompletes, les absències i els conflictes de restricció tenen comportaments diferents i comprovables. |
| DTO per publicar universitat i `open-in-view=false` | Les relacions JPA es conserven; el JSON té una forma finita i es prepara dins la transacció del servei. |

La [fitxa de la proposta a la wiki](../../../../wiki/fonts/code-along-ut2-2627.md) en registra l'estat i la validació. Els [apunts en elaboració](../acces-a-dades-amb-spring.md) encara conserven fragments de la primera font: s'haurà de fer la revisió posterior contra aquest projecte.

## Obrir, executar i reiniciar

Cal un **JDK 25**. Obre aquesta carpeta, la que conté `pom.xml`, com a projecte Maven. El wrapper inclòs usa Maven 3.9.11; la primera execució necessita accés als repositoris de Maven.

```bash
./mvnw verify
./mvnw spring-boot:run
```

A Windows, empra `mvnw.cmd verify` i `mvnw.cmd spring-boot:run`. Després de `verify`, també es pot executar el JAR:

```bash
java -jar target/unitat2_2627-0.0.1-SNAPSHOT.jar
```

L'aplicació escolta a `http://localhost:8080`. Atura-la amb `Ctrl+C` i torna-la a iniciar per restaurar les dades. No hi ha una ruta HTTP per reinicialitzar la base de dades.

La consola és a `http://localhost:8080/h2-console`; URL JDBC `jdbc:h2:mem:testdb`, usuari `sa`, contrasenya buida. Hibernate recrea l'esquema i Spring Boot executa `data.sql` després. Cada arrencada aporta **21 llibres**, amb identificadors de l'1 al 21. Les entitats d'universitat comencen sense registres; es creen amb el seu fitxer HTTP. Les dades no es conserven en aturar l'aplicació.

Les dependències de persistència són `spring-boot-starter-data-jpa` i H2 amb scope `runtime`. La proposta afegeix `spring-boot-starter-validation` per al bloc posterior de validació HTTP. Amb **Boot 3.5.7** no s'afegeix `spring-boot-h2console`, que correspon a l'adaptació documentada per a Boot 4 als apunts.

## Ordre proposat del code along

És una pauta de preparació, no un registre de sessions impartides. El projecte conté la solució completa; es pot programar per blocs i reservar les ampliacions per al moment que pertoqui.

| Pas | Fitxers i aturada d'explicació |
|---|---|
| 1. Preparar la persistència | `pom.xml`, `application.properties`, `data.sql`: dependències, connexió, creació d'esquema i càrrega a l'arrencada. |
| 2. Primera entitat | `Book`, `Genre`: taula, identificador, columnes, restriccions, enumeració i `@Transient`. Separar el mapatge JPA de la validació i de Jackson, afegits per a les ampliacions. |
| 3. Primer recorregut complet | `BookRepository`, CRUD de `BookService` i primer bloc de `BookController`; executar **`test-api.http`**, recuperat de la versió antiga. |
| 4. Consultes i escriptures ampliades | Mètodes derivats, JPQL, SQL natiu, `Pageable` i `Page`; **`book_read.http`**, **`book_write.http`** i, opcionalment, **`book_pagination.http`**. |
| 5. Relacions | `Teacher`, `Course`, `Student` i repositoris: professor–cursos, cursos–estudiants, costat propietari, taula intermèdia, cascada i `orphanRemoval`. |
| 6. Publicar relacions | `UniversityDto`, `UniversityService`, `UniversityController` i **`university.http`**: referències per ID, transaccions, càrrega lazy i forma del JSON. |
| 7. Comprovar i tractar errors | `@Valid`, `ResourceNotFoundException`, `ApiExceptionHandler` i proves: distingir petició invàlida, absència i conflicte de dades. |

El primer exemple HTTP conserva peticions literals i un ID conegut per facilitar-ne la lectura. L'exemple de relacions, posterior, usa el client HTTP d'IntelliJ per guardar els IDs rebuts; altres clients poden copiar aquests IDs manualment.

## Fitxers HTTP: bàsic i ampliacions

Tots són a `src/test/http/`. Es conserven **els tres noms de fitxer originals**; el `test-api.http` antic s'ha traslladat de recursos de producció a aquesta carpeta i se n'han activat les peticions. Els separadors són `###`, sense les línies `---` que hi havia a l'altra còpia.

| Fitxer | Funció i condicions |
|---|---|
| [test-api.http](src/test/http/test-api.http) | **Primer exemple.** Cinc peticions CRUD: crear, llistar, actualitzar, llegir i eliminar. Executar en ordre just després d'una arrencada nova: el llibre creat té ID 22. |
| [book_read.http](src/test/http/book_read.http) | **Ampliació de lectura.** Onze peticions: les deu del professor i una de pàgina amb totals. Els recomptes comentats pressuposen els 21 llibres inicials. |
| [book_write.http](src/test/http/book_write.http) | **Ampliació d'escriptura.** Set peticions: les sis del professor revisades i el PUT incomplet separat com a cas 400. El cas d'ISBN duplicat envia tots els camps obligatoris i dona 409. |
| [book_pagination.http](src/test/http/book_pagination.http) | **Ampliació opcional.** Afegeix tres llibres inventats a un autor que ja en té tres i mostra límit de cinc i segona pàgina. No altera el `data.sql` inicial. |
| [university.http](src/test/http/university.http) | **Relacions.** Crea professor, dos estudiants i curs; comprova les lectures, `IgnoreCase` i un camí antic. Guarda els IDs reals amb scripts del client HTTP d'IntelliJ. |

Reinicia abans de repetir un fitxer d'escriptura o de comprovar recomptes de les dades inicials. Repetir POST sense reiniciar pot donar 409 per unicitat. El preu simulat canvia entre lectures i no s'ha de fer servir per comparar resultats.

## Contracte de les peticions

- `POST /books` crea un llibre i retorna **201**. L'ID i `currentPrice` són camps de sortida: el client no els assigna. El preu es simula al servei i no es desa.
- `PUT /books/{id}` substitueix els camps editables. Cal enviar títol, autor i ISBN; les dates i el gènere, que el model permet ometre, queden a `null` si no s'envien. No s'ha implementat PATCH.
- Les lectures i actualitzacions d'un llibre absent, la seva eliminació i la cerca d'un ISBN absent retornen **404**. Una cerca que retorna una llista pot donar una llista buida amb **200**.
- Un cos amb camps obligatoris absents, longituds invàlides o ISBN sense 13 dígits retorna **400**. No es comprova el dígit de control bibliogràfic de l'ISBN.
- Un ISBN duplicat o un parell títol–autor duplicat retorna **409**. Una actualització en conflicte es desfà i conserva el llibre anterior.
- `DELETE /books/{id}` retorna **204** quan elimina el llibre.
- `/books/author/{author}/top5` cerca l'autor exacte. `/nativeTop5` cerca un fragment; no són filtres idèntics. Tots dos ordenen per ID. `/page?page=0&size=5` publica `content`, `page`, `size`, `totalElements` i `totalPages`; `page` comença en zero i `size` va d'1 a 100.
- La creació d'un curs rep `name`, `credits`, `teacherId` opcional i `studentIds` opcional. Els IDs han de correspondre a registres existents; una referència absent dona 404. Els estudiants repetits al cos no dupliquen matrícules. S'assignen les relacions des de `Course`, el costat propietari.
- Les respostes d'universitat inclouen professor i estudiants del curs, sense tornar a publicar els cursos des d'aquests objectes. Les entitats mantenen les relacions bidireccionals; els DTO defineixen la forma externa.

## Validació i límits

**Verificació del 2026-10-09:** `mvn verify` completa la compilació, l'empaquetament i **14 proves sense errors ni fallades**. Les proves usen H2, Hibernate i MVC, sense substituir els repositoris per mocks. Comproven càrrega real a l'arrencada, absència de columna per a `currentPrice`, CRUD, cerques, consulta nativa, paginació amb sis llibres, validació, conflictes i rollback, relacions amb `open-in-view=false`, camins compatibles, `IgnoreCase`, cascada i `orphanRemoval`.

També s'ha arrencat el JAR amb Tomcat en un port local temporal, s'han observat els 21 llibres per HTTP i la consola H2 ha respost amb 200. La validació es refereix a aquesta proposta; les fonts històriques continuen sense corregir-se. Les proves i la preparació no acrediten que el contingut ja s'hagi impartit.

S'han executat també **les 38 peticions dels cinc fitxers HTTP** contra el servidor real amb cURL, inclosos els casos 400 i 409. Per a universitat, els IDs s'han substituït amb els valors rebuts a les respostes; aquesta comprovació no és una execució dels scripts dins IntelliJ.

Després de les escriptures s'ha reiniciat el servidor i s'ha comprovat el retorn als **21 llibres originals**, amb *Clean Code* restaurat i universitat buida. Els servidors temporals de comprovació s'han aturat.

La comprovació d'HTTP automatitzada al projecte usa MockMvc; els scripts d'IDs de `university.http` estan pensats per a IntelliJ. No hi ha interfície gràfica de l'aplicació ni autenticació. La proposta prepara el contingut de persistència, consultes i relacions de la UT2.

**Traçabilitat orientativa:** dependències i connexió, `RA6.a` i `RA6.b`; entitats, consultes i conjunts de dades, `RA6.c` i `RA6.e`; publicació amb controladors i DTO, `RA6.d`; CRUD, `RA6.f`; comprovacions i documentació, `RA6.g`. Fonts: `raw/sources/2526_Servidor-master/00_general/RA.md`, apartat RA6, i `raw/sources/IFC33C - Desenvolupament web en entorn servidor.xlsx`, full `UT2`. El detall curricular viu a la [fitxa d'UT2](../../../../wiki/moduls/0613-desenvolupament-web-entorn-servidor/unitats/ut2-acces-dades.md).
