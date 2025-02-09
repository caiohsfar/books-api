package com.codeelevate.books.infra.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book")
public class BookEntity implements Serializable {
    @Id
    private Long id;

    @Column(name = "title", columnDefinition = "text")
    private String title;

    @Column(name = "author", columnDefinition = "text")
    private String author;

    @Column(name = "main_genre", columnDefinition = "text")
    private String mainGenre;

    @Column(name = "sub_genre", columnDefinition = "text")
    private String subGenre;

    @Column(name = "type", columnDefinition = "text")
    private String type;

    @Column(name = "price", columnDefinition = "text")
    private String price;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "no_people_rated")
    private Float noPeopleRated;

    @Column(columnDefinition = "text")
    private String url;


}
