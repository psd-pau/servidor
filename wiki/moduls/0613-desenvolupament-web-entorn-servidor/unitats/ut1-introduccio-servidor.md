# U1. Introducció a la programació en entorn servidor

**Durada programada:** 20 hores, setembre. **Pes de RA1:** 5% del mòdul. Font: [[../../../fonts/programacio-didactica-2026-2027|programació 2026–2027]], fulls `UT1`, `6-Distribució temporal` i `2-ANCORATGE CURRICULAR`.

## RA i criteris d'avaluació

- RA1 — caracterització de la programació web en entorn servidor.
- CA a concretar: `RA1.a` a `RA1.g`: models client/servidor, generació dinàmica, servidors web i d'aplicacions, tecnologies, integració amb marques i avaluació de frameworks.

## Finalitat i enfocament docent

L'alumnat es familiaritza amb el funcionament del servidor i amb la manera com Spring gestiona els objectes de l'aplicació. El professor combina apunts teòrics, esquemes de suport i un projecte Java que desenvolupa en **code along**, fent visibles els conceptes a mesura que apareixen. Aquest ús dels materials del curs anterior ha estat confirmat pel professor.

## Continguts i tecnologia

El marc curricular inclou model client-servidor, eines, generació dinàmica i integració amb llenguatges de marques. Els materials rebuts desenvolupen concretament Spring Core, IoC, DI per constructor i setter, selecció de dependències, beans, scopes, inicialització diferida i cicle de vida. Els esquemes MVC i el document d'estructura situen aquests conceptes dins l'arquitectura per capes del mòdul. L'assignació curricular d'U1 es manté en `RA1`.

## Materials i code along

