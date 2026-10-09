---
lang: ca-ES
---

# Projecte ampliat 2026–2027 — Reserva per a UT3 i integració final

**Concreció posterior de David Pons, 2026-10-09:** aquesta primera proposta incorpora DTO i tractament de l'API que es treballaran a UT3. Es conserva íntegrament, amb el codi, README i les proves existents, per preparar aquestes ampliacions i la integració final. La [[code-along-ut2-2627-simplificat|versió simplificada `unitat2_2627_simplificat`]] és ara la proposta de referència per a UT2, centrada en entitats i repositoris. La comparació i la validació següents descriuen el projecte ampliat.

## Decisió del professor i estat

**Decisió directa de David Pons, 2026-10-09:** `unitat_2_ordinador_profe` passa a ser la [[code-along-ut2-ordinador-profe|font històrica canònica]]. El professor confirma que `unitat_2_classe2526` és anterior, amb les referències del 2 i del 14 d'octubre, i demana combinar-ne les millors parts. Per a HTTP concreta la progressió: exemple senzill primer i fitxers més complexos com a ampliació, conservant-los tots.

La primera proposta preparada és `materials/0613-desenvolupament-web-entorn-servidor/UT2/unitat2_2627/`. El [[../../materials/0613-desenvolupament-web-entorn-servidor/UT2/unitat2_2627/README.md|README original de la proposta]] documenta les decisions, l'arrencada, els fitxers HTTP, el contracte i la progressió. Està compilada i provada; per la concreció posterior del professor es conserva com a versió ampliada. No és un registre de projecte impartit a classe. Els originals es conserven.

## Integració i correccions

| Procedència | Tria aplicada |
|---|---|
| Font canònica del professor | Paquet `cat.paucasesnoves.unitat2`, `Long` a `Book.id`, columnes simples amb mapatge per defecte, injecció per l'únic constructor, API de consultes de llibres, retorn `Page<Book>` i comentaris docents. |
| Còpia de classe anterior | Boot 3.5.7, cerca `findByFullNameContainingIgnoreCase` i fitxer HTTP senzill `test-api.http`. |
| Compatibilitat entre còpies | Base `/university` i àlies `/api/university`; cerca de cursos amb `/courses/teacher/{name}` i `/courses/teacher/name/{name}`. Es preserven els noms dels tres fitxers HTTP originals, tots a `src/test/http/`. |
| Correcció de SQL | Taula `BOOK`, `LIKE CONCAT('%', :name, '%')`, paràmetre vinculat i `ORDER BY id LIMIT 5`; paginació derivada ordenada també per ID. |
| Correcció del PUT | Cas d'ISBN duplicat amb cos complet, separat del cas de cos incomplet; validació 400, absència 404, conflicte 409 i rollback comprovat. |
| Publicació de relacions | DTO d'entrada i sortida, curs amb referències per ID, transaccions al servei i `open-in-view=false`. El mapatge JPA bidireccional, cascada i `orphanRemoval` es conserven. |
| Configuració | `.properties` en UTF-8 amb comentaris refets; dades inicials originals, normalitzades a LF. `DB_CLOSE_ON_EXIT=FALSE`, codificació explícita del script i càrrega després de JPA. |
| Ampliacions de preparació | Resposta `BookPage`, ruta de pàgina amb totals, cerca HTTP d'estudiants, fitxers de relacions i demostració de paginació amb sis llibres, i proves d'integració. |

**Tria tècnica de la proposta:** Java 25 i Boot 3.5.7, la revisió superior dels dos projectes històrics. El POM afegeix el starter de validació a les dependències existents. UT1 actual usa Boot 4.1.1; la fusió d'UT2 conserva la branca dels exemples aportats i no incorpora una migració major. La consola H2 funciona amb les dependències de Boot 3.5; el mòdul `spring-boot-h2console` explicat als apunts només pertoca a l'adaptació a Boot 4.

## Progressió i comprovacions

**Progressió demanada i concretada com a pauta:** dependències i inicialització → `Book` → CRUD amb `test-api.http` → consultes amb `book_read.http` → escriptures i errors amb `book_write.http` → paginació opcional amb `book_pagination.http` → relacions d'universitat i publicació amb `university.http`. El README indica els punts d'aturada; no es fixen sessions ni s'inventen activitats impartides.

