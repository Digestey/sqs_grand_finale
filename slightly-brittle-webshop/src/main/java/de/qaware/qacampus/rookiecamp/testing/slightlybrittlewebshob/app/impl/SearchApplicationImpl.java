package de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.app.impl;

import de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.app.SearchApplication;
import de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.domain.OverallStorage;
import de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.domain.Store;
import de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.domain.StoreStorage;
import de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.repo.BookStoreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class SearchApplicationImpl implements SearchApplication {

    @Autowired
    private BookStoreRepo repo;

    @Override
    @Transactional
    public Map<Store, StoreStorage> getStorageOfAllStores() {
        OverallStorage storage = repo.getStorage();
        return storage.obtainStorageMap();
    }

    @Override
    @Transactional
    public StoreStorage getStorageOf(Store store) {
        OverallStorage storage = repo.getStorage();
        return storage.obtainStoreStorage(store);
    }

    @Override
    @Transactional
    public Map<Store, Integer> getBookStorageByISBN(String isbn) {
        OverallStorage storage = repo.getStorage();
        return storage.obtainStorageCountForBook(isbn);
    }
}
