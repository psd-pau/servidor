---
lang: ca-ES
author: David Pons
title: "UT2 — Accés a dades amb Spring"
date: 2026-10-08
date_modified: 2026-10-09
status: "Esborrany inicial"
---

# UT2 — Accés a dades amb Spring

**CIFP Pau Casesnoves · IFC33C · Curs 2026–2027**  
**Mòdul:** 0613. Desenvolupament web en entorn servidor  
**Professor:** David Pons

## Presentació

En aquesta unitat introduirem **JPA i Spring Data JPA** per treballar amb dades d'una base de dades relacional des de la nostra aplicació Java. Començarem per entendre el problema que resol el mapatge entre objectes i taules, i després incorporarem les dependències necessàries al projecte Spring Boot.

A la UT1 hem vist com una petició arriba a un controlador i com aquest col·labora amb un servei. Ara afegim una peça nova: la base de dades. Una aplicació pot recuperar informació, modificar-la i conservar-la entre peticions. Per fer-ho, ha d'establir una connexió, representar les dades en objectes Java i decidir quines operacions executa en cada cas.

Seguirem dos fils d'exemple. Amb els **llibres** estudiarem les entitats, les restriccions, els repositoris, les consultes i les operacions de creació, lectura, actualització i eliminació. Amb **professors, cursos i estudiants** estudiarem com es representen les relacions entre taules. Els fragments són una selecció didàctica del code along del curs anterior; els punts que necessiten una adaptació s'indiquen explícitament. Quan un fragment omet imports, constructors o altres mètodes, cal conservar-los al projecte Java.

El resultat d'aprenentatge principal és **RA6: desenvolupar aplicacions web amb accés a bases de dades**. Al llarg de la unitat hem de poder justificar la tecnologia d'accés, configurar una connexió, recuperar i modificar dades, publicar-les amb criteri i comprovar el comportament amb proves. Aquest document introdueix els conceptes; les activitats i les evidències d'avaluació es concretaran per separat.

## Índex

1. JPA i Spring Data JPA: per què ens és útil un ORM?
2. Primera passa: incorporar les dependències de persistència.
3. El recorregut d'una dada: HTTP, servei, repositori i base de dades.
4. Connexió a H2 i inicialització de dades.
5. Entitats JPA: el llibre com a primera taula.
6. `JpaRepository` i les operacions bàsiques.
7. Consultes derivades, JPQL i paginació.
8. Relacions entre entitats: professors, cursos i estudiants.
9. Publicació de dades, absències i proves.
10. Mapa del code along i punts de comprovació.

## 1. JPA i Spring Data JPA: per què ens és útil un ORM?

### 1.1. Què és JPA?

**JPA** és l'estàndard que defineix com gestionar la persistència d'objectes Java en una base de dades relacional. El nom prové de *Java Persistence API*; actualment l'especificació s'anomena **Jakarta Persistence**, tot i que continuam emprant habitualment les sigles JPA. Les anotacions que farem servir pertanyen al paquet `jakarta.persistence`.

En aquest context, **persistir** significa desar les dades dels objectes a la base de dades per poder-les recuperar quan les necessitam, sense dependre de conservar el mateix objecte Java en memòria.

JPA defineix com indicar que una classe representa dades persistents, com identificar-ne els objectes i com descriure la correspondència amb les taules. També estableix operacions per desar, recuperar i eliminar entitats. Per executar aquestes operacions necessitam una implementació de l'especificació; en la nostra aplicació aquesta feina la farà **Hibernate**.

Abans d'estudiar les anotacions, convé entendre per què necessitam aquesta correspondència entre objectes i dades relacionals.

### 1.2. El problema: programam amb objectes i desam dades en taules

En Java representarem un llibre amb un objecte `Book`, que té atributs com `title`, `author`, `isbn` i `publishedDate`. En una base de dades relacional, aquest mateix llibre es representa amb una fila de la taula `BOOK`, formada per columnes.

| A l'aplicació Java | A la base de dades de l'exemple |
|---|---|
| Classe `Book` | Taula `BOOK` |
| Un objecte `Book` | Una fila de `BOOK` |
| Atribut `title` de tipus `String` | Columna `title` de tipus text |
| Atribut `publishedDate` de tipus `LocalDate` | Columna `published_date` de tipus `DATE` |
| Atribut `id` | Clau primària de la fila |

Podríem fer aquesta feina directament amb **JDBC**, l'API de Java per accedir a bases de dades. Per recuperar un llibre hauríem de preparar una consulta SQL, indicar-ne els paràmetres, executar-la, llegir les columnes del resultat i construir l'objecte `Book`. Per desar-lo, hauríem de fer el camí invers: extreure'n els atributs i passar-los a un `INSERT` o un `UPDATE`.

Aquest procediment és vàlid, però una part considerable del codi es repeteix per a cada entitat i cada operació. A més, hem de mantenir manualment la correspondència entre noms, tipus i valors. Si afegim una propietat al model, també hem de revisar el codi que la desa i la recupera.

### 1.3. La solució: mapatge objecte-relacional

Un **ORM** (*Object-Relational Mapping*, mapatge objecte-relacional) permet descriure aquesta correspondència perquè una eina gestioni bona part de la conversió entre objectes i files. En lloc de repetir tota la lectura i escriptura de columnes a cada operació, definim el mapatge de les entitats i treballam amb aquestes entitats des de Java.

En el cas dels llibres, indicarem que `Book` és una entitat, que es correspon amb `BOOK` i que `id` n'és l'identificador. A partir d'aquesta informació, Hibernate podrà generar les operacions SQL necessàries per recuperar o desar llibres, dins el context de persistència i les transaccions corresponents.

Això ens és útil perquè:

- **Reduïm codi repetitiu:** delegam la conversió habitual entre atributs i columnes.
- **Treballam amb el model del problema:** el servei pot coordinar operacions amb llibres, cursos o estudiants com a objectes Java.
- **Definim el mapatge en un lloc identificable:** les entitats descriuen identificadors, columnes i relacions.
- **Facilitam el manteniment:** moltes operacions bàsiques es poden reutilitzar sense escriure una consulta SQL diferent per a cada cas.

L'ORM continua executant SQL i la base de dades continua aplicant les seves restriccions. Hem d'entendre les taules, les claus i les relacions per definir un mapatge correcte. També hem de decidir quines dades necessita cada consulta: recuperar totes les files i filtrar-les després al servei pot ser ineficient. Per això observarem l'SQL generat durant el code along i aprendrem a formular consultes específiques quan calgui.

### 1.4. Com encaixen JPA, Hibernate i Spring Data JPA?

Les tres peces col·laboren, però cadascuna té una funció:

| Peça | Funció |
|---|---|
| **JPA / Jakarta Persistence** | Especifica les regles, anotacions i operacions de persistència. |
| **Hibernate** | Implementa JPA i gestiona el mapatge i l'execució de les operacions SQL. |
| **Spring Data JPA** | Facilita la creació de repositoris basats en JPA i aporta operacions comunes i mecanismes de consulta. |
| **H2** | És el motor de base de dades que executa el SQL i emmagatzema les files de l'exemple. |

