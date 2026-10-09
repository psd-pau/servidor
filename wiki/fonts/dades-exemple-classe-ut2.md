---
lang: ca-ES
---

# UT2 — Configuració H2 comentada i dades de demostració

## Procedència i inventari

**Font aportada per David Pons el 2026-10-09:** carpeta `materials-anteriors/0613-desenvolupament-web-entorn-servidor/UT2/dades exemple classe/`. El professor la identifica com a suport per explicar la configuració i carregar dades de demostració. La seva incorporació no acredita que ja s'hagi emprat a classe enguany.

| Fitxer original | Contingut i format observats |
|---|---|
| [[../../materials-anteriors/0613-desenvolupament-web-entorn-servidor/UT2/dades exemple classe/application.properties|`application.properties`]] | 12 propietats actives; comentaris sobre connexió, JPA/Hibernate, inicialització SQL i consola H2. Text compatible amb ISO-8859-1, amb salts LF; no és UTF-8. |
| [[../../materials-anteriors/0613-desenvolupament-web-entorn-servidor/UT2/dades exemple classe/data.sql|`data.sql`]] | 21 sentències `INSERT INTO BOOK`, agrupades per gènere. UTF-8 amb salts CRLF. No crea les taules. |

La carpeta conté aquests dos fitxers: no hi ha `pom.xml` ni cap declaració de dependència Maven dins el `.properties`. La dependència corresponent es pot documentar a partir del [[../../materials-anteriors/0613-desenvolupament-web-entorn-servidor/UT2/unitat_2_classe2526/pom.xml|POM del code along històric]], que inclou `org.springframework.boot:spring-boot-starter-data-jpa` i:

