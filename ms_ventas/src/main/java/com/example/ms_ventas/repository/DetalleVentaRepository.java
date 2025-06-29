package com.example.ms_ventas.repository;

import com.example.ms_ventas.entity.DetalleVenta;
import com.example.ms_ventas.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {

    List<DetalleVenta> findByVentaId(Long ventaId);

    void deleteByVenta(Venta venta);

    void deleteByVentaId(Long ventaId);
}
