package cat.paucasesnoves.unitat2.domain.entity;

import cat.paucasesnoves.unitat2.domain.enums.Genre;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity

/*
    Exemple de restricció única composta: combina title + author.
    Quan és composta es posa aquí, quan és un sol atribut, millor sobre ell.
 */

@Table(name = "BOOK", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"title", "author"})
})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Columna simple amb restriccions bàsiques
    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, length = 100)
    private String author;


    // Exemple de camp amb unicitat individual. Millor aquí
    // ISBN ha de ser únic per definició
    @Column(nullable = false, unique = true, length = 13)
    private String isbn;

    // Data de publicació
    /**
        No és obligatori posar @Column a tots els camps.
            - Si no hi poses res, Hibernate genera la columna amb el nom del camp (published_date) i un tipus per defecte segons el tipus Java:
                - String → VARCHAR
                - int → INTEGER
                - LocalDate → DATE
                - LocalDateTime → TIMESTAMP
        Es posa @Column quan vols afegir restriccions o personalitzar:
            - Canviar el nom de la columna (@Column(name = "pub_date")).
            - Definir longitud (@Column(length = 100)).
            - Definir unicitat o nullabilitat (@Column(nullable = false, unique = true)).
        En el nostre cas, publishedDate no té requisits especials: n’hi ha prou amb deixar que Hibernate el generi sol.
    **/
    private LocalDate publishedDate;

    // Enumeració guardada com a text (no com a número)
    @Enumerated(EnumType.STRING)
    private Genre genre;

    // Exemple de camp que NO volem persistir
    /**
     * @Transient indica que aquest camp no es desa a la base de dades.
     * És útil quan:
     * - El camp només té sentit dins la lògica de l'aplicació (ex: flags temporals).
     * - Volem un camp "calculat" que no té columna a la taula (ex: edat a partir d'una data de naixement).
     * - Guardar-lo seria redundant o trencaria la normalització de dades.
     *
     * En aquest exemple, "currentPrice" podria ser una informació de negoci calculada
     * que no cal guardar a la taula BOOK.
     * BookService el simula amb Random; no es consulta cap servei extern.
     */
    @Transient
    private BigDecimal currentPrice;

    // Constructors
    public Book() {}
    public Book(String title, String author, String isbn, LocalDate publishedDate, Genre genre) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishedDate = publishedDate;
        this.genre = genre;
        this.currentPrice = BigDecimal.ZERO;
    }

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public LocalDate getPublishedDate() { return publishedDate; }
    public void setPublishedDate(LocalDate publishedDate) { this.publishedDate = publishedDate; }

    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }

    public BigDecimal getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(BigDecimal currentPrice) { this.currentPrice = currentPrice; }
}
