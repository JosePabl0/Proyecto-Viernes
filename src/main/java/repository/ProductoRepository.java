package com.inksticker.repository;

import com.inksticker.entity.Producto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Page<Producto> findByActivoTrue(Pageable pageable);

    Page<Producto> findByCategoriaIdAndActivoTrue(
            Long categoriaId, Pageable pageable);

    List<Producto> findByCategoriaIdAndActivoTrue(Long categoriaId);

    List<Producto> findByActivoTrue();

    Page<Producto> findByNombreContainingIgnoreCaseAndActivoTrue(
            String nombre, Pageable pageable);

    boolean existsByNombre(String nombre);

    void deleteByNombreIn(List<String> nombres);
}