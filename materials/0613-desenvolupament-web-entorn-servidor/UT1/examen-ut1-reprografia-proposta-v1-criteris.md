---
lang: ca-ES
author: David Pons
status: Proposta docent — ús del professorat
date: 2026-10-14
---

# Primera proposta d'examen d'UT1 — Criteris del professorat

Enunciat: [servei de reprografia de la biblioteca](examen-ut1-reprografia-proposta-v1.md). És una primera proposta per revisar, no una prova aprovada o realitzada. David Pons confirma 3 hores (180 minuts) per llegir i resoldre. La data de realització i els materials permesos continuen sense fixar.

Versió amb enunciats i respostes: [solucionari en Markdown](examen-ut1-reprografia-proposta-v1-solucionari.md), [DOCX](examen-ut1-reprografia-proposta-v1-solucionari.docx) i [PDF](examen-ut1-reprografia-proposta-v1-solucionari.pdf). Contrasta les preguntes amb el PDF d'enunciat compactat pel professor el 2026-10-14; conserva els mateixos punts i afegeix codi, justificacions i crèdit parcial.

## Abast i pesos

**Criteri de temps confirmat per David Pons, 2026-10-14:** dissenyar la prova per a **90–115 minuts estimats**, incloses lectura i revisió, tot i disposar de **180 minuts**. El professor assenyala ritmes especialment lents i possibles queixes importants davant la percepció de temps ajustat, vinculades a les expectatives observades els darrers anys. Mantén el marge deliberat i no ampliïs el volum per ocupar les tres hores. L'estimació actual de 92–115 minuts s'ajusta al criteri; no és una exigència que tot l'alumnat acabi dins aquest rang.

**Indicació de David Pons, 2026-10-14:** context nou; conservar els continguts anteriors llevat de sessió i carret, i incorporar request amb proxy, obtenció de prototypes amb `ObjectProvider` i configuració amb `@Configuration` i `@Bean`.

Es mantenen selecció de dependències, dependència opcional, inicialització diferida i cicle de vida. El barem històric donava 2 punts a cadascun d'aquests quatre blocs. La proposta els dona **1,5 punts cadascun**, conservant-ne el mateix pes relatiu; request i factory també valen 1,5, i configuració val 1. Total: **6 × 1,5 + 1 = 10 punts**. Aquesta redistribució és provisional, no un barem acordat ni una modificació dels pesos dels RA.

El mateix cas permet diferenciar dos nivells d'identitat: un context per petició HTTP i un treball nou per document, inclosos dos documents dins la mateixa petició. Es demana codi rellevant, amb imports, paquets i implementacions mecàniques omissibles. Amb les 3 hores disponibles, es considera suficient el marge per a les set parts. L'estimació docent de resolució és de 92–115 minuts, sense prova cronometrada.

## Barem i evidències