![Diagrama de les peces de persistència: aplicació Spring Boot, Spring Data JPA, JPA, Hibernate, JDBC i base de dades relacional.](spring-jpa-diagram.png)

*Figura 1. Relació entre les peces de persistència d'una aplicació Spring Boot.*

El diagrama situa **Spring Data JPA** com a suport per als repositoris, **JPA** com a API estàndard i **Hibernate** com a implementació que fa el mapatge. **JDBC** permet executar les operacions SQL mitjançant el driver de la base de dades. El cilindre inferior, **RDBMS** (*Relational Database Management System*), representa el sistema gestor de bases de dades relacionals; en el nostre exemple és **H2**.

La imatge empra el nom històric *Java Persistence API* per a JPA; als nostres imports l'identificarem com `jakarta.persistence`. Les franges ajuden a distingir responsabilitats: JPA defineix l'estàndard que Hibernate implementa. Les fletxes des de l'aplicació també mostren que es pot emprar l'API JPA directament; nosaltres treballarem amb els repositoris de Spring Data JPA.

Quan parlam de «Spring JPA» en aquest context, ens referim a **Spring Data JPA**. Aquesta biblioteca permet declarar una interfície de repositori per als llibres; Spring en crea una implementació amb operacions com cercar-los o desar-los, recolzant-se en JPA i Hibernate. Més endavant veurem com es declara aquesta interfície amb `JpaRepository`.

Spring Boot facilita la integració i la configuració d'aquestes peces. El nostre codi definirà les entitats, els repositoris i els casos d'ús; les biblioteques resoldran bona part del mecanisme de persistència. Per disposar-ne al projecte, la primera passa és incorporar les dependències.

## 2. Primera passa: incorporar les dependències de persistència

### 2.1. Quines dependències afegim respecte de la UT1?

Una **dependència** és una biblioteca que el projecte necessita per compilar o executar una funcionalitat. En el projecte Maven les declaram al fitxer **`pom.xml`**, dins l'element `<dependencies>`. Maven les descarrega i també resol les biblioteques de les quals depenen.

Per començar a treballar amb persistència afegirem dues dependències: **Spring Data JPA** i **H2 Database**. Si cream el projecte amb Spring Initializr, podem seleccionar-les amb aquests noms. Si partim d'un projecte existent, afegim els blocs següents dins el seu `<dependencies>`, al costat de les dependències que ja té:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

### 2.2. Què aporta `spring-boot-starter-data-jpa`?

Un **starter** de Spring Boot agrupa les dependències habituals per a una funcionalitat. Aquest starter incorpora **Spring Data JPA**, **Hibernate** i el suport de **Spring ORM** per integrar la persistència amb Spring. També aporta, de manera transitiva, l'API de Jakarta Persistence i el suport JDBC necessari.

Una **dependència transitiva** és una biblioteca que Maven incorpora perquè una altra dependència la necessita. Per això, en aquest projecte, no hem d'afegir manualment una dependència independent per a cada peça del conjunt. Amb el starter disposarem de les anotacions de `jakarta.persistence` i de les interfícies de repositori de Spring Data JPA.

El starter aporta les eines de persistència, però encara ens falta el motor de base de dades amb què treballaran.

### 2.3. Què aporta `h2` i què significa `runtime`?

**H2** és una base de dades relacional escrita en Java que pot executar-se dins el mateix procés de l'aplicació. La farem servir en memòria per centrar-nos en les entitats i els repositoris sense haver d'instal·lar i administrar un servidor de base de dades separat.

La dependència `com.h2database:h2` inclou el **motor H2** i el seu **driver JDBC**, que permet establir la connexió amb aquesta base de dades. Hibernate fa servir JDBC per executar-hi les operacions SQL.

`<scope>runtime</scope>` indica que aquesta biblioteca és necessària durant l'execució i les proves, però no s'inclou al classpath de compilació del codi principal. Les nostres classes treballaran amb les API de JPA i Spring Data, sense importar classes pròpies d'H2; el motor i el driver s'utilitzaran quan l'aplicació s'executi.

L'ús d'H2 en memòria és una decisió de configuració de l'exemple: les dades desapareixen quan acaba el procés. JPA també permet treballar amb bases de dades que conserven la informació en disc; el mapatge objecte-relacional i la durada de les dades són qüestions diferents.

### 2.4. Què hem de comprovar després d'editar el POM?

En un projecte que usa el parent de Spring Boot, aquest gestiona les versions de les dependències anteriors. Per això els fragments no inclouen `<version>`: empram les versions coordinades per la versió de Spring Boot del projecte.

Després de desar el `pom.xml`, **recarregam o sincronitzam el projecte Maven a l'IDE** perquè descarregui i reconegui les dependències. Comprovam que apareixen les biblioteques de Spring Data JPA, Hibernate, Jakarta Persistence i H2, i que el POM no presenta errors de resolució.

Les dependències web de la UT1 continuen donant suport als controladors i a les peticions HTTP. Les novetats de persistència són el starter JPA i H2. Afegir-les posa les biblioteques a disposició del projecte; a continuació haurem de configurar la connexió i definir les entitats que s'hi desaran.

**Aturada del code along:** abans de configurar la base de dades, identificam les dues dependències afegides i explicam què aporta cadascuna. Hem de poder justificar per què necessitam tant les eines de persistència com el motor H2, i distingir una dependència declarada al POM d'una propietat de configuració a `application.properties`.

## 3. El recorregut d'una dada

Si el navegador demana un llibre, el controlador interpreta la petició HTTP, el servei decideix què necessita i el repositori consulta la base de dades. La resposta recorre el camí invers. En aquest exemple, l'aplicació envia dades JSON; no genera una pàgina HTML.

```text
GET /books/1
    -> BookController.getById(1)
    -> BookService.getById(1)
    -> BookRepository.findById(1)
    -> base de dades H2
    -> Book -> resposta HTTP
```

| Peça | Responsabilitat |
|---|---|
| Entitat (`Book`) | Representa una dada que JPA pot desar en una taula. |
| Repositori (`BookRepository`) | Defineix les operacions de persistència i les consultes. |
| Servei (`BookService`) | Coordina el cas d'ús i les decisions de l'aplicació. |
| Controlador (`BookController`) | Rep la petició, delega i prepara la resposta web. |
| Base de dades H2 | Emmagatzema les files mentre l'aplicació està en marxa. |

Aquest recorregut situa les peces que acabam d'introduir: el repositori es recolza en Spring Data JPA i Hibernate per accedir a H2. El servei continua decidint què necessita el cas d'ús i el controlador prepara la resposta HTTP.

## 4. Connexió a H2 i inicialització de dades

### 4.1. Com funcionarà la base de dades del projecte?

**Cada vegada que arrenquem l'aplicació, es crearà l'esquema a H2 i es carregaran les dades de demostració de `data.sql`.** Així tindrem llibres disponibles per començar a practicar consultes sense haver d'introduir-los primer mitjançant peticions HTTP.

