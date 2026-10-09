# U1. Introducció a la programació en entorn servidor

**Durada programada:** 20 hores, setembre. **Pes de RA1:** 5% del mòdul. Font: [[../../../fonts/programacio-didactica-2026-2027|programació 2026–2027]], fulls `UT1`, `6-Distribució temporal` i `2-ANCORATGE CURRICULAR`.

## RA i criteris d'avaluació

- RA1 — caracterització de la programació web en entorn servidor.
- CA a concretar: `RA1.a` a `RA1.g`: models client/servidor, generació dinàmica, servidors web i d'aplicacions, tecnologies, integració amb marques i avaluació de frameworks.

## Finalitat i enfocament docent

L'alumnat es familiaritza amb el funcionament del servidor i amb la manera com Spring gestiona els objectes de l'aplicació. El professor combina apunts teòrics, esquemes de suport i un projecte Java que desenvolupa en **code along**, fent visibles els conceptes a mesura que apareixen. Aquest ús dels materials del curs anterior ha estat confirmat pel professor.

## Continguts i tecnologia

El marc curricular inclou model client-servidor, eines, generació dinàmica i integració amb llenguatges de marques. Els materials rebuts desenvolupen concretament Spring Core, IoC, DI per constructor i setter, selecció de dependències, beans, scopes, inicialització diferida i cicle de vida. La preparació actual amplia els scopes amb `ObjectProvider` encapsulat en una factory i incorpora `@Configuration` i `@Bean` amb un `DateTimeFormatter`. Els esquemes MVC i el document d'estructura situen aquests conceptes dins l'arquitectura per capes del mòdul. L'assignació curricular d'U1 es manté en `RA1`.

## Materials i code along

