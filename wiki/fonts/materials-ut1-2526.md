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

S'han localitzat cinc fitxers: PDF, ZIP i tres suports. El professor esmenta quatre materials de suport; el quart no s'ha localitzat en aquesta ingesta.

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

La [[../moduls/0613-desenvolupament-web-entorn-servidor/unitats/ut1-introduccio-servidor|fitxa d'U1]] recull aquest enfocament com a base docent existent. El paquet aprofundeix especialment en el funcionament del framework i del servidor, relacionable amb `RA1.c`, `RA1.d` i `RA1.g`. Els esquemes ajuden a presentar client/servidor i generació de vistes; la presència d'un concepte al material no certifica per si sola l'assoliment d'un CA ni canvia l'assignació de RA entre unitats.
