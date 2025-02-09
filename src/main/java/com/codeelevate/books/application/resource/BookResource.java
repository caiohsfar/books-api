package com.codeelevate.books.application.resource;

import com.codeelevate.books.application.dto.BookDTO;
import com.codeelevate.books.application.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookResource {

    private final BookService bookService;


    @GetMapping
    public ResponseEntity<Page<BookDTO>> getAllBooks(@PageableDefault(page = 0, size = 10, sort = "author", direction = Sort.Direction.ASC) Pageable pageable,
                                                     @RequestParam(defaultValue = "true") boolean exactMatching,
                                                     @RequestParam(required = false) String author,
                                                     @RequestParam(required = false) String mainGenre,
                                                     @CookieValue("sessionId") String sessionId) {

        return ResponseEntity.ok(bookService.getAllBooks(pageable, author, mainGenre, exactMatching, sessionId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id, @CookieValue("sessionId") String sessionId) {

        return ResponseEntity.ok(bookService.getBookById(id, sessionId));
    }

    @GetMapping("/recently-viewed")
    public ResponseEntity<List<BookDTO>> getRecentlyViewed(@RequestParam(defaultValue = "10") int limit, @CookieValue("sessionId") String sessionId) {

        return ResponseEntity.ok(bookService.getRecentlyViewed(limit, sessionId));
    }

}
