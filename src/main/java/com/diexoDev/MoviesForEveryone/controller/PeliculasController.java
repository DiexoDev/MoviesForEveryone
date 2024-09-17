package com.diexoDev.MoviesForEveryone.controller;

import com.diexoDev.MoviesForEveryone.entity.Pelicula;
import com.diexoDev.MoviesForEveryone.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class PeliculasController {

    @Autowired
    private PeliculaRepository repository;

    @GetMapping("/")
    public String paginaInicio() {
        return "index";
    }
    @GetMapping("/cartelera")
    public ModelAndView verPaginaDeInicio() {
        List<Pelicula> ultimasPeliculas = repository.findAll(PageRequest.of(0, 4, Sort.by("fechaEstreno").descending())).toList();
        return new ModelAndView("cartelera")
                .addObject("ultimasPeliculas", ultimasPeliculas);
    }

    @GetMapping("/peliculas")
    public ModelAndView mostrarTodasLasPeliculas(@PageableDefault(sort = "fechaEstreno",direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Pelicula> peliculas = repository.findAll(pageable);
        return new ModelAndView("peliculas")
                .addObject("peliculas",peliculas);
    }
    @GetMapping("/pelicula/{id}")
    public ModelAndView verInformacionPelicula(@PathVariable Integer id) {
        Pelicula pelicula = repository.getOne(id);
        return new ModelAndView("infoPelicula")
                .addObject("pelicula", pelicula);
    }




}
