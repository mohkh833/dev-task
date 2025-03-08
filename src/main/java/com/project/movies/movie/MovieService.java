package com.project.movies.movie;

import com.project.movies.common.PageResponse;
import com.project.movies.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public MovieResponse save(MovieRequest request, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Movie movie = movieMapper.toMovie(request);
        movie.setOwner(user);
        movieRepository.save(movie).getId();
        return this.findById(movie.getId());
    }

    public void deleteMovie(Integer movieId, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        // Ensure only the owner can delete the movie
        if (!movie.getOwner().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to delete this movie");
        }

        movieRepository.delete(movie);
    }


    public MovieResponse findById(Integer movieId) {
        return movieRepository.findById(movieId)
                .map(movieMapper::toMovieResponse)
                .orElseThrow(() -> new EntityNotFoundException("No Movie found with the ID: " + movieId));
    }

    public PageResponse<MovieResponse> findAllMovies(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = (Pageable) PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Movie> movies = movieRepository.findAllDisplayableMovies(pageable, user.getId());
        List<MovieResponse> bookResponses = movies.stream()
                .map(movieMapper::toMovieResponse)
                .toList();
        return new PageResponse<>(
                bookResponses,
                movies.getNumber(),
                movies.getSize(),
                movies.getTotalElements(),
                movies.getTotalPages(),
                movies.isFirst(),
                movies.isLast()
        );
    }

    public PageResponse<MovieResponse> searchMovies(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        Page<Movie> movies = movieRepository.searchMovies(keyword, pageable);
        List<MovieResponse> movieResponses = movies.stream()
                .map(movieMapper::toMovieResponse)
                .toList();

        return new PageResponse<>(
                movieResponses,
                movies.getNumber(),
                movies.getSize(),
                movies.getTotalElements(),
                movies.getTotalPages(),
                movies.isFirst(),
                movies.isLast()
        );
    }


}