La [[../../../fonts/materials-ut1-2526|fitxa dels materials d'U1]] enllaça el PDF de 30 pàgines, el projecte `unitat1.zip`, els dos esquemes MVC i el document d'estructura de projectes. També relaciona cada bloc teòric amb les classes i rutes HTTP del projecte.

**Apunts revisats, 2026-10-08:** [[../../../../materials/0613-desenvolupament-web-entorn-servidor/UT1/introduccio-a-spring.md|Introducció a Spring en Markdown]] desenvolupa els blocs del PDF original i els actualitza amb precisions sobre DI, scopes, proxies, lazy i cicle de vida, més `ObjectProvider` amb factory i l'exemple `/hora`. Per indicació de David Pons, es marca com a revisat i se'n generen el [[../../../../materials/0613-desenvolupament-web-entorn-servidor/UT1/introduccio-a-spring.docx|DOCX editable]] i el [[../../../../materials/0613-desenvolupament-web-entorn-servidor/UT1/introduccio-a-spring.pdf|PDF de 30 pàgines per a l'alumnat]], amb la skill del centre i llengua `ca-ES`. La fitxa de materials conserva l'evolució editorial, i el README d'UT1 documenta la funció dels fitxers i el circuit de regeneració.

El fil dels exemples va de `Cotxe` i `Motor` per explicar dependències a comptadors i tickets per als scopes, UUIDs de petició i sessió, un bean d'inicialització costosa i un generador d'informes amb hooks de cicle de vida. És el repertori present als materials, sense fixar sessions ni donar per impartida aquesta seqüència el curs actual.

### Projecte impartit `unitat1_2627`: referència actual dels apunts

**Confirmació del professor, 2026-10-08:** `materials/0613-desenvolupament-web-entorn-servidor/UT1/unitat1_2627/` és el code along realitzat a classe enguany. La [[../../../fonts/code-along-ut1-2627|fitxa del projecte impartit]] documenta les classes i rutes. Els apunts en Markdown s'han adaptat a `CotxeService → Cotxe`, `CartController → ShopingCartService → ShopingCartFactory`, la ruta `/lazy`, `ReportService.generateReport()` i `HoraConfiguration`, amb `/hora` dins `Unitat1Contoller`. Es conserven els noms exactes dels fitxers i les sortides observables. `ProvaHashController` queda exclòs per indicació expressa del professor.

La confirmació identifica contingut treballat a classe, sense fixar-ne la seqüència exacta o donar per demostrada l'adquisició dels CA per l'alumnat. `ReportJobFactory` continua com a disseny de la pràctica, mentre que el code along implementa la factory de carrets. Les còpies descrites a continuació es mantenen com a antecedents de preparació.

**Revisió crítica dels apunts, 2026-10-08:** s'ha comprovat la coherència amb el codi de referència i s'han reforçat la introducció de beans, el recorregut HTTP, la separació entre preparació d'objectes i execució de peticions, i la distinció de variants i ampliacions. El final dels apunts proposa comprovacions amb resultats esperats i diagnòstic de discrepàncies, sense donar-les per realitzades. La fitxa del projecte registra l'abast de la revisió estàtica i les millores de codi identificades; no s'ha executat ni modificat el projecte Java.

### Projecte de preparació `UT1_2627`: `ObjectProvider`, `@Configuration` i `@Bean`

**Actualització del professor, 2026-10-07:** el projecte Spring complet `materials/0613-desenvolupament-web-entorn-servidor/UT1/UT1_2627/` incorpora dos exemples per explicar conceptes nous al code along: obtenció de beans `prototype` amb `ObjectProvider` i definició de beans amb `@Configuration` i `@Bean`. Conté `pom.xml`, Maven Wrapper, recursos i una prova de càrrega del context. El POM declara Java 25, Spring Boot 4.1.1 i Spring Web MVC. És material preparat; no consta encara com a activitat impartida.

**Beans `prototype`:** `domain/ShoppingCart.java` és un `@Component` amb `@Scope("prototype")` i un UUID propi. `controller/CartController.java`, singleton per defecte, rep un carret directament pel constructor i també una `ShoppingCartFactory`. `domain/ShoppingCartFactory.java` rep `ObjectProvider<ShoppingCart>` i cada crida a `createCart()` executa `provider.getObject()`.

| Ruta i classes de referència | Evidència observable prevista |
|---|---|
| `GET /cart/direct` → `CartController.direct()` → `directCart.getId()` | Les peticions successives mostren el mateix UUID mentre es manté el context: el carret es resol una vegada en construir el controlador. |
| `GET /cart/factory` → `CartController.factory()` → `ShoppingCartFactory.createCart()` | Les peticions successives mostren UUIDs diferents: cada crida al proveïdor demana un bean nou de tipus `prototype`. |
| `CheckoutService.startCheckout()` → `ShoppingCartFactory.createCart()` | Exemple d'ús de la mateixa fàbrica des d'un servei; escriu l'UUID a consola, però cap controlador del projecte invoca aquest mètode. |

**Progressió proposada a partir del codi:** presentar `ShoppingCart` i la injecció directa, repetir `/cart/direct` i aturar-se a explicar que l'scope `prototype` no renova automàticament una dependència ja injectada en un singleton; introduir la fàbrica i `ObjectProvider`, repetir `/cart/factory` i comparar els UUIDs. És una proposta de suport al code along, pendent de concretar-ne l'ordre a l'aula. La comparació aporta evidències del funcionament del framework en relació amb `RA1.c` i `RA1.g`, sense afegir criteris ni ponderacions.

**Beans de configuració:** el projecte complet incorpora `config/HoraConfig.java`, `domain/RellotgeServidor.java`, `service/HoraService.java` i `controller/HoraController.java`, amb el comportament de `/hora` descrit a continuació. La ruta base `/` ofereix també la resposta inicial de `PrimerControlador`.

### De l'exemple del carret a `ReportJobFactory`

**Decisió docent de David Pons, 2026-10-08:** el projecte `UT1_2627` recull proves preparades abans de classe. L'explicació d'`ObjectProvider` es vincula al problema d'injectar un bean `prototype` dins un `singleton` i es trasllada al cas dels informes. La font d'aquesta concreció és el resum de la conversa amb el client web de ChatGPT aportat pel professor en aquesta data.

En la pràctica, `ReportJob` és `prototype` i els serveis de generació són `singleton`. Si injectam directament un `ReportJob` al constructor d'un servei, Spring crea una instància en construir aquell singleton i el servei la reutilitza en totes les generacions. Per obtenir un treball nou per informe, injectam un `ObjectProvider<ReportJob>` i demanam el bean quan comença cada generació. Aquest comportament correspon a la [documentació dels scopes de Spring](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html#beans-factory-scopes-sing-prot).

> `prototype` significa que Spring crea una nova instància cada vegada que se li demana el bean. `ObjectProvider` permet fer aquesta petició en el moment que la necessitam.

**Disseny de la solució actual de la UT:** encapsular el proveïdor en una simple factory, seguint el patró ja present a `domain/ShoppingCartFactory.java`:

```java
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@Component
public class ReportJobFactory {
    private final ObjectProvider<ReportJob> provider;

    public ReportJobFactory(ObjectProvider<ReportJob> provider) {
        this.provider = provider;
    }

    public ReportJob createJob() {
        return provider.getObject();
    }
}
```

Els serveis reben `ReportJobFactory` per constructor i, dins cada generació, fan:

```java
ReportJob job = reportJobFactory.createJob();
```

La factory encapsula el mecanisme específic de Spring i desacobla els serveis d'`ObjectProvider`: aquests només demanen un treball nou. La instància nova és conseqüència de l'scope `prototype` de `ReportJob`; el proveïdor permet decidir quan es demana. El tractament docent es limita a aquesta petició amb `getObject()`, sense ampliar altres funcionalitats del proveïdor ni alternatives. Referència tècnica: [API d'ObjectProvider](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/ObjectProvider.html).

**Punt d'explicació i evidència prevista:** relacionar els UUIDs de `/cart/direct` i `/cart/factory` amb els IDs dels informes; generar dos informes del mateix format i comprovar que cada generació obté un `ReportJob` diferent. L'alumnat ha de poder explicar per què la injecció directa el reutilitzaria i on es fa la nova petició al contenidor. És una concreció docent vinculada a `RA1.c` i `RA1.g`, pendent de registrar-ne la realització a classe.

Aquest fragment fixa el disseny de la solució dels informes; `UT1_2627` conté l'exemple equivalent de carrets, no classes `ReportJob` o `ReportJobFactory`. La [[../../../fonts/practica-ut1-reports-2526|solució històrica]] emprava `ObjectFactory` dins `ReportJobService` i es conserva com a antecedent.

### Antecedent i seqüència preparada de l'exemple `/hora`

A `materials/0613-desenvolupament-web-entorn-servidor/UT1/unitat1/src/main/java/cat/paucasesnovescifp/unitat1/` hi ha una còpia de les classes Java del code along anterior amb quatre classes noves. Es distribueix només el contingut del paquet Java; per executar-lo cal situar-lo dins un projecte Spring Boot amb Web, com el de referència.

El cas nou és `GET /hora`: `HoraController → HoraService → RellotgeServidor`. `HoraConfig`, anotada amb `@Configuration`, registra amb `@Bean` un `java.time.format.DateTimeFormatter` amb el patró `dd/MM/yyyy HH:mm:ss`. `RellotgeServidor` rep aquest objecte per constructor i formata l'hora local del servidor. La classe de la biblioteca Java no es pot anotar amb `@Component`; la fàbrica explícita permet posar-la sota la gestió de Spring. No s'hi afegeix cap solució de la pràctica d'informes.

**Seqüència decidida pel professor:** impartir aquest exemple **després del bloc de cicle de vida dels beans**, quan ja s'hagin treballat `@PostConstruct`, `@PreDestroy` i l'exemple existent de `ReportGenerator`. Això situa `@Configuration` i `@Bean` al final d'aquesta progressió de Spring Core, abans de plantejar l'activitat pràctica d'informes; l'exemple de l'hora no n'avança la solució.

**Progressió preparada del code along per a `/hora`:** començar per la classe `RellotgeServidor` i veure que necessita un formatador; mostrar `HoraConfig` i aturar-se a distingir el mètode `@Bean` de l'anotació `@Component`; afegir `HoraService` i `HoraController` per observar la injecció i la resposta de `/hora`. És material previst, no una activitat ja impartida. Es relaciona amb el funcionament del framework dins `RA1.c` i `RA1.g`; la resposta HTTP n'és l'evidència observable, no un nou criteri d'avaluació.

## Seguiment i avaluació

Antecedent disponible: [[../../../fonts/examens-2526#UT1 — Spring Core|examen d'UT1 del curs 2025–2026]], cas TechShop amb cinc parts de 2 punts sobre injecció, dependència opcional, carret de sessió, inicialització diferida i cicle de vida. La fitxa enllaça el DOCX original i diferencia el carret de sessió de l'exemple actual de beans `prototype`. És una referència històrica, no una prova del curs actual.

Hi ha una [[../../../fonts/practica-ut1-reports-2526|pràctica de Spring Core sobre gestió d'informes]], amb enunciat i proposta de solució del curs anterior. Reuneix interfícies i generadors, DI, scopes, cache, cicle de vida i inicialització diferida en un mateix cas. La fitxa documenta també les diferències entre els requisits i el codi rebut.

El professor l'ha aportada com a activitat per practicar: complementa el code along i prepara l'examen, amb ponderació zero segons el criteri general del mòdul.

Les activitats realitzades i les evidències d'avaluació es registraran durant el curs. La comparativa i el prototip que figuraven a la versió inicial de la wiki eren propostes genèriques, no activitats descrites pel professor. El code along queda documentat com a forma de treball; la seva ingesta no introdueix instruments ni ponderacions nous.

## Referències

- [[../../../orientacio-docent|Orientació docent del mòdul]].
- [[../../../fonts/servidor-master-2526|Font de partida]] · [[../desenvolupament|Desenvolupament]].
