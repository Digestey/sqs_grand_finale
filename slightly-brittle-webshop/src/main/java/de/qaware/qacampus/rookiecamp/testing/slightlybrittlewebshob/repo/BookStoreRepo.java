package de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.repo;

import de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.domain.OverallStorage;

public interface BookStoreRepo {

    OverallStorage getStorage();

    void setStorage(OverallStorage storage);
}
