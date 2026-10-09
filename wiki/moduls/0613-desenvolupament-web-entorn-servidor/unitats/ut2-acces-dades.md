# U2. Accés a dades

**Durada programada:** 40 hores, setembre–octubre. **Pes de RA6:** 20% del mòdul. Font: [[../../../fonts/programacio-didactica-2026-2027|programació 2026–2027]], fulls `UT2`, `6-Distribució temporal` i `2-ANCORATGE CURRICULAR`.

## RA i criteris d'avaluació

- RA6 — desenvolupament d'aplicacions web amb accés a bases de dades.
- CA a concretar: `RA6.a` a `RA6.g`: tecnologies d'accés, connexió, recuperació, publicació, conjunts de dades, actualització/eliminació, proves i documentació.

## Finalitat i evidència

Construir una aplicació que consulti i actualitzi dades persistents i publiqui el resultat de forma controlada. Evidència orientativa: cas d'ús amb operacions de lectura i modificació, proves i documentació.

## Continguts i tecnologia

Persistència, connexions, recuperació i edició d'informació. La proposta docent de partida usa JPA/Hibernate i Spring Data JPA; el repositori ha de retornar només les dades que necessita cada cas d'ús.

## Materials, avaluació i millores

- Antecedent del curs 2025–2026: [[../../../fonts/code-along-ut2-2526|code along `unitat_2_classe2526`]], conservat en ZIP i extret a `materials-anteriors/0613-desenvolupament-web-entorn-servidor/UT2/`. La fitxa inventaria les classes i els punts que cal revisar.
- Font canònica confirmada el 2026-10-09: [[../../../fonts/code-along-ut2-ordinador-profe|projecte `unitat_2_ordinador_profe` i comparació de versions]]. El professor confirma que és posterior a la còpia de classe, amb les referències del 2 i del 14 d'octubre. Es conserven els originals i la comparació dels seus punts pendents.
- Referència confirmada pel professor per generar els materials teòrics d'UT2, 2026-10-09: [[../../../fonts/code-along-ut2-2627-simplificat|`unitat2_2627_simplificat`]], a `materials/0613-desenvolupament-web-entorn-servidor/UT2/`. Els apunts `acces-a-dades-amb-spring.md` s'han de revisar i ampliar prenent aquesta versió com a base. Conserva mapatge JPA, repositoris, dades, consultes i paginació; simplifica serveis i controladors, amb llibres, IDs i noms com a respostes. Sense DTO, validació HTTP ni tractament REST propi d'errors. Deu proves de persistència superades; comprovacions i progressió al README. Suport a `RA6.a`–`RA6.g`, amb publicació elemental per observar les dades. No es registra com a impartit i els apunts encara s'hi han d'alinear.
- Projecte ampliat conservat: [[../../../fonts/code-along-ut2-2627|`unitat2_2627`, fusió i validació inicial]], amb DTO, validació i tractament REST d'errors. Per indicació del professor, aquests continguts es prepararan per a UT3 i la integració de la unitat final. El projecte, el README i les seves proves es mantenen íntegres.
- Suport aportat el 2026-10-09: [[../../../fonts/dades-exemple-classe-ut2|configuració H2 comentada i dades de demostració]], a `materials-anteriors/0613-desenvolupament-web-entorn-servidor/UT2/dades exemple classe/`. Inclou 12 propietats i 21 llibres de cinc gèneres, equivalents als del projecte històric. La fitxa documenta les dependències del POM de referència, la codificació dels comentaris i les comprovacions proposades; revisió estàtica, sense execució ni registre d'ús a l'aula.
- Material en elaboració: [[../../../../materials/0613-desenvolupament-web-entorn-servidor/UT2/acces-a-dades-amb-spring.md|esborrany inicial dels apunts d'accés a dades]] i [[../../../../materials/0613-desenvolupament-web-entorn-servidor/UT2/README.md|README d'UT2]]. Encara no hi ha DOCX ni PDF revisats per a l'alumnat. El projecte històric serveix de base, no de versió final del curs actual.
- Decisió docent de David Pons, 2026-10-09: començar els apunts explicant JPA i la utilitat de l'ORM, i presentar tot seguit les dependències de persistència com a primera passa pràctica. S'han ampliat aquests dos apartats amb el mapatge `Book`–`BOOK`, la distinció JPA/Hibernate/Spring Data JPA/H2 i els fragments Maven de `spring-boot-starter-data-jpa` i `h2`; suport orientatiu a `RA6.a` i preparació de `RA6.b`. Ampliació parcial del Markdown, no revisió completa ni registre d'una sessió impartida.
- Suport visual aportat el 2026-10-09: [[../../../fonts/diagrama-spring-jpa-ut2|`spring-jpa-diagram.png`]], conservat als materials actuals i inserit a l'apartat 1.4 dels apunts. Visualitza les peces de persistència i acompanya la identificació de tecnologies de `RA6.a`, amb text alternatiu, peu i explicació en català de JDBC, RDBMS/H2 i Jakarta Persistence.
- Funcionament concretat pel professor, 2026-10-09: carregar `data.sql` a l'arrencada. L'apartat 4 dels apunts explica les 12 propietats de configuració, les opcions de `ddl-auto`, la creació de l'esquema abans de les insercions i el retorn als 21 llibres inicials en reiniciar H2 en memòria. Inclou pautes proposades de comprovació i el requisit del mòdul de consola per a Spring Boot 4. Suport a `RA6.b`, `RA6.c`, `RA6.e` i preparació de `RA6.g`; sense execució verificada. Procedència i contrast a la [[../../../fonts/dades-exemple-classe-ut2|fitxa de configuració i dades]].
- Ampliació d'entitats, 2026-10-09: l'apartat 5 dels apunts explica el mapatge de `Book` i incorpora una fitxa de consulta de les anotacions sense relacions emprades a les quatre entitats. Distingeix identificador, generació, unicitat individual i composta, columnes, enumeracions i camps no persistents, amb ampliacions habituals identificades. L'inventari detallat i el contrast oficial consten a la [[../../../fonts/code-along-ut2-2526|fitxa del code along]]. Suport orientatiu a `RA6.c` i `RA6.e`; comprovacions de restriccions proposades per a `RA6.g`. Per indicació del professor, l'apartat de relacions es manté intacte per treballar-lo més endavant.
- Proposta de preparació: estructurar els nous apunts al voltant de la connexió H2, les entitats i les restriccions, els repositoris i el CRUD de llibres, les consultes derivades/JPQL/paginació, i les relacions del model d'universitat. Explicitar al dossier les diferències entre mètodes del servei i rutes exposades, i afegir exemples verificats per a `RA6.g`.
- Registre d'aula: les activitats i els instruments s'hi anotaran quan es facin. L'aplicació de consulta i modificació és un exemple orientatiu de la wiki.
- Cal especificar model de dades, restriccions de seguretat i instrument de qualificació abans d'impartir-la.
- [[../../../fonts/servidor-master-2526|Font de partida]] · [[../desenvolupament|Desenvolupament]].
