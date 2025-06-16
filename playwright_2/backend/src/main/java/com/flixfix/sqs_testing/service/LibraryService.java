package com.flixfix.sqs_testing.service;

import com.flixfix.sqs_testing.persistence.BookStoragePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryService {
    private final BookStoragePort storagePort;


    public List<BookDto> getAvailableBooks() {
        return storagePort.findByLentFalse();
    }

    public List<BookDto> getLentBooks() {
        return storagePort.findByLentTrue();
    }

    public void lendBook(Long id) {
        var book = storagePort.findById(id);
        book.setLent(true);
        storagePort.save(book);
    }

    public List<BookDto> searchBooksByTitle(String query) {
        return storagePort.findByTitle(query);
    }
}
