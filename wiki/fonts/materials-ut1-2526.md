# Materials d'U1 del curs anterior

## Procedència i ús docent

Materials aportats pel professor el 2026-09-21 a `materials-anteriors/0613-desenvolupament-web-entorn-servidor/UT1/`. Segons la seva explicació, combina els continguts teòrics amb un projecte Java que va desenvolupant en **code along** per mostrar els conceptes. Aquesta és una pràctica docent confirmada pel professor; la correspondència detallada següent prové de la lectura dels fitxers.

## Inventari

| Material original | Funció |
|---|---|
| [[../../materials-anteriors/0613-desenvolupament-web-entorn-servidor/UT1/1. Introducció a Spring.pdf|1. Introducció a Spring.pdf]] | Apunts de 28 pàgines: Spring Core, IoC, DI, beans, scopes, lazy initialization i cicle de vida. |
| [[../../materials-anteriors/0613-desenvolupament-web-entorn-servidor/UT1/unitat1.zip|unitat1.zip]] | Projecte Java/Spring Boot per al code along, amb exemples observables mitjançant peticions HTTP i missatges de consola. |
| [[../../materials-anteriors/0613-desenvolupament-web-entorn-servidor/UT1/Estructura recomanada projectes.txt|Estructura recomanada projectes.txt]] | Responsabilitats de domini, repositori, servei i controlador; evolució d'una estructura per capes a mòduls funcionals, amb DTOs, controladors MVC/REST i recursos web. |
| [[../../materials-anteriors/0613-desenvolupament-web-entorn-servidor/UT1/mvc basic.jpg|mvc basic.jpg]] | Esquema general del recorregut entre usuari, controlador, model, base de dades i vista. |
| [[../../materials-anteriors/0613-desenvolupament-web-entorn-servidor/UT1/mvc spring.jpg|mvc spring.jpg]] | Esquema de Spring MVC: DispatcherServlet, HandlerMapping, HandlerAdapter, controlador, serveis, repositoris, Model, ViewResolver i vista. |

A la primera ingesta s'havien localitzat aquests cinc fitxers: PDF, ZIP i tres suports. El professor havia esmentat quatre materials de suport; el quart no es va localitzar aleshores.

Posteriorment s'han afegit l'[[practica-ut1-reports-2526|activitat Spring Core — Reports i la seva proposta de solució]], documentades en una fitxa pròpia com a pràctica complementària al code along.

## Correspondència teoria–code along

Els camins de classes de la taula són relatius a `unitat1/src/main/java/cat/paucasesnovescifp/unitat1/`, dins el ZIP. Les rutes HTTP estan declarades a `controller/Unitat1Controller.java`. És una lectura del codi font, sense executar el projecte ni acreditar el resultat de les proves.

| Teoria al PDF | Codi del projecte | Què permet observar en la demostració |
|---|---|---|
| Spring Core i IoC, p. 3–5 | `Unitat1Application.java`, anotacions de components i `GET /` | Arrencada de l'aplicació, objectes gestionats pel contenidor i resposta «Hola món!». |
| DI, p. 6–7 | `service/Cotxe.java`, `domain/Motor*.java`, `domain/Gps.java`; `GET /cotxe` | Motor injectat per constructor, selecció amb `@Qualifier`, alternativa amb `@Primary` i GPS opcional per setter. Un `CommandLineRunner` també invoca `conduir()` a l'arrencada. |
| Beans i singleton, p. 8–12 | `domain/Contador.java`, `service/ContadorServiceA.java` i `ContadorServiceB.java`; `GET /singleton` | Dos serveis comparteixen el comptador gestionat pel contenidor. |
| Prototype, p. 12–13 | `domain/Ticket.java`, `service/TicketService.java`; `GET /prototype` | Dos objectes `Ticket` s'injecten al constructor del servei. El servei conserva aquests dos objectes; el codi no demana nous tickets a cada petició. |
| Request i session, p. 13–16 | `domain/RequestBean.java`, `domain/SessionBean.java` i els serveis corresponents; `GET /request`, `GET /session` | Identificadors UUID i proxies per comparar l'abast d'una petició amb el d'una sessió. |
| Lazy initialization, p. 16–23 | `domain/HeavyBean.java`, `service/HeavyService.java`; `GET /heavy` | Constructor amb una espera simulada de cinc segons i `@Lazy` tant al bean com al punt d'injecció. |
| Cicle de vida, p. 23–28 | `domain/ReportGenerator.java`, `service/ReportService.java`; `GET /report` | Missatges del constructor, `@PostConstruct` i `@PreDestroy`, i generació d'un informe. |

## Característiques de la còpia rebuda

`unitat1/pom.xml` declara Java **25**, Spring Boot **3.5.6**, Web, DevTools i Starter Test. El projecte inclou Maven Wrapper i una prova `contextLoads()` a `src/test/`. Aquestes són les versions de la còpia històrica, sense decidir encara l'entorn del curs actual.

El projecte conté `domain`, `service` i `controller`; `templates/` i `static/` són buits. El controlador retorna cadenes amb `@RestController`. Els esquemes MVC i el document d'estructura expliquen una arquitectura més àmplia que la implementada en aquest primer exemple: no hi ha repositoris, persistència ni plantilles renderitzades al ZIP.

