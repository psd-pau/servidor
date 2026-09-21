# Pràctica d'U1 — Gestor d'informes amb Spring Core

## Fonts i ús

Materials del curs anterior aportats pel professor el 2026-09-21:

- [[../../materials-anteriors/0613-desenvolupament-web-entorn-servidor/UT1/Activitat Spring Core 2526 - Reports.txt|Enunciat: Activitat Spring Core 2526 - Reports.txt]].
- [[../../materials-anteriors/0613-desenvolupament-web-entorn-servidor/UT1/2526 solucio_practica_1.zip|Proposta de solució: 2526 solucio_practica_1.zip]].

L'activitat permet practicar els conceptes introduïts al [[materials-ut1-2526|code along d'U1]] en una aplicació de gestió d'informes. Segons l'orientació del professor, les pràctiques preparen l'examen; aquesta ingesta no els assigna cap pes nou ni acredita que ja s'hagin fet el curs actual.

## Què demana l'enunciat

| Bloc | Requisit |
|---|---|
| Interfície i implementacions | `ReportGenerator` amb versions PDF i HTML; selecció amb `@Primary` i `@Qualifier`. |
| Injecció de dependències | `ReportService` rep el generador per constructor i una marca d'aigua opcional per setter. |
| Scopes | Un `ReportJob` prototype nou amb ID únic per informe i un `ReportStatistics` singleton amb el recompte global. |
| Cicle de vida | `ReportCache` carrega dades de prova reals en un mapa amb `@PostConstruct`, hi desa informes nous i el buida amb `@PreDestroy`. |
| Inicialització diferida | `ExportEngine` costós d'inicialitzar, creat només en demanar una exportació; observar el retard de la primera crida. |
| Accés HTTP | `GET /report/pdf`, `/report/html`, `/report/stats` i `/report/export`. |

## Organització de la proposta de solució

Els camins següents són relatius a `solucio_practica_1/src/main/java/cat/paucasesnovescifp/solucio_practica_1/`, dins el ZIP.

- `domain/ReportGenerator.java`, `PdfReportGenerator.java` i `HtmlReportGenerator.java`: interfície i dues implementacions; PDF té `@Primary`. Els informes generats són cadenes de demostració, no fitxers PDF binaris.
- `service/ReportServiceHtml.java` i `ReportServicePdf.java`: variants amb `@Qualifier` i setter per a la marca d'aigua. Els comentaris expliquen que permeten observar la duplicació de codi.
- `service/ReportService.java`: versió comuna que rep un `Map<String, ReportGenerator>`, selecciona el generador, crea un UUID, afegeix la marca, desa el resultat i notifica la generació del treball.
- `service/ReportJobService.java`: demana cada nou `ReportJob` amb `ObjectFactory<ReportJob>.getObject()`, l'inicialitza i actualitza historial i estadístiques. Aquest punt amplia l'exemple de `TicketService` del code along, que conservava dos tickets injectats al constructor.
- `service/ReportStatistics.java`: acumula totals, recompte per format i moment de la darrera generació.
- `service/ReportCache.java`: carrega quatre informes de prova al mapa, desa els nous i buida el mapa en tancar el context.
- `controller/ReportController.java`: les rutes HTML/PDF usen el servei comú; les crides a les variants estan comentades. També exposa les estadístiques.
- `controller/ExportController.java` i `service/ExportEngine.java`: separen l'exportació en un altre controlador i recorren els informes de la memòria cau.

`pom.xml` declara Java 25 i Spring Boot 3.5.6, amb Web, DevTools i Starter Test. Només s'ha trobat la prova bàsica `contextLoads()`. S'ha llegit el codi font, sense executar el projecte ni modificar el ZIP.

## Diferències entre l'enunciat i la còpia de solució

Observacions de lectura per interpretar la proposta quan es reutilitzi:

| Punt | Enunciat | Codi rebut |
|---|---|---|
| Marca d'aigua | Dependència opcional per setter. | El servei comú la rep al constructor com a dependència obligatòria. Els setters dels serveis específics duen `@Autowired` sense `required = false`; la comprovació de `null` al mètode no implementa l'opcionalitat demanada. |
| Lazy | Cost d'inicialització ajornat fins a la primera exportació. | No hi ha `@Lazy` ni configuració global equivalent a `application.properties`. El constructor només rep la cache; `Thread.sleep(2000)` és dins `exportAll()`, per informe i a cada exportació. |
| Selecció de generador | Exercitar `@Primary` i `@Qualifier`. | Les anotacions són presents, però les rutes de generació utilitzen el mapa de generadors del servei comú. Els serveis específics romanen com a variants de suport. |
| Controlador d'exportació | Ruta inclosa al controlador d'informes. | La ruta es conserva en un `ExportController` separat; el comentari del codi ho justifica per mostrar separació de responsabilitats. |

La proposta permet estudiar l'evolució del disseny. Aquestes diferències queden documentades i no es pressuposa que cada requisit de l'enunciat estigui implementat en aquesta versió.

## Encaix docent

La pràctica pertany a [[../moduls/0613-desenvolupament-web-entorn-servidor/unitats/ut1-introduccio-servidor|U1]], vinculada a `RA1`. Com a interpretació docent, aporta sobretot pràctica del funcionament de l'entorn servidor i de Spring (`RA1.c`, `RA1.g`). Les rutes REST i la cache són suports per observar els conceptes; aquesta activitat no introdueix per si sola una qualificació de `RA6` o `RA7`.

Pot servir com a material de repàs per explicar quina dependència s'injecta, quan es crea una instància, què comparteixen els serveis i quan s'executen les inicialitzacions. Això descriu el seu aprofitament formatiu, sense fixar el contingut d'un examen.
