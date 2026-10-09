# Desenvolupament operatiu — Programació en entorn servidor

Aquest espai recull la seqüència de les quatre unitats. Les hores i els mesos provenen del [[../../fonts/programacio-didactica-2026-2027|full de programació d'aula 2026–2027]], que també s'usa per enregistrar activitats durant el curs. Les fitxes de la wiki poden créixer amb els materials i les experiències reals, seguint l'[[../../orientacio-docent|enfocament docent]].

La metodologia comuna és el **code along**: el professor duu un projecte Spring prèviament resolt i en va programant les classes amb l'alumnat, fent pauses per introduir i explicar els conceptes de cada unitat.

| Unitat | Hores | Mesos previstos | RA |
|---|---:|---|---|
| [[unitats/ut1-introduccio-servidor|U1. Introducció a la programació en entorn servidor]] | 20 | Setembre | RA1 |
| [[unitats/ut2-acces-dades|U2. Accés a dades]] | 40 | Setembre–octubre | RA6 |
| [[unitats/ut3-serveis-web|U3. Serveis web]] | 50 | Octubre–desembre | RA7, RA9 |
| [[unitats/ut4-aplicacio-web|U4. Aplicacions web]] | 120 | Desembre–març | RA2, RA3, RA4, RA5, RA8 |

Vegeu també el [[../../matrius/mapa-ra-unitats|mapa de traçabilitat]].

**Antecedents d'avaluació incorporats el 2026-10-09:** els [[../../fonts/examens-2526|quatre exàmens del curs 2025–2026]] documenten Spring Core (UT1), entitats i repositoris JPA (UT2), DTO i capa REST (UT3), i formularis MVC/Thymeleaf (UT4). La fitxa enllaça els DOCX originals de `Examens2526/`, detalla les parts i els barems sobre 10 i assenyala la capçalera incorrecta d'UT3. Són fonts històriques per preparar l'avaluació, sense canviar les ponderacions vigents ni registrar proves del curs actual.

U1 disposa de [[../../fonts/materials-ut1-2526|materials del curs anterior ingerits]]: apunts, suports d'arquitectura i projecte Java per al code along.

U2 disposa del [[../../fonts/code-along-ut2-2526|code along del curs 2025–2026]] extret a `materials-anteriors/` i d'un [[../../../materials/0613-desenvolupament-web-entorn-servidor/UT2/acces-a-dades-amb-spring.md|esborrany inicial de teoria]] a `materials/`. Els apunts segueixen dos fils: llibres (entitats, repositoris, consultes i CRUD) i universitat (relacions entre entitats). La progressió proposada és una pauta de preparació, no un registre de sessions impartides. El DOCX i el PDF teòrics d'U2 encara no s'han creat.

**Suport incorporat a U2, 2026-10-09:** la carpeta `dades exemple classe/` aporta un [[../../fonts/dades-exemple-classe-ut2|`application.properties` comentat i un `data.sql` de demostració]]. La fitxa inventaria la configuració H2 i les 21 insercions, les contrasta amb el model `Book` i proposa comprovacions del recompte i dels gèneres per al code along. Els originals es conserven; la revisió no implica execució ni ús ja confirmat a classe.

**Ordre inicial dels apunts d'U2, decisió docent del 2026-10-09:** justificació de l'ORM i definició de JPA → distinció entre especificació, implementació Hibernate, repositoris Spring Data JPA i motor H2 → incorporació del starter JPA i H2 al POM. Els dos apartats inicials del Markdown ja desenvolupen aquesta introducció; la resta del dossier es revisarà progressivament. La primera aturada pràctica del code along proposa identificar les dependències i comprovar-ne la resolució amb Maven abans de configurar la connexió.

El [[../../fonts/diagrama-spring-jpa-ut2|diagrama de Spring Data JPA aportat el 2026-10-09]] acompanya aquesta introducció a l'apartat 1.4 dels apunts. S'hi relacionen els rètols de JPA, Hibernate, JDBC i RDBMS amb les funcions explicades i amb H2 com a gestor de l'exemple.

**Funcionament d'inicialització concretat el 2026-10-09:** el projecte carregarà les dades de demostració en arrencar. L'apartat 4 dels apunts desenvolupa les propietats de connexió i inicialització de la [[../../fonts/dades-exemple-classe-ut2|font comentada]]: Hibernate crea l'esquema i Spring Boot executa `data.sql` després. Amb H2 en memòria i `create`, cada arrencada torna al conjunt de 21 llibres inicials. Les consultes de recompte i el reinici són comprovacions proposades; no s'han executat ni es registren com a contingut impartit.

**Ampliació del mapatge d'entitats, 2026-10-09:** l'apartat 5 desenvolupa `Book` i una fitxa de consulta sense relacions a partir de les anotacions de `Book`, `Course`, `Student` i `Teacher`, inventariades a la [[../../fonts/code-along-ut2-2526|fitxa de font]]. Les opcions d'ampliació es distingeixen del codi observat. Les comprovacions proposades relacionen anotacions i restriccions de l'esquema; les relacions del dossier es conserven per a una ampliació posterior, segons la indicació expressa del professor.

**Referència canònica d'UT2 concretada, 2026-10-09:** David Pons confirma que el [[../../fonts/code-along-ut2-ordinador-profe|projecte `unitat_2_ordinador_profe`]] és posterior a `unitat_2_classe2526`, amb les referències del 2 i del 14 d'octubre, i el designa canònic. Demana integrar les millors parts de les dues còpies i preservar els fitxers HTTP, amb el senzill com a primer exemple i els més complexos com a ampliacions.

**Projecte ampliat conservat:** [[../../fonts/code-along-ut2-2627|`unitat2_2627`]] viu a `materials/.../UT2/`, amb la fusió, DTO, validació, errors i comprovacions ja documentats. Es conserva íntegre com a reserva per preparar serveis web i integració final.

**Concreció posterior del professor, 2026-10-09:** UT2 ha de focalitzar entitats i repositoris, amb serveis i controladors mínims per provar les operacions. S'ha creat [[../../fonts/code-along-ut2-2627-simplificat|`unitat2_2627_simplificat`]], referència confirmada per generar els materials teòrics d'aquesta unitat. Manté mapatge JPA, consultes, dades i repositoris, i retorna llibres, IDs o noms sense DTO ni tractament elaborat d'API. Deu proves de persistència superades i fitxers HTTP adaptats. A UT3 s'introduiran DTO i disseny REST; la unitat final unirà les parts. Aquesta seqüència consta també a l'orientació docent. No es registra cap sessió impartida.

**Ampliació de repositoris als apunts, 2026-10-09:** els apartats 6 i 7 detallen CRUD, retorns, identificadors, restriccions, sincronització i les 12 signatures de consulta dels quatre repositoris de la versió simplificada. Inclouen JPQL, SQL natiu corregit, paginació i les rutes realment programades, amb diferència entre `Page<Book>` al repositori i llista per HTTP. Signatures, consultes i fragments contrastats amb el codi; `mvn verify` amb 10 proves superades. L'apartat de relacions i el codi font es conserven. L'alineació de la resta de l'esborrany i les exportacions continuen pendents.

**Ampliació posterior i revisió per a l'alumnat, 2026-10-09:** els apunts relacionen JPQL amb l'SQL de MySQL de primer i desenvolupen els quatre tipus de relació, inclosa la un-a-un absent del projecte. Cascada, orfes, càrrega i propietat es presenten com a decisions separades; variants addicionals identificades. Les comprovacions de classe s'expliquen amb `.http`; s'han verificat els exemples JPQL i 23 peticions. Configuració, `Long`, camps implícits i rutes alineats amb el projecte actual. El document d'alumnat és autònom, sense contingut normatiu ni context intern; les fonts i la traçabilitat curricular es conserven a la wiki. Els projectes es mantenen intactes, les exportacions continuen pendents i no es registra cap examen o sessió impartida.

Per al curs 2026–2027, el projecte complet `materials/0613-desenvolupament-web-entorn-servidor/UT1/UT1_2627/` prepara dos exemples nous: comparació de la injecció directa d'un bean `prototype` amb l'obtenció de noves instàncies mitjançant `ObjectProvider`, i registre d'un `DateTimeFormatter` amb `@Configuration` i `@Bean`. La [[unitats/ut1-introduccio-servidor|fitxa d'U1]] documenta les classes, les rutes observables i els punts d'explicació; la incorporació del projecte no implica que ja s'hagin impartit.

