package io.javabrains.movieinfoservice.services;

import io.javabrains.movieinfoservice.models.Movie;
import io.javabrains.movieinfoservice.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MovieService {

    @Autowired
    MovieRepository movieRepository;


    public List<Movie> getAll() {
        return movieRepository.findAll();
    }

    public Optional<Movie> findById(int id) {
        return movieRepository.findById(id);
    }

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    public void delete(int id){
        movieRepository.deleteById(id);
    }
}
