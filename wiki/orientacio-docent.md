# Orientació docent del mòdul

## Propòsit

L'objectiu és que l'alumnat construeixi aplicacions web funcionals i, sobretot, entengui com organitzar-les perquè siguin coherents i mantenibles. El curs és pràctic i treballa amb **Java i l'ecosistema Spring** per consolidar una manera de desenvolupar en lloc de canviar de framework a cada bloc.

**Procedència:** `raw/sources/2526_Servidor-master/00_general/CONTEXT_ASSIGNATURA.md` i `00_general/README.md`, conservats a [[fonts/servidor-master-2526|la font original]]. Aquesta pàgina sintetitza decisions docents personals; els RA i CA es documenten separadament al [[moduls/0613-desenvolupament-web-entorn-servidor|hub curricular]].

## Metodologia comuna: code along

**Aclariment directe del professor, 2026-09-21:** totes les unitats segueixen aquesta manera de treballar. El professor prepara prèviament un projecte Spring amb les classes Java necessàries ja programades. A classe les va programant amb l'alumnat i s'atura per explicar els conceptes a mesura que apareixen en el codi.

El projecte resolt és la referència de preparació del professor. La construcció progressiva a l'aula és el fil que introdueix i relaciona els conceptes; els apunts i esquemes hi donen suport. Per preparar futurs materials, cal tenir presents tant les classes necessàries com l'ordre de construcció i els punts d'aturada per a les explicacions, quan el professor els concreti.

