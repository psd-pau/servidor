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

Compilació, empaquetament i `mvn verify` amb **10 proves, zero fallades i zero errors**, usant JDK 25 i Maven 3.9.11. Es comproven dades inicials, enum com a text, camp `@Transient`, identificador generat, CRUD, consultes, paginació amb sis llibres, restriccions individuals/compostes/nullabilitat, propietat i lectura inversa de relacions, consultes sobre relacions, `IgnoreCase`, cascada i `orphanRemoval`.

Les proves de persistència són transaccionals i desfan les dades al final de cada cas. La comprovació per HTTP es fa en un procés de servidor separat, amb `open-in-view=false`; això verifica el servei sense dependre de la transacció del test. Les fonts històriques i el projecte complet es conserven, i els apunts Markdown encara s'han d'alinear amb aquesta versió simplificada en una revisió posterior.

**Comprovació dels fitxers HTTP:** 40 peticions executades amb cURL contra Tomcat: 11 de lectura, 5 del primer CRUD, 2 de restriccions, 5 d'escriptura ampliada, 7 de paginació i 10 d'universitat. Les respostes de relacions contenen els noms esperats i les pàgines retornen els recomptes esperats. La consola H2 ha respost amb 200. Els errors de les dues restriccions es deixen amb la resposta per defecte; no s'hi introdueix un contracte REST nou.

**Conservació verificada:** els 86 fitxers del projecte complet inventariats abans de la còpia mantenen els seus hashes; també el Markdown dels apunts. Els quatre repositoris, les tres entitats d'universitat i `Genre` són idèntics. `Book` és idèntic després de retirar exclusivament imports i anotacions de validació/Jackson; no s'ha canviat cap declaració JPA. Enllaços de documentació comprovats.
