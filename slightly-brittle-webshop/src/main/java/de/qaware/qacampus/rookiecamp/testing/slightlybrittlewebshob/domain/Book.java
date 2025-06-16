package de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class Book {
    private final String isbn;
    private final List<String> authors;
    private final String title;

    @JsonCreator
    public Book(
            @JsonProperty("isbn") String isbn,
            @JsonProperty("authors") List<String> authors,
            @JsonProperty("title") String title)
    {
        this.isbn = isbn;
        this.authors = authors;
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}
