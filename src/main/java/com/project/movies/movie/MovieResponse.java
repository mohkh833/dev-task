package com.project.movies.movie;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieResponse {
    private Integer id;
    private String title;
    private String poster;
    private String Released;
    private double rate;

}
