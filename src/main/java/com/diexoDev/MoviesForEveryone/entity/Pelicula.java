package com.diexoDev.MoviesForEveryone.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @ToString
public class Pelicula {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Column(name = "idPelicula")
    private Integer id;

    @NotBlank
    @Column(name = "titulo")
    private String titulo;
    @NotBlank
    @Column(name = "director")
    private String director;
    @NotBlank
    @Column(name = "descripcion", length = 800)
    private String descripcion;

    @ElementCollection(targetClass = Genero.class)
    @CollectionTable(name = "pelicula_generos", joinColumns = @JoinColumn(name = "pelicula_id"))
    @Enumerated(EnumType.STRING) @NotEmpty
    @Column(name = "genero")
    private List<Genero> generos;

    @NotNull
    @Column(name = "duracion")
    private double duracion;

    @NotNull
    @Column(name = "estreno") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaEstreno;

    @NotBlank
    @Column(name = "idioma")
    private String idioma;

    @NotBlank
    private String linkTrailer;

    private String rutaPortada;

    @Transient
    private MultipartFile portada;
    /*No se guarda en la base de datos, es un dato temporal. Se guarda en la propiedad definida en properties
    * storage.location*/

    public Pelicula(Integer id, @NotBlank String titulo, @NotBlank String director, @NotBlank String descripcion, @NotNull LocalDate fechaEstreno,
                    @NotBlank String idioma,@NotBlank String linkTrailer, String rutaPortada, @NotEmpty List<Genero> generos,
                    MultipartFile portada) {
        super();
        this.id = id;
        this.titulo = titulo;
        this.director = director;
        this.descripcion = descripcion;
        this.fechaEstreno = fechaEstreno;
        this.idioma = idioma;
        this.linkTrailer = linkTrailer;
        this.rutaPortada = rutaPortada;
        this.generos = generos;
        this.portada = portada;
    }

    public Pelicula(@NotBlank String titulo, @NotBlank String director, @NotBlank String descripcion, @NotNull LocalDate fechaEstreno,
                    @NotBlank String idioma,@NotBlank String linkTrailer, String rutaPortada, @NotEmpty List<Genero> generos,
                    MultipartFile portada) {
        super();
        this.titulo = titulo;
        this.director = director;
        this.descripcion = descripcion;
        this.fechaEstreno = fechaEstreno;
        this.idioma = idioma;
        this.linkTrailer = linkTrailer;
        this.rutaPortada = rutaPortada;
        this.generos = generos;
        this.portada = portada;
    }
}
