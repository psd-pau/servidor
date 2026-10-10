# Wiki de Desenvolupament web en entorn servidor

Base de coneixement de treball per preparar el mòdul `0613. Desenvolupament web en entorn servidor` de DAW.

## Punt de partida

- [[orientacio-docent|Orientació docent]]: code along a totes les unitats a partir de projectes Spring preparats pel professor, arquitectura per capes i pràctiques professionals. És la porta d'entrada a l'enfocament del mòdul.
- [[curs-programacio-entorn-servidor|Mòdul 0613. Desenvolupament web en entorn servidor]]: resum del context i tecnologia de referència.
- [[fonts/servidor-master-2526|Servidor master 2025-2026]]: font original de l'enfocament docent i dels primers materials del mòdul.
- [[fonts/programacio-didactica-2026-2027|Full de programació d'aula 2026–2027]]: hores, distribució en UT, RA i qualificació; el registre d'activitats s'emplena durant el curs.
- [[moduls/0613-desenvolupament-web-entorn-servidor|0613. Desenvolupament web en entorn servidor]]: porta d'entrada curricular amb RA, CA i seqüència inicial.
- [[matrius/mapa-ra-unitats|Mapa RA–unitats]]: relació entre les quatre unitats inicials i els nou RA.
- [[matrius/qualificacio-ra-2026-2027|Ponderació dels RA 2026–2027]]: pesos de qualificació i repartiment centre–empresa.
- [[plantilles/fitxa-unitat|Plantilla de fitxa d'unitat]]: format per documentar el desenvolupament operatiu.

## Materials de referència

- [[presentacio-modul|Presentació del mòdul 2026–2027]]: PPTX i PDF de benvinguda, connexions amb DAW, unitats, RA, hores i criteris d'avaluació.

- [[../skills/presentacions-pau-casesnoves/SKILL|Guia de presentacions del centre]]: colors corporatius, format comú, autoria David Pons i suport al code along.
- [[../skills/documents-alumnat-pau-casesnoves/SKILL|Guia de documents per a l'alumnat]]: dossiers, apunts i guies en DOCX i PDF per a IFC33C, amb identitat del centre i suport al code along de Java i Spring.
- [[../skills/examens-pau-casesnoves/SKILL|Guia d'exàmens en paper]]: plantilla del centre, problemes de resposta oberta i puntuació per parts que avaluen la tria i l'aplicació de les eines treballades.
- [[fonts/examens-2526|Exàmens del curs 2025–2026, UT1–UT4]]: quatre enunciats DOCX de Spring Core, JPA, API REST i vistes; inventari de requisits i barems sobre 10, correspondència amb les unitats i punts de revisió abans de reutilitzar-los. Originals conservats a `Examens2526/`.

- [[fonts/materials-ut1-2526|Materials d'U1 del curs anterior]]: apunts de Spring Core, suports d'arquitectura i correspondència amb el projecte de code along.
- [[fonts/code-along-ut1-2627|Code along d'U1 impartit el curs 2026–2027]]: projecte `unitat1_2627` confirmat pel professor com a codi treballat a classe; classes, rutes i correspondència amb els apunts actualitzats.
- [[fonts/code-along-ut2-2526|Code along d'U2 del curs 2025–2026]]: ZIP original i projecte font extret; inventari de JPA, H2, consultes, relacions i punts de revisió per preparar els nous apunts teòrics i el PDF d'U2.
- [[fonts/code-along-ut2-ordinador-profe|Font canònica d'UT2: projecte de l'ordinador del professor]]: `unitat_2_ordinador_profe`, confirmat pel professor com a posterior a la còpia de classe; inventari, comparació i discrepàncies dels originals.
- [[fonts/code-along-ut2-2627-simplificat|Code along simplificat per a UT2, 2026–2027]]: `unitat2_2627_simplificat`, referència confirmada pel professor per generar els materials teòrics de l'alumnat; centrada en entitats i repositoris, amb controladors i serveis mínims, JPA i consultes conservats; 10 proves de persistència i 40 peticions HTTP verificades.
- [[fonts/code-along-ut2-2627|Projecte ampliat conservat per preparar UT3 i la integració final]]: `unitat2_2627`, fusió amb DTO, validació i tractament REST d'errors; conserva la compilació, les 14 proves i la verificació HTTP de la primera proposta. Per a UT2 es prioritza la versió simplificada.
- [[fonts/dades-exemple-classe-ut2|Configuració i dades d'exemple d'UT2]]: `application.properties` comentat amb 12 propietats i `data.sql` amb 21 llibres; comparació amb el projecte històric, dependència H2 de referència i revisió de codificació.
- [[fonts/diagrama-spring-jpa-ut2|Diagrama de les peces de Spring Data JPA]]: imatge de Spring Boot, Spring Data JPA, JPA, Hibernate, JDBC i gestor relacional, incorporada a l'apartat 1.4 dels apunts d'UT2 amb una explicació en català.
- [[../materials/0613-desenvolupament-web-entorn-servidor/UT2/acces-a-dades-amb-spring.md|Accés a dades amb Spring — apunts d'UT2 revisats]]: JPA, configuració, entitats, CRUD i consultes dels quatre repositoris; introducció a JPQL des de MySQL, quatre tipus de relació, propietari, cascada, orfes i càrrega. Guia de comprovacions amb `.http`, amb 23 peticions verificades i exemples JPQL executats en la revisió anterior. Text autònom per a l'alumnat, contrastat amb la versió simplificada i l'examen d'UT2 de 2025–2026; revisió de coherència completada el 2026-10-09. Exportacions del 2026-10-10: [[../materials/0613-desenvolupament-web-entorn-servidor/UT2/acces-a-dades-amb-spring.docx|DOCX editable]] i [[../materials/0613-desenvolupament-web-entorn-servidor/UT2/acces-a-dades-amb-spring.pdf|PDF de 48 pàgines]], amb índex clicable en pàgina pròpia, apartats principals en pàgines noves i esquema addicional de les relacions d'universitat. El [[../materials/0613-desenvolupament-web-entorn-servidor/UT2/README.md|README d'UT2]] conserva el context docent, les comprovacions i la regeneració.
- [[../materials/0613-desenvolupament-web-entorn-servidor/UT1/introduccio-a-spring.md|Introducció a Spring — apunts revisats 2026–2027]]: teoria contrastada amb `unitat1_2627`, amb [[../materials/0613-desenvolupament-web-entorn-servidor/UT1/introduccio-a-spring.docx|DOCX editable]] i [[../materials/0613-desenvolupament-web-entorn-servidor/UT1/introduccio-a-spring.pdf|PDF de 30 pàgines per a l'alumnat]]. El [[../materials/0613-desenvolupament-web-entorn-servidor/UT1/README.md|README d'UT1]] explica la funció dels fitxers, el context i la regeneració.
- [[moduls/0613-desenvolupament-web-entorn-servidor/unitats/ut1-introduccio-servidor|U1 del curs actual]]: code along impartit `unitat1_2627`, antecedent de proves `UT1_2627`, comparació de beans `prototype` amb injecció directa i `ObjectProvider`, trasllat a `ReportJobFactory` i exemple de `@Configuration` i `@Bean` amb `GET /hora`.
- [[fonts/practica-ut1-reports-2526|Pràctica d'U1: gestor d'informes]]: enunciat, proposta de solució i connexió amb els conceptes del code along.

## Manteniment

- [[log|Log]]: registre cronològic d'ingestes, decisions i revisions.

## Criteri de treball

El disseny de les aplicacions i la separació de responsabilitats provenen de l'enfocament docent del ZIP. Els RA i CA permeten traçar i avaluar el que es treballa. Les activitats reals s'incorporen a les fitxes a mesura que es fan; els camps buits del full d'aula són espai de registre. La wiki és una eina personal de planificació, no substitueix els documents i acords del centre.
