package com.codeelevate.books.infra.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Represents a book entity in the database.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book")
public class BookEntity implements Serializable {

    /**
     * The unique identifier of the book.
     */
    @Id
    private Long id;

    /**
     * The title of the book.
     */
    @Column(name = "title", columnDefinition = "text")
    private String title;

    /**
     * The author of the book.
     */
    @Column(name = "author", columnDefinition = "text")
    private String author;

    /**
     * The main genre of the book.
     */
    @Column(name = "main_genre", columnDefinition = "text")
    private String mainGenre;

    /**
     * The sub-genre of the book.
     */
    @Column(name = "sub_genre", columnDefinition = "text")
    private String subGenre;

    /**
     * The type of the book.
     */
    @Column(name = "type", columnDefinition = "text")
    private String type;

    /**
     * The price of the book.
     */
    @Column(name = "price", columnDefinition = "text")
    private String price;

    /**
     * The rating of the book.
     */
    @Column(name = "rating")
    private Float rating;

    /**
     * The number of people who rated the book.
     */
    @Column(name = "no_people_rated")
    private Float noPeopleRated;

    /**
     * The URL of the book.
     */
    @Column(columnDefinition = "text")
    private String url;

}