La càrrega es fa durant l'arrencada, no cada vegada que arriba una petició web.

Per aconseguir aquest funcionament, situarem dos fitxers a **`src/main/resources/`**:

| Fitxer | Responsabilitat |
|---|---|
| `application.properties` | Configura la connexió, la creació de l'esquema, l'ordre d'inicialització i les eines d'inspecció. |
| `data.sql` | Conté les instruccions SQL que carreguen les dades inicials. |

Les dependències del POM aporten les biblioteques; aquests fitxers determinen com les usam i amb quines dades començam. Hibernate crearà les taules a partir de les entitats Java. Per tant, perquè es pugui carregar `BOOK`, l'entitat `Book` haurà d'estar definida al projecte, encara que n'estudiem les anotacions a l'apartat següent.

### 4.2. La configuració completa

El contingut següent recull totes les propietats de la configuració d'exemple. Les línies que comencen amb `#` són comentaris i no s'executen:

```properties
spring.application.name=unitat2

# Connexió a la base de dades H2 en memòria
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# Hibernate: dialecte i creació de l'esquema
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create

# Visualització del SQL generat per Hibernate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Executar data.sql després de la inicialització de JPA
spring.jpa.defer-datasource-initialization=true

# Consola web per inspeccionar H2
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

### 4.3. Nom de l'aplicació i connexió

**`spring.application.name=unitat2`** identifica l'aplicació Spring Boot. Aquest nom és independent del nom de la base de dades, que en l'exemple és `testdb`.

Les propietats **`spring.datasource.*`** configuren la font de connexions amb què l'aplicació accedeix a la base de dades:

| Propietat | Què indica el valor de l'exemple? |
|---|---|
| `spring.datasource.url` | `jdbc:h2:mem:testdb` és una URL JDBC d'H2; `mem` selecciona el mode en memòria i `testdb` és el nom de la base de dades. |
| `spring.datasource.driver-class-name` | `org.h2.Driver` és el driver JDBC que aporta la dependència H2. Spring Boot també pot deduir-lo a partir de la URL; aquí el feim explícit. |
| `spring.datasource.username` | `sa` és l'usuari amb què ens connectam a la base de dades d'exemple. |
| `spring.datasource.password` | El valor és buit: la connexió de demostració no requereix contrasenya. |

H2 també pot emmagatzemar dades en fitxers, però aquí empram **H2 en memòria**. En aturar el procés, les dades d'aquesta instància desapareixen. Els llibres afegits o modificats durant l'execució estaran disponibles per a les peticions següents mentre l'aplicació continuï en marxa; quan la tornem a arrencar, començarem amb les dades inicials del script.

### 4.4. Dialecte i creació de l'esquema

**`spring.jpa.database-platform=org.hibernate.dialect.H2Dialect`** indica a Hibernate quin dialecte SQL ha d'emprar. Un dialecte adapta el SQL a les característiques del gestor de base de dades. Aquí seleccionam el d'H2; Hibernate també pot detectar-lo a partir de la connexió.

**`spring.jpa.hibernate.ddl-auto`** determina què fa Hibernate amb l'**esquema**, és a dir, l'estructura de taules, columnes i restriccions descrita per les entitats.

| Valor | Comportament |
|---|---|
| `none` | No crea, modifica ni valida l'esquema automàticament. Les taules s'han de preparar per un altre mecanisme. |
| `validate` | Comprova que l'esquema existent és compatible amb el mapatge de les entitats. Si no ho és, l'arrencada falla; no crea ni corregeix les taules. |
| `update` | Intenta adaptar l'esquema existent al mapatge, per exemple creant taules o afegint columnes. No recrea totes les taules a cada arrencada. |
| `create` | Elimina les taules del mapatge que ja existeixin i les torna a crear a l'arrencada. Les dades anteriors d'aquestes taules es perden. |
| `create-drop` | Recrea l'esquema a l'arrencada i també l'elimina quan es tanca la gestió de persistència de l'aplicació. |

**En aquest projecte triam `create`** per partir d'un esquema nou a cada arrencada i tornar-lo a omplir amb les dades de demostració. Aquesta propietat crea l'estructura; la càrrega dels llibres la farà Spring Boot amb `data.sql`. Són dues tasques diferents.

Canviar `create` per `update` no faria que H2 en memòria conservàs dades després d'aturar el procés. Tampoc faria que `data.sql` es carregàs una única vegada: la inicialització del script té la seva pròpia configuració. Si empràssim una base persistent amb `update` i repetíssim les mateixes insercions a cada arrencada, podríem provocar errors per duplicats. Per al nostre exemple mantenim la combinació H2 en memòria, `create` i càrrega inicial.

### 4.5. Veure l'SQL que genera Hibernate

Aquestes dues propietats faciliten relacionar una operació Java amb el SQL que executa Hibernate:

| Propietat | Opcions i efecte |
|---|---|
| `spring.jpa.show-sql` | Amb `true`, mostra el SQL generat per Hibernate a la consola; amb `false`, desactiva aquesta sortida. |
| `spring.jpa.properties.hibernate.format_sql` | Amb `true`, presenta aquest SQL amb salts de línia i sagnat; amb `false`, no aplica aquest format. |

El prefix `spring.jpa.properties.` permet passar propietats pròpies d'Hibernate a la seva configuració. El format modifica com es mostra el SQL, sense canviar el resultat de les operacions. Aquestes opcions mostren el SQL d'Hibernate; no hem de confondre aquesta sortida amb el contingut de `data.sql`, que executa l'inicialitzador de Spring Boot.

### 4.6. Carregar `data.sql` després de crear les taules

El fitxer **`data.sql`** conté **21 instruccions `INSERT INTO BOOK (...)`**. Cada inserció aporta el títol, l'autor, l'ISBN, la data de publicació i el gènere d'un llibre. Per exemple:

```sql
INSERT INTO BOOK (title, author, isbn, published_date, genre)
VALUES ('Clean Code', 'Robert C. Martin', '9780132350884', '2008-08-01', 'SCIENCE');
```

El script carrega files, però **no crea la taula `BOOK`**. La taula ha d'existir abans que es puguin executar les insercions.

Per defecte, Spring Boot intenta inicialitzar els scripts SQL abans de la inicialització de JPA. En el nostre cas, això podria provocar un error perquè Hibernate encara no hauria creat la taula. **`spring.jpa.defer-datasource-initialization=true`** canvia aquest ordre: ajorna la inicialització SQL fins que JPA s'ha inicialitzat i Hibernate ha creat l'esquema. Amb `false`, es manté l'ordre anterior.

La seqüència prevista d'una arrencada correcta és:

```text
Arrencada de Spring Boot
    -> connexió a H2 en memòria
    -> inicialització de JPA i creació de les taules amb Hibernate
    -> execució de data.sql per Spring Boot
    -> 21 llibres inicials disponibles per consultar
