package com.codeelevate.books.application.resource;

import com.codeelevate.books.application.dto.BookDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Books", description = "APIs for managing books")
public interface BookResource {

    @GetMapping
    @Operation(summary = "Get all books", description = "Retrieve all books with optional filtering and pagination")
    @Parameter(in = ParameterIn.QUERY
            , description = "Zero-based page index (0..N)"
            , name = "page"
            , content = @Content(schema = @Schema(type = "integer", defaultValue = "0")))
    @Parameter(in = ParameterIn.QUERY
            , description = "The size of the page to be returned"
            , name = "size"
            , content = @Content(schema = @Schema(type = "integer", defaultValue = "10")))
    @Parameter(in = ParameterIn.QUERY
            , description = "Sorting criteria in the format: property(,asc|desc). "
            + "Default sort order is ascending. " + "Multiple sort criteria are supported."
            , name = "sort"
            , content = @Content(array = @ArraySchema(schema = @Schema(type = "string"))))
    ResponseEntity<Page<BookDTO>> getAllBooks(
            @Parameter(hidden = true) @PageableDefault(page = 0, size = 10, sort = "author", direction = Sort.Direction.ASC) Pageable pageable,
            @Parameter(description = "Whether to use exact matching for filtering") @RequestParam(defaultValue = "true") boolean exactMatching,
            @Parameter(description = "Author to filter by") @RequestParam(required = false) String author,
            @Parameter(description = "Main genre to filter by") @RequestParam(required = false) String mainGenre,
            @Parameter(description = "Session ID for caching") @RequestHeader(required = false, value = "example_session") String sessionId);

    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID", description = "Retrieve a book by its ID")
    ResponseEntity<BookDTO> getBookById(
            @Parameter(description = "Book ID") @PathVariable Long id,
            @Parameter(description = "Session ID for caching") @RequestHeader(required = false, value = "example_session") String sessionId);


    @GetMapping("/recently-viewed")
    @Operation(summary = "Get recently viewed books", description = "Retrieve recently viewed books")
    ResponseEntity<List<BookDTO>> getRecentlyViewed(
            @Parameter(description = "Maximum number of books to retrieve") @RequestParam(defaultValue = "10") int limit,
            @Parameter(description = "Session ID for caching") @RequestHeader(required = false, value = "example_session") String sessionId);
}