---
lang: ca-ES
---

# Exàmens del curs 2025–2026 — UT1 a UT4

## Procedència i inventari

David Pons aporta el directori `materials-anteriors/0613-desenvolupament-web-entorn-servidor/Examens2526/` el **2026-10-09**, com a exàmens del curs anterior. Conté quatre enunciats editables en DOCX. S'han llegit el cos, les taules, les capçaleres i els peus dels documents; els originals es conserven intactes.

| Fitxer original | Títol interior | Cas i contingut principal | Barem |
|---|---|---|---|
| [[../../materials-anteriors/0613-desenvolupament-web-entorn-servidor/Examens2526/Examen unitat 1.docx|Examen unitat 1.docx]] | Examen Spring Core | TechShop: injecció de dependències, scopes, inicialització diferida i cicle de vida. | 5 parts de 2 punts; total 10. |
| [[../../materials-anteriors/0613-desenvolupament-web-entorn-servidor/Examens2526/Examen unitat 2.docx|Examen unitat 2.docx]] | Examen Spring JPA | Restaurants i ressenyes: entitats, relació 1–N, repositoris, JPQL i servei. | 6 + 2 + 1 + 1; total 10. |
| [[../../materials-anteriors/0613-desenvolupament-web-entorn-servidor/Examens2526/Examen unitat 3.docx|Examen unitat 3.docx]] | Examen API Rest | Restaurants: DTO, mapper, controlador REST i tractament d'errors. | 2 + 2 + 4 + 2; total 10. |
| [[../../materials-anteriors/0613-desenvolupament-web-entorn-servidor/Examens2526/Examen unitat 4.docx|Examen unitat 4.docx]] | Examen Vistes | Cerca de restaurants: formulari validat, DTO de vista, controlador MVC i Thymeleaf. | 3 + 3 + 4; total 10. |

Els quatre documents indiquen **curs 25/26** i **grup IFC33C**. Les caselles de data, alumne/a i qualificació són buides. La denominació del mòdul als originals és «Programació en entorn servidor». El peu duu el codi `MDO20301`; a UT2–UT4 indica que els criteris de qualificació són a cada pregunta. La capçalera gràfica correspon al CIFP Pau Casesnoves.

No s'han aportat solucions, correccions d'alumnes ni rúbriques separades en aquest directori. La font acredita el contingut dels enunciats històrics, però no permet establir la data concreta d'administració o els resultats. La ingesta no registra cap examen del curs 2026–2027 com a realitzat.

## UT1 — Spring Core