Els missatges de consola són part de les demostracions de creació i destrucció d'objectes. L'[[../orientacio-docent|orientació general del mòdul]] introdueix també SLF4J com a pràctica professional; es conserva el context introductori dels exemples originals.

## Connexió amb U1

### Revisió dels apunts per al curs 2026–2027

**Criteri final de document autònom, 2026-10-08:** per reiteració del professor, també s'han retirat les referències bibliogràfiques i els enllaços tècnics dins els apartats dels apunts. Els casos reals es presenten en dues llistes amb subtítols explícits per a `@PostConstruct` i `@PreDestroy`. La procedència queda documentada aquí i a la fitxa del code along; el text d'alumnat segueix el fil d'una única aplicació.

**Referència actualitzada dels exemples, 2026-10-08:** després de la primera redacció i les revisions següents, el professor ha aportat el [[code-along-ut1-2627|projecte impartit `unitat1_2627`]]. Els apunts s'han sincronitzat amb aquesta còpia de classe: noms, rutes, mètodes, sortides i separació de domini, serveis i controladors. Les decisions descrites a continuació documenten l'evolució editorial; la font original es conserva i les còpies de preparació ja no determinen els exemples actuals. `ProvaHashController` queda exclòs segons la indicació expressa del professor.

**Primer intent demanat pel professor, 2026-10-08:** s'ha creat [[../../materials/0613-desenvolupament-web-entorn-servidor/UT1/introduccio-a-spring.md|introduccio-a-spring.md]] com a desenvolupament teòric per a l'alumnat, amb estat d'esborrany i metadades `ca-ES`. S'han llegit les 28 pàgines de `1. Introducció a Spring.pdf` mitjançant extracció de text i s'ha contrastat el contingut amb el codi dels exemples i la documentació oficial. El PDF original es conserva; aquesta versió queda pendent de revisió docent abans de generar DOCX i PDF amb la skill del centre.

| Bloc del PDF original | Apartat del nou Markdown |
|---|---|
| Spring Core, p. 3 | 1. Spring Framework, Spring Core i Spring Boot. |
| IoC, p. 4–5 | 2. Inversió de control. |
| DI per constructor i setter, p. 6–7 | 3. Injecció de dependències; s'expliciten interfícies, `@Primary`, `@Qualifier` i GPS opcional presents al code along. |
| Beans, p. 8 | 4. Beans i contenidor. |
| Scopes i exemples, p. 8–16 | 5. Singleton, prototype, request, session i application; s'hi integra `ObjectProvider` i la factory. |
| Lazy, p. 16–23 | 6. Inicialització diferida; es mantenen les variants eager, lazy local i global, i les seves conseqüències. |
| Cicle de vida, p. 23–28 | 7. Construcció, injecció, inicialització, ús, finalització i aplicacions dels callbacks. |
| Ampliació actual del professor | 8. `@Configuration` i `@Bean` amb `/hora`; 9. Relació entre conceptes i exemples. |

**Refinaments editorials:** distinció entre una classe i un bean; `final` conserva la referència però no fa immutable la dependència; un setter no és opcional sense configuració; els tickets injectats es conserven en el servei singleton; el proxy d'scope delega en la instància activa, no és reemplaçat al camp del servei; lazy al bean i al punt d'injecció tenen funcions diferents; el cost simulat és a `HeavyBean`, mentre `HeavyService` es pot crear a l'arrencada; la injecció per constructor forma part de la construcció; límits de `@PreDestroy` i destrucció dels prototypes. Es millora també la gestió de la interrupció a l'exemple d'espera i es diferencien la classe `ReportGenerator` del code along i la interfície homònima de la pràctica.

El document és d'apunts, no un nou enunciat ni una solució completa dels informes. No fixa sessions ni dóna continguts per impartits. La revisió és textual i conceptual; els fragments no s'han compilat ni s'han executat els projectes.

**Revisió demanada pel professor, 2026-10-08:** l'apartat de casos d'ús de `@PostConstruct` i `@PreDestroy` s'ha convertit en dues llistes desordenades amb exemples de Redis/Lettuce, Kafka, RabbitMQ, executors, credencials i claus, Vault i Eureka. Es distingeix la possible preparació de recursos propis d'un exemple real de biblioteca: `DiscoveryClient.shutdown()` d'Eureka està anotat amb `@PreDestroy`. S'han contrastat els mètodes amb les fonts oficials enllaçades als apunts. Segons l'aclariment docent, l'alumnat ha seguit un únic projecte al llarg del code along: s'han eliminat les distincions entre còpies de preparació de la taula de rutes i del relat dels apunts. El document es prepara com a material autònom, sense apartat final de fonts ni remissions als originals; la procedència es conserva en aquesta fitxa. Els enllaços tècnics de consulta dins els apartats es mantenen.

La [[../moduls/0613-desenvolupament-web-entorn-servidor/unitats/ut1-introduccio-servidor|fitxa d'U1]] recull aquest enfocament com a base docent existent. El paquet aprofundeix especialment en el funcionament del framework i del servidor, relacionable amb `RA1.c`, `RA1.d` i `RA1.g`. Els esquemes ajuden a presentar client/servidor i generació de vistes; la presència d'un concepte al material no certifica per si sola l'assoliment d'un CA ni canvia l'assignació de RA entre unitats.
