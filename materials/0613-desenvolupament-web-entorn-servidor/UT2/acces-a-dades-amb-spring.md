---
lang: ca-ES
author: David Pons
title: "UT2 — Accés a dades amb Spring"
date: 2026-10-08
status: "Esborrany inicial"
---

# UT2 — Accés a dades amb Spring

**CIFP Pau Casesnoves · IFC33C · Curs 2026–2027**  
**Mòdul:** 0613. Desenvolupament web en entorn servidor  
**Professor:** David Pons

## Presentació

A la UT1 hem vist com una petició arriba a un controlador i com aquest col·labora amb un servei. Ara afegim una peça nova: la base de dades. Una aplicació pot recuperar informació, modificar-la i conservar-la entre peticions. Per fer-ho, ha d'establir una connexió, representar les dades en objectes Java i decidir quines operacions executa en cada cas.

Seguirem dos fils d'exemple. Amb els **llibres** estudiarem les entitats, les restriccions, els repositoris, les consultes i les operacions de creació, lectura, actualització i eliminació. Amb **professors, cursos i estudiants** estudiarem com es representen les relacions entre taules. Els fragments són una selecció didàctica del code along del curs anterior; els punts que necessiten una adaptació s'indiquen explícitament. Quan un fragment omet imports, constructors o altres mètodes, cal conservar-los al projecte Java.

El resultat d'aprenentatge principal és **RA6: desenvolupar aplicacions web amb accés a bases de dades**. Al llarg de la unitat hem de poder justificar la tecnologia d'accés, configurar una connexió, recuperar i modificar dades, publicar-les amb criteri i comprovar el comportament amb proves. Aquest document introdueix els conceptes; les activitats i les evidències d'avaluació es concretaran per separat.

## Índex

1. El recorregut d'una dada: HTTP, servei, repositori i base de dades.
2. Connexió a H2 i inicialització de dades.
3. Entitats JPA: el llibre com a primera taula.
4. `JpaRepository` i les operacions bàsiques.
5. Consultes derivades, JPQL i paginació.
6. Relacions entre entitats: professors, cursos i estudiants.
7. Publicació de dades, absències i proves.
8. Mapa del code along i punts de comprovació.

## 1. El recorregut d'una dada

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

**JPA** és una especificació de persistència per a Java. Ens permet descriure com es relacionen classes i taules. **Hibernate** és la implementació que utilitza aquest projecte. **Spring Data JPA** proporciona repositoris i genera moltes operacions a partir de les interfícies que declaram. Cap d'aquestes peces és, per si mateixa, la base de dades: en el code along la base de dades és H2.

Un objecte Java i una fila SQL no són exactament el mateix. JPA fa el mapatge entre tots dos, però hem de decidir la clau primària, les restriccions, les relacions i les dades que realment necessitam consultar. Aquesta distinció ajuda a interpretar l'SQL que Hibernate mostra a la consola.

## 2. Connexió a H2 i inicialització de dades

El projecte de referència incorpora les dependències `spring-boot-starter-data-jpa`, `spring-boot-starter-web` i `h2` al `pom.xml`. El primer starter aporta l'accés a JPA mitjançant Spring Data; el segon permet atendre les peticions web; H2 és la base de dades emprada als exemples.

Aquestes propietats són la part essencial de la configuració històrica:

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
spring.jpa.defer-datasource-initialization=true

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

`jdbc:h2:mem:testdb` identifica una base de dades **en memòria**. És útil per observar el funcionament durant el code along: en aturar el procés desapareixen les dades d'aquesta instància. Amb `ddl-auto=create`, Hibernate torna a crear l'esquema a l'arrencada. Aquesta configuració és adequada per a l'exemple, però destruiria les dades anteriors si s'aplicàs a una base persistent. `show-sql` ajuda a relacionar una operació Java amb les consultes SQL produïdes.

El fitxer `data.sql` del projecte conté 21 instruccions `INSERT INTO book (...)`. Aporta dades inicials per consultar els llibres sense haver de crear-los primer per HTTP. `spring.jpa.defer-datasource-initialization=true` fa que la inicialització SQL es difereixi fins després de la creació de l'esquema per Hibernate. La consola H2 permet inspeccionar les taules a `/h2-console` mentre l'aplicació funciona; les credencials i aquesta consola són configuració de desenvolupament.

**Aturada del code along:** després de configurar la connexió, comprovam què passa a l'arrencada, quines taules es creen i què conté `BOOK`. Després relacionam una fila de `data.sql` amb la representació Java que definirem a continuació. Si l'arrencada falla, llegim el primer error SQL o de configuració abans d'atribuir-lo al controlador.

## 3. Entitats JPA: el llibre com a primera taula

