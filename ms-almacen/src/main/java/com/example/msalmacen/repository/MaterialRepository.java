package com.example.msalmacen.repository;

import com.example.msalmacen.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repositorio de Materiales.
 * Añade findByNombreMaterial para evitar duplicados al crear.
 */
public interface MaterialRepository extends JpaRepository<Material, Long> {
    Optional<Material> findByNombreMaterial(String nombreMaterial);
}
