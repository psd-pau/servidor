# Code along d'U1 impartit el curs 2026–2027

## Font i estat

**Confirmació directa de David Pons, 2026-10-08:** el projecte `materials/0613-desenvolupament-web-entorn-servidor/UT1/unitat1_2627/` és el code along que realment ha fet a classe enguany. Aquesta font passa a ser la referència dels exemples dels [[../../materials/0613-desenvolupament-web-entorn-servidor/UT1/introduccio-a-spring.md|apunts d'introducció a Spring]]. El projecte `UT1_2627/` continua documentat com a proves prèvies, i `unitat1/` com a antecedent.

El POM declara Java 25, Spring Boot 4.1.1 i Spring Web MVC. La classe d'arrencada és `Unitat12627Application`, al paquet `cat.paucasesnovescifp.unitat1_2627`. S'ha llegit el codi font sense executar ni modificar el projecte. La confirmació del professor acredita l'ús a classe; no s'infereixen sessions, ordre exacte de programació, hores efectives ni assoliment individual de CA.

**Excepció expressa del professor:** `controller/ProvaHashController.java` s'ha omès de la revisió i dels apunts. Correspon a una explicació complementària de `HashMap` per cobrir un coneixement previ que faltava; no s'incorpora com a contingut nou d'aquests apunts.

## Classes i rutes de referència

Els camins són relatius a `src/main/java/cat/paucasesnovescifp/unitat1_2627/`.

| Ruta | Codi de referència | Comportament llegit al codi |
|---|---|---|
| `/` | `controller/Unitat1Contoller.java` | Retorna `hola mon`. |
| `/cotxe` | `Unitat1Contoller → service/CotxeService → domain/Cotxe` | `conduir()` delega en `engega()`. El cotxe és `@Component`, selecciona `motorBenzina` amb `@Qualifier` i rep el GPS opcional per setter. `MotorElectric` és `@Primary`. |
| `/singleton` | `ContadorServiceA`, `ContadorServiceB`, `domain/Contador` | Comparteixen `AtomicInteger`; el controlador concatena `A 1` i `B 2` sense separador addicional, si no hi ha crides prèvies. |
| `/prototype` | `service/TicketService`, `domain/Ticket` | `printTickets()` mostra els dos tickets prototype injectats en construir el servei i conservats després. |
| `/cart/direct` | `controller/CartController`, `domain/ShoppingCart` | El controlador singleton conserva el carret injectat directament. |
| `/cart/factory` | `CartController → service/ShopingCartService → domain/ShopingCartFactory` | `startCheckout()` obté el carret amb `createCart()`, que executa `ObjectProvider.getObject()`, i retorna l'UUID com a text. |
| `/request` | `service/RequestService`, `domain/RequestBean` | Proxy de petició; el servei declara explícitament `@Scope("singleton")`. |
| `/session` | `service/SessionService`, `domain/SessionBean` | Proxy de sessió; l'UUID es crea al constructor del bean. |
| `/lazy` | `service/HeavyService`, `domain/HeavyBean` | `@Lazy` tant al bean com al punt d'injecció; espera de cinc segons al constructor i missatges abans i després de la inicialització. |
| `/report` | `service/ReportService`, `domain/ReportGenerator` | `generateReport()` delega en `generate()`; constructor, `@PostConstruct` i `@PreDestroy` registren els moments del cicle de vida. |
| `/hora` | `Unitat1Contoller → service/HoraService → domain/RellotgeServidor` | `config/HoraConfiguration.java` declara `DateTimeFormatter` amb el patró `dd/MM/yyyy HH:mm:ss`. |

Es conserven els identificadors exactes `Unitat1Contoller`, `ShopingCartFactory` i `ShopingCartService`, tal com apareixen al codi de classe. La diferència ortogràfica amb `ShoppingCart` no es normalitza als apunts per evitar que els noms deixin de correspondre als fitxers.

## Actualització dels apunts

S'han adaptat els noms, els mètodes, les anotacions, les sortides i els recorreguts dels exemples al projecte impartit. El controlador de l'hora es mostra com un fragment d'`Unitat1Contoller`; no hi ha un `HoraController` separat. El carret obtingut amb factory passa pel servei, mentre que la injecció directa queda al controlador per fer la comparació senzilla.

Es mantenen les variants conceptuals que permeten explicar DI sense qualificador i inicialització sense `@Lazy`, indicant que són variants de comparació. També es conserva la connexió a `ReportJobFactory` com a disseny de la pràctica d'informes; el projecte impartit no conté les classes d'aquesta pràctica. Els casos reals de callbacks, l'absència de fonts al final i el caràcter autònom dels apunts es mantenen segons les indicacions anteriors.

Aquest material correspon a [[../moduls/0613-desenvolupament-web-entorn-servidor/unitats/ut1-introduccio-servidor|U1]], assignada a `RA1`; la interpretació docent del funcionament de Spring es manté vinculada a `RA1.c` i `RA1.g`, sense introduir ponderacions o proves d'avaluació noves.