```

Spring Boot cerca `data.sql` al classpath; en un projecte Maven, el situam a `src/main/resources/data.sql` perquè s'hi incorpori. La consola H2 no és qui carrega el fitxer: quan hi entram després d'una arrencada correcta, les insercions ja s'han executat.

La càrrega de scripts també es pot controlar amb **`spring.sql.init.mode`**:

| Valor | Quan s'executen els scripts d'inicialització? |
|---|---|
| `embedded` | En bases de dades incrustades en memòria, com la H2 de l'exemple. És el valor per defecte. |
| `always` | També en bases de dades d'altres tipus. |
| `never` | Es desactiva la inicialització mitjançant aquests scripts. |

**No afegim aquesta propietat a la configuració de l'exemple:** amb H2 en memòria el valor per defecte ja permet carregar `data.sql`. `defer-datasource-initialization` n'ordena l'execució; `sql.init.mode` determina si la inicialització SQL s'ha de fer.

Si una inserció conté un error o vulnera una restricció, la inicialització SQL fa fallar l'arrencada per defecte. Llegirem el missatge d'error per comprovar el nom de la taula, les columnes i els valors. Després d'una arrencada correcta esperam trobar els 21 llibres; les dades d'universitat es prepararan més endavant.

### 4.7. Inspeccionar les dades amb la consola H2

**`spring.h2.console.enabled=true`** habilita la consola web H2 quan el projecte disposa del suport necessari. Amb `false`, es desactiva. **`spring.h2.console.path=/h2-console`** fixa el camí d'accés; si el canviam, també canviarà l'adreça que haurem d'obrir al navegador.

Amb el port HTTP 8080, obrirem **`http://localhost:8080/h2-console`** mentre l'aplicació estigui en marxa. Per consultar la mateixa base de dades que usa l'aplicació, introduirem:

| Camp de la consola | Valor |
|---|---|
| Driver Class | `org.h2.Driver` |
| JDBC URL | `jdbc:h2:mem:testdb` |
| User Name | `sa` |
| Password | Buit |

La URL JDBC ha de coincidir amb la de `application.properties`; si indicam un altre nom, no estarem consultant la mateixa base de dades. La consola és una eina d'inspecció per al desenvolupament i el code along.

**Suport de consola segons la versió de Spring Boot:** al projecte de referència amb Spring Boot 3.5, la dependència H2 i el suport web permeten habilitar-la amb aquesta propietat. En un projecte amb **Spring Boot 4**, cal afegir també el mòdul de consola al `<dependencies>` del POM:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-h2console</artifactId>
    <scope>runtime</scope>