```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

Aquest fragment procedeix del projecte històric, no dels fitxers nous. El POM també incorpora `spring-boot-starter-web`, que dona suport a l'aplicació web de l'exemple.

## Configuració inventariada

| Propietat | Valor de la font | Funció en l'exemple |
|---|---|---|
| `spring.application.name` | `unitat2` | Nom de l'aplicació; no és el nom de la base de dades. |
| `spring.datasource.url` | `jdbc:h2:mem:testdb` | Connexió a una H2 en memòria anomenada `testdb`. |
| `spring.datasource.driver-class-name` | `org.h2.Driver` | Classe del controlador JDBC d'H2. |
| `spring.datasource.username` | `sa` | Usuari de connexió. |
| `spring.datasource.password` | Buit | Contrasenya de connexió de la demostració. |
| `spring.jpa.database-platform` | `org.hibernate.dialect.H2Dialect` | Dialecte SQL d'Hibernate per a H2. |
| `spring.jpa.hibernate.ddl-auto` | `create` | Recreació de l'esquema a l'arrencada. |
| `spring.jpa.show-sql` | `true` | Visualització del SQL generat. |
| `spring.jpa.properties.hibernate.format_sql` | `true` | Format llegible del SQL d'Hibernate. |
| `spring.jpa.defer-datasource-initialization` | `true` | Execució dels scripts després de la inicialització de JPA. |
| `spring.h2.console.enabled` | `true` | Activació de la consola web H2. |
| `spring.h2.console.path` | `/h2-console` | Camí de la consola web. |

Els comentaris expliquen les alternatives de `ddl-auto`: `none`, `validate`, `update`, `create` i `create-drop`. En ampliar els apunts, convé precisar que `validate` comprova la correspondència de l'esquema amb les entitats, més enllà de l'existència de les taules.

La lectura és coherent amb l'esquema de demostració del projecte anterior. Spring Boot pot deduir el driver a partir de la URL; declarar-lo explícitament facilita identificar-lo a classe. La consola pressuposa una aplicació web servlet amb H2 al classpath. Referència de contrast: [bases de dades SQL de Spring Boot 3.5](https://docs.spring.io/spring-boot/3.5/reference/data/sql.html).

## Dades i correspondència amb el model

Cada inserció aporta `title`, `author`, `isbn`, `published_date` i `genre`. Corresponen a l'entitat [[../../materials-anteriors/0613-desenvolupament-web-entorn-servidor/UT2/unitat_2_classe2526/src/main/java/cat/paucasesnoves/unitat_2/domain/entity/Book.java|`Book` del projecte històric]]: l'identificador es genera amb `IDENTITY`, `publishedDate` es correspon amb `published_date` amb la convenció de noms del projecte i `genre` s'emmagatzema com a text. El script omet correctament `id` i el camp `currentPrice`, que és `@Transient`.

| Gènere | Llibres |
|---|---:|
| `SCIENCE` | 8 |
| `NOVEL` | 6 |
| `POETRY` | 2 |
| `HISTORY` | 3 |
| `ESSAY` | 2 |
| **Total** | **21** |

Hi ha **14 autors diferents**, amb diversos llibres de Robert C. Martin, Craig Walls, Gabriel García Márquez, J.K. Rowling i Yuval Noah Harari. Això permet preparar exemples de filtratge, recompte i paginació amb dades conegudes.

**Comprovacions estàtiques efectuades:** s'han analitzat les 21 insercions; els ISBN tenen 13 dígits i són únics; tampoc hi ha duplicats del parell títol–autor. Títols i autors respecten els límits de 150 i 100 caràcters del model, les dates tenen format `AAAA-MM-DD` i els cinc gèneres existeixen a `Genre`. L'apòstrof de `Philosopher''s Stone` està escapat dins el literal SQL. No s'ha verificat l'exactitud bibliogràfica ni el dígit de control dels ISBN.

Les dates van de `0180-01-01` a `2018-09-15`. `0500-01-01` és un any positiu: no representa l'any 500 abans de Crist. Cal tractar aquest conjunt com a dades de demostració, sense deduir-ne informació històrica.

## Comparació amb el code along anterior

**Identificació addicional, 2026-10-09:** `application.properties` i `data.sql` són còpies exactes, byte a byte, dels recursos de [[code-along-ut2-ordinador-profe|`unitat_2_ordinador_profe/unitat2/unitat2/`]]. La fitxa d'aquest projecte recull la comparació de contingut i els SHA-256 coincidents. Aquesta correspondència identifica un projecte complet que conté el suport separat, sense confirmar-ne la cronologia ni canviar la referència dels apunts.

- Les 12 claus de configuració són les mateixes. L'únic valor diferent és `spring.application.name`: `unitat2` aquí i `unitat_2` al projecte. Hi ha també diferències de línies buides.
- El SQL conté exactament els mateixos registres i comentaris que el `data.sql` històric després de normalitzar els salts de línia i la caixa de `BOOK`/`book` a les insercions.
- El conjunt inicialitza només llibres; no aporta dades de professors, cursos, estudiants ni matrícules.

La [[code-along-ut2-2526|fitxa del code along]] conserva l'inventari del projecte complet; aquesta fitxa documenta els dos suports aportats separadament.

## Ús docent i punts de revisió

**Reutilització al projecte actual, 2026-10-09:** la [[code-along-ut2-2627|proposta `unitat2_2627`]] conserva les 21 insercions de `data.sql`, amb salts LF, i refà els comentaris de configuració en UTF-8. El nom de l'aplicació passa a `unitat2_2627`; la URL afegeix `DB_CLOSE_ON_EXIT=FALSE`, es fixa `spring.sql.init.encoding=UTF-8` i es desactiva OSIV amb `spring.jpa.open-in-view=false`. Manté H2 en memòria, `create` i inicialització després de JPA. La càrrega dels 21 llibres s'ha comprovat en l'arrencada de la proposta, per prova automatitzada i HTTP real; els dos fitxers originals continuen intactes.

**Funcionament concretat per David Pons, 2026-10-09:** el projecte ha de carregar les dades de `data.sql` durant l'arrencada. Els [[../../materials/0613-desenvolupament-web-entorn-servidor/UT2/acces-a-dades-amb-spring.md|apunts vigents]], apartat 4, ja desenvolupen les 12 propietats de la font, les cinc opcions de `ddl-auto` i la seqüència Hibernate crea l'esquema → Spring Boot executa les insercions. L'explicació fixa H2 en memòria, `ddl-auto=create` i `defer-datasource-initialization=true` per tornar al conjunt inicial a cada arrencada; no implica que el projecte complet d'enguany s'hagi aportat o provat. La ubicació dels recursos, les consultes de recompte i la comprovació de reinici són pautes de preparació i comprovació, no activitats impartides.

**Proposta de preparació:** situar els fitxers a `src/main/resources/` del projecte de referència i explicar la seqüència dependències → connexió → entitat → esquema → dades. En aquesta configuració, `defer-datasource-initialization=true` permet que Hibernate creï la taula abans de carregar `data.sql`; per a H2 en memòria, la inicialització SQL s'activa per defecte, sense necessitat d'afegir `spring.sql.init.mode=always`. Contrast: [inicialització de bases de dades de Spring Boot 3.5](https://docs.spring.io/spring-boot/3.5/how-to/data-initialization.html).

Com a comprovació proposada del code along, es pot obrir `http://localhost:8080/h2-console` si es manté el port 8080, connectar-se a `jdbc:h2:mem:testdb` amb `sa` i contrasenya buida, i executar:

