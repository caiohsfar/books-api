package com.codeelevate.books.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Represents a book in the domain model.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book implements Serializable {

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