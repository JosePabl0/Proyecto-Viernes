package com.inksticker.controller;

import com.inksticker.entity.ItemCarrito;
import com.inksticker.service.CarritoService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @GetMapping
    public String mostrarCarrito(Model model) {

        List<ItemCarrito> items = carritoService.obtenerItems();
        BigDecimal total = carritoService.calcularTotal();
        int cantidadProductos = carritoService.contarProductos();

        model.addAttribute("items", items);
        model.addAttribute("total", total);
        model.addAttribute("cantidadProductos", cantidadProductos);

        return "cart/carrito";
    }

    @PostMapping("/agregar")
    public String agregarProducto(
            @RequestParam Long productoId,
            @RequestParam(defaultValue = "1") int cantidad,
            RedirectAttributes redirectAttributes) {

        try {
            carritoService.agregarProducto(productoId, cantidad);
            redirectAttributes.addFlashAttribute(
                    "mensaje", "¡Producto agregado al carrito correctamente!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/tienda";
        }

        return "redirect:/carrito";
    }

    @PostMapping("/actualizar")
    public String actualizarCantidad(
            @RequestParam Long productoId,
            @RequestParam int cantidad,
            RedirectAttributes redirectAttributes) {

        carritoService.actualizarCantidad(productoId, cantidad);

        redirectAttributes.addFlashAttribute(
                "mensaje", "Cantidad actualizada correctamente.");

        return "redirect:/carrito";
    }

    @PostMapping("/eliminar")
    public String eliminarProducto(
            @RequestParam Long productoId,
            RedirectAttributes redirectAttributes) {

        carritoService.eliminarProducto(productoId);

        redirectAttributes.addFlashAttribute(
                "mensaje", "Producto eliminado del carrito.");

        return "redirect:/carrito";
    }

    @PostMapping("/vaciar")
    public String vaciarCarrito(RedirectAttributes redirectAttributes) {

        carritoService.vaciarCarrito();

        redirectAttributes.addFlashAttribute(
                "mensaje", "El carrito se ha vaciado correctamente.");

        return "redirect:/carrito";
    }
}