</dependency>
```

Aquest mòdul dona suport a la consola web; la persistència continua recolzant-se en el starter JPA i en H2.

### 4.8. Comprovació del funcionament previst

Quan `Book` estigui definida i els dos fitxers de recursos siguin al projecte, comprovarem una arrencada completa i executarem aquestes consultes a la consola H2:

```sql
SELECT COUNT(*) FROM BOOK;
SELECT genre, COUNT(*) FROM BOOK GROUP BY genre ORDER BY genre;
```

El recompte esperat del script és **21 llibres**: 8 de `SCIENCE`, 6 de `NOVEL`, 2 de `POETRY`, 3 de `HISTORY` i 2 de `ESSAY`. Això permet comprovar que les dades s'han carregat, a més de verificar que la connexió funciona.

**Aturada del code along:** distingim la creació de taules de la inserció de dades, identificam quina propietat assegura l'ordre i comprovam els recomptes. Després podem modificar un llibre, aturar el procés i tornar a arrencar: amb aquesta configuració, tornarem al conjunt inicial de `data.sql`. Aquesta comprovació mostra que les dades duren mentre l'aplicació està en marxa i que el script es torna a carregar a cada arrencada.

Ara podem estudiar com les anotacions de `Book` defineixen l'estructura que Hibernate necessita crear abans de carregar aquests llibres.

## 5. Entitats JPA: el llibre com a primera taula

### 5.1. De classe Java a entitat persistent

Una **entitat** és una classe del model que JPA pot gestionar i de la qual pot desar i recuperar dades. Cada llibre es representa amb un objecte `Book` i amb una fila de `BOOK`. El mapatge estableix quins atributs es desen, quin identifica la fila i quines restriccions ha de respectar.

La classe necessita `@Entity`, un identificador i un constructor sense arguments `public` o `protected` perquè JPA pugui crear-ne instàncies en recuperar les dades. També pot tenir altres constructors per facilitar la creació d'objectes des de l'aplicació. L'entitat ha de ser una classe no `final`; els camps persistents tampoc no han de ser `final`.

En aquest exemple, les anotacions són damunt els **camps**. JPA hi accedeix directament, inclosos els camps privats; els getters i setters permeten que la resta del codi treballi amb l'objecte. Aquesta és la modalitat d'accés per camps. Col·locar el mapatge damunt els getters correspon a una altra modalitat, l'accés per propietats; al nostre projecte mantenim el mapatge per camps.

Les anotacions de persistència provenen de `jakarta.persistence`. `Book` combina els atributs següents:

| Atribut Java | Representació en aquest exemple |
|---|---|
| `id` | Clau primària generada per la base de dades. |
| `title`, `author`, `isbn` | Columnes de text amb restriccions. |
| `publishedDate` | Columna de data `published_date`. |
| `genre` | Columna que conté el nom de la constant de l'enumeració. |
| `currentPrice` | Dada temporal de l'objecte, exclosa del mapatge persistent. |

Aquest és el fragment de mapatge de `Book`; el projecte complet conserva els constructors, getters i setters:

```java
@Entity
@Table(name = "BOOK",
       uniqueConstraints = @UniqueConstraint(columnNames = {"title", "author"}))
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, length = 100)
    private String author;

    @Column(nullable = false, length = 13, unique = true)
    private String isbn;

    @Column
    private LocalDate publishedDate;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Transient
    private BigDecimal currentPrice;

    // Constructors, getters i setters omesos.
}
```

### 5.2. Identificar l'entitat, la taula i les restriccions d'unicitat

**`@Entity`** identifica `Book` com una entitat JPA. **`@Table(name = "BOOK")`** especifica el nom de la taula principal amb què es correspon. Són dues decisions diferents: una identifica la classe que JPA gestionarà i l'altra concreta el mapatge amb la base de dades.

`@Table` també permet declarar restriccions que afecten diverses columnes. A `Book`, `uniqueConstraints = @UniqueConstraint(columnNames = {"title", "author"})` exigeix que **la combinació de títol i autor sigui única**. Poden existir dos llibres amb el mateix autor o amb el mateix títol, però no dues files amb tots dos valors iguals. Per això `Clean Code` i `Clean Architecture`, del mateix autor, poden coexistir al conjunt inicial.

En canvi, `@Column(unique = true)` damunt `isbn` imposa la unicitat d'**una sola columna**: dos llibres no poden compartir ISBN. A les altres entitats, `Course.name` també té `unique = true`; els camps `Student.email` i `Teacher.department`, anotats només amb `@Column`, no tenen aquesta restricció al codi de referència.

`columnNames` conté **noms de columnes SQL**, no noms de camps Java. Això és especialment rellevant si el nom del camp i el de la columna són diferents. Les anotacions serveixen per generar aquestes restriccions quan Hibernate crea l'esquema; a l'apartat anterior hem configurat `ddl-auto=create`. Si l'esquema es prepara per un altre mecanisme, ha de conservar les mateixes restriccions.

### 5.3. Clau primària i generació de l'identificador

**`@Id`** indica quin camp és la clau primària. A `Book` és `id`, que identifica cada fila independentment del títol o de l'ISBN. L'anotació identifica la clau, però no determina per si sola com se n'obté el valor.

**`@GeneratedValue(strategy = GenerationType.IDENTITY)`** demana una generació basada en una columna d'identitat de la base de dades. En inserir un llibre nou, H2 genera l'identificador i JPA el recupera. Per això les insercions de `data.sql` no necessiten aportar `id`.

`Course`, `Student` i `Teacher` empren `@GeneratedValue` **sense paràmetres**. Això selecciona `GenerationType.AUTO`: el proveïdor decideix l'estratègia adequada al tipus de clau i a la base de dades. No significa necessàriament `IDENTITY`. La fitxa de consulta recull les altres estratègies.

El codi de `Book` usa `long`, mentre que les altres entitats usen `Long`. El tipus primitiu té `0` com a valor inicial; l'embolcall `Long` pot ser `null`. Cal reconèixer aquesta diferència en llegir els exemples. Un identificador generat identifica una fila; no s'ha d'emprar com un recompte de registres ni pressuposar que tots els valors seran consecutius.

### 5.4. Columnes, tipus i valors permesos

**`@Column`** permet concretar el nom, la longitud i altres característiques d'una columna. No és obligatòria per a cada camp bàsic persistent: sense l'anotació s'aplica el mapatge per defecte. Escriure `@Column` sense paràmetres, com a `publishedDate`, fa explícit aquest mapatge sense modificar-ne les opcions.

En el model de llibres:

- `title` té `nullable = false` i `length = 150`.
- `author` té `nullable = false` i `length = 100`.
- `isbn` té `nullable = false`, `length = 13` i `unique = true`.

`nullable = false` implica que la columna no admet `NULL`. No impedeix per si sol una cadena buida ni comprova que un ISBN sigui bibliogràficament correcte. `length = 13` limita la longitud de la columna de text; no exigeix que tots els ISBN tenguin exactament 13 caràcters. Aquestes restriccions de mapatge s'han de distingir de la validació de les dades que rep l'aplicació.

El tipus Java orienta el tipus de columna que Hibernate genera per al gestor emprat:

| Tipus Java | Representació SQL habitual |
|---|---|
| `String` | `VARCHAR`, amb la longitud del mapatge. |
| `int` / `Integer` | `INTEGER`. |
| `long` / `Long` | `BIGINT`. |
| `LocalDate` | `DATE`. |
| `LocalDateTime` | `TIMESTAMP`. |
| `BigDecimal` | `NUMERIC` o `DECIMAL`, amb precisió i escala. |

La denominació exacta depèn del dialecte i del mapatge. Al nostre projecte, la convenció de noms de Spring Boot transforma `publishedDate` en `published_date`, i `fullName` en `full_name`. Per fixar un nom explícit podem emprar `@Column(name = "...")`.

Els tipus de `java.time`, com `LocalDate`, es mapegen directament. No necessiten `@Temporal`, una anotació pròpia del mapatge dels tipus antics `java.util.Date` i `Calendar`.

### 5.5. Enumeracions i dades sense persistència

**`@Enumerated(EnumType.STRING)`** indica que `genre` es desa amb el nom de la constant: `NOVEL`, `ESSAY`, `POETRY`, `SCIENCE` o `HISTORY`. Això concorda amb els valors del script de dades inicials.

L'altra opció és **`EnumType.ORDINAL`**, que desa la posició numèrica de la constant, començant per zero. Amb l'ordre actual de `Genre`, `NOVEL` correspondria a `0` i `SCIENCE` a `3`. Si reordenàssim les constants, aquests nombres podrien passar a representar un altre gènere. Amb `STRING`, reordenar les constants no canvia el significat dels noms desats; reanomenar-les, en canvi, requereix tenir en compte les dades existents. `@Enumerated` sense valor explícit usa `ORDINAL`.

**`@Transient`** exclou `currentPrice` del mapatge JPA: no es crea una columna per a aquest camp i no se'n desa ni recupera el valor amb la fila. Al codi de referència el servei assigna un preu aleatori quan llegeix llibres; dues lectures poden mostrar preus diferents. És una dada temporal de demostració.

`@Transient` només afecta la persistència. El camp continua existint a l'objecte Java i pot aparèixer a la resposta JSON segons la configuració de serialització. Si en una altra aplicació necessitàssim conservar un preu real a la base de dades, hauríem de mapar-lo com a camp persistent.

### 5.6. Fitxa de consulta: anotacions i paràmetres sense relacions

Aquesta fitxa recull **totes les anotacions i opcions de mapatge sense relacions presents a `Book`, `Course`, `Student` i `Teacher`**. Les opcions que s'indiquen com a **ampliació** són habituals o ajuden a interpretar les estratègies de generació, però no apareixen als exemples del projecte. Els fragments d'ampliació són alternatives didàctiques, no canvis que s'hagin aplicat a les entitats.

#### 5.6.1. Resum de les anotacions emprades

| Anotació | Què fa? | Ús al codi de referència |
|---|---|---|
| `@Entity` | Declara una classe com a entitat JPA. | Les quatre entitats. |
| `@Table` | Configura la taula principal i les seves restriccions. | `BOOK`, `COURSE`, `STUDENT` i `TEACHER`; `Book` també declara `uniqueConstraints`. |
| `@UniqueConstraint` | Declara la unicitat d'una columna o combinació de columnes, dins la configuració de taula. | Parell `title`–`author` de `BOOK`. |
| `@Id` | Identifica el camp de clau primària. | `id` a les quatre entitats; no té paràmetres. |
| `@GeneratedValue` | Configura la generació automàtica de la clau. | `IDENTITY` a `Book`; `AUTO` implícit a les altres tres. |
| `@Column` | Configura una columna d'un camp persistent. | Sense paràmetres o amb `nullable`, `length` i `unique`. |
| `@Enumerated` | Configura com es desa una enumeració. | `EnumType.STRING` a `Book.genre`. |
| `@Transient` | Exclou un camp o propietat del mapatge persistent. | `Book.currentPrice`; no té paràmetres. |

#### 5.6.2. Taules, restriccions i índexs

| Anotació i paràmetre | Funció | Exemple i presència |
|---|---|---|
| `@Table.name` | Nom de la taula. | `name = "BOOK"`; emprat a les quatre entitats. |
| `@Table.uniqueConstraints` | Una o diverses restriccions d'unicitat. | `@UniqueConstraint(columnNames = {"title", "author"})`; emprat a `Book`. |
| `@Table.schema` | Esquema de la base de dades on se situa la taula. | `schema = "PUBLIC"`; ampliació. |
| `@Table.catalog` | Catàleg de la base de dades. La seva interpretació depèn del gestor. | `catalog = "biblioteca"`; ampliació. |
| `@Table.indexes` | Un o diversos índexs per a la taula. | `@Index(name = "idx_book_author", columnList = "author")`; ampliació. |
| `@UniqueConstraint.columnNames` | Noms SQL de les columnes que han de ser úniques conjuntament; és obligatori. | `{"title", "author"}`; emprat a `Book`. |
| `@UniqueConstraint.name` | Nom explícit de la restricció; si s'omet, el proveïdor en tria un. | `name = "uk_book_title_author"`; ampliació. |
| `@Index.columnList` | Columnes SQL de l'índex, en una cadena separada per comes; és obligatori. | `columnList = "author, title"`; ampliació. |
| `@Index.name` | Nom de l'índex; si s'omet, el proveïdor en tria un. | `name = "idx_book_author_title"`; ampliació. |
| `@Index.unique` | Indica si l'índex és únic; per defecte `false`. | `unique = true`; ampliació. |

`uniqueConstraints` i `indexes` admeten una anotació o un conjunt entre claus `{...}`. Un índex ordinari pot facilitar cerques, però no imposa unicitat. Les declaracions de restriccions i índexs intervenen quan es genera l'esquema.

#### 5.6.3. Identificadors i estratègies de generació

| Paràmetre de `@GeneratedValue` | Funció |
|---|---|
| `strategy` | Estratègia de generació. Per defecte és `GenerationType.AUTO`. |
| `generator` | Nom d'un generador declarat amb `@SequenceGenerator` o `@TableGenerator`; ampliació. |

| `GenerationType` | Com es genera l'identificador? | Situació als exemples |
|---|---|---|
| `IDENTITY` | Mitjançant una columna d'identitat de la base de dades, en inserir la fila. | Emprat explícitament a `Book`. |
| `AUTO` | El proveïdor tria una estratègia segons el tipus de clau i les capacitats de la base de dades. | Emprat implícitament a `Course`, `Student` i `Teacher`. |
| `SEQUENCE` | Mitjançant una seqüència de la base de dades, que proporciona valors numèrics. | Ampliació. |
| `TABLE` | Mitjançant una taula auxiliar que gestiona la generació de valors. | Ampliació. |
| `UUID` | El proveïdor genera un identificador UUID per a un camp `UUID` o `String`. Disponible des de Jakarta Persistence 3.1. | Ampliació; no s'aplica al `long id` de `Book`. |

Per a `SEQUENCE` podem configurar **`@SequenceGenerator`** amb `name` (nom que usa `generator`), `sequenceName` (nom SQL de la seqüència), `initialValue` (inici, per defecte `1`) i `allocationSize` (mida d'assignació de valors, per defecte `50`). També admet `schema` i `catalog`. Aquest és un exemple d'ampliació per a un identificador numèric:

```java
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq_gen")
@SequenceGenerator(name = "book_seq_gen", sequenceName = "book_seq", allocationSize = 1)
private Long id;
```

`book_seq_gen` identifica el generador dins JPA; `book_seq` identifica la seqüència SQL. `allocationSize = 1` simplifica l'exemple; la configuració s'ha de coordinar amb la seqüència de la base de dades. `Book` continua emprant `IDENTITY` al projecte de referència.

Per a `TABLE`, **`@TableGenerator`** permet concretar `name`, `table` (taula auxiliar), `pkColumnName` (columna que identifica el generador), `valueColumnName` (columna que guarda el valor) i `pkColumnValue` (clau de la fila del generador). També té `initialValue` (per defecte `0`), `allocationSize` (per defecte `50`), `schema`, `catalog`, `uniqueConstraints` i `indexes`. És una ampliació de consulta; les quatre entitats no declaren generadors propis.

#### 5.6.4. Opcions de `@Column`

| Paràmetre | Funció i valor per defecte | Presència o exemple |
|---|---|---|
| `name` | Nom de columna; si s'omet, parteix del nom del camp o propietat i de les convencions de noms. | `name = "published_date"`; ampliació explícita. |
| `nullable` | Admet `NULL`; per defecte `true`. | `false` a títol, autor, ISBN, nom del curs i noms complets d'estudiant i professor. |
| `unique` | Declara unicitat per a una sola columna; per defecte `false`. | `true` a `Book.isbn` i `Course.name`. |
| `length` | Longitud de columnes de text; per defecte `255`. | `150`, `100` i `13` a `Book`. |
| `precision` | Nombre total de dígits d'una columna decimal; `0` deixa la precisió a la inferència del proveïdor. | `precision = 10`; ampliació per a `BigDecimal`. |
| `scale` | Nombre de dígits decimals; per defecte `0`. | `scale = 2`; ampliació per a `BigDecimal`. |
| `insertable` | Inclou la columna als `INSERT` generats per JPA; per defecte `true`. | `insertable = false`; ampliació. |
| `updatable` | Inclou la columna als `UPDATE` generats per JPA; per defecte `true`. | `updatable = false`; ampliació. |
| `columnDefinition` | Fragment SQL de definició de columna per generar l'esquema; per defecte s'infereix del mapatge. | `columnDefinition = "varchar(500)"`; ampliació que depèn del gestor. |
| `table` | Taula on es troba la columna; per defecte, la taula principal. | `table = "BOOK"`; ampliació, redundant en aquest cas. |

Les opcions es poden combinar en una mateixa anotació. Per exemple, en una entitat que hagués de **persistir un preu**, podríem declarar:

```java
@Column(nullable = false, precision = 10, scale = 2)
private BigDecimal price;
```

En aquest exemple d'ampliació, la columna decimal té deu dígits totals i dos decimals. És un camp persistent diferent del `currentPrice` temporal de `Book`.

`insertable = false` o `updatable = false` limiten el SQL que genera JPA; no impedeixen canviar el valor de l'objecte ni modificar la columna amb SQL directe. Un camp amb `insertable = false, updatable = false` encara es pot recuperar de la base de dades: això el diferencia d'un camp `@Transient`.

Les variants **sense paràmetres** que també hem emprat són `Book.publishedDate`, `Course.credits`, `Student.email` i `Teacher.department`. `@Column` hi aplica els valors per defecte. A `Course.credits`, `int` és un tipus primitiu: l'objecte Java no pot representar-hi `null`, encara que no s'hagi indicat `nullable = false`.

#### 5.6.5. Enumeracions, camps exclosos i ampliacions habituals

| Anotació | Paràmetres o ús | Presència |
|---|---|---|
| `@Enumerated` | `value = EnumType.STRING` o `EnumType.ORDINAL`. Es pot ometre `value =` en escriure l'anotació. | `@Enumerated(EnumType.STRING)` a `Book.genre`. |
| `@Transient` | No té paràmetres. Exclou el camp del mapatge persistent. | `Book.currentPrice`. |
| `@Basic` | Mapatge bàsic, habitualment implícit. `optional` indica si el valor pot ser nul (per defecte `true`); `fetch` pot ser `EAGER` (per defecte) o `LAZY` (una indicació al proveïdor). | Ampliació: `@Basic(optional = false)`. `LAZY` no garanteix una càrrega diferida en totes les configuracions. |
| `@Lob` | No té paràmetres. Mapeja un objecte gran: text (`String`, habitualment `CLOB`) o dades binàries (`byte[]`, habitualment `BLOB`). | Ampliació: `@Lob private String description;`. |
| `@Version` | No té paràmetres. El proveïdor gestiona una versió per detectar conflictes d'actualització concurrent mitjançant bloqueig optimista. | Ampliació: `@Version private Long version;`. |

Les ampliacions no exigeixen afegir aquests camps al model de llibres. Serveixen per reconèixer opcions freqüents quan un altre cas d'ús les necessiti.

### 5.7. Comprovació del mapatge del llibre

**Aturada del code along:** abans d'arrencar, predim quins camps tindran columna i quines restriccions hi haurà. Després inspeccionam `BOOK` a H2: identificador generat, longituds, camps no nuls, unicitat d'ISBN i unicitat conjunta de títol i autor. Comprovam que `genre` conté noms i que `currentPrice` no té columna.

Com a comprovació proposada de les restriccions, podem provar una inserció amb ISBN repetit i una altra amb la mateixa combinació de títol i autor, encara que canviï l'ISBN. En tots dos casos esperam un rebuig de la base de dades per les restriccions corresponents. També hem de poder explicar per què dues files amb el mateix autor i títols diferents sí que són vàlides.

## 6. `JpaRepository` i les operacions bàsiques

El repositori de llibres estén `JpaRepository<Book, Long>`. `Book` és l'entitat gestionada i `Long` és el tipus de l'identificador amb què treballa la interfície del repositori. Spring Data JPA hi aporta, entre altres mètodes, `findAll()`, `findById(id)`, `save(book)`, `deleteById(id)` i `count()`.

```java
public interface BookRepository extends JpaRepository<Book, Long> {
    // Les consultes específiques s'hi afegeixen més endavant.
}
```

`findById(id)` retorna un `Optional<Book>` perquè la fila pot no existir. `save(book)` s'utilitza per desar una entitat nova o els canvis d'una entitat existent; el detall de si l'operació acaba en una inserció o una actualització depèn de l'estat de l'entitat. No hem d'interpretar `save` com una ordre SQL fixa sense mirar el context.

El servei històric separa les operacions web de l'accés a les dades. Aquest és el nucli de l'actualització:

```java
public Book update(Long id, Book book) {
    return repo.findById(id).map(existing -> {
        existing.setTitle(book.getTitle());
        existing.setAuthor(book.getAuthor());
        existing.setIsbn(book.getIsbn());
        existing.setPublishedDate(book.getPublishedDate());
        existing.setGenre(book.getGenre());
        return repo.save(existing);
    }).orElse(null);
}
```

Primer es cerca el llibre existent. Després es modifiquen els camps permesos i es desa. Si no existeix, aquesta **versió històrica** retorna `null`: és un comportament que revisarem abans de prendre'l com a resposta web definitiva. Igualment, el `delete(id)` del servei crida `repo.deleteById(id)`; el codi no especifica una resposta pròpia per a un identificador inexistent.

El controlador històric publica aquestes rutes:

| Petició | Mètode del controlador | Operació |
|---|---|---|
| `GET /books` | `getAll()` | Retorna els llibres. |
| `GET /books/{id}` | `getById(id)` | Cerca un llibre. |
| `POST /books` | `create(book)` | Desa un llibre rebut al cos JSON. |
| `PUT /books/{id}` | `update(id, book)` | Substitueix els camps previstos del llibre trobat. |
| `DELETE /books/{id}` | `delete(id)` | Demana eliminar-lo. |

Una petició `POST` porta un cos semblant a aquest:

```json
{
  "title": "Un llibre de prova",
  "author": "Autoria de prova",
  "isbn": "9780000000001",
  "publishedDate": "2026-01-15",
  "genre": "SCIENCE"
}
```

Aquest exemple il·lustra la forma de les dades. El projecte històric pot rebutjar-lo si l'ISBN ja existeix o si s'incompleix una restricció. La resposta web exacta davant aquests casos s'ha de comprovar, perquè el controlador no hi defineix cap tractament d'errors específic.

**Aturada del code along:** seguim `POST /books` des del JSON fins a `save`, i `GET /books/{id}` des del repositori fins a la resposta. Contrastam el que canvia a la taula després d'un `PUT` o un `DELETE`. Aquesta observació permet explicar `RA6.c`, `RA6.d` i `RA6.f` amb evidències del comportament, sense confondre la ruta HTTP amb el mètode del repositori.

## 7. Consultes derivades, JPQL i paginació

Spring Data JPA pot construir una consulta a partir del nom d'un mètode del repositori. El nom expressa una propietat de l'entitat i una condició:

```java
Book findByIsbn(String isbn);
List<Book> findByAuthor(String author);
List<Book> findByTitleContaining(String keyword);
List<Book> findByGenreOrderByPublishedDateDesc(Genre genre);
```

`findByAuthor` cerca una coincidència d'autor; `findByTitleContaining` cerca títols que contenen un text; el darrer mètode filtra per gènere i ordena per data de publicació descendent. La consulta es formula sobre propietats de `Book`, encara que els noms de les columnes SQL puguin tenir una forma diferent. Per a una cerca sense coincidències, convé pensar quin tipus de resultat espera el cas d'ús: una llista buida és diferent d'un llibre absent.

Quan la consulta no queda prou clara amb el nom, `@Query` permet escriure-la. **JPQL** consulta entitats i atributs Java, no noms de taules i columnes SQL. Aquest fragment és del repositori històric:

```java
@Query("select count(b) from Book b " +
       "where b.author = :author and b.genre = :genre")
