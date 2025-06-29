package com.example.msproducto.service.impl;

import com.example.msproducto.client.AlmacenClient;
import com.example.msproducto.client.DisenioClient;
import com.example.msproducto.dto.DisenioDTO;
import com.example.msproducto.dto.MaterialUsadoDTO;
import com.example.msproducto.dto.ProductoDTO;
import com.example.msproducto.entity.DetalleMaterial;
import com.example.msproducto.entity.Producto;
import com.example.msproducto.repository.ProductoRepository;
import com.example.msproducto.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final DisenioClient disenioClient;
    private final AlmacenClient almacenClient;

    @Override
    public Producto crearProducto(Producto producto) {
        // Obtener el diseño asociado desde ms-diseno
        DisenioDTO disenio = disenioClient.obtenerDisenioPorId(producto.getIdDisenio());

        if (disenio == null || disenio.getMaterialesUsados() == null || disenio.getMaterialesUsados().isEmpty()) {
            throw new RuntimeException("No se encontraron materiales para el diseño con ID " + producto.getIdDisenio());
        }

        List<DetalleMaterial> detalles = new ArrayList<>();

        for (MaterialUsadoDTO mat : disenio.getMaterialesUsados()) {
            double cantidadTotal = mat.getCantidadUsada() * producto.getCantidadProducida();

            // Validación: verificar stock antes de descontar
            Double stockDisponible = almacenClient.obtenerStock(mat.getIdMaterial());
            if (stockDisponible == null || stockDisponible < cantidadTotal) {
                throw new RuntimeException("Stock insuficiente para el material ID " + mat.getIdMaterial());
            }

            // Crear detalle del material
            DetalleMaterial detalle = new DetalleMaterial();
            detalle.setIdMaterial(mat.getIdMaterial());
            detalle.setNombreMaterial(mat.getNombreMaterial());
            detalle.setCantidadTotal(cantidadTotal);
            detalle.setProducto(producto);
            detalles.add(detalle);

            // Descontar stock en ms-almacen
            almacenClient.restarStock(mat.getIdMaterial(), cantidadTotal);
        }

        producto.setMaterialesConsumidos(detalles);

        return productoRepository.save(producto);
    }

    @Override
    public List<ProductoDTO> listarProductos() {
        List<Producto> productos = productoRepository.findAll();

        return productos.stream().map(producto -> {
            DisenioDTO disenio = disenioClient.obtenerDisenioPorId(producto.getIdDisenio());

            ProductoDTO dto = new ProductoDTO();
            dto.setIdProducto(producto.getIdProducto());
            dto.setNombreProducto(producto.getNombreProducto());
            dto.setCantidadProducida(producto.getCantidadProducida());
            dto.setDisenio(disenio);
            dto.setMaterialesConsumidos(producto.getMaterialesConsumidos());

            return dto;
        }).toList();
    }
}
