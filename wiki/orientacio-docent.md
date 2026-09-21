# Orientació docent del mòdul

## Propòsit

L'objectiu és que l'alumnat construeixi aplicacions web funcionals i, sobretot, entengui com organitzar-les perquè siguin coherents i mantenibles. El curs és pràctic i treballa amb **Java i l'ecosistema Spring** per consolidar una manera de desenvolupar en lloc de canviar de framework a cada bloc.

**Procedència:** `raw/sources/2526_Servidor-master/00_general/CONTEXT_ASSIGNATURA.md` i `00_general/README.md`, conservats a [[fonts/servidor-master-2526|la font original]]. Aquesta pàgina sintetitza decisions docents personals; els RA i CA es documenten separadament al [[moduls/0613-desenvolupament-web-entorn-servidor|hub curricular]].

## Metodologia comuna: code along

**Aclariment directe del professor, 2026-09-21:** totes les unitats segueixen aquesta manera de treballar. El professor prepara prèviament un projecte Spring amb les classes Java necessàries ja programades. A classe les va programant amb l'alumnat i s'atura per explicar els conceptes a mesura que apareixen en el codi.

El projecte resolt és la referència de preparació del professor. La construcció progressiva a l'aula és el fil que introdueix i relaciona els conceptes; els apunts i esquemes hi donen suport. Per preparar futurs materials, cal tenir presents tant les classes necessàries com l'ordre de construcció i els punts d'aturada per a les explicacions, quan el professor els concreti.

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