La [[../../../fonts/materials-ut1-2526|fitxa dels materials d'U1]] enllaça el PDF de 28 pàgines, el projecte `unitat1.zip`, els dos esquemes MVC i el document d'estructura de projectes. També relaciona cada bloc teòric amb les classes i rutes HTTP del projecte.

El fil dels exemples va de `Cotxe` i `Motor` per explicar dependències a comptadors i tickets per als scopes, UUIDs de petició i sessió, un bean d'inicialització costosa i un generador d'informes amb hooks de cicle de vida. És el repertori present als materials, sense fixar sessions ni donar per impartida aquesta seqüència el curs actual.

### Projecte actual `UT1_2627`: `ObjectProvider`, `@Configuration` i `@Bean`

**Actualització del professor, 2026-10-07:** el projecte Spring complet `materials/0613-desenvolupament-web-entorn-servidor/UT1/UT1_2627/` incorpora dos exemples per explicar conceptes nous al code along: obtenció de beans `prototype` amb `ObjectProvider` i definició de beans amb `@Configuration` i `@Bean`. Conté `pom.xml`, Maven Wrapper, recursos i una prova de càrrega del context. El POM declara Java 25, Spring Boot 4.1.1 i Spring Web MVC. És material preparat; no consta encara com a activitat impartida.

**Beans `prototype`:** `domain/ShoppingCart.java` és un `@Component` amb `@Scope("prototype")` i un UUID propi. `controller/CartController.java`, singleton per defecte, rep un carret directament pel constructor i també una `ShoppingCartFactory`. `domain/ShoppingCartFactory.java` rep `ObjectProvider<ShoppingCart>` i cada crida a `createCart()` executa `provider.getObject()`.

| Ruta i classes de referència | Evidència observable prevista |
|---|---|
| `GET /cart/direct` → `CartController.direct()` → `directCart.getId()` | Les peticions successives mostren el mateix UUID mentre es manté el context: el carret es resol una vegada en construir el controlador. |
| `GET /cart/factory` → `CartController.factory()` → `ShoppingCartFactory.createCart()` | Les peticions successives mostren UUIDs diferents: cada crida al proveïdor demana un bean nou de tipus `prototype`. |
| `CheckoutService.startCheckout()` → `ShoppingCartFactory.createCart()` | Exemple d'ús de la mateixa fàbrica des d'un servei; escriu l'UUID a consola, però cap controlador del projecte invoca aquest mètode. |

**Progressió proposada a partir del codi:** presentar `ShoppingCart` i la injecció directa, repetir `/cart/direct` i aturar-se a explicar que l'scope `prototype` no renova automàticament una dependència ja injectada en un singleton; introduir la fàbrica i `ObjectProvider`, repetir `/cart/factory` i comparar els UUIDs. És una proposta de suport al code along, pendent de concretar-ne l'ordre a l'aula. La comparació aporta evidències del funcionament del framework en relació amb `RA1.c` i `RA1.g`, sense afegir criteris ni ponderacions.

**Beans de configuració:** el projecte complet incorpora `config/HoraConfig.java`, `domain/RellotgeServidor.java`, `service/HoraService.java` i `controller/HoraController.java`, amb el comportament de `/hora` descrit a continuació. La ruta base `/` ofereix també la resposta inicial de `PrimerControlador`.

### Antecedent i seqüència preparada de l'exemple `/hora`

A `materials/0613-desenvolupament-web-entorn-servidor/UT1/unitat1/src/main/java/cat/paucasesnovescifp/unitat1/` hi ha una còpia de les classes Java del code along anterior amb quatre classes noves. Es distribueix només el contingut del paquet Java; per executar-lo cal situar-lo dins un projecte Spring Boot amb Web, com el de referència.

El cas nou és `GET /hora`: `HoraController → HoraService → RellotgeServidor`. `HoraConfig`, anotada amb `@Configuration`, registra amb `@Bean` un `java.time.format.DateTimeFormatter` amb el patró `dd/MM/yyyy HH:mm:ss`. `RellotgeServidor` rep aquest objecte per constructor i formata l'hora local del servidor. La classe de la biblioteca Java no es pot anotar amb `@Component`; la fàbrica explícita permet posar-la sota la gestió de Spring. No s'hi afegeix cap solució de la pràctica d'informes.

**Seqüència decidida pel professor:** impartir aquest exemple **després del bloc de cicle de vida dels beans**, quan ja s'hagin treballat `@PostConstruct`, `@PreDestroy` i l'exemple existent de `ReportGenerator`. Això situa `@Configuration` i `@Bean` al final d'aquesta progressió de Spring Core, abans de plantejar l'activitat pràctica d'informes; l'exemple de l'hora no n'avança la solució.

**Progressió preparada del code along per a `/hora`:** començar per la classe `RellotgeServidor` i veure que necessita un formatador; mostrar `HoraConfig` i aturar-se a distingir el mètode `@Bean` de l'anotació `@Component`; afegir `HoraService` i `HoraController` per observar la injecció i la resposta de `/hora`. És material previst, no una activitat ja impartida. Es relaciona amb el funcionament del framework dins `RA1.c` i `RA1.g`; la resposta HTTP n'és l'evidència observable, no un nou criteri d'avaluació.

## Seguiment i avaluació

Hi ha una [[../../../fonts/practica-ut1-reports-2526|pràctica de Spring Core sobre gestió d'informes]], amb enunciat i proposta de solució del curs anterior. Reuneix interfícies i generadors, DI, scopes, cache, cicle de vida i inicialització diferida en un mateix cas. La fitxa documenta també les diferències entre els requisits i el codi rebut.

El professor l'ha aportada com a activitat per practicar: complementa el code along i prepara l'examen, amb ponderació zero segons el criteri general del mòdul.

Les activitats realitzades i les evidències d'avaluació es registraran durant el curs. La comparativa i el prototip que figuraven a la versió inicial de la wiki eren propostes genèriques, no activitats descrites pel professor. El code along queda documentat com a forma de treball; la seva ingesta no introdueix instruments ni ponderacions nous.

## Referències

- [[../../../orientacio-docent|Orientació docent del mòdul]].
- [[../../../fonts/servidor-master-2526|Font de partida]] · [[../desenvolupament|Desenvolupament]].
