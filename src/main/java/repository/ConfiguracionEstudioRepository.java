package com.inksticker.repository;

import com.inksticker.entity.ConfiguracionEstudio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracionEstudioRepository extends JpaRepository<ConfiguracionEstudio, Long> {
}