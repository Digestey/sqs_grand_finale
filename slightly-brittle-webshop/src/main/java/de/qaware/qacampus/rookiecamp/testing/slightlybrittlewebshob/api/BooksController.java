package de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.api;


import de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.api.mapper.DTOMapper;
import de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.app.OrderApplication;
import de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.app.SearchApplication;
import de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.domain.Store;
import de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.domain.StoreStorage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
public class BooksController {

    private final SearchApplication searchApplication;
    private final OrderApplication orderApplication;

    public BooksController(SearchApplication searchApplication, OrderApplication orderApplication) {
        this.searchApplication = searchApplication;
        this.orderApplication = orderApplication;
    }

    @GetMapping(path = "/books")
    Map<Store, StoreStorage> getStorage(@RequestParam(name = "store-id", required = false) String storeId) {
        if (storeId == null)
            return searchApplication.getStorageOfAllStores();

        Store store = DTOMapper.toDTO(storeId);
        if (store == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find store id");

        return Map.of(store, searchApplication.getStorageOf(store));
    }

    @GetMapping(path = "/books/{isbn}")
    Map<Store, Integer> getBookFromAllStorages(@PathVariable(name = "isbn") String isbn) {
        Map<Store, Integer> storageCount = searchApplication.getBookStorageByISBN(isbn);

        if (storageCount.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find book");

        return storageCount;
    }

    @DeleteMapping(path = "/books/{isbn}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void removeBookFromAllStorages(@PathVariable(name = "isbn") String isbn) {
        orderApplication.removeBookFromAllStores(isbn);
    }
}
