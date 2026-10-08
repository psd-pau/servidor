# Desenvolupament operatiu — Programació en entorn servidor

Aquest espai recull la seqüència de les quatre unitats. Les hores i els mesos provenen del [[../../fonts/programacio-didactica-2026-2027|full de programació d'aula 2026–2027]], que també s'usa per enregistrar activitats durant el curs. Les fitxes de la wiki poden créixer amb els materials i les experiències reals, seguint l'[[../../orientacio-docent|enfocament docent]].

La metodologia comuna és el **code along**: el professor duu un projecte Spring prèviament resolt i en va programant les classes amb l'alumnat, fent pauses per introduir i explicar els conceptes de cada unitat.

| Unitat | Hores | Mesos previstos | RA |
|---|---:|---|---|
| [[unitats/ut1-introduccio-servidor|U1. Introducció a la programació en entorn servidor]] | 20 | Setembre | RA1 |
| [[unitats/ut2-acces-dades|U2. Accés a dades]] | 40 | Setembre–octubre | RA6 |
| [[unitats/ut3-serveis-web|U3. Serveis web]] | 50 | Octubre–desembre | RA7, RA9 |
| [[unitats/ut4-aplicacio-web|U4. Aplicacions web]] | 120 | Desembre–març | RA2, RA3, RA4, RA5, RA8 |

Vegeu també el [[../../matrius/mapa-ra-unitats|mapa de traçabilitat]].

U1 disposa de [[../../fonts/materials-ut1-2526|materials del curs anterior ingerits]]: apunts, suports d'arquitectura i projecte Java per al code along.

Per al curs 2026–2027, el projecte complet `materials/0613-desenvolupament-web-entorn-servidor/UT1/UT1_2627/` prepara dos exemples nous: comparació de la injecció directa d'un bean `prototype` amb l'obtenció de noves instàncies mitjançant `ObjectProvider`, i registre d'un `DateTimeFormatter` amb `@Configuration` i `@Bean`. La [[unitats/ut1-introduccio-servidor|fitxa d'U1]] documenta les classes, les rutes observables i els punts d'explicació; la incorporació del projecte no implica que ja s'hagin impartit.

**Concreció del professor, 2026-10-08:** `UT1_2627` recull les proves prèvies a classe. L'outline de Spring Core incorpora la petició de prototypes en el bloc de scopes i manté els beans de configuració després del cicle de vida:

| Bloc de la progressió | Referència i punt d'explicació |
|---|---|
| IoC, beans i DI | Dependències per constructor i setter; selecció amb `@Primary` i `@Qualifier`. |
| Scopes i petició de noves instàncies | Comparar `/cart/direct` i `/cart/factory`: un prototype injectat directament en un singleton es conserva; `ObjectProvider.getObject()` demana una instància quan cal. La factory encapsula aquesta petició. |
| Inicialització diferida i cicle de vida | Exemples existents de cost d'inicialització, `@PostConstruct`, `@PreDestroy` i `ReportGenerator`. |
| Configuració explícita de beans | `/hora`: `HoraConfig` registra el `DateTimeFormatter` amb `@Bean`; `RellotgeServidor` el rep per constructor. |
| Aplicació a la pràctica d'informes | `ReportJob` prototype i serveis singleton: la solució actual preveu `ReportJobFactory` amb `ObjectProvider<ReportJob>`, i cada generació fa `reportJobFactory.createJob()`. |

Aquest outline organitza la preparació i els punts d'aturada del code along; no fixa sessions ni acredita continguts ja impartits. La fitxa d'U1 inclou l'explicació breu i el codi de referència de `ReportJobFactory`. Es mantenen `RA1`, la interpretació docent de `RA1.c` i `RA1.g`, les hores i les ponderacions vigents.
