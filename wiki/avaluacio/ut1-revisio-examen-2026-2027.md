---
lang: ca-ES
---

# UT1 — Revisió de l'examen anterior per preparar el curs 2026–2027

**Data de revisió:** 2026-10-14. **Estat:** anàlisi docent prèvia a la redacció. Les adaptacions següents són propostes, no un enunciat ni un barem acordats.

## Fonts i abast del contrast

- Original recuperat: [[../../materials-anteriors/0613-desenvolupament-web-entorn-servidor/Examens2526/Examen unitat 1.docx|Examen unitat 1.docx]], curs 25/26, IFC33C, «Examen Spring Core», cas TechShop. Cinc parts de 2 punts; total 10. És l'únic examen anterior d'UT1 localitzat al repositori; la [[../fonts/examens-2526|fitxa d'exàmens]] en documenta la procedència.
- Contingut impartit: [[../fonts/code-along-ut1-2627|projecte `unitat1_2627`]], confirmat per David Pons el 2026-10-08. S'ha llegit el codi Java pertinent, excloent `ProvaHashController` segons la indicació expressa del professor.
- Suport teòric: [[../../materials/0613-desenvolupament-web-entorn-servidor/UT1/introduccio-a-spring.md|apunts revisats d'introducció a Spring]], especialment els blocs 3–8. Les variants i les integracions il·lustratives dels apunts no s'assimilen automàticament a codi impartit.
- Criteri de preparació: [[../orientacio-docent|orientació docent]] i [[../../skills/examens-pau-casesnoves/SKILL|skill d'exàmens]]: resposta oberta en paper, aplicació de les eines deduïdes dels requisits i parts puntuades separadament.

## Encaix de les cinc parts

**Valoració:** l'examen anterior és una base adequada. Totes les parts tenen un equivalent conceptual al projecte impartit; no s'hi detecten exigències de JPA, REST avançat o vistes que pertanyin a unitats posteriors. La cobertura del repertori actual de Spring Core és parcial.

| Part històrica | Evidència de classe i dels apunts | Valoració i ajust proposat |
|---|---|---|
| Processadors de pagament (2 punts) | `Motor`, `MotorElectric` amb `@Primary`, `MotorBenzina` i selecció a `Cotxe` amb `@Qualifier`; apartat 3.3. | Encaix directe. Descriure un processador per defecte i un altre que una operació concreta necessita, sense ordenar usar les anotacions. Concretar un contracte mínim per evitar signatures arbitràries. |
| Comandes i descompte opcional (2 punts) | Motor obligatori per constructor i GPS opcional amb `@Autowired(required = false)` al setter de `Cotxe`; apartat 3.4. | Encaix directe. Demanar que el servei funcioni també sense el component de descompte i que el codi contempli l'absència. No cal convertir `Optional` en un requisit. |
| Carret de sessió injectat en un servei singleton (2 punts) | `SessionBean` amb proxy i `SessionService`; apartat 5.5. | Encaix conceptual, encara que el carret del code along és prototype. Cal valorar la connexió entre scopes, no només escriure `session`. Precisar que dues pestanyes comparteixen la mateixa sessió i que els altres clients tenen sessions independents. |
| Correu amb creació ajornada (2 punts) | `HeavyBean` i `HeavyService`, amb `@Lazy` al component i al paràmetre del constructor; apartats 6.3–6.4. | Encaix directe. Descriure el cost i el moment del primer ús; el servei ha d'estar disponible a l'arrencada. Es pot simular la connexió amb missatges, sense implementar SMTP. |
| Preparació i tancament de recursos (2 punts) | `ReportGenerator`, `@PostConstruct` i `@PreDestroy`; apartats 7.1–7.2. | Encaix directe. Situar la preparació amb les dependències ja injectades i el tancament en una aturada ordenada del context. Simular els recursos evita feina mecànica d'entrada/sortida de fitxers. |

L'enunciat antic del carret demana un identificador i una llista de productes; el `SessionBean` de classe només mostra identitat. Afegir-hi una llista és una aplicació senzilla de l'estat de sessió, però no és una classe copiada del projecte. El comportament depèn de la sessió HTTP, no del nombre de pestanyes, equips o persones autenticades.

## Continguts impartits que l'antecedent no avalua

| Contingut | Referència impartida | Evidència possible per a la nova prova |
|---|---|---|
| Prototype injectat directament i petició de nous beans | `TicketService`, `/cart/direct`, `ShopingCartFactory` amb `ObjectProvider.getObject()`, `ShopingCartService` i `/cart/factory`; apartats 5.2–5.3. | Resoldre una operació que requereixi un objecte gestionat nou cada vegada i explicar per què conservar-lo al constructor d'un singleton no satisfà el requisit. |
| Configuració explícita d'un objecte de biblioteca | `HoraConfiguration`, `DateTimeFormatter` i `RellotgeServidor`; apartat 8. | Posar sota la gestió del contenidor un formatador extern amb un patró donat i injectar-lo al component que l'utilitza. |
| Objecte compartit dins una petició | `RequestBean` amb proxy i `RequestService`; apartat 5.4. | Distingir un identificador propi de cada petició del que es conserva durant la sessió; pot ser un subapartat breu de raonament. |

IoC, el recorregut controlador–servei–domini i la creació dels beans són pressupòsits del cas antic, però no s'hi demana una explicació directa. Es poden fer observables amb una justificació curta integrada, sense ampliar la prova amb teoria desconnectada del problema.

## Proposta per redactar la nova versió

Conservar TechShop o un cas equivalent, ajuntar la selecció de processadors i el servei de comandes en un bloc de dependències i alliberar espai per a la configuració explícita. Ampliar el bloc de gestió d'instàncies amb un treball independent per operació: el carret continua associat a la sessió i el treball nou representa una responsabilitat diferent. La distinció request–session pot formar part d'una justificació curta. Mantenir la inicialització diferida i el cicle de vida.

Reformular «marca una com a primària» i «injecta l'altra amb `@Qualifier`» com a requisits de selecció. Fer el mateix amb els títols i el barem visible perquè l'alumnat hagi de decidir. Donar només els contractes i fragments de suport necessaris, limitar codi repetitiu i permetre crèdit parcial sense penalitzar diverses vegades un mateix error.

El barem antic de 2 + 2 + 2 + 2 + 2 és un antecedent. Redistribuir els punts quan es concreti el nou enunciat; afegir dues preguntes completes sense reduir les altres augmentaria el volum manuscrit. La durada i els materials permesos no consten a l'original i no s'han fixat en aquesta revisió.

## Traçabilitat curricular i límits

UT1 manté l'assignació a **RA1** segons els fulls `UT1` i `2-ANCORATGE CURRICULAR` de [[../fonts/programacio-didactica-2026-2027|la programació d'aula]]. Font exacta dels CA: [[../../raw/sources/2526_Servidor-master/00_general/RA.md|`raw/sources/2526_Servidor-master/00_general/RA.md`]].

La tria i justificació dels mecanismes de Spring aporta evidència principal de `RA1.g`; la interpretació de la creació, l'execució i la finalització dels components es vincula també a `RA1.c`, seguint el criteri docent ja registrat. És una interpretació d'avaluació, no una formulació curricular nova. Aquesta prova de Spring Core no acredita per si sola tots els CA de RA1, especialment la comparació client–servidor i la integració amb llenguatges de marques. El carret s'avalua aquí com a aplicació dels scopes; no s'hi afegeix una ponderació de RA4.

En redactar la prova, cal relacionar cada subapartat amb CA, evidència i punts al document del professor. La nova còpia emprarà la plantilla del centre, UT1, curs 26/27, IFC33C i «Desenvolupament web en entorn servidor»; la data quedarà buida fins que es conegui.

Contrast tècnic de les distincions entre scopes i de la inicialització diferida: [documentació oficial de Spring sobre scopes](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html) i [beans d'inicialització diferida](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-lazy-init.html). Revisió documental i estàtica, sense executar el projecte ni modificar l'examen històric; no registra una prova preparada o realitzada.
