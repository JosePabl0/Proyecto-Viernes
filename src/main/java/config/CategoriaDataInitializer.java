package com.inksticker.config;

import com.inksticker.entity.Categoria;
import com.inksticker.repository.CategoriaRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CategoriaDataInitializer implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;

    public CategoriaDataInitializer(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public void run(String... args) {

        List<Categoria> categoriasIniciales = List.of(
                new Categoria(
                        "Bálsamos",
                        "Productos y accesorios de cuidado.",
                        "https://images.unsplash.com/photo-1608248543803-ba4f8c70ae0b?auto=format&fit=crop&w=900&q=85"
                ),
                new Categoria(
                        "Liquidación",
                        "Productos disponibles en liquidación.",
                        "https://images.unsplash.com/photo-1542291026-7eec264c27ff?auto=format&fit=crop&w=900&q=85"
                ),
                new Categoria(
                        "Stencil",
                        "Materiales y diseños para stencil.",
                        "https://images.unsplash.com/photo-1513364776144-60967b0f800f?auto=format&fit=crop&w=900&q=85"
                ),
                new Categoria(
                        "Varios",
                        "Encuentra diferentes productos y accesorios.",
                        "https://images.unsplash.com/photo-1547891654-e66ed7ebb968?auto=format&fit=crop&w=900&q=85"
                ),
                new Categoria(
                        "Máquinas",
                        "Equipos y máquinas para tatuaje.",
                        "https://images.unsplash.com/photo-1565058379802-bbe93b2f703a?auto=format&fit=crop&w=900&q=85"
                ),
                new Categoria(
                        "Ropa y Accesorios",
                        "Ropa y complementos para tu estilo.",
                        "https://images.unsplash.com/photo-1483985988355-763728e1935b?auto=format&fit=crop&w=900&q=85"
                ),
                new Categoria(
                        "Equipo Médico",
                        "Artículos y equipo para uso profesional.",
                        "https://images.unsplash.com/photo-1584982751601-97dcc096659c?auto=format&fit=crop&w=900&q=85"
                ),
                new Categoria(
                        "Kits",
                        "Kits y conjuntos de productos.",
                        "https://images.unsplash.com/photo-1600185365483-26d7a4cc7519?auto=format&fit=crop&w=900&q=85"
                )
        );

        for (Categoria categoria : categoriasIniciales) {
            boolean existe = categoriaRepository
                    .existsByNombreIgnoreCase(categoria.getNombre());

            if (!existe) {
                categoriaRepository.save(categoria);
            }
        }
    }
}
