---
lang: ca-ES
---

# UT2 — Code along simplificat: entitats i repositoris

## Decisió docent i estat

**Concreció directa de David Pons, 2026-10-09:** UT2 ha de focalitzar entitats i repositoris. Cal disposar de serveis i controladors senzills, com als projectes originals, per poder fer les comprovacions de persistència. DTO, disseny de l'API REST i tractament elaborat de mètodes i respostes es treballaran a UT3; la unitat final integrarà les parts del mòdul. Es demana conservar el projecte complet ja preparat.

S'ha creat `materials/0613-desenvolupament-web-entorn-servidor/UT2/unitat2_2627_simplificat/`, amb [[../../materials/0613-desenvolupament-web-entorn-servidor/UT2/unitat2_2627_simplificat/README.md|README i progressió]]. **Referència confirmada per David Pons, 2026-10-09:** aquesta és la versió sobre la qual es generaran els materials teòrics de l'alumnat d'UT2, a partir de `acces-a-dades-amb-spring.md`. Les revisions dels exemples, noms, consultes i comportaments dels apunts s'han de contrastar amb aquest projecte. [[code-along-ut2-2627|`unitat2_2627`]] es conserva íntegre com a versió ampliada i reserva d'exemples per preparar UT3 i la integració final. La [[code-along-ut2-ordinador-profe|font històrica canònica]] continua sent la còpia de l'ordinador del professor. No es registra cap projecte nou com a impartit.

## Abast de la simplificació

| Part | Resultat |
|---|---|
| Entitats | Mateixos camps i anotacions JPA que al projecte complet; `Teacher`, `Course`, `Student` i `Genre` idèntics. De `Book` es retiren només les anotacions i imports de Bean Validation i Jackson afegits al preparar la versió ampliada. |
| Repositoris | Els quatre fitxers es conserven idèntics: CRUD heretat, consultes derivades, JPQL, SQL natiu corregit, `Page<Book>` i `IgnoreCase`. |
| Controladors | Delegació directa al servei; llibres o llistes, IDs en crear universitat i noms en consultar relacions. Cap DTO, `@Valid`, `ResponseEntity`, `@ResponseStatus` ni gestor propi d'excepcions. |
| Serveis | CRUD i consultes curts. `UniversityService` conserva una transacció per accedir a col·leccions lazy i assigna les relacions des del costat propietari. |
| Relacions publicades | IDs i noms suficients per observar el resultat; no es retorna tot el graf bidireccional ni s'afegeixen anotacions de Jackson a les entitats. |
| Configuració | Java 25, Boot 3.5.7, H2 i els 21 llibres inicials. Es retira el starter de validació; es manté OSIV desactivat i la càrrega després de JPA. |
| HTTP | Es conserven els cinc noms de fitxer de la versió ampliada, adaptats, i s'afegeix un fitxer de restriccions. Creació i consultes de relacions amb peticions literals i IDs inicials coneguts. |
| Proves | Una classe amb deu casos centrats en persistència; comprovació dels fitxers HTTP separada dels tests transaccionals. |

La versió torna a les **14 classes/interfícies/enumeracions de producció** del model original. Els controls retirats pertanyen a la preparació de l'API de les unitats posteriors; la versió simplificada manté les restriccions del mapatge i de la BD. El comportament d'absències i errors és elemental i es documenta com a tal, sense atribuir-li el contracte de la versió ampliada.

## Progressió i evidències de persistència

Dependències i inicialització → `Book` → CRUD i HTTP senzill → consultes derivades/JPQL/natives → restriccions i modificació → `Pageable` i `Page` → model d'universitat i costat propietari → comprovacions de relacions, cascada i `orphanRemoval`. El controlador és un instrument per veure els resultats, i el servei manté la separació de responsabilitats de l'[[../orientacio-docent|enfocament del mòdul]].