Referència: [[../moduls/0613-desenvolupament-web-entorn-servidor/unitats/ut1-introduccio-servidor|fitxa d'UT1]]. El cas de TechShop demana programar o explicar components del backend amb Spring Boot.

| Part | Punts | Què demana |
|---|---:|---|
| Injecció i estereotips | 2 | Interfície `PaymentProcessor`, implementacions `CreditCardProcessor` i `PaypalProcessor`, una primària i selecció de l'altra amb `@Qualifier`. Només signatures i anotacions. |
| Servei amb dependència opcional | 2 | `OrderService` amb processador obligatori i `DiscountService` opcional; explicar què passa si no existeix el bean opcional. |
| Scopes | 2 | `ShoppingCart` amb identificador i llista de productes, mantingut durant la sessió i injectat en un servei singleton. Raonar el comportament en dues pestanyes i en dos equips. |
| Inicialització | 2 | `EmailSender` amb creació ajornada fins al primer ús i injecció a `NotificationService` que respecti aquest comportament. |
| Cicle de vida | 2 | `LogManager` singleton amb obertura d'un recurs abans de posar el bean a disposició i tancament abans de destruir-lo. Es permet simular-ho amb missatges. |

El carret de l'examen és **de sessió**. És un cas diferent de l'exemple actual de petició de nous beans `prototype` mitjançant una factory; no s'ha d'identificar automàticament un exemple amb l'altre. El document combina codi i explicació del comportament del contenidor.

## UT2 — Spring JPA

Referència: [[../moduls/0613-desenvolupament-web-entorn-servidor/unitats/ut2-acces-dades|fitxa d'UT2]]. El model conté dos tipus d'entitat:

- `Restaurant`: `id` autogenerat de tipus `Long`, `name` obligatori de 2–60 caràcters, `cif` obligatori i únic de 9–12 caràcters, `openingDate` (`LocalDate`), `avgPrice` (`BigDecimal`) i `active` (`Boolean`).
- `Review`: `id` autogenerat de tipus `Long`, `rating` obligatori d'1 a 5, `comment` de màxim 300 caràcters, `visitDate` (`LocalDate`) i `recommended` (`Boolean`).
- Relació restaurant–ressenyes **un-a-molts / molts-a-un**: cada ressenya pertany a un restaurant; la clau forana `restaurant_id` no pot ser nul·la.

| Pregunta | Punts | Què demana |
|---|---:|---|
| 1. Entitats | 6 | Codi complet de les dues entitats, identificadors, relació, clau forana, constructor buit, getters i setters. Exclou validacions avançades i lògica addicional. |
| 2. `ReviewRepository` | 2 | Definició del repositori, signatura donada `List<Review> findByRestaurantId(Long restaurantId)` i consulta JPQL amb `@Query` per a `searchReviews(Long restaurantId, Integer minRating, LocalDate from, LocalDate to)`. Filtrar per restaurant, puntuació mínima i interval de dates; ordenar per data de visita descendent. |
| 3. `RestaurantRepository` | 1 | Signatura d'una consulta derivada per obtenir restaurants actius ordenats per nom ascendent, sense `@Query`. |
| 4. `RestaurantService` | 1 | Estereotip de servei, injecció dels dos repositoris per constructor i delegació des de `getActiveRestaurants()` i `getFilteredReviews(...)`. |

La major part del barem recau en entitats i repositoris (**9/10 punts**); el servei és una delegació mínima. No es demana controlador, DTO, disseny REST ni fitxer de proves HTTP. Això encaixa amb el focus de la [[code-along-ut2-2627-simplificat|referència simplificada actual]], sense convertir aquest examen en el barem del curs nou.

La prova històrica només planteja la relació 1–N. La decisió actual del professor de desenvolupar **els quatre tipus de relació JPA** als apunts es manté; aquest antecedent no en redueix l'abast.

## UT3 — API REST

Referència: [[../moduls/0613-desenvolupament-web-entorn-servidor/unitats/ut3-serveis-web|fitxa d'UT3]]. Es dona el contracte de `RestaurantService` amb `findById(Long id)` i `create(Restaurant r)`, la classe de domini `Restaurant` i `NotFoundException`. Aquest codi no s'ha de modificar: la tasca és construir la capa REST.

| Part | Punts | Què demana |
|---|---:|---|
| DTO | 2 | `RestaurantCreateRequestDto` amb `name`, `city`, `active`; `RestaurantResponseDto` amb aquests camps i `id`. |
| Mapper manual | 2 | `RestaurantMapper` amb `toDto(Restaurant r)` i `toDomain(RestaurantCreateRequestDto dto)`. |
| Controlador | 4 | `RestaurantRestController`, base `/api/restaurants`. Dos endpoints: GET per identificador amb 200 i DTO de resposta; POST amb DTO d'entrada, 201 i DTO de resposta. |
| Tractament d'errors | 2 | `RestControllerAdvice` que capturi `NotFoundException` i retorni 404 amb un JSON que contingui `message`. |

**Discrepància de capçalera:** `Examen unitat 3.docx` diu «PROVA D’AVALUACIÓ UNITAT 2». El títol «Examen API Rest», els paquets `unitat3` i les tasques sustenten la classificació com a UT3. S'indexa segons el fitxer i el contingut; no es corregeix l'original.

El servei i el domini són dades del problema, no parts que s'hagin d'implementar. La prova permet veure la progressió històrica de persistència a representació externa, codis HTTP i errors, coherent amb la separació actual entre UT2 i UT3.

## UT4 — Vistes amb Spring MVC i Thymeleaf

Referència: [[../moduls/0613-desenvolupament-web-entorn-servidor/unitats/ut4-aplicacio-web|fitxa d'UT4]]. Els serveis i repositoris ja existeixen. Es demana implementar la capa web de cerca de restaurants amb GET i POST sobre `/restaurants/search`.

| Part | Punts | Què demana |
|---|---:|---|
| DTO de formulari i vista | 3 | `RestaurantSearchFormDTO`: `name` opcional de 2–40 caràcters si s'informa, `city` obligatori i no buit de màxim 40, `minRating` opcional d'1 a 5, `onlyActive` opcional. `RestaurantSearchResultViewDto`: `id`, `name`, `city`, `active`, `avgRating`, `reviewCount`. |
| Controlador MVC | 3 | GET per presentar el formulari; POST per validar-lo, tornar-hi amb errors o cridar `RestaurantService.searchRestaurants(String name, String city, Integer minRating, Boolean onlyActive)`, convertir els resultats al DTO de vista i afegir-los al model. |
| Templates | 4 | `restaurants/search-form.html` amb acció, mètode, camps vinculats al DTO i errors; `restaurants/search-results.html` amb iteració i valors dels resultats. Només cal l'HTML rellevant. |

El cas concentra la prova en formularis, validació i separació de presentació i negoci. No demana tornar a implementar la persistència o l'API REST, ni planteja sessió, autenticació o JavaScript.

## Encaix i consideracions per reutilitzar-los

Els documents són antecedents útils per estudiar la dificultat, la divisió en parts i l'evolució entre capes. Els tres darrers comparteixen el domini de restaurants; la primera prova utilitza TechShop. No s'ha localitzat una solució associada que permeti comprovar com es resolien els requisits.

La relació amb els RA següents és una **interpretació docent del contingut**, no una matriu de qualificació present als originals. Font curricular de contrast: [[../../raw/sources/2526_Servidor-master/00_general/RA.md|RA.md del repositori original]]; distribució actual: [[programacio-didactica-2026-2027|full de programació 2026–2027]].

| Examen | Encaix principal observable | Límit de la lectura |
|---|---|---|
| UT1 | Funcionament de l'entorn i framework, dins `RA1`. | No evidencia tots els criteris de `RA1`. |
| UT2 | Mapatge, accés i consultes a dades, dins `RA6`. | No es demana desplegar, executar proves o implementar tot el CRUD. |
| UT3 | Construcció de la capa d'un servei web, dins `RA7`. | No hi ha tasca de client consumidor ni d'integració de fonts externes que acrediti `RA9`. |
| UT4 | Formularis i separació entre negoci i presentació, especialment `RA3` i `RA5`. | No cobreix per si sola el conjunt de `RA2`, `RA3`, `RA4`, `RA5` i `RA8` assignat a UT4. |

Abans de reutilitzar els enunciats convé revisar:

- **Identificació:** corregir en una nova còpia la capçalera d'UT3 i usar el nom actual del mòdul, «Desenvolupament web en entorn servidor» (0613), amb curs i data corresponents.
- **Grau de pista tècnica:** alguns apartats prescriuen anotacions o eines (`@Qualifier`, `@Query`, DTO, `RestControllerAdvice`). El [[../orientacio-docent|criteri docent actual]] prioritza que l'alumnat les dedueixi dels requisits. Els originals documenten el plantejament anterior.
- **Precisió dels requisits:** a UT2 cal concretar com s'avaluaran les longituds mínimes i els intervals de valors, distingint mapatge i validació. A UT4 cal precisar el tractament del nom opcional quan arriba buit del formulari. Són punts de revisió per a una futura versió, no correccions efectuades.
- **Cobertura:** relacionar cada part del nou examen amb les evidències i els CA que realment avalua. Els 10 punts de cada document són el seu barem intern; no alteren les ponderacions actuals dels RA.

La indexació conserva aquests exàmens com a materials històrics del professorat. No els trasllada als materials vigents d'alumnat ni fixa el contingut dels exàmens futurs.
