package de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.app;

import de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.domain.Book;
import de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.domain.Store;

import java.util.Collection;

public interface OrderApplication {

    void addBookToStore(Collection<Book> book, Store store);
    void addBookToAllStores(Collection<Book> book);

    void removeBookFromAllStores(String isbn);
}
