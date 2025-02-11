package com.codeelevate.books.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Book.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    /**
     * The unique identifier of the book.
     */
    private Long id;

    /**
     * The title of the book.
     */
    private String title;

    /**
     * The author of the book.
     */
    private String author;

    /**
     * The main genre of the book.
     */
    private String mainGenre;

    /**
     * The sub-genre of the book.
     */
    private String subGenre;

    /**
     * The type of the book.
     */
    private String type;

    /**
     * The price of the book.
     */
    private String price;

    /**
     * The rating of the book.
     */
    private Float rating;

    /**
     * The number of people who rated the book.
     */
    private Float noPeopleRated;

    /**
     * The URL of the book.
     */
    private String url;

}