package com.project.movies.movie;

import com.project.movies.common.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService service;

    @PostMapping
    public ResponseEntity<MovieResponse> saveMovie(
            @Valid @RequestBody MovieRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.save(request, connectedUser));
    }

    @GetMapping("{movie-id}")
    public ResponseEntity<MovieResponse> findMovieById(
            @PathVariable("movie-id") Integer movieId
    ) {
        return ResponseEntity.ok(service.findById(movieId));
    }

    @DeleteMapping("/{movie-id}")
    public ResponseEntity<Void> deleteMovie(
            @PathVariable("movie-id") Integer movieId,
            Authentication connectedUser
    ) {
        service.deleteMovie(movieId, connectedUser);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/")
    public ResponseEntity<PageResponse<MovieResponse>> findAllMovies(
            @RequestParam(name ="page", defaultValue = "0", required = false) int page,
            @RequestParam(name ="size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.findAllMovies(page, size, connectedUser));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<MovieResponse>> searchMovies(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return  ResponseEntity.ok(service.searchMovies(keyword,page, size));
    }
}
