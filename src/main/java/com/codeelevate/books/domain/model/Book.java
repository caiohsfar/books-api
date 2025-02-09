package com.codeelevate.books.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Book implements Serializable {
    private Long id;

    private String title;

    private String author;

    private String mainGenre;

    private String subGenre;

    private String type;

    private String price;

    private Float rating;

    private Float noPeopleRated;

    private String url;


}