Una entitat és una classe que JPA pot gestionar i persistir. El model històric `Book` comença així:

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

    private LocalDate publishedDate;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Transient
    private BigDecimal currentPrice;

    // Constructors, getters i setters omesos.
}
```

`@Entity` marca la classe com a persistent i `@Table(name = "BOOK")` fixa el nom de la taula. `@Id` identifica la clau primària; `@GeneratedValue` demana que se'n generi el valor. Per a `Book`, l'estratègia és `IDENTITY`: la base de dades genera l'identificador quan s'insereix la fila. `title` i `author` no poden ser nuls i tenen longitud màxima; la parella títol–autor també té una restricció d'unicitat. `isbn` té una restricció d'unicitat individual. Una restricció de base de dades continua existint encara que una petició HTTP no hagi validat prèviament els valors.

`publishedDate` és un `LocalDate`, que es desa com una data. `Genre` és un `enum`; amb `EnumType.STRING`, JPA en desa el nom, com ara `SCIENCE`, en lloc de dependre de la posició de la constant. Si en el futur reordenam les constants, els noms desats continuen tenint el mateix significat. El valor `currentPrice`, en canvi, està marcat amb `@Transient`: **no és una columna persistent**. Al projecte històric el servei li assigna un preu aleatori quan llegeix llibres; per això dues lectures poden mostrar preus diferents. És un exemple de dada derivada o temporal, no un preu comercial estable.

**Aturada del code along:** abans d'arrencar, predim quines propietats tindran columna i quines no. Després inspeccionam l'esquema generat i comprovam com es reflecteixen la clau primària, les restriccions i l'enumeració. La classe necessita un constructor sense arguments perquè JPA pugui crear-ne instàncies; el codi històric també inclou getters i setters.

## 4. `JpaRepository` i les operacions bàsiques

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

## 5. Consultes derivades, JPQL i paginació

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

## 6. Relacions entre entitats

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

## 7. Publicació de dades, absències i proves

La ruta `/books` retorna entitats `Book` directament. Com que `Book` no té relacions, serveix per introduir el recorregut entre JPA i JSON. Al model d'universitat, en canvi, les relacions són bidireccionals: un professor referencia cursos, un curs referencia el professor i els estudiants, i cada estudiant referencia cursos. Retornar aquest graf directament pot generar problemes de serialització, carregar més dades de les necessàries o dependre de si una relació lazy encara es pot carregar.

Una resposta web hauria de representar les dades que necessita el client. Un **DTO** és una classe o registre Java creat per a aquesta resposta, independent del mapatge JPA. Per exemple, per mostrar els estudiants d'un curs podria bastar una llista amb identificador i nom. Aquesta és una **proposta de millora** per al curs actual; el projecte històric retorna entitats i no conté aquests DTO.

També hem de decidir què passa quan un identificador no existeix. El servei dels llibres retorna `null` en algunes cerques i actualitzacions, i el d'universitat llança una excepció si no troba el curs. Cap d'aquestes decisions expressa per si sola una resposta HTTP clara per a l'usuari. En elaborar l'aplicació actual caldrà definir i provar, com a mínim, els casos d'èxit, d'identificador absent i de dades que vulneren restriccions.

El projecte anterior només inclou una prova `contextLoads()`, que comprova que el context de Spring pot arrencar. Una prova útil per a aquesta unitat hauria de comprovar també una consulta o una modificació de dades i el resultat esperat. La documentació ha d'explicar la configuració de la base de dades, com executar l'aplicació i quines peticions permeten observar cada comportament. Això dona evidències per a `RA6.g`, que no es poden deduir només de la presència d'una classe de prova.

## 8. Mapa del code along i punts de comprovació

La taula següent ordena els conceptes per estudiar-los; és una **progressió proposada** a partir del projecte de referència, no el registre de les sessions que s'hagin impartit enguany.

| Pas | Classe o fitxer | Pregunta que hem de poder respondre |
|---|---|---|
| 1. Connexió | `pom.xml`, `application.properties`, `data.sql` | Com es crea la base de dades i d'on surten les primeres files? |
| 2. Mapatge | `Book`, `Genre` | Quines propietats es desen i quines restriccions tenen? |
| 3. CRUD | `BookRepository`, `BookService`, `BookController` | Quin recorregut segueix una petició de lectura o modificació? |
| 4. Consultes | `BookRepository` | Quan basta un nom de mètode, quan cal JPQL i com limitam resultats? |
| 5. Relacions | `Teacher`, `Course`, `Student` | On és cada clau forana i quin costat gestiona cada relació? |
| 6. Publicació i comprovació | `UniversityController`, proves | Quines dades enviam al client i com demostram que el resultat és correcte? |

Abans de considerar acabat el material caldrà contrastar aquests exemples amb el projecte que s'empri realment a classe el curs 2026–2027, executar les peticions i les proves pertinents, i revisar les decisions de publicació de dades. Aquest esborrany és la base del futur dossier editable i del PDF, encara pendents de preparació.
