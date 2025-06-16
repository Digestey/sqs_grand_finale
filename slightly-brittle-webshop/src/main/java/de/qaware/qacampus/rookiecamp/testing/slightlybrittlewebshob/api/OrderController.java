package de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.api;

import de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.app.OrderApplication;
import de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.domain.Book;
import de.qaware.qacampus.rookiecamp.testing.slightlybrittlewebshob.domain.Store;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class OrderController {

    private final OrderApplication orderApplication;


    public OrderController(OrderApplication orderApplication) {
        this.orderApplication = orderApplication;
    }


    @PostMapping("order")
    @ResponseStatus(value = HttpStatus.CREATED)
    void addBookToStorageOf(
            @RequestParam(name = "store-id", required = false) Store store,
            @RequestBody Collection<Book> book
    ) {
        if (store == null) {
            orderApplication.addBookToAllStores(book);
        } else {
            orderApplication.addBookToStore(book, store);
        }

    }

}
