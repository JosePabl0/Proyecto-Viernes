package com.inksticker.service;

import com.inksticker.entity.Producto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductoService {

    Page<Producto> listarProductosActivos(Pageable pageable);

    Page<Producto> listarProductosPorCategoria(
            Long categoriaId, Pageable pageable);

    List<Producto> listarProductosPorCategoria(Long categoriaId);

    List<Producto> listarProductosActivos();

    Page<Producto> buscarProductos(String nombre, Pageable pageable);

    Optional<Producto> buscarPorId(Long id);

    Producto guardarProducto(Producto producto);

    void eliminarProducto(Long id);
}