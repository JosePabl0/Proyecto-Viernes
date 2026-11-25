package com.inksticker.repository;

import com.inksticker.entity.Banner;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {

    List<Banner> findByActivoTrueOrderByOrdenAscIdAsc();

    List<Banner> findByTipoAndActivoTrueOrderByOrdenAscIdAsc(String tipo);

    List<Banner> findAllByOrderByOrdenAscIdAsc();
}