**Correspondència orientativa amb `RA6`:** connexió i tecnologies, `RA6.a`–`RA6.b`; entitats, consultes i dades, `RA6.c` i `RA6.e`; publicar llibres, IDs i noms, `RA6.d`; inserir, actualitzar i eliminar, `RA6.f`; proves i README, `RA6.g`. Fonts exactes: `raw/sources/2526_Servidor-master/00_general/RA.md`, apartat «RA6 — Desenvolupa aplicacions web amb accés a bases de dades», i `raw/sources/IFC33C - Desenvolupament web en entorn servidor.xlsx`, full `UT2`. La simplificació és una decisió docent, no una reducció dels criteris curriculars.

## Comprovacions executades

### Ampliació dels apunts de repositoris, 2026-10-09

Per encàrrec de David Pons, els apartats 6 i 7 d'[[../../materials/0613-desenvolupament-web-entorn-servidor/UT2/acces-a-dades-amb-spring.md|accés a dades amb Spring]] s'han ampliat i alineat amb aquesta versió. S'hi expliquen herència i injecció de `JpaRepository`, les quatre operacions CRUD, `Optional`, identificadors nous, actualització dels camps editables, absència en eliminar, restriccions, `flush` i transaccions. Les variants heretades habituals que no criden els serveis s'identifiquen com a ampliacions.

L'inventari de consultes cobreix les **12 signatures declarades als quatre repositoris**: vuit a `BookRepository` (inclosa la sobrecàrrega amb `Pageable`), dues a `CourseRepository`, una a `TeacherRepository` i una a `StudentRepository`. Inclou les dues consultes JPQL i l'SQL natiu literal corregit, `Containing`, `IgnoreCase`, `GreaterThan`, navegació de propietats, ordenació, vinculació amb `@Param` i tipus de retorn. S'explica `Page<Book>` i es distingeixen les metadades del contingut retornat per HTTP. El nom `searchTop5ByAuthor` té `@Query`: el límit prové de l'SQL declarat. `YEAR` es precisa com a forma admesa per Hibernate, sense atribuir-li portabilitat a qualsevol proveïdor JPA.

La taula de rutes coincideix amb els controladors actuals; les consultes de departament i crèdits es comproven als tests i no tenen ruta pròpia. Les cerques sobre relacions només s'identifiquen; l'apartat 8 es conserva per ampliar-lo posteriorment. La presentació i l'índex indiquen l'alineació parcial amb el projecte actual.

**Verificació d'aquesta revisió:** signatures i tres consultes declarades contrastades literalment, cinc fragments CRUD idèntics a `BookService`, codi font dels dos projectes conservat amb SHA-256 i apartats 1–5 i 8–10 dels apunts intactes. S'ha tornat a executar `mvn verify`: 10 proves superades, zero fallades i zero errors, amb H2 i les dades del projecte. L'execució ha requerit sortir del sandbox perquè la seva restricció d'adjunció d'agents JVM impedia inicialitzar Mockito; no s'ha modificat el projecte per resoldre-ho. No s'han creat proves noves, exportacions ni registres d'activitats impartides.

### Verificació inicial del projecte

Compilació, empaquetament i `mvn verify` amb **10 proves, zero fallades i zero errors**, usant JDK 25 i Maven 3.9.11. Es comproven dades inicials, enum com a text, camp `@Transient`, identificador generat, CRUD, consultes, paginació amb sis llibres, restriccions individuals/compostes/nullabilitat, propietat i lectura inversa de relacions, consultes sobre relacions, `IgnoreCase`, cascada i `orphanRemoval`.

Les proves de persistència són transaccionals i desfan les dades al final de cada cas. La comprovació per HTTP es fa en un procés de servidor separat, amb `open-in-view=false`; això verifica el servei sense dependre de la transacció del test. Les fonts històriques i el projecte complet es conserven. Les revisions dels apunts han alineat configuració, mapatge, repositoris i relacions amb aquesta versió; el document continua en elaboració i encara no s'ha exportat.

**Comprovació dels fitxers HTTP:** 40 peticions executades amb cURL contra Tomcat: 11 de lectura, 5 del primer CRUD, 2 de restriccions, 5 d'escriptura ampliada, 7 de paginació i 10 d'universitat. Les respostes de relacions contenen els noms esperats i les pàgines retornen els recomptes esperats. La consola H2 ha respost amb 200. Els errors de les dues restriccions es deixen amb la resposta per defecte; no s'hi introdueix un contracte REST nou.

