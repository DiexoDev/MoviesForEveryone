package com.diexoDev.MoviesForEveryone.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface AlmacenService {

    public void iniciarAlmacenDeArchivos();

    public String almacenarArchivo(MultipartFile archivo);

    public Path cargarArchivo(String nombreArchivo);

    public Resource cargarComoRecurso(String nombreArchivo);

    public void eliminarArchivo(String nombreArchivo);
}
