package com.inksticker.service.impl;

import com.inksticker.entity.ItemCarrito;
import com.inksticker.entity.Producto;
import com.inksticker.repository.ProductoRepository;
import com.inksticker.service.CarritoService;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CarritoServiceImpl implements CarritoService {

    private static final String CLAVE_CARRITO = "carrito";

    private final ProductoRepository productoRepository;
    private final HttpSession session;

    public CarritoServiceImpl(
            ProductoRepository productoRepository,
            HttpSession session) {
        this.productoRepository = productoRepository;
        this.session = session;
    }

    @SuppressWarnings("unchecked")
    private List<ItemCarrito> obtenerCarrito() {
        List<ItemCarrito> carrito
                = (List<ItemCarrito>) session.getAttribute(CLAVE_CARRITO);

        if (carrito == null) {
            carrito = new ArrayList<>();
            session.setAttribute(CLAVE_CARRITO, carrito);
        }

        return carrito;
    }

    @Override
    public void agregarProducto(Long productoId, int cantidad) {

        if (cantidad <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad debe ser mayor que cero.");
        }

        Producto producto = productoRepository.findById(productoId)
                .filter(Producto::isActivo)
                .orElseThrow(() -> new IllegalArgumentException(
                        "El producto no existe o no está disponible."));

        List<ItemCarrito> carrito = obtenerCarrito();

        for (ItemCarrito item : carrito) {
            if (item.getProducto().getId().equals(productoId)) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }

        carrito.add(new ItemCarrito(producto, cantidad));
    }

    @Override
    public List<ItemCarrito> obtenerItems() {
        return new ArrayList<>(obtenerCarrito());
    }

    @Override
    public void actualizarCantidad(Long productoId, int cantidad) {

        if (cantidad <= 0) {
            eliminarProducto(productoId);
            return;
        }

        for (ItemCarrito item : obtenerCarrito()) {
            if (item.getProducto().getId().equals(productoId)) {
                item.setCantidad(cantidad);
                return;
            }
        }
    }

    @Override
    public void eliminarProducto(Long productoId) {

        Iterator<ItemCarrito> iterator = obtenerCarrito().iterator();

        while (iterator.hasNext()) {
            ItemCarrito item = iterator.next();

            if (item.getProducto().getId().equals(productoId)) {
                iterator.remove();
                return;
            }
        }
    }

    @Override
    public void vaciarCarrito() {
        obtenerCarrito().clear();
    }

    @Override
    public BigDecimal calcularTotal() {

        BigDecimal total = BigDecimal.ZERO;

        for (ItemCarrito item : obtenerCarrito()) {
            total = total.add(item.getSubtotal());
        }

        return total;
    }

    @Override
    public int contarProductos() {

        int cantidadTotal = 0;

        for (ItemCarrito item : obtenerCarrito()) {
            cantidadTotal += item.getCantidad();
        }

        return cantidadTotal;
    }
}
