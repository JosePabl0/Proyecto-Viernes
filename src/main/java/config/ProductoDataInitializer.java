package com.inksticker.config;

import com.inksticker.entity.Categoria;
import com.inksticker.entity.Producto;
import com.inksticker.repository.CategoriaRepository;
import com.inksticker.repository.ProductoRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProductoDataInitializer implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    public ProductoDataInitializer(
            CategoriaRepository categoriaRepository,
            ProductoRepository productoRepository) {

        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {

        // Eliminar productos antiguos de prueba para evitar duplicados o mezcla de nombres.
        productoRepository.deleteByNombreIn(List.of(
                "Tatuajes Temporales Minimalistas",
                "Pack de Stickers Personalizados",
                "Tatuajes Temporales Florales",
                "Stickers para Laptop",
                "Bálsamo Labial Hidratante",
                "Bálsamo Natural de Coco",
                "Bálsamo Labial de Frutos Rojos",
                "Bálsamo Protector"
        ));

        // Buscar la categoría Bálsamos.
        Categoria categoriaBalsamos = categoriaRepository
                .findAll()
                .stream()
                .filter(c -> c.getNombre().equalsIgnoreCase("Bálsamos"))
                .findFirst()
                .orElse(null);

        if (categoriaBalsamos == null) {
            return;
        }

        // Producto 1
        crearProductoSiNoExiste(
                "Bálsamo Hidratante para Tatuajes",
                "Fórmula enriquecida con manteca de karité ideal "
                + "para mantener la piel hidratada y los colores "
                + "de tus tatuajes brillantes y protegidos.",
                "2500.00",
                "https://images.unsplash.com/photo-1608248597359-9d57a07004f2?auto=format&fit=crop&w=600&q=80",
                "Manteca de Karité y Vitamina E",
                "50 ml",
                "MÁS VENDIDO",
                categoriaBalsamos
        );

        // Producto 2
        crearProductoSiNoExiste(
                "Bálsamo Natural de Coco",
                "Bálsamo orgánico a base de aceite de coco natural "
                + "diseñado para calmar la piel recién tatuada y "
                + "acelerar la regeneración celular.",
                "3000.00",
                "https://images.unsplash.com/photo-1526947425960-945c6e72858f?auto=format&fit=crop&w=600&q=80",
                "Aceite de Coco Orgánico",
                "100 g",
                "NATURAL",
                categoriaBalsamos
        );

        // Producto 3
        crearProductoSiNoExiste(
                "Bálsamo Reparador de Frutos Rojos",
                "Protector y bálsamo con aroma a frutos rojos, "
                + "especial para la hidratación diaria de labios "
                + "y pequeñosstrateas de piel sensible.",
                "2750.00",
                "https://images.unsplash.com/photo-1556228720-195a672e8a03?auto=format&fit=crop&w=600&q=80",
                "Extracto de Frutos Rojos",
                "30 ml",
                "NUEVO",
                categoriaBalsamos
        );

        // Producto 4
        crearProductoSiNoExiste(
                "Bálsamo Protector Solar SPF 30",
                "Escudo protector diseñado específicamente para "
                + "cuidar tus tatuajes de los rayos UV, evitando "
                + "la decoloración prematura de la tinta.",
                "3500.00",
                "https://images.unsplash.com/photo-1598440947619-2c35fc9aa908?auto=format&fit=crop&w=600&q=80",
                "Filtro UV / Cera de Abeja",
                "60 ml",
                "PROTECCIÓN UV",
                categoriaBalsamos
        );
    }

    private void crearProductoSiNoExiste(
            String nombre,
            String descripcion,
            String precio,
            String imagen,
            String material,
            String medidas,
            String etiqueta,
            Categoria categoria) {

        if (productoRepository.existsByNombre(nombre)) {
            return;
        }

        Producto producto = new Producto(
                nombre,
                descripcion,
                new BigDecimal(precio),
                imagen,
                categoria
        );

        producto.setMaterial(material);
        producto.setMedidas(medidas);
        producto.setEtiqueta(etiqueta);
        producto.setActivo(true);

        productoRepository.save(producto);
    }
}