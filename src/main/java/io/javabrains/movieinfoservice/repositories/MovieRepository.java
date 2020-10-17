package io.javabrains.movieinfoservice.repositories;

import io.javabrains.movieinfoservice.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Integer> {
}
