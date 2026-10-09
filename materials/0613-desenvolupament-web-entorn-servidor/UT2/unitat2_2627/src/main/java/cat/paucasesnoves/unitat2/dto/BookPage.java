package cat.paucasesnoves.unitat2.dto;

import cat.paucasesnoves.unitat2.domain.entity.Book;
import java.util.List;

/** Representació explícita de la pàgina, sense publicar la implementació de Page. */
public record BookPage(List<Book> content, int page, int size, long totalElements, int totalPages) {}
