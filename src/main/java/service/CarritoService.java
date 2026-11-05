package com.inksticker.service;

import com.inksticker.entity.ItemCarrito;
import java.math.BigDecimal;
import java.util.List;

public interface CarritoService {

    void agregarProducto(Long productoId, int cantidad);

    List<ItemCarrito> obtenerItems();

    void actualizarCantidad(Long productoId, int cantidad);

    void eliminarProducto(Long productoId);

    void vaciarCarrito();

    BigDecimal calcularTotal();

    int contarProductos();
}