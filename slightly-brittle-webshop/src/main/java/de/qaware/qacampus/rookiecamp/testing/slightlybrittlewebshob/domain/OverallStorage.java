package de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
public class OverallStorage {
    @JsonProperty
    private Map<Store, StoreStorage> storages = new HashMap<>();

    public StoreStorage obtainStoreStorage(Store store) {
        return this.storages.computeIfAbsent(store, s -> new StoreStorage());
    }

    public Collection<StoreStorage> obtainStoreStorageList() {
        return this.storages.values();
    }

    public Map<Store, StoreStorage> obtainStorageMap() {
        return Collections.unmodifiableMap(this.storages);
    }

    public Map<Store, Integer> obtainStorageCountForBook(String isbn) {
        Map<Store, Integer> retVal = new TreeMap<>();

        for (var store : storages.entrySet()) {
            retVal.put(store.getKey(), store.getValue().getBookCount(isbn));
        }

        return retVal;
    }
}
