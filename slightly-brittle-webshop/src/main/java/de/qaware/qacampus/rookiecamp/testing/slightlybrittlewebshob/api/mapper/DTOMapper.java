package de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.api.mapper;

import de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.domain.Store;

public class DTOMapper {
    /**
     * Converts the storeId to a suitable store
     * @param storeId
     * @return the store or null if not found
     */
    public static Store toDTO(String storeId) {
        for (Store store : Store.values()) {
            if (store.name().equalsIgnoreCase(storeId)) {
                return store;
            }
        }
        return null;
    }
}