long countBooksByAuthorAndGenre(@Param("author") String author,
                                @Param("genre") Genre genre);
```

`Book` és el nom de l'entitat i `b.author` i `b.genre` són els seus atributs. `:author` i `:genre` són paràmetres; `@Param` els associa amb els arguments Java. El projecte també inclou una consulta per any de publicació amb `YEAR(b.publishedDate)`. Les funcions disponibles poden dependre del proveïdor JPA i de la base de dades: si canviam de tecnologia, les hem de tornar a comprovar.

Per limitar resultats, el mateix repositori declara una versió de `findByAuthor` que accepta `Pageable`. El servei en demana els cinc primers:

```java
List<Book> findByAuthor(String author, Pageable pageable);

// Al servei:
repo.findByAuthor(author, PageRequest.of(0, 5));
```

`PageRequest.of(0, 5)` indica la **primera pàgina**, de mida cinc: les pàgines es compten des de zero. Si ens importa quins són «els primers», hem de definir també una ordenació; sense ordre explícit, la selecció de cinc files no equival a una classificació estable. `Pageable` evita haver d'escriure `LIMIT` dins una consulta SQL per a aquest cas.

El repositori històric conté també una consulta SQL nativa amb `author like '%:author%' limit 5`. Aquí `:author` ha quedat dins un literal de text i no s'ha de copiar com si fos una consulta parametritzada correcta. A més, `LIMIT` és sintaxi SQL dependent de la base de dades. Per als nous exemples emprarem la versió amb `Pageable`; si necessitam SQL natiu, formularem i provarem la consulta per separat.

**Límit de la còpia històrica:** aquests mètodes de cerca, recompte i paginació existeixen al repositori i al servei, però `BookController` només exposa les cinc rutes CRUD de l'apartat anterior. No podem provar les consultes específiques enviant una petició HTTP a una ruta que encara no s'ha programat. Les podem observar amb una prova del repositori o afegint més endavant una ruta justificada.

## 8. Relacions entre entitats

El segon fil del projecte representa una universitat petita. Un professor pot impartir diversos cursos; cada curs té una referència al professor. Un curs pot tenir molts estudiants i un estudiant pot estar matriculat a diversos cursos.

```text
TEACHER 1 ---- * COURSE
COURSE  * ---- * STUDENT
                  mitjançant ENROLLMENT
