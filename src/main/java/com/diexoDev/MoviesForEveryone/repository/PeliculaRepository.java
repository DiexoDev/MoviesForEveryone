package com.diexoDev.MoviesForEveryone.repository;

import com.diexoDev.MoviesForEveryone.entity.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeliculaRepository extends JpaRepository<Pelicula, Integer> {
}
