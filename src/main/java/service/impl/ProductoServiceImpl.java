package com.inksticker.service.impl;

import com.inksticker.entity.Producto;
import com.inksticker.repository.ProductoRepository;
import com.inksticker.service.ProductoService;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Producto> listarProductosActivos(Pageable pageable) {
        return productoRepository.findByActivoTrue(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Producto> listarProductosPorCategoria(
            Long categoriaId, Pageable pageable) {
        return productoRepository.findByCategoriaIdAndActivoTrue(
                categoriaId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarProductosPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaIdAndActivoTrue(categoriaId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarProductosActivos() {
        return productoRepository.findByActivoTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Producto> buscarProductos(String nombre, Pageable pageable) {
        return productoRepository.findByNombreContainingIgnoreCaseAndActivoTrue(
                nombre, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public void eliminarProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Producto no encontrado."));

        producto.setActivo(false);
        productoRepository.save(producto);
    }
}