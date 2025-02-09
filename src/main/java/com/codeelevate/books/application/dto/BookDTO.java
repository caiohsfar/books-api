package com.codeelevate.books.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
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
