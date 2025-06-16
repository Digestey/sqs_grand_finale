package de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.app.impl;

import de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.app.OrderApplication;
import de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.domain.Book;
import de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.domain.OverallStorage;
import de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.domain.Store;
import de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.domain.StoreStorage;
import de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.repo.BookStoreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class OrderApplicationImpl implements OrderApplication {

    @Autowired
    private BookStoreRepo repo;

    @Override
    @Transactional
    public void addBookToStore(Collection<Book> books, Store store) {
        OverallStorage storage = repo.getStorage();

        StoreStorage storeStorage = storage.obtainStoreStorage(store);
        books.forEach(storeStorage::addBook);

        repo.setStorage(storage);
    }

    @Override
    @Transactional
    public void addBookToAllStores(Collection<Book> books) {
        OverallStorage storage = repo.getStorage();

        for (Store store : Store.values()) {
            StoreStorage storeStorage = storage.obtainStoreStorage(store);
            books.forEach(storeStorage::addBook);
        }

        repo.setStorage(storage);
    }

    @Override
    @Transactional
    public void removeBookFromAllStores(String isbn) {
        OverallStorage storage = repo.getStorage();

        storage.obtainStoreStorageList().forEach(storeStorage ->
                storeStorage.removeBook(isbn)
        );

        repo.setStorage(storage);
    }

}
