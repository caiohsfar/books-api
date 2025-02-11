Feature: Books API

  Scenario: Get all books
    Given I have a list of books
    When I request all books
    Then I receive a list of books

  Scenario: Get books by author and main genre
    Given I have a list of books
    When I request books by author "Arthur Conan Doyle" and main genre "Arts, Film & Photography"
    Then I receive a list of books by that author and genre

  Scenario: Get books by author with exact match false
    Given I have a list of books
    When I request books by author "Arthur Conan Doyle" with exact match false
    Then I receive a list of books by that author

  Scenario: Get books by main genre
    Given I have a list of books
    When I request books by main genre "Arts, Film & Photography"
    Then I receive a list of books by that genre

  Scenario: Get first page of books
    Given I have a list of books
    When I request the first page of books with size 5
    Then I receive a list of 5 books

  Scenario: Get second page of books
    Given I have a list of books
    When I request the second page of books with size 5
    Then I receive a list of 5 books

  Scenario: Get empty page of books
    Given I have a list of books
    When I request a page of books that does not exist
    Then I receive an empty list of books

  Scenario: Get book by ID
    Given there is a book with ID 1 in the database
    When I request the book by ID 1
    Then I receive the book with ID 1
    And the book details should be "Black Holes (L) : The Reith Lectures [Paperback] Hawking, Stephen" and "Stephen Hawking"

  Scenario: Fail to retrieve book with non-existent ID
    Given there is no book with ID 999 in the database
    When I request the book by missing ID 999
    Then the response status should be 404
    And I receive an error message "Book not found"