**Progressió concretada per David Pons, 2026-10-09:** a UT2 el focus és la persistència, especialment entitats i repositoris. Els serveis i controladors són el suport mínim per fer les proves i observar els resultats, seguint la separació per capes introduïda a UT1. A UT3 s'introdueixen DTO i disseny de l'API REST, amb el tractament dels mètodes i les respostes. La unitat final uneix les parts en una aplicació integrada. Les bones pràctiques s'incorporen progressivament: no s'ha de carregar una unitat amb tota la infraestructura de les següents. La [[fonts/code-along-ut2-2627-simplificat|versió simplificada d'UT2]] concreta aquesta decisió; el projecte ampliat es conserva com a reserva d'exemples. És una decisió docent sobre la seqüència, no una reformulació dels RA.

**Criteri dels apunts d'UT2, indicació directa del 2026-10-09:** l'alumnat disposarà del code along i del document de teoria. Els apunts han de ser autònoms i no necessiten el context de les converses, normativa, RA/CA, antecedents o estructura del repositori de preparació. Aquesta informació es conserva a la wiki i al README docent; les referències pràctiques dels apunts són les classes i els recursos del projecte de l'alumne. Les proves de classe s'explicaran amb fitxers `.http`. Les relacions JPA s'estudiaran amb els quatre tipus, encara que algun no aparegui al code along, atesa la seva importància per al futur examen. És una concreció de materials i preparació, no el registre d'una prova ja realitzada.

## Avaluació: casos de resposta oberta en paper

**Decisió docent directa de David Pons, 2026-10-07:** el grup és **IFC33C** i els exàmens plantegen un problema que s'ha de resoldre amb resposta oberta en paper. S'hi avalua que l'alumnat sap identificar quines eines, anotacions, llibreries i conceptes treballats a la UT necessita a cada part i aplicar-los correctament. Aquesta concreció amplia la decisió d'exàmens en paper recollida a la [[presentacio-modul|presentació inicial]].

El cas es desglossa en parts puntuades separadament. L'enunciat descriu els requisits i el comportament esperat sense prescriure la solució tècnica: per exemple, pot requerir conservar informació d'un usuari entre peticions sense ordenar crear un bean de sessió. Els components de petició, la inicialització diferida, les operacions REST, les entitats JPA i les consultes de repositori són exemples possibles segons la unitat treballada, no un temari obligatori de cada prova. El barem concret s'estableix en preparar cada examen i es relaciona amb els RA, CA i evidències pertinents.

La [[../skills/examens-pau-casesnoves/SKILL|skill d'exàmens]] concreta aquest criteri i el format del centre. És una decisió docent, no una prescripció curricular del BOE ni el registre d'una prova ja realitzada. El nom del mòdul es manté fix: **Desenvolupament web en entorn servidor** (0613), denominació curricular del BOE en català. Els materials i les proves treballen amb **Java i Spring**; les eines de generació no depenen de Python, absent en aquest entorn.

## Fil conductor: responsabilitats clares

La font descriu una arquitectura per capes, anomenada «Clean Architecture light aplicada a Spring»:

`domain → repository → service → controller → view / API`

| Capa | Responsabilitat docent |
|---|---|
| `domain` | Representar el model conceptual del problema. |
| `repository` | Accedir a les dades i formular consultes que retornin just la informació necessària per al cas d'ús. |
| `service` | Implementar la lògica de negoci i coordinar les operacions de l'aplicació. |
| `controller` | Rebre peticions HTTP, preparar la resposta i delegar al servei; evitar-hi la lògica de negoci. |
| `view / API` | Presentar informació a persones o clients externs sense exposar directament el model intern. |

El principi de treball és que **cada capa faci la seva feina**. La font posa èmfasi en no recuperar dades sobreres per filtrar-les després al servei, en mantenir les plantilles Thymeleaf simples i en usar DTOs i mappers quan calgui separar entitats i representació externa.

## Tecnologia i pràctiques professionals

- **Eines recurrents:** Java, Spring Boot, Spring MVC, Spring Data JPA, APIs REST i Thymeleaf.
- **Serveis orientats a casos d'ús:** donar noms i comportament propis de l'aplicació (`createOrder`, `cancelOrder`, `getUserOrders`) en lloc de limitar tots els serveis a mètodes CRUD genèrics.
- **Domini i aplicació:** el model del problema és al domini; els serveis executen casos d'ús i coordinen dependències.
- **Diagnòstic:** fer servir SLF4J i nivells `INFO`, `WARN` i `ERROR` en lloc de basar-se en sortides de consola.
- **Proves:** introduir proves automatitzades especialment a la capa de serveis, on resideix la lògica de negoci, i documentar el comportament.
- **Seguretat transversal:** autenticació, control d'accés, validació d'entrades i tractament adequat dels errors formen part del disseny de l'aplicació.

Aquestes pràctiques concreten la filosofia del professor; s'han d'ajustar a les necessitats del cas i als RA i CA que es vulguin evidenciar.

## Aplicació a les quatre unitats

Totes quatre unitats introdueixen els conceptes mitjançant el code along descrit més amunt. A U1 ja s'han incorporat els [[fonts/materials-ut1-2526|materials del curs anterior]], que en mostren una concreció amb IoC, DI i gestió dels beans.

| Unitat | Com s'hi veu l'enfocament |
|---|---|
| [[moduls/0613-desenvolupament-web-entorn-servidor/unitats/ut1-introduccio-servidor|U1]] | Entendre el recorregut d'una petició i el paper de cada component de servidor. |
| [[moduls/0613-desenvolupament-web-entorn-servidor/unitats/ut2-acces-dades|U2]] | Dissenyar repositoris i consultes ajustades als casos d'ús. |
| [[moduls/0613-desenvolupament-web-entorn-servidor/unitats/ut3-serveis-web|U3]] | Exposar serveis i intercanviar dades sense confondre l'API amb el domini. |
| [[moduls/0613-desenvolupament-web-entorn-servidor/unitats/ut4-aplicacio-web|U4]] | Integrar formularis, estat, autenticació i presentació mantenint la lògica de negoci al servei. |

La seqüència d'UT i les hores vigents es prenen del [[fonts/programacio-didactica-2026-2027|full de programació d'aula]]. Les activitats que es facin es poden incorporar progressivament a les fitxes d'UT.