**Conservació verificada en crear la versió simplificada:** els 86 fitxers del projecte complet inventariats abans de la còpia mantenien els seus hashes; també el Markdown dels apunts, abans de l'ampliació de repositoris. Els quatre repositoris, les tres entitats d'universitat i `Genre` són idèntics. `Book` és idèntic després de retirar exclusivament imports i anotacions de validació/Jackson; no s'ha canviat cap declaració JPA. Enllaços de documentació comprovats.

## Fonts tècniques del contrast de repositoris

Documentació oficial de la branca Spring Data 3.5 i API/codi 3.5.5, versió emprada per Spring Boot 3.5.7 al projecte:

- [API de `CrudRepository` 3.5.5](https://docs.spring.io/spring-data/commons/docs/3.5.5/api/org/springframework/data/repository/CrudRepository.html): retorns, IDs absents i operacions heretades.
- [Persistència d'entitats](https://docs.spring.io/spring-data/jpa/reference/3.5/jpa/entity-persistence.html): detecció d'entitats noves, `persist` i `merge`.
- [Codi de `SimpleJpaRepository` 3.5.5](https://raw.githubusercontent.com/spring-projects/spring-data-jpa/3.5.5/spring-data-jpa/src/main/java/org/springframework/data/jpa/repository/support/SimpleJpaRepository.java): implementació de `save`, `deleteById`, `saveAndFlush` i `flush`.
- [Consultes JPA](https://docs.spring.io/spring-data/jpa/reference/3.5/jpa/query-methods.html): noms derivats, operadors, `@Query`, paràmetres i SQL natiu.
- [Definició de mètodes de consulta](https://docs.spring.io/spring-data/jpa/reference/3.5/repositories/query-methods-details.html): navegació de propietats, ordenació, límits i paginació.
- [Valors absents als repositoris](https://docs.spring.io/spring-data/jpa/reference/3.5/repositories/null-handling.html): `Optional`, `null` i col·leccions buides.
- [Transaccions](https://docs.spring.io/spring-data/jpa/reference/3.5/jpa/transactions.html): configuració del CRUD heretat i límits al servei.
- [API de `Page` 3.5.5](https://docs.spring.io/spring-data/commons/docs/3.5.5/api/org/springframework/data/domain/Page.html): contingut i totals de la pàgina.
- [Funcions de data d'Hibernate 6.6](https://docs.hibernate.org/orm/6.6/querylanguage/html_single/#datetime-functions): `year(...)` com a abreviació d'HQL que no forma part de l'estàndard JPQL.

## Ampliació de JPQL, relacions i comprovacions HTTP, 2026-10-09

**Indicació directa de David Pons:** introducció breu a JPQL des de l'SQL/MySQL treballat a primer, explicació dels quatre tipus de relació JPA encara que no tots apareguin al code along, i comprovacions d'alumnat amb fitxers `.http`. Les relacions són contingut important per preparar el futur examen; aquesta revisió amplia teoria, no crea un examen ni registra una prova impartida.

Els apunts incorporen una comparació SQL–JPQL de taules/entitats, columnes/atributs, selecció, àlies, paràmetres i unions. L'apartat 8 desenvolupa `@ManyToOne`, `@OneToMany`, `@ManyToMany` i `@OneToOne`, cardinalitat i obligatorietat, navegació uni/bidireccional, propietari, `mappedBy`, claus i taula d'unió. Inclou cascades, `orphanRemoval`, càrrega `LAZY`/`EAGER`, transaccions i una fitxa de paràmetres. Els exemples un-a-un, un-a-molts unidireccional, matrícula amb atributs i relació autoreferenciada s'identifiquen com a models addicionals; no s'han afegit al projecte Java.

L'apartat 9 és una guia d'execució amb `.http`, amb dades inicials, petició i resultat esperat, CRUD complet, consultes, restriccions, assignació i matrícula. Els tests JUnit de preparació es conserven al projecte, però no es presenten com el procediment de proves que han de seguir els alumnes. Les rutes disponibles no permeten executar tots els exemples teòrics: això es precisa sense inventar endpoints d'un-a-un, retirada d'orfes o eliminació de professors.

**Criteri per a futures revisions dels apunts:** l'alumnat disposarà del code along i del document. Les explicacions han de ser autònomes i orientades als conceptes i a les operacions que pot executar. RA/CA, fonts, normativa, historial de versions, decisions de preparació i context de les converses queden a la wiki i al README docent. Als apunts només es mantenen referències a classes i recursos propis del projecte de l'alumne, com `pom.xml`, `application.properties`, `data.sql`, `src/main/resources` i els fitxers `.http`.

La revisió general retira mencions curriculars, preparació i versions anteriors, propostes de DTO, `contextLoads`, noms de tests interns i rutes del repositori de materials. S'han alineat `Book.id` amb `Long`, les columnes implícites, les rutes d'universitat i les **14 propietats** de la configuració actual. La informació de consola específica de Boot 4 es conserva a les fonts anteriors i no s'inclou als apunts del code along amb Boot 3.5. L'estat del Markdown és «En elaboració»; no és encara una exportació revisada.

**Comprovacions executades en aquesta ampliació:**

- Les 12 signatures i les tres consultes declarades coincideixen amb els repositoris; cinc fragments CRUD i els dos mètodes d'assignació/matrícula coincideixen amb els serveis. Mapatge de `Book`, relacions i 14 propietats contrastats amb el codi. Fonts dels dos projectes conservades amb SHA-256.
- Execució dels dos blocs JPQL literals amb Hibernate/H2 en un programa temporal, comprovant ordre de llibres, equivalència amb SQL, projecció de títols, `JOIN` de professor i consulta d'estudiants per taula intermèdia. Verificació dels valors per defecte i paràmetres contra l'API de Jakarta Persistence del projecte.
- **23 peticions HTTP verificades**: nou peticions literals del document, deu del fitxer d'universitat, dos casos d'unicitat, un cas de títol nul i una lectura posterior que confirma els 21 llibres. Servidor temporal a localhost aturat en acabar. Aquesta comprovació usa cURL, no el client de l'IDE.
- Blocs de codi i referències locals comprovats; cerca de context intern i rutes antigues sense coincidències inadequades al text de l'alumnat. No s'han creat proves permanents ni modificat el projecte. No s'ha repetit la suite JUnit: les deu proves superades de la revisió anterior continuen documentades com a verificació prèvia.

### Fonts tècniques addicionals

- [Jakarta Persistence 3.1, relacions i llenguatge de consultes](https://jakarta.ee/specifications/persistence/3.1/jakarta-persistence-spec-3.1.html): seccions 2.10–2.11, 4 i 11.1; propietat, variants de mapatge, eliminació d'orfes i anotacions.
- [API de `ManyToMany`](https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/manytomany), [API de `JoinColumn`](https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/joincolumn) i [API de `JoinTable`](https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/jointable): paràmetres de la taula d'unió i de les claus foranes.
- [API de `CascadeType`](https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/cascadetype) i [API de `FetchType`](https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/FetchType.html): operacions propagades i càrrega.
- [Guia d'Hibernate 6.6](https://docs.hibernate.org/orm/6.6/userguide/html_single/): associacions, manteniment dels dos costats, col·leccions i càrrega. [Guia d'HQL](https://docs.hibernate.org/orm/6.6/querylanguage/html_single/): entitats, atributs, paràmetres i unions.
- [Client HTTP d'IntelliJ IDEA](https://www.jetbrains.com/help/idea/http-client-in-product-code-editor.html): fitxers de peticions, capçaleres, cos i execució al client de l'IDE.
- [Inicialització de dades en Boot 3.5](https://docs.spring.io/spring-boot/3.5/how-to/data-initialization.html) i [tancament de bases H2](https://h2database.com/html/features.html#closing_a_database): ordre d'inicialització i opció `DB_CLOSE_ON_EXIT`.