**Concreció del professor, 2026-10-08:** `UT1_2627` recull les proves prèvies a classe. L'outline de Spring Core incorpora la petició de prototypes en el bloc de scopes i manté els beans de configuració després del cicle de vida:

| Bloc de la progressió | Referència i punt d'explicació |
|---|---|
| IoC, beans i DI | Dependències per constructor i setter; selecció amb `@Primary` i `@Qualifier`. |
| Scopes i petició de noves instàncies | Comparar `/cart/direct` i `/cart/factory`: un prototype injectat directament en un singleton es conserva; `ObjectProvider.getObject()` demana una instància quan cal. La factory encapsula aquesta petició. |
| Inicialització diferida i cicle de vida | Exemples existents de cost d'inicialització, `@PostConstruct`, `@PreDestroy` i `ReportGenerator`. |
| Configuració explícita de beans | `/hora`: `HoraConfiguration` registra el `DateTimeFormatter` amb `@Bean`; `RellotgeServidor` el rep per constructor. |
| Aplicació a la pràctica d'informes | `ReportJob` prototype i serveis singleton: la solució actual preveu `ReportJobFactory` amb `ObjectProvider<ReportJob>`, i cada generació fa `reportJobFactory.createJob()`. |

Aquest outline organitza la preparació i els punts d'aturada del code along; no fixa sessions ni acredita continguts ja impartits. La fitxa d'U1 inclou l'explicació breu i el codi de referència de `ReportJobFactory`. Es mantenen `RA1`, la interpretació docent de `RA1.c` i `RA1.g`, les hores i les ponderacions vigents.

**Actualització amb el projecte impartit, 2026-10-08:** David Pons ha aportat `materials/0613-desenvolupament-web-entorn-servidor/UT1/unitat1_2627/` i confirma que és el code along fet a classe. Aquesta còpia passa a ser la referència dels exemples d'alumnat; `UT1_2627/` conserva l'estat de proves prèvies. La [[../../fonts/code-along-ut1-2627|fitxa de font]] registra els recorreguts amb `CotxeService`, `ShopingCartService` i `ShopingCartFactory`, la ruta `/lazy` i la configuració `HoraConfiguration` amb `/hora` a `Unitat1Contoller`. L'outline segueix sent una organització conceptual, no un registre de sessions. `ProvaHashController` queda fora dels apunts per indicació del professor.
