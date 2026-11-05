package com.inksticker.controller;

import com.inksticker.entity.Categoria;
import com.inksticker.entity.Producto;
import com.inksticker.service.CategoriaService;
import com.inksticker.service.ProductoService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class TiendaController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    public TiendaController(
            ProductoService productoService,
            CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    // Mostrar página principal de categorías o productos de una categoría
    @GetMapping("/tienda")
    public String mostrarTienda(
            @RequestParam(required = false) Long categoria,
            @RequestParam(defaultValue = "0") int pagina,
            Model model) {

        List<Categoria> categorias
                = categoriaService.listarCategoriasActivas();

        model.addAttribute("categorias", categorias);

        // Si no se seleccionó una categoría, mostrar la página de categorías
        if (categoria == null) {
            return "product/tienda";
        }

        // Buscar la categoría seleccionada
        Categoria categoriaSeleccionada = categoriaService
                .buscarPorId(categoria)
                .filter(Categoria::isActiva)
                .orElse(null);

        if (categoriaSeleccionada == null) {
            return "redirect:/tienda";
        }

        // Consultar productos activos de esa categoría
        int tamanoPagina =6;

        Pageable pageable = PageRequest.of(
                Math.max(0, pagina), tamanoPagina);

        Page<Producto> productos
                = productoService.listarProductosPorCategoria(
                        categoria, pageable);

        model.addAttribute("productos", productos);
        model.addAttribute("categoriaSeleccionada",
                categoriaSeleccionada);
        model.addAttribute("categoria", categoria);
        model.addAttribute("paginaActual", pagina);

        return "product/tienda";
    }

    // Mostrar detalle de un producto
    @GetMapping("/producto/{id}")
    public String mostrarDetalleProducto(
            @PathVariable Long id,
            Model model) {

        Producto producto = productoService.buscarPorId(id)
                .filter(Producto::isActivo)
                .orElse(null);

        if (producto == null) {
            return "redirect:/tienda";
        }

        List<Producto> recomendados = productoService
                .listarProductosPorCategoria(
                        producto.getCategoria().getId())
                .stream()
                .filter(p -> !p.getId().equals(producto.getId()))
                .limit(4)
                .toList();

        model.addAttribute("producto", producto);
        model.addAttribute("recomendados", recomendados);

        return "product/detalle";
    }
}
