package com.codeelevate.books;

import com.codeelevate.books.application.dto.BookDTO;
import com.codeelevate.books.domain.model.Book;
import feign.FeignException;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class BooksApiSteps {

    @Autowired
    private BooksClient booksClient;

    @Mock
    private RedisTemplate<String, Book> redisTemplate;

    @Mock
    private ZSetOperations<String, Book> zSetOperations;

    private Page<BookDTO> books;
    private List<BookDTO> recentBooks;

    private BookDTO book;

    private Throwable exception;


    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForZSet()).thenReturn(zSetOperations);
    }

    @Given("I have a list of books")
    public void i_have_a_list_of_books() {
        books = booksClient.getBooks(null, null, null, null, null);
        assertThat(books).isNotEmpty();
    }


    @Given("there is a book with ID {int} in the database")
    public void there_is_a_book_with_id_in_the_database(int id) {

    }

    @Given("there is no book with ID 999 in the database")
    public void there_is_no_book() {

    }

    @When("I request the book by ID {long}")
    public void i_request_the_book_by_id(long id) {
        book = booksClient.getBookById(id);
    }


    @When("I request all books")
    public void i_request_all_books() {
        books = booksClient.getBooks(null, null, null, null, null);
    }

    @When("I request books by author {string} and main genre {string}")
    public void i_request_books_by_author_and_main_genre(String author, String mainGenre) {
        books = booksClient.getBooks(author, mainGenre, null, 0, 10);
    }

    @When("I request books by author {string} with exact match false")
    public void i_request_books_by_author_with_exact_match_false(String author) {
        books = booksClient.getBooks(author, null, false, 0, 10);
    }

    @When("I request books by main genre {string}")
    public void i_request_books_by_main_genre(String mainGenre) {
        books = booksClient.getBooks(null, mainGenre, true, 0, 10);
    }

    @When("I request the first page of books with size {int}")
    public void i_request_the_first_page_of_books_with_size(int size) {
        books = booksClient.getBooks(null, null, true, 0, size);
    }

    @When("I request the second page of books with size {int}")
    public void i_request_the_second_page_of_books_with_size(int size) {
        books = booksClient.getBooks(null, null, true, 1, size);
    }

    @When("I request a page of books that does not exist")
    public void i_request_a_page_of_books_that_does_not_exist() {
        books = booksClient.getBooks(null, null, true, 100, 10);
    }

    @When("I request the book by missing ID {long}")
    public void i_request_the_book_by_missing_id(long id) {
        exception = catchThrowable(() -> book = booksClient.getBookById(id));
    }


    @Then("I receive a list of books")
    public void i_receive_a_list_of_books() {
        assertThat(books.getContent()).isNotEmpty();
    }

    @Then("the book details should be {string} and {string}")
    public void the_book_details_should_be(String title, String author) {
        assertThat(book.getAuthor()).isEqualTo(author);
        assertThat(book.getTitle()).isEqualTo(title);
    }

    @Then("I receive a list of books by that author and genre")
    public void i_receive_a_list_of_books_by_that_author_and_genre() {
        assertThat(books.getContent()).isNotEmpty();
        for (BookDTO book : books.getContent()) {
            assertThat(book.getAuthor()).isEqualTo("Arthur Conan Doyle");
            assertThat(book.getMainGenre()).isEqualTo("Arts, Film & Photography");
        }
    }

    @Then("I receive a list of books by that author")
    public void i_receive_a_list_of_books_by_that_author() {
        assertThat(books.getContent()).isNotEmpty();
        for (BookDTO book : books.getContent()) {
            assertThat(book.getAuthor()).isEqualTo("Arthur Conan Doyle");
        }
    }

    @Then("I receive a list of books by that genre")
    public void i_receive_a_list_of_books_by_that_genre() {
        assertThat(books.getContent()).isNotEmpty();
        for (BookDTO book : books.getContent()) {
            assertThat(book.getMainGenre()).isEqualTo("Arts, Film & Photography");
        }
    }

    @Then("I receive a list of {int} books")
    public void i_receive_a_list_of_books(int size) {
        assertThat(books.getContent()).hasSize(size);
    }

    @Then("I receive an empty list of books")
    public void i_receive_an_empty_list_of_books() {
        assertThat(books.getContent()).isEmpty();
    }

    @Then("I receive the book with ID {long}")
    public void i_receive_the_book_with_id(Long id) {
        assertThat(book).isNotNull();
        assertThat(book.getId()).isEqualTo(id);
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int statusCode) {
        assertThat(exception).isInstanceOf(FeignException.class);
        FeignException httpException = (FeignException) exception;
        assertThat(httpException.status()).isEqualTo(statusCode);
    }

    @Then("I receive an error message {string}")
    public void iReceiveAnErrorMessage(String errorMessage) {
        FeignException httpException = (FeignException) exception;
        assertThat(httpException.getMessage()).contains(errorMessage);
    }

}