Els CA són una interpretació docent d'aquestes evidències de Spring, seguint la traçabilitat ja establerta a UT1. Font curricular exacta: [RA.md](../../../raw/sources/2526_Servidor-master/00_general/RA.md); assignació d'UT1 i ponderació vigent a la [programació d'aula](../../../wiki/fonts/programacio-didactica-2026-2027.md).

| Part | Punts per subapartat | Evidència esperada | CA pertinents |
|---|---|---|---|
| 1. Preparadors | a: 0,75; b: 0,75 | Interfície i implementacions registrades; preferència per text amb `@Primary`, selecció d'imatge amb `@Qualifier` al constructor i servei singleton. | `RA1.g` |
| 2. Marca d'aigua | a: 0,5; b: 0,5; c: 0,5 | Dependència imprescindible per constructor; setter amb `@Autowired(required = false)` per a la marca; comprovar l'absència abans d'aplicar-la; context que pot arrencar sense aquest bean. | `RA1.c`, `RA1.g` |
| 3. Traça | a: 0,5; b: 0,5; c: 0,5 | Bean request amb UUID i proxy de classe; serveis singleton amb injecció directa per constructor; explicar la delegació cap al context de la petició activa. | `RA1.c`, `RA1.g` |
| 4. Treballs | a: 0,5; b: 0,75; c: 0,25 | Bean prototype; factory amb `ObjectProvider<TreballImpressio>` i `getObject()` per operació; el servei demana un treball dins el mètode, en lloc de conservar-ne un. | `RA1.c`, `RA1.g` |
| 5. Connexió | a: 0,5; b: 0,5; c: 0,5 | `@Lazy` al motor i al punt d'injecció del servei; inicialització al primer ús i reutilització posterior. | `RA1.c`, `RA1.g` |
| 6. Recursos | a: 0,5; b: 0,5; c: 0,5 | Component singleton amb `@PostConstruct` i `@PreDestroy`; preparar una vegada i alliberar en tancar el context ordenadament, no per impressió. | `RA1.c`, `RA1.g` |
| 7. Formatador | a: 0,5; b: 0,5 | `@Configuration` i mètode `@Bean` que retorna `DateTimeFormatter`; component receptor amb injecció per constructor i formatació de `LocalDateTime`. | `RA1.g` |

La prova no acredita tots els CA de RA1 ni introdueix una ponderació de RA4 pel fet d'usar un scope web. La llengua i el contingut de l'enunciat d'alumnat no inclouen aquesta matriu ni les eines esperades.

## Referències mínimes de correcció

### Request i proxy

```java
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ContextImpressio {
    private final String id = UUID.randomUUID().toString();
    public String getId() { return id; }
}
```

Cada servei és `@Service`, singleton per defecte, i conserva un camp `private final ContextImpressio context` assignat al constructor. No ha de cridar `getId()` al constructor: en aquest moment encara no hi ha una petició activa. El camp conserva el proxy; durant una petició, els dos serveis deleguen en el mateix objecte de context. En una altra petició el proxy resol un altre objecte, també si és el mateix navegador.

Acceptar `@RequestScope`, que incorpora el proxy de classe, amb explicació correcta. Un `@Scope("request")` sense proxy no satisfà la injecció directa en singletons creats a l'arrencada; una injecció amb provider tampoc satisfà el contracte explícit d'aquest apartat, que demana un camp de tipus `ContextImpressio`. `new ContextImpressio()` o un UUID creat separadament a cada servei tampoc garanteixen la identitat compartida requerida. Classe i mètode consultat han de permetre el proxy de classe; el camp receptor pot ser `final`.

### Petició de nous treballs

```java
@Component
@Scope("prototype")
public class TreballImpressio { /* codi donat a l'enunciat */ }

@Component
public class FabricaTreballs {
    private final ObjectProvider<TreballImpressio> provider;
    public FabricaTreballs(ObjectProvider<TreballImpressio> provider) {
        this.provider = provider;
    }
    public TreballImpressio nouTreball() {
        return provider.getObject();
    }
}
```

`ServeiImpressio` afegeix la fàbrica al constructor ja present a l'apartat 3. Només cal mostrar les addicions; no cal tornar a copiar el servei. El mètode exigit pot ser `return fabrica.nouTreball().executar(nomDocument);`. No s'exigeix cap bucle: es pot suposar que la capa que recorre els documents invoca aquest mètode per cadascun. El context de petició es comparteix, però els treballs són diferents, fins i tot dins la mateixa petició. Si el bean fos singleton, el provider no en crearia un de nou; l'scope i la petició en el moment d'ús són evidències diferents.

### Altres decisions de correcció

- Part 1a: 0,25 per contracte i declaracions compatibles; 0,25 per registrar tots dos candidats; 0,25 per la preferència de text. Part 1b: 0,25 per servei, camp i constructor; 0,25 per selecció correcta; 0,25 per justificació del candidat per defecte. El nom del qualificador ha de correspondre al bean registrat; s'admeten noms explícits coherents.
- Part 2: `MarcaAigua` és un contracte donat; no cal implementar-lo. La referència de classe és el GPS opcional dels apunts. Admetre una alternativa vàlida d'injecció opcional si en justifica el comportament, sense exigir una API que no s'hagi treballat. Fer opcional la dependència no resol per si mateix una ambigüitat de candidats.
- Part 3a: 0,25 per identitat i abast de petició; 0,25 per configurar la intermediació que permet la injecció directa. Part 3b: 0,25 per cada servei. Part 3c: 0,25 per delegació i disponibilitat a l'arrencada; 0,25 per identitats dins i entre peticions.
- Part 4b: 0,25 per declarar i injectar el provider; 0,25 per demanar l'objecte dins `nouTreball()`; 0,25 per injectar la fàbrica i usar-la dins el mètode del servei. Si hi ha un error d'scope a 4a, valorar igualment la petició correcta al contenidor a 4b.
- Part 5: no basta `@Lazy` només al component si la injecció directa força la seva creació, ni només al paràmetre si el component continua subjecte a la preinstanciació habitual. El constructor pot limitar-se a un missatge; no cal espera ni client d'impressora. Part 5a: 0,25 per component i simulació; 0,25 per ajornament del bean. Part 5b: 0,25 per estructura i delegació; 0,25 per ajornament al punt d'injecció.
- Part 6: callbacks de Jakarta (`jakarta.annotation`). 0,5 per cada mètode configurat al moment correcte i 0,5 per explicar els moments i distingir-los de les crides de negoci. El requisit és tancament ordenat; no s'exigeix raonar terminacions forçades ni neteja de prototypes.
- Part 7a: 0,25 per configuració i registre del resultat; 0,25 pel tipus i patró correctes. Part 7b: 0,25 per component i injecció; 0,25 per `ara()` i localització del canvi de patró. No crear el formatador dins `SegellTemps`.

L'absència d'`@Autowired` en un constructor únic és correcta. Els fragments poden ometre imports i paquets. Diferenciar errors conceptuals de petites errades de transcripció manuscrita; les anotacions i els contractes essencials sí que formen part de l'evidència. No arrossegar un error d'un apartat a totes les parts següents ni penalitzar-lo repetidament. Els desglossaments anteriors són propostes de crèdit parcial.

## Fonts de preparació i verificació

- [Code along impartit `unitat1_2627`](../../../wiki/fonts/code-along-ut1-2627.md): `Cotxe`, `MotorElectric`, `RequestBean`, `RequestService`, `ShopingCartFactory`, `HeavyBean`, `HeavyService`, `ReportGenerator` i `HoraConfiguration`. `ProvaHashController` queda exclòs.
- [Apunts revisats](introduccio-a-spring.md), apartats 3, 5, 6, 7 i 8; [comparativa amb l'examen anterior](../../../wiki/avaluacio/ut1-revisio-examen-2026-2027.md).
- [Scopes de Spring](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html), [API de `RequestScope`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/annotation/RequestScope.html), [API de `ObjectProvider`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/ObjectProvider.html), [inicialització diferida](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-lazy-init.html).

La revisió del contingut és estàtica i documental; aquests fragments no constitueixen un projecte executable ni acrediten proves del context de Spring. DOCX i PDF de l'enunciat es generen amb la plantilla d'examen del centre. La revisió gràfica i les comprovacions d'exportació es registren al README i al log quan es completen.
