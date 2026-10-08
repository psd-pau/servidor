package cat.paucasesnoves.unitat_2.domain.entity;

import cat.paucasesnoves.unitat_2.domain.enums.Genre;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "BOOK",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"title", "author"})
        }
        )
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, length = 100)
    private String author;

    //Exemple unicitat individual
    @Column(nullable = false, length = 13, unique = true)
    private String isbn;

    /*
    No és necessari posar @Column a totes les columnes
    Sols es posa quan es vol modificar alguna cosa.
    Hibernate per defecte aplica el tipus de la bbdd
        - String -> VARCHAR
        - int -> INTEGER
        - LocalDate -> DATE
        - LocalDateTime -> TIMESTAMP
     */
    @Column
    private LocalDate publishedDate;

    //Per defecte ordinal, però no existeix ordre
    //entre generes literaris
    @Enumerated(EnumType.STRING)
    private Genre genre;

    /*
    Atributs derivats.
    Sense persistència a bbdd
     */
    @Transient
    private BigDecimal currentPrice;

    //Constructors
    public Book(){}

    public Book(String title, String author, String isbn,
                LocalDate publishedDate, Genre genre){
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishedDate = publishedDate;
        this.genre = genre;
        this.currentPrice = BigDecimal.ZERO;
    }

    //Getters i setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }
}
