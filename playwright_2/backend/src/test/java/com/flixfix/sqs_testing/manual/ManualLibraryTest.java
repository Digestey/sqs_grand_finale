package com.flixfix.sqs_testing.manual;

import com.flixfix.sqs_testing.persistence.BookStoragePort;
import com.flixfix.sqs_testing.service.BookDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ManualLibraryTest {

    @Autowired
    private BookStoragePort bookStoragePort;

    @Test
    void shouldFindLentBooksIfPreloaded() {
        List<BookDto> lentBooks = bookStoragePort.findByLentTrue();
        assertNotNull(lentBooks);
    }
}