```

Al costat de `Course`, la relació amb `Teacher` és `@ManyToOne` i la clau forana és `teacher_id` a la taula `COURSE`:

```java
@ManyToOne
@JoinColumn(name = "teacher_id")
private Teacher teacher;
```

Al costat de `Teacher`, `@OneToMany(mappedBy = "teacher")` indica que el camp `teacher` de `Course` és qui governa el mapatge de la relació. En el projecte, aquesta relació inclou `cascade = CascadeType.ALL` i `orphanRemoval = true`: són decisions amb efectes en desar o eliminar dades relacionades, i s'han de justificar abans d'aplicar-les a un model real.

La relació entre `Course` i `Student` necessita una taula intermèdia. `Course` és el costat que defineix la taula `ENROLLMENT` i les seves claus foranes; `Student` usa `mappedBy = "students"` per indicar el costat invers:

```java
// A Course:
@ManyToMany
@JoinTable(name = "ENROLLMENT",
    joinColumns = @JoinColumn(name = "course_id"),
    inverseJoinColumns = @JoinColumn(name = "student_id"))
private List<Student> students = new ArrayList<>();

// A Student:
@ManyToMany(mappedBy = "students")
private List<Course> courses = new ArrayList<>();
```

`mappedBy` referencia el **nom del camp Java** de l'altre costat, no el nom d'una taula. La col·lecció és una manera de navegar per la relació des de l'objecte; la persistència de l'enllaç depèn de com es mantengui el costat propietari i de com es desin les entitats. Per això no basta afegir un element a qualsevol llista i donar per fet que la taula intermèdia quedarà actualitzada.

`CourseRepository.findByTeacherFullName(name)` mostra una consulta derivada que travessa una relació: va de `Course.teacher` a `Teacher.fullName`. `UniversityController` exposa `GET /api/university/courses/teacher/name/{name}` i `GET /api/university/courses/{id}/students`, a més de tres rutes `POST` per crear professors, estudiants i cursos. El projecte no carrega dades inicials d'universitat a `data.sql`, de manera que primer cal crear dades coherents o preparar una càrrega de prova per observar les consultes.

**Aturada del code along:** dibuixam les taules i les claus foranes abans d'escriure anotacions. Després identificam quin costat és propietari de cada relació i quina consulta necessita realment el cas d'ús. Si només volem els noms dels estudiants, recuperar un graf complet d'entitats pot ser innecessari.

## 9. Publicació de dades, absències i proves

La ruta `/books` retorna entitats `Book` directament. Com que `Book` no té relacions, serveix per introduir el recorregut entre JPA i JSON. Al model d'universitat, en canvi, les relacions són bidireccionals: un professor referencia cursos, un curs referencia el professor i els estudiants, i cada estudiant referencia cursos. Retornar aquest graf directament pot generar problemes de serialització, carregar més dades de les necessàries o dependre de si una relació lazy encara es pot carregar.

Una resposta web hauria de representar les dades que necessita el client. Un **DTO** és una classe o registre Java creat per a aquesta resposta, independent del mapatge JPA. Per exemple, per mostrar els estudiants d'un curs podria bastar una llista amb identificador i nom. Aquesta és una **proposta de millora** per al curs actual; el projecte històric retorna entitats i no conté aquests DTO.

També hem de decidir què passa quan un identificador no existeix. El servei dels llibres retorna `null` en algunes cerques i actualitzacions, i el d'universitat llança una excepció si no troba el curs. Cap d'aquestes decisions expressa per si sola una resposta HTTP clara per a l'usuari. En elaborar l'aplicació actual caldrà definir i provar, com a mínim, els casos d'èxit, d'identificador absent i de dades que vulneren restriccions.

El projecte anterior només inclou una prova `contextLoads()`, que comprova que el context de Spring pot arrencar. Una prova útil per a aquesta unitat hauria de comprovar també una consulta o una modificació de dades i el resultat esperat. La documentació ha d'explicar la configuració de la base de dades, com executar l'aplicació i quines peticions permeten observar cada comportament. Això dona evidències per a `RA6.g`, que no es poden deduir només de la presència d'una classe de prova.

## 10. Mapa del code along i punts de comprovació

La taula següent ordena els conceptes per estudiar-los; és una **progressió proposada** a partir del projecte de referència, no el registre de les sessions que s'hagin impartit enguany.

| Pas | Classe o fitxer | Pregunta que hem de poder respondre |
|---|---|---|
| 1. Dependències | `pom.xml` | Què aporten el starter JPA i H2, i per què necessitam tots dos? |
| 2. Connexió | `application.properties`, `data.sql` | Com es crea la base de dades i d'on surten les primeres files? |
| 3. Mapatge | `Book`, `Genre` | Quines propietats es desen i quines restriccions tenen? |
| 4. CRUD | `BookRepository`, `BookService`, `BookController` | Quin recorregut segueix una petició de lectura o modificació? |
| 5. Consultes | `BookRepository` | Quan basta un nom de mètode, quan cal JPQL i com limitam resultats? |
| 6. Relacions | `Teacher`, `Course`, `Student` | On és cada clau forana i quin costat gestiona cada relació? |
| 7. Publicació i comprovació | `UniversityController`, proves | Quines dades enviam al client i com demostram que el resultat és correcte? |

Abans de considerar acabat el material caldrà contrastar aquests exemples amb el projecte que s'empri realment a classe el curs 2026–2027, executar les peticions i les proves pertinents, i revisar les decisions de publicació de dades. Aquest esborrany és la base del futur dossier editable i del PDF, encara pendents de preparació.
