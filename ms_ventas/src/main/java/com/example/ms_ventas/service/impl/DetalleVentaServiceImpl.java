package com.example.ms_ventas.service.impl;

import com.example.ms_ventas.entity.DetalleVenta;
import com.example.ms_ventas.repository.DetalleVentaRepository;
import com.example.ms_ventas.service.DetalleVentaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleVentaServiceImpl implements DetalleVentaService {

    private final DetalleVentaRepository repository;

    public DetalleVentaServiceImpl(DetalleVentaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<DetalleVenta> obtenerPorVentaId(Long ventaId) {
        return repository.findByVentaId(ventaId);
    }

    @Override
    public Optional<DetalleVenta> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public DetalleVenta registrar(DetalleVenta detalleVenta) {
        return repository.save(detalleVenta);
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
