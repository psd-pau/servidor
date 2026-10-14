---
lang: ca-ES
---

# UT1 — Primera proposta d'examen: reprografia d'una biblioteca

**Preparació:** 2026-10-14. **Estat:** primera proposta docent per revisar; no és una prova aprovada ni realitzada.

## Indicació del professor i context

David Pons demana un examen nou amb una situació diferent de TechShop. Vol conservar els continguts anteriors llevat de sessió i carret, i incorporar un bean de petició amb un ús que permeti valorar el proxy, l'obtenció de nous objectes amb `ObjectProvider` i la configuració explícita de beans. La seva concreció posterior manté també el pes dels blocs de selecció, dependència opcional, inicialització diferida i cicle de vida.

La proposta situa el cas al servei de reprografia d'una biblioteca. Una petició HTTP pot contenir diversos documents: tots comparteixen un context de traça, però cada document disposa d'un treball d'impressió independent. Dos serveis singleton creats a l'arrencada reben directament el context pel constructor. Aquesta restricció fa observable l'ús i la justificació del proxy. La factory obté un treball per document, fins i tot dins la mateixa petició, per distingir prototype de request. Un formatador extern proporciona la data i l'hora dels registres.

## Materials de la proposta

- [[../../materials/0613-desenvolupament-web-entorn-servidor/UT1/examen-ut1-reprografia-proposta-v1.md|Enunciat en Markdown]], font editable de preparació.
- [[../../materials/0613-desenvolupament-web-entorn-servidor/UT1/examen-ut1-reprografia-proposta-v1.docx|DOCX amb la plantilla d'examen del centre]].
- [[../../materials/0613-desenvolupament-web-entorn-servidor/UT1/examen-ut1-reprografia-proposta-v1.pdf|PDF exportat del mateix DOCX]].
- [[../../materials/0613-desenvolupament-web-entorn-servidor/UT1/examen-ut1-reprografia-proposta-v1-criteris.md|Criteris del professorat]], amb evidències, crèdit parcial, referències de correcció i alternatives acceptables. Separats de l'enunciat.
- **Versió amb respostes, 2026-10-14:** [[../../materials/0613-desenvolupament-web-entorn-servidor/UT1/examen-ut1-reprografia-proposta-v1-solucionari.md|Markdown]], [[../../materials/0613-desenvolupament-web-entorn-servidor/UT1/examen-ut1-reprografia-proposta-v1-solucionari.docx|DOCX]] i [[../../materials/0613-desenvolupament-web-entorn-servidor/UT1/examen-ut1-reprografia-proposta-v1-solucionari.pdf|PDF]]. Reprodueix els set enunciats amb codi, justificacions, desglossament de correcció i alternatives, en flux continu sense salts obligatoris per apartat.
- [[../../materials/0613-desenvolupament-web-entorn-servidor/UT1/eines/GeneraExamenReprografia.java|Generador Java]], sense dependència de Python i sense sobreescriptura automàtica de documents existents.

**Revisió de maquetació del professor, 2026-10-14:** David Pons ha compactat el PDF d'enunciat per eliminar els salts fixos i els espais que pressuposaven que hi cabria el codi. El PDF consultat té quatre pàgines. Els set enunciats i les puntuacions del solucionari s'han contrastat amb aquesta versió; el PDF i el DOCX de l'enunciat es conserven sense canvis en generar les respostes. Abans de regenerar l'enunciat, cal revisar la sincronització de la maquetació entre Markdown, DOCX i PDF i conservar la revisió del professor.

## Barem proposat

| Part | Contingut docent que avalua | Punts provisionals |
|---|---|---:|
| 1. Preparació de documents | Interfície, estereotips, preferència i selecció de dependències. | 1,5 |
| 2. Marca d'aigua opcional | Dependència obligatòria, injecció opcional i tractament de l'absència. | 1,5 |
| 3. Traça d'una operació | Request, proxy i injecció en singletons. | 1,5 |
| 4. Un treball per document | Prototype i factory amb `ObjectProvider`. | 1,5 |
| 5. Connexió amb la impressora | Inicialització diferida del component i del punt d'injecció. | 1,5 |
| 6. Preparació i alliberament de recursos | Cicle de vida amb inicialització i tancament ordenat. | 1,5 |
| 7. Data i hora dels registres | `@Configuration`, `@Bean` i injecció d'un objecte de biblioteca. | 1 |
| **Total** | | **10** |

Els quatre blocs conservats valien 2 punts cadascun a l'antecedent. La proposta de 1,5 punts cadascun en conserva el mateix pes relatiu i permet integrar els tres continguts nous sobre 10. Aquesta redistribució i els crèdits parcials encara no són un barem acordat. El temps disponible confirmat és de 3 hores (180 minuts) per llegir i resoldre. La data de realització i els materials permesos continuen sense fixar. Es demanen fragments pertinents i justificacions; les operacions es poden simular i els apartats es valoren separadament.

## Estimació del temps de resolució, 2026-10-14

**Temps disponible confirmat per David Pons:** **3 hores (180 minuts)** per llegir i resoldre la prova. **Objectiu de disseny confirmat:** **90–115 minuts de resolució estimada, incloses lectura i revisió**, malgrat disposar de tres hores. L'estimació detallada d'aquesta versió és de **92–115 minuts**, dins l'objectiu; és una estimació docent, sense prova cronometrada. La proposta inicial de reservar 120 minuts és un antecedent de la conversa i no substitueix aquest criteri de disseny. No s'ha incorporat la durada als documents d'enunciat en aquesta actualització de context.

David Pons adverteix que l'alumnat pot ser especialment lent i que la percepció de temps ajustat pot generar queixes importants, segons els hàbits i expectatives observats en aquest context durant els darrers anys. El marge entre temps estimat i disponible és deliberat. Mantén el volum de la prova dins els 90–115 minuts estimats; no afegeixis tasques perquè hi hagi 180 minuts disponibles. El criteri general queda a l'[[../orientacio-docent#Temps de disseny dels exàmens|orientació docent]].

| Tasca | Minuts estimats |
|---|---:|
| 1. Selecció de preparadors | 10–12 |
| 2. Dependència opcional | 12–15 |
| 3. Context request i proxy | 15–18 |
| 4. Prototype, provider i factory | 15–18 |
| 5. Inicialització diferida | 12–15 |
| 6. Cicle de vida | 8–10 |
| 7. Configuració del formatador | 10–12 |
| Lectura inicial i revisió final | 10–15 |
| **Total orientatiu** | **92–115** |

Hipòtesis: alumnat familiaritzat amb el code along, resolució manuscrita amb els fragments demanats, imports i paquets omesos, signatures sense cossos on l'enunciat ho permet i justificacions breus. No s'espera copiar les explicacions extenses ni tot el codi de suport del solucionari. En ampliar `ServeiImpressio` a l'apartat 4 basta mostrar les addicions, sense tornar a copiar allò resolt a l'apartat 3.

Les parts 3 i 4 concentren la decisió conceptual més exigent, a més del volum d'escriptura. La puntuació igual no implica el mateix temps de resolució. Noranta minuts serien ajustats per a la versió completa; si aquesta fos la franja disponible, convindria reduir codi repetitiu amb esquelets de suport abans de donar per suficient el temps. No hi ha dades de resolució real d'aquesta prova ni evidències que permetin establir una mitjana del grup.

## Traçabilitat i estat

UT1 continua assignada a RA1. Les evidències de tria i ús de Spring s'associen a `RA1.g`, i la interpretació de la creació, resolució i finalització dels objectes també a `RA1.c`, segons la interpretació docent ja registrada. El detall de cada part és al document de criteris. Font exacta: [[../../raw/sources/2526_Servidor-master/00_general/RA.md|RA.md original]] i fulls `UT1` i `2-ANCORATGE CURRICULAR` de [[../fonts/programacio-didactica-2026-2027|la programació d'aula]]. La prova no acredita tots els CA de RA1 ni altera els pesos dels RA.

El contingut s'ha contrastat amb [[../fonts/code-along-ut1-2627|`unitat1_2627`]], els apunts revisats i la [[ut1-revisio-examen-2026-2027|comparativa de l'examen anterior]]. Els títols i requisits de l'enunciat no indiquen les anotacions esperades. La verificació tècnica és documental i estàtica; no s'ha creat ni executat un projecte Spring de solució.

**Verificació del solucionari:** 15 tipus Java compilats en un directori temporal amb Spring 7.0.9, afegint només imports, unificant la versió ampliada de `ServeiImpressio` i simulant el contracte donat de `MarcaAigua`. La compilació no acredita execució dels scopes o del context. Contingut íntegre de 243 paràgrafs i línies verificat al DOCX i al PDF, 14 parts XML vàlides, logotip intacte, fonts incrustades, autoria David Pons i llengua `ca-ES`; PDF etiquetat d'11 pàgines A4, amb numeració automàtica i revisió visual de capçalera, pàgines de codi i darrera pàgina. Els criteris de correcció mantenen la traçabilitat interpretativa a `RA1.c` i `RA1.g`.

Contrast tècnic de les explicacions: [injecció opcional i constructors](https://docs.spring.io/spring-framework/reference/core/beans/annotation-config/autowired.html), [scopes i proxies](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html), [inicialització diferida](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-lazy-init.html) i [callbacks de cicle de vida](https://docs.spring.io/spring-framework/reference/core/beans/annotation-config/postconstruct-and-predestroy-annotations.html).
