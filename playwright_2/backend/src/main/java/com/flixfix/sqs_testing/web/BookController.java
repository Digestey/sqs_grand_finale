package com.flixfix.sqs_testing.web;

import com.flixfix.sqs_testing.service.BookDto;
import com.flixfix.sqs_testing.service.LibraryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@Tag(name = "Books", description = "Endpoints for managing book availability and lending")
public class BookController {

    private final LibraryService service;

    public BookController(LibraryService service) {
        this.service = service;
    }

    @GetMapping("/available")
    @Operation(summary = "Get available books", description = "Retrieves a list of all currently available books in the library.")
    @ApiResponse(
            responseCode = "200",
            description = "List of available books returned successfully",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = BookDto.class))
            )
    )
    public List<BookDto> available() {
        return service.getAvailableBooks();
    }


    @GetMapping("/lent")
    @Operation(summary = "Get lent books", description = "Retrieves a list of books that are currently lent out.")
    @ApiResponse(
            responseCode = "200",
            description = "List of lent books returned successfully",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = BookDto.class))
            )
    )
    public List<BookDto> lent() {
        return service.getLentBooks();
    }

    @Operation(summary = "Lend a book", description = "Marks the book with the given ID as lent.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book lent successfully"),
            @ApiResponse(responseCode = "404", description = "Book with the given ID not found"),
            @ApiResponse(responseCode = "400", description = "Book is already lent or unavailable")
    })
    @PostMapping("/lend/{id}")
    public void lend(@PathVariable Long id) {
        service.lendBook(id);
    }

    @Operation(summary = "Search books by title", description = "Returns books whose titles contain the given query string (case-insensitive).")
    @ApiResponse(
            responseCode = "200",
            description = "List of matching books returned successfully",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = BookDto.class))
            )
    )
    @GetMapping("/search")
    public List<BookDto> searchBooks(@RequestParam("q") String query) {
        return service.searchBooksByTitle(query);
    }

}
