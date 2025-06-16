package com.flixfix.sqs_testing.service;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Represents a book in the library")
public class BookDto {

    @Schema(description = "Unique identifier of the book", example = "42")
    private Long id;

    @Schema(description = "Title of the book", example = "The Hitchhiker's Guide to the Galaxy")
    private String title;

    @Schema(description = "Indicates whether the book is currently lent out", example = "false")
    private boolean lent;
}
