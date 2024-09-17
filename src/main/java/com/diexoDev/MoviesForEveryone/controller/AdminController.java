package com.diexoDev.MoviesForEveryone.controller;

import com.diexoDev.MoviesForEveryone.entity.Genero;
import com.diexoDev.MoviesForEveryone.entity.Pelicula;
import com.diexoDev.MoviesForEveryone.repository.PeliculaRepository;
import com.diexoDev.MoviesForEveryone.service.AlmacenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private PeliculaRepository repository;
    @Autowired
    private AlmacenServiceImpl service;

    @GetMapping("")
    public ModelAndView verPaginaDeInicio(@PageableDefault(sort = "titulo", size = 5) Pageable pageable) {
        Page<Pelicula> peliculas = repository.findAll(pageable);
        return new ModelAndView("admin/admin").addObject("peliculas", peliculas);
    }
    /*Metodo get para mostrar el formulario*/
    @GetMapping("/peliculas/nuevo")
    public ModelAndView mostrarFormularioPelicula(){
        List<Genero> generos = Arrays.asList(Genero.values());
        return new ModelAndView("admin/nueva-pelicula").addObject( "pelicula", new Pelicula())
                .addObject("generos", Arrays.asList(Genero.values()));
    }
    /*Metodo post para guardar los datos
    BindingResult funciona para capturar y manejar los errores del metodo.*/
    @PostMapping("/peliculas/nuevo")
    public ModelAndView guardarPelicula(@Validated Pelicula pelicula, BindingResult bindingResult){
        if (bindingResult.hasErrors() || pelicula.getPortada() == null || pelicula.getPortada().isEmpty()) {
            if(pelicula.getPortada().isEmpty()) {
                bindingResult.rejectValue("portada","MultipartNotEmpty");
            }
        List<Genero> generos = Arrays.asList(Genero.values());
        return new ModelAndView("admin/nueva-pelicula")
                .addObject("pelicula", pelicula)
                .addObject("generos", generos);
        }
        String rutaPortada = service.almacenarArchivo(pelicula.getPortada());
        pelicula.setRutaPortada(rutaPortada);

        repository.save(pelicula);
        return new ModelAndView("redirect:/admin");
    }

    @GetMapping("/peliculas/{id}/editar")
    public ModelAndView mostrarFormEditarPelicula(@PathVariable Integer id){
        Pelicula pelicula = repository.getOne(id);
        List<Genero> generos = Arrays.asList(Genero.values());

        return new ModelAndView("admin/editar-pelicula")
                .addObject("pelicula", pelicula)
                .addObject("generos", generos);
    }

    @PostMapping("/peliculas/{id}/editar")
    public ModelAndView actualizarPelicula(@PathVariable Integer id, @Validated Pelicula pelicula, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            List<Genero> generos = Arrays.asList(Genero.values());
            return new ModelAndView("admin/editar-pelicula")
                    .addObject("pelicula", pelicula)
                    .addObject("generos", generos);
        }
        Pelicula peliculaDB = repository.getOne(id);
        peliculaDB.setTitulo(pelicula.getTitulo());
        peliculaDB.setDirector(pelicula.getDirector());
        peliculaDB.setDescripcion(pelicula.getDescripcion());
        peliculaDB.setFechaEstreno(pelicula.getFechaEstreno());
        peliculaDB.setIdioma(pelicula.getIdioma());
        peliculaDB.setLinkTrailer(pelicula.getLinkTrailer());
        peliculaDB.setGeneros(pelicula.getGeneros());

        if(!pelicula.getPortada().isEmpty()){
            service.eliminarArchivo(peliculaDB.getRutaPortada());
            String rutaPortada = service.almacenarArchivo(pelicula.getPortada());
            peliculaDB.setRutaPortada(rutaPortada);
        }
        repository.save(peliculaDB);
        return new ModelAndView("redirect:/admin");
    }

    @PostMapping("/peliculas/{id}/eliminar")
    public String eliminarPelicula(@PathVariable Integer id) {
        Pelicula pelicula = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID de pelicula invalido:" + id));
        service.eliminarArchivo(pelicula.getRutaPortada());
        repository.delete(pelicula);
        return "redirect:/admin";
    }


}
