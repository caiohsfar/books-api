package com.codeelevate.books;

import com.codeelevate.books.application.dto.BookDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "booksClient", url = "http://localhost:8081/books")
public interface BooksClient {

    @GetMapping
    Page<BookDTO> getBooks(@RequestParam(required = false) String author,
                           @RequestParam(required = false) String mainGenre,
                           @RequestParam(defaultValue = "true") Boolean exactMatching,
                           @RequestParam Integer page,
                           @RequestParam Integer size);

    @GetMapping("/{id}")
    BookDTO getBookById(@PathVariable Long id);

    @GetMapping("/recently-viewed")
    List<BookDTO> getRecentlyViewed(@RequestParam int limit, @RequestParam String sessionId);
}
