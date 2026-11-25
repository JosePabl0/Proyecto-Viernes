package com.inksticker.controller;

import com.inksticker.entity.Banner;
import com.inksticker.entity.Categoria;
import com.inksticker.entity.ConfiguracionEstudio;
import com.inksticker.entity.Producto;
import com.inksticker.entity.Usuario;
import com.inksticker.repository.BannerRepository;
import com.inksticker.repository.CategoriaRepository;
import com.inksticker.repository.ConfiguracionEstudioRepository;
import com.inksticker.repository.ProductoRepository;
import com.inksticker.repository.UsuarioRepository;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ConfiguracionEstudioRepository configuracionEstudioRepository;
    private final BannerRepository bannerRepository;
    private final UsuarioRepository usuarioRepository;

    public AdminController(
            ProductoRepository productoRepository,
            CategoriaRepository categoriaRepository,
            ConfiguracionEstudioRepository configuracionEstudioRepository,
            BannerRepository bannerRepository,
            UsuarioRepository usuarioRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.configuracionEstudioRepository = configuracionEstudioRepository;
        this.bannerRepository = bannerRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping({"", "/"})
    public String panelPrincipal(Principal principal, Model model) {
        Usuario adminActual = obtenerAdminActual(principal);
        long totalProductos = productoRepository.count();
        long totalCategorias = categoriaRepository.count();
        long totalBanners = bannerRepository.count();
        long totalUsuarios = usuarioRepository.count();

        List<Producto> ultimosProductos = productoRepository.findAll().stream()
                .sorted(Comparator.comparing(Producto::getId, Comparator.nullsLast(Comparator.reverseOrder())))
                .limit(5)
                .toList();

        List<String> avisosSistema = new ArrayList<>();
        if (totalBanners == 0) {
            avisosSistema.add("No hay banners registrados todavía en la base de datos.");
        }
        avisosSistema.add("Catálogo actual: " + totalProductos + " productos y " + totalCategorias + " categorías.");
        avisosSistema.add("Usuarios registrados en el sistema: " + totalUsuarios + ".");

        model.addAttribute("adminActual", adminActual);
        model.addAttribute("totalUsuarios", totalUsuarios);
        model.addAttribute("totalProductos", totalProductos);
        model.addAttribute("totalCategorias", totalCategorias);
        model.addAttribute("totalBanners", totalBanners);
        model.addAttribute("ultimosProductos", ultimosProductos);
        model.addAttribute("avisosSistema", avisosSistema);
        model.addAttribute("configEstudio", obtenerConfiguracionActual());
        return "admin/panel";
    }

    @GetMapping("/usuarios")
    public String gestionarUsuarios(Principal principal, Model model) {
        model.addAttribute("adminActual", obtenerAdminActual(principal));
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "admin/usuarios";
    }

    @GetMapping("/productos")
    public String gestionarProductos(Principal principal, Model model) {
        model.addAttribute("adminActual", obtenerAdminActual(principal));
        model.addAttribute("productos", productoRepository.findAllByOrderByIdDesc());
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "admin/productos";
    }

    @GetMapping("/pedidos")
    public String gestionarPedidos(Principal principal, Model model) {
        model.addAttribute("adminActual", obtenerAdminActual(principal));
        return "admin/pedidos";
    }

    @GetMapping("/roles")
    public String gestionarRoles(Principal principal, Model model) {
        model.addAttribute("adminActual", obtenerAdminActual(principal));
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "admin/roles";
    }

    @GetMapping("/reportes")
    public String verReportes(Principal principal, Model model) {
        model.addAttribute("adminActual", obtenerAdminActual(principal));
        model.addAttribute("totalProductos", productoRepository.count());
        model.addAttribute("totalCategorias", categoriaRepository.count());
        model.addAttribute("totalBanners", bannerRepository.count());
        model.addAttribute("totalUsuarios", usuarioRepository.count());
        return "admin/reportes";
    }

    @GetMapping("/configuracion")
    public String configuracionAdmin(Principal principal, Model model) {
        Usuario adminActual = obtenerAdminActual(principal);
        model.addAttribute("adminActual", adminActual);
        model.addAttribute("configEstudio", obtenerConfiguracionActual());
        return "admin/configuracion";
    }

    @PostMapping("/configuracion/perfil")
    public String actualizarPerfilAdmin(
            Principal principal,
            @RequestParam String nombre,
            @RequestParam(required = false) String telefono,
            @RequestParam(required = false) String provincia,
            @RequestParam(required = false) String canton,
            @RequestParam(required = false) String distrito,
            @RequestParam(required = false) String direccion,
            RedirectAttributes redirectAttributes) {

        Usuario adminActual = obtenerAdminActual(principal);
        if (adminActual != null) {
            adminActual.setNombre(nombre.trim());
            adminActual.setTelefono(telefono != null ? telefono.trim() : "");
            adminActual.setProvincia(provincia != null ? provincia.trim() : "");
            adminActual.setCanton(canton != null ? canton.trim() : "");
            adminActual.setDistrito(distrito != null ? distrito.trim() : "");
            adminActual.setDireccion(direccion != null ? direccion.trim() : "");
            usuarioRepository.save(adminActual);
            redirectAttributes.addFlashAttribute("exitoMsg", "Datos personales del administrador actualizados.");
        }
        return "redirect:/admin/configuracion";
    }

    @GetMapping("/catalogo")
    public String gestionarCatalogo(
            Principal principal,
            @RequestParam(name = "buscar", required = false) String buscar,
            @RequestParam(name = "categoriaId", required = false) Long categoriaId,
            @RequestParam(name = "editarProductoId", required = false) Long editarProductoId,
            @RequestParam(name = "editarCategoriaId", required = false) Long editarCategoriaId,
            Model model) {

        List<Producto> productos;
        if (buscar != null && !buscar.isBlank()) {
            productos = productoRepository.findByNombreContainingIgnoreCaseOrderByIdDesc(buscar.trim());
        } else if (categoriaId != null) {
            productos = productoRepository.findByCategoriaIdOrderByIdDesc(categoriaId);
        } else {
            productos = productoRepository.findAllByOrderByIdDesc();
        }

        Producto productoForm = new Producto();
        if (editarProductoId != null) {
            productoForm = productoRepository.findById(editarProductoId).orElse(new Producto());
        }

        Categoria categoriaForm = new Categoria();
        if (editarCategoriaId != null) {
            categoriaForm = categoriaRepository.findById(editarCategoriaId).orElse(new Categoria());
        }

        model.addAttribute("adminActual", obtenerAdminActual(principal));
        model.addAttribute("activeSection", "catalogo");
        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("productoForm", productoForm);
        model.addAttribute("categoriaForm", categoriaForm);
        model.addAttribute("buscarFiltro", buscar != null ? buscar : "");
        model.addAttribute("categoriaFiltroId", categoriaId);
        return "admin/catalogo";
    }

    @PostMapping("/productos/guardar")
    public String guardarProducto(
            @Valid @ModelAttribute("productoForm") Producto productoForm,
            BindingResult result,
            @RequestParam(name = "categoriaId", required = false) Long categoriaId,
            RedirectAttributes redirectAttributes) {

        if (categoriaId != null) {
            categoriaRepository.findById(categoriaId).ifPresent(productoForm::setCategoria);
        }

        if (productoForm.getCategoria() == null) {
            redirectAttributes.addFlashAttribute("errorMsg", "Debes seleccionar una categoria valida.");
            return "redirect:/admin/catalogo";
        }

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMsg", "Revisa los campos obligatorios del producto.");
            return "redirect:/admin/catalogo";
        }

        productoRepository.save(productoForm);
        redirectAttributes.addFlashAttribute("exitoMsg", "Producto guardado correctamente.");
        return "redirect:/admin/catalogo";
    }

    @PostMapping("/productos/{id}/toggle")
    public String alternarEstadoProducto(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productoRepository.findById(id).ifPresent(producto -> {
            producto.setActivo(!producto.isActivo());
            productoRepository.save(producto);
        });
        redirectAttributes.addFlashAttribute("exitoMsg", "Estado del producto actualizado.");
        return "redirect:/admin/catalogo";
    }

    @PostMapping("/productos/{id}/eliminar")
    public String eliminarProducto(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("exitoMsg", "Producto eliminado.");
        }
        return "redirect:/admin/catalogo";
    }

    @PostMapping("/categorias/guardar")
    public String guardarCategoria(
            @Valid @ModelAttribute("categoriaForm") Categoria categoriaForm,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMsg", "El nombre de la categoria es obligatorio.");
            return "redirect:/admin/catalogo";
        }

        categoriaRepository.save(categoriaForm);
        redirectAttributes.addFlashAttribute("exitoMsg", "Categoria guardada correctamente.");
        return "redirect:/admin/catalogo";
    }

    @PostMapping("/categorias/{id}/toggle")
    public String alternarEstadoCategoria(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        categoriaRepository.findById(id).ifPresent(categoria -> {
            categoria.setActiva(!categoria.isActiva());
            categoriaRepository.save(categoria);
        });
        redirectAttributes.addFlashAttribute("exitoMsg", "Estado de la categoria actualizado.");
        return "redirect:/admin/catalogo";
    }

    @PostMapping("/categorias/{id}/eliminar")
    public String eliminarCategoria(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            categoriaRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("exitoMsg", "Categoria eliminada.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMsg",
                    "No se puede eliminar la categoria porque tiene productos asociados. Puedes desactivarla.");
        }
        return "redirect:/admin/catalogo";
    }

    @GetMapping("/estudio")
    public String editarConfiguracionEstudio(Principal principal, Model model) {
        model.addAttribute("adminActual", obtenerAdminActual(principal));
        model.addAttribute("activeSection", "estudio");
        model.addAttribute("configEstudio", obtenerConfiguracionActual());
        return "admin/estudio";
    }

    @PostMapping("/estudio/guardar")
    public String guardarConfiguracionEstudio(
            @Valid @ModelAttribute("configEstudio") ConfiguracionEstudio configEstudio,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("activeSection", "estudio");
            model.addAttribute("errorMsg", "Verifica el nombre del estudio y el numero de WhatsApp.");
            return "admin/estudio";
        }

        configEstudio.setId(1L);
        configuracionEstudioRepository.save(configEstudio);
        redirectAttributes.addFlashAttribute("exitoMsg", "Configuracion del estudio guardada correctamente.");
        return "redirect:/admin/estudio";
    }

    @GetMapping("/banners")
    public String gestionarBanners(
            Principal principal,
            @RequestParam(name = "editarId", required = false) Long editarId,
            Model model) {

        Banner bannerForm = new Banner();
        if (editarId != null) {
            bannerForm = bannerRepository.findById(editarId).orElse(new Banner());
        }

        model.addAttribute("adminActual", obtenerAdminActual(principal));
        model.addAttribute("activeSection", "banners");
        model.addAttribute("banners", bannerRepository.findAllByOrderByOrdenAscIdAsc());
        model.addAttribute("bannerForm", bannerForm);
        return "admin/banners";
    }

    @PostMapping("/banners/guardar")
    public String guardarBanner(
            @Valid @ModelAttribute("bannerForm") Banner bannerForm,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMsg",
                    "El titulo y la imagen del banner son obligatorios.");
            return "redirect:/admin/banners";
        }

        bannerRepository.save(bannerForm);
        redirectAttributes.addFlashAttribute("exitoMsg", "Banner guardado correctamente.");
        return "redirect:/admin/banners";
    }

    @PostMapping("/banners/{id}/toggle")
    public String alternarEstadoBanner(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        bannerRepository.findById(id).ifPresent(banner -> {
            banner.setActivo(!banner.isActivo());
            bannerRepository.save(banner);
        });
        redirectAttributes.addFlashAttribute("exitoMsg", "Estado del banner actualizado.");
        return "redirect:/admin/banners";
    }

    @PostMapping("/banners/{id}/eliminar")
    public String eliminarBanner(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (bannerRepository.existsById(id)) {
            bannerRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("exitoMsg", "Banner eliminado.");
        }
        return "redirect:/admin/banners";
    }

    private Usuario obtenerAdminActual(Principal principal) {
        if (principal == null || principal.getName() == null) {
            return null;
        }
        return usuarioRepository.findByCorreoIgnoreCase(principal.getName()).orElse(null);
    }

    private ConfiguracionEstudio obtenerConfiguracionActual() {
        return configuracionEstudioRepository.findById(1L).orElseGet(() -> {
            ConfiguracionEstudio cfg = new ConfiguracionEstudio();
            cfg.setId(1L);
            cfg.setNombreEstudio("InkSticker Studio");
            cfg.setWhatsapp("50688888888");
            return cfg;
        });
    }
}