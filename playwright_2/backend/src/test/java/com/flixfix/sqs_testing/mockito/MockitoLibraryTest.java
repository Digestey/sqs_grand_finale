package com.flixfix.sqs_testing.mockito;

import com.flixfix.sqs_testing.persistence.BookStoragePort;
import com.flixfix.sqs_testing.service.BookDto;
import com.flixfix.sqs_testing.service.LibraryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MockitoLibraryTest {

    @Mock
    private BookStoragePort bookStoragePort;

    @InjectMocks
    private LibraryService libraryService;

    @Test
    void shouldReturnAvailableBooks() {
        List<BookDto> mockBooks = List.of(new BookDto(1L, "Mockito Book", false));
        when(bookStoragePort.findByLentFalse()).thenReturn(mockBooks);

        var result = libraryService.getAvailableBooks();

        assertEquals(1, result.size());
        assertEquals("Mockito Book", result.get(0).getTitle());
    }
}
