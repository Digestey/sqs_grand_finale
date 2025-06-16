package de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.app;

import de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.domain.Store;
import de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.domain.StoreStorage;

import java.util.Map;

public interface SearchApplication {

    Map<Store, StoreStorage> getStorageOfAllStores();
    StoreStorage getStorageOf(Store store);

    Map<Store, Integer> getBookStorageByISBN(String isbn);
}
