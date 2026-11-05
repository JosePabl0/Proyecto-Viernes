package com.inksticker.service;

import com.inksticker.entity.Categoria;
import java.util.List;
import java.util.Optional;

public interface CategoriaService {

    List<Categoria> listarCategoriasActivas();

    List<Categoria> listarTodasLasCategorias();

    Optional<Categoria> buscarPorId(Long id);

    Optional<Categoria> buscarPorNombre(String nombre);

    Categoria guardarCategoria(Categoria categoria);

    void eliminarCategoria(Long id);
}