```sql
SELECT COUNT(*) FROM BOOK;
SELECT genre, COUNT(*) FROM BOOK GROUP BY genre ORDER BY genre;
```

Els resultats esperats de la lectura del script són 21 llibres i els recomptes de la taula anterior; **no són resultats d'una execució verificada**. H2 en memòria no conserva dades entre execucions; la càrrega inicial les torna a proporcionar. Referència: [bases de dades en memòria d'H2](https://h2database.com/html/features.html#in_memory_databases).

**Revisió editorial pendent:** llegir el `.properties` amb la codificació adequada i preparar qualsevol còpia futura en UTF-8. A més dels accents, hi ha signes `?` literals als comentaris, com `l?SQL`; un canvi de codificació no els restaura. Es conserven els originals sense modificar-los.

**Traçabilitat docent orientativa:** dependències i connexió donen suport a `RA6.a` i `RA6.b`; dades inicials i consultes proposades, a `RA6.c` i `RA6.e`; la documentació i una futura comprovació registrada, a `RA6.g`. Fonts curriculars: `raw/sources/2526_Servidor-master/00_general/RA.md`, apartat RA6, i `raw/sources/IFC33C - Desenvolupament web en entorn servidor.xlsx`, full `UT2`. Els fitxers no acrediten per si sols publicació web (`RA6.d`) ni actualització/eliminació (`RA6.f`).

S'han examinat els originals i contrastat la configuració amb documentació oficial. No s'ha compilat ni arrencat el projecte; no s'ha localitzat `java` al `PATH`. La [[../moduls/0613-desenvolupament-web-entorn-servidor/unitats/ut2-acces-dades|fitxa d'UT2]] i el [[../../materials/0613-desenvolupament-web-entorn-servidor/UT2/README.md|README dels materials vigents]] enllacen aquesta font per a futures revisions dels apunts.

**Contrast tècnic de l'ampliació de l'apartat 4:** [catàleg de propietats de Spring Boot 3.5](https://docs.spring.io/spring-boot/3.5/appendix/application-properties/index.html), [guia d'Hibernate ORM 6.6](https://docs.hibernate.org/orm/6.6/userguide/html_single/), [inicialització SQL en Spring Boot 4.1](https://docs.spring.io/spring-boot/4.1/how-to/data-initialization.html) i [consola H2 en Spring Boot 4.1](https://docs.spring.io/spring-boot/reference/data/sql.html#data.sql.h2-web-console). Es distingeix la generació d'esquema de l'execució de scripts i s'explica `spring.sql.init.mode` com a propietat addicional, absent de la font. La consola de Spring Boot 4 requereix el mòdul `org.springframework.boot:spring-boot-h2console`; els apunts n'inclouen el fragment Maven condicional. És una adaptació per versió, no un component observat al projecte històric amb Spring Boot 3.5.7.