El projecte conserva 21 llibres inicials. Les dades de professors, cursos i estudiants es creen amb HTTP; la demostració de sis llibres d'un autor és opcional i no s'ha afegit a `data.sql`. Cada fitxer explica quan cal reiniciar per reproduir els recomptes.

**Validació executada el 2026-10-09:** JDK Microsoft 25.0.4.1, Maven 3.9.11 i `mvn verify`, amb **14 proves, zero fallades i zero errors**. S'han localitzat JDK i Maven fora del `PATH`; les dependències per a la verificació s'han resolt en un repositori temporal a `/tmp`, sense escriure a la configuració personal de Maven. La verificació cobreix inicialització d'arrencada, mapatge transitori, CRUD, consultes, restriccions i rollback, paginació, referències i JSON d'universitat, càrrega amb OSIV desactivat, compatibilitat de rutes, `IgnoreCase`, cascada i `orphanRemoval`. S'ha arrencat també el JAR amb Tomcat: HTTP real amb 21 llibres i consola H2 amb 200.

Els fitxers Java de prova són `Unitat2ApplicationTests` i `CodeAlongIntegrationTests`. La prova de context inicial comprova `data.sql` sense preparar registres dins el test; les proves d'integració preparen les dades abans de cada cas i no envolten les peticions en una transacció de test, per no ocultar problemes de càrrega lazy. No s'han executat ni alterat els projectes històrics.

**Verificació dels exemples HTTP:** les 38 peticions dels cinc fitxers s'han executat contra el servidor real amb cURL, amb els estats esperats: 11 de lectura, 5 del primer CRUD, 7 d'escriptura ampliada, 7 de paginació i 8 d'universitat. Les variables d'IDs s'han resolt amb les respostes rebudes; no s'han executat els scripts dins el client d'IntelliJ. La consola H2 i l'arrencada s'han comprovat separadament.

**Reinici verificat:** després de les escriptures de demostració, una nova arrencada ha retornat 21 llibres, *Clean Code* amb el títol original i la consulta d'universitat buida. S'han aturat els servidors de prova. S'han verificat també els enllaços locals de la documentació i la conservació dels originals i dels apunts amb hashes.

**Suport curricular orientatiu:** `RA6.a`–`RA6.g`, segons les correspondències detallades al README i a la [[../moduls/0613-desenvolupament-web-entorn-servidor/unitats/ut2-acces-dades|fitxa d'UT2]]. Fonts exactes: `raw/sources/2526_Servidor-master/00_general/RA.md`, apartat RA6, i `raw/sources/IFC33C - Desenvolupament web en entorn servidor.xlsx`, full `UT2`.

## Relació amb els apunts

Els [[../../materials/0613-desenvolupament-web-entorn-servidor/UT2/acces-a-dades-amb-spring.md|apunts Markdown]] es mantenen sense canvis de contingut durant aquesta preparació. La següent revisió d'UT2 els haurà d'alinear amb la versió simplificada: identificador `Long`, rutes disponibles, `Page`, SQL natiu corregit i proves de relacions. Les explicacions introductòries i la càrrega de dades continuen sent aplicables. DTO, validació i tractament elaborat d'errors d'aquesta versió s'han de reservar per preparar els materials de serveis web, segons la decisió del professor.

**Fonts oficials de contrast:** [consultes de Spring Data JPA 3.5](https://docs.spring.io/spring-data/jpa/reference/3.5/jpa/query-methods.html), [inicialització de dades de Spring Boot 3.5](https://docs.spring.io/spring-boot/3.5/how-to/data-initialization.html), [proves d'aplicacions de Spring Boot 3.5](https://docs.spring.io/spring-boot/3.5/reference/testing/spring-boot-applications.html) i [injecció per constructor de Spring Framework](https://docs.spring.io/spring-framework/reference/core/beans/annotation-config/autowired.html). El contrast s'acompanya ara d'execució de la proposta local, no de les fonts històriques.
