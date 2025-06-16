package com.flixfix.sqs_testing.persistence;

import com.flixfix.sqs_testing.service.BookDto;
import com.flixfix.sqs_testing.service.BookMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookStoragePort {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public List<BookDto> findByLentFalse() {
        var books = bookRepository.findByLentFalse();
        return bookMapper.toBoList(books);
    }

    public List<BookDto> findByLentTrue() {
        var books = bookRepository.findByLentTrue();
        return bookMapper.toBoList(books);
    }

    public BookDto findById(Long id) {
        var book = bookRepository.findById(id);
        return book.map(bookMapper::toBo).orElseThrow();
    }

    public void save(BookDto book) {
        var updatedBook = bookMapper.toEntity(book);
        bookRepository.save(updatedBook);
    }

    public List<BookDto> findByTitle(String query) {
        var foundBooks = bookRepository.findByTitleContainingIgnoreCase(query);
        return bookMapper.toBoList(foundBooks);
    }
}
