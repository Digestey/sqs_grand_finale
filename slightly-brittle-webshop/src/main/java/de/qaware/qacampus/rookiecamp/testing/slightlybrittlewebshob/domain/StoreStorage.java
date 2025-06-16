package de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreStorage {
    private Map<String, Integer> isbnToBookCount = new HashMap<>();
    private Set<Book> books = new HashSet<>();

    public void addBook(Book book) {
        Integer bookCount = isbnToBookCount.putIfAbsent(book.getIsbn(), 1);

        if (bookCount != null) {
            isbnToBookCount.put(book.getIsbn(), bookCount + 1);
        }

        books.add(book);
    }

    public void removeBook(String isbn) {
        isbnToBookCount.remove(isbn);
        books.removeIf(book -> book.getIsbn().equals(isbn));
    }

    public int getBookCount(String isbn) {
        return isbnToBookCount.getOrDefault(isbn, 0);
    }
}
