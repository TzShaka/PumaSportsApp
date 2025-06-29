package com.example.ms_ventas.repository;

import com.example.ms_ventas.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    // Puedes agregar consultas personalizadas si lo necesitas
}
