package com.example.ms_ventas.service.impl;

import com.example.ms_ventas.client.AlmacenClient;
import com.example.ms_ventas.client.ClienteClient;
import com.example.ms_ventas.dto.ClienteDTO;
import com.example.ms_ventas.dto.DetalleVentaDTO;
import com.example.ms_ventas.dto.VentaDTO;
import com.example.ms_ventas.entity.DetalleVenta;
import com.example.ms_ventas.entity.Venta;
import com.example.ms_ventas.repository.DetalleVentaRepository;
import com.example.ms_ventas.repository.VentaRepository;
import com.example.ms_ventas.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleVentaRepository;
    private final ClienteClient clienteClient;
    private final AlmacenClient almacenClient;

    @Override
    @Transactional
    public VentaDTO registrarVenta(VentaDTO ventaDTO) {
        ClienteDTO cliente;
        if (ventaDTO.getClienteId() != null) {
            cliente = clienteClient.obtenerClientePorId(ventaDTO.getClienteId());
        } else {
            cliente = clienteClient.crearCliente(
                    ClienteDTO.builder()
                            .id(null)
                            .nombre(ventaDTO.getClienteNombre())
                            .email(ventaDTO.getClienteEmail())
                            .telefono(ventaDTO.getClienteTelefono())
                            .build()
            );
        }

        Venta venta = Venta.builder()
                .clienteId(cliente.getId())
                .fecha(ventaDTO.getFecha())
                .total(0.0)
                .build();

        venta = ventaRepository.save(venta);

        List<DetalleVenta> detalles = new ArrayList<>();
        BigDecimal totalVenta = BigDecimal.ZERO;

        for (DetalleVentaDTO det : ventaDTO.getDetalles()) {
            almacenClient.descontarStock(det.getProductoId(), det.getCantidad());

            BigDecimal precio = BigDecimal.valueOf(det.getPrecioUnitario());
            BigDecimal cantidad = BigDecimal.valueOf(det.getCantidad());
            BigDecimal subtotal = precio.multiply(cantidad);
            BigDecimal impuesto = subtotal.multiply(BigDecimal.valueOf(0.18));
            BigDecimal descuento = det.getDescuento() != null
                    ? BigDecimal.valueOf(det.getDescuento())
                    : BigDecimal.ZERO;
            BigDecimal totalItem = subtotal.add(impuesto).subtract(descuento);

            DetalleVenta detalle = DetalleVenta.builder()
                    .venta(venta)
                    .productoId(det.getProductoId())
                    .descripcion(det.getDescripcion())
                    .cantidad(det.getCantidad())
                    .precioUnitario(det.getPrecioUnitario())
                    .subtotal(subtotal.doubleValue())
                    .impuesto(impuesto.doubleValue())
                    .descuento(descuento.doubleValue())
                    .totalItem(totalItem.doubleValue())
                    .build();

            detalle = detalleVentaRepository.save(detalle);
            detalles.add(detalle);

            totalVenta = totalVenta.add(totalItem);
        }

        venta.setTotal(totalVenta.doubleValue());
        ventaRepository.save(venta);

        List<DetalleVentaDTO> detalleDTOs = new ArrayList<>();
        for (DetalleVenta d : detalles) {
            detalleDTOs.add(DetalleVentaDTO.builder()
                    .id(d.getId())
                    .productoId(d.getProductoId())
                    .descripcion(d.getDescripcion())
                    .cantidad(d.getCantidad())
                    .precioUnitario(d.getPrecioUnitario())
                    .subtotal(d.getSubtotal())
                    .impuesto(d.getImpuesto())
                    .descuento(d.getDescuento())
                    .totalItem(d.getTotalItem())
                    .build());
        }

        return VentaDTO.builder()
                .id(venta.getId())
                .clienteId(cliente.getId())
                .clienteNombre(cliente.getNombre())
                .clienteEmail(cliente.getEmail())
                .clienteTelefono(cliente.getTelefono())
                .fecha(venta.getFecha())
                .total(totalVenta.doubleValue())
                .detalles(detalleDTOs)
                .build();
    }

    @Override
    public VentaDTO obtenerVentaPorId(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));

        List<DetalleVenta> listaDetalles = detalleVentaRepository.findByVentaId(venta.getId());
        List<DetalleVentaDTO> detalleDTOs = new ArrayList<>();
        for (DetalleVenta d : listaDetalles) {
            detalleDTOs.add(DetalleVentaDTO.builder()
                    .id(d.getId())
                    .productoId(d.getProductoId())
                    .descripcion(d.getDescripcion())
                    .cantidad(d.getCantidad())
                    .precioUnitario(d.getPrecioUnitario())
                    .subtotal(d.getSubtotal())
                    .impuesto(d.getImpuesto())
                    .descuento(d.getDescuento())
                    .totalItem(d.getTotalItem())
                    .build());
        }

        ClienteDTO cliente = clienteClient.obtenerClientePorId(venta.getClienteId());

        return VentaDTO.builder()
                .id(venta.getId())
                .clienteId(cliente.getId())
                .clienteNombre(cliente.getNombre())
                .clienteEmail(cliente.getEmail())
                .clienteTelefono(cliente.getTelefono())
                .fecha(venta.getFecha())
                .total(venta.getTotal())
                .detalles(detalleDTOs)
                .build();
    }

    @Override
    public List<VentaDTO> listarVentas() {
        List<Venta> ventas = ventaRepository.findAll();
        List<VentaDTO> listaDTO = new ArrayList<>();

        for (Venta v : ventas) {
            List<DetalleVenta> listaDetalles = detalleVentaRepository.findByVentaId(v.getId());
            List<DetalleVentaDTO> detalleDTOs = new ArrayList<>();
            for (DetalleVenta d : listaDetalles) {
                detalleDTOs.add(DetalleVentaDTO.builder()
                        .id(d.getId())
                        .productoId(d.getProductoId())
                        .descripcion(d.getDescripcion())
                        .cantidad(d.getCantidad())
                        .precioUnitario(d.getPrecioUnitario())
                        .subtotal(d.getSubtotal())
                        .impuesto(d.getImpuesto())
                        .descuento(d.getDescuento())
                        .totalItem(d.getTotalItem())
                        .build());
            }

            ClienteDTO cliente = clienteClient.obtenerClientePorId(v.getClienteId());

            listaDTO.add(VentaDTO.builder()
                    .id(v.getId())
                    .clienteId(cliente.getId())
                    .clienteNombre(cliente.getNombre())
                    .clienteEmail(cliente.getEmail())
                    .clienteTelefono(cliente.getTelefono())
                    .fecha(v.getFecha())
                    .total(v.getTotal())
                    .detalles(detalleDTOs)
                    .build());
        }

        return listaDTO;
    }

    @Override
    @Transactional
    public void eliminarVenta(Long id) {
        if (!ventaRepository.existsById(id)) {
            throw new RuntimeException("No existe venta con ID: " + id);
        }

        List<DetalleVenta> detalles = detalleVentaRepository.findByVentaId(id);
        for (DetalleVenta d : detalles) {
            almacenClient.descontarStock(d.getProductoId(), d.getCantidad());
        }

        detalleVentaRepository.deleteByVentaId(id);
        ventaRepository.deleteById(id);
    }


    // 🆕 Método agregado
    @Override
    @Transactional
    public VentaDTO actualizarVenta(Long id, VentaDTO ventaDTO) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));

        ClienteDTO cliente = clienteClient.obtenerClientePorId(ventaDTO.getClienteId());

        venta.setClienteId(cliente.getId());
        venta.setFecha(ventaDTO.getFecha());

        // 🔄 Revertir stock de detalles anteriores
        List<DetalleVenta> detallesAnteriores = detalleVentaRepository.findByVentaId(venta.getId());
        for (DetalleVenta d : detallesAnteriores) {
            almacenClient.descontarStock(d.getProductoId(), d.getCantidad());
        }

        // 🗑️ Eliminar detalles anteriores
        detalleVentaRepository.deleteByVentaId(venta.getId());

        List<DetalleVenta> nuevosDetalles = new ArrayList<>();
        BigDecimal totalVenta = BigDecimal.ZERO;

        for (DetalleVentaDTO det : ventaDTO.getDetalles()) {
            almacenClient.descontarStock(det.getProductoId(), det.getCantidad());

            BigDecimal precio = BigDecimal.valueOf(det.getPrecioUnitario());
            BigDecimal cantidad = BigDecimal.valueOf(det.getCantidad());
            BigDecimal subtotal = precio.multiply(cantidad);
            BigDecimal impuesto = subtotal.multiply(BigDecimal.valueOf(0.18));
            BigDecimal descuento = det.getDescuento() != null
                    ? BigDecimal.valueOf(det.getDescuento())
                    : BigDecimal.ZERO;
            BigDecimal totalItem = subtotal.add(impuesto).subtract(descuento);

            DetalleVenta detalle = DetalleVenta.builder()
                    .venta(venta)
                    .productoId(det.getProductoId())
                    .descripcion(det.getDescripcion())
                    .cantidad(det.getCantidad())
                    .precioUnitario(det.getPrecioUnitario())
                    .subtotal(subtotal.doubleValue())
                    .impuesto(impuesto.doubleValue())
                    .descuento(descuento.doubleValue())
                    .totalItem(totalItem.doubleValue())
                    .build();

            detalleVentaRepository.save(detalle);
            nuevosDetalles.add(detalle);

            totalVenta = totalVenta.add(totalItem);
        }

        venta.setTotal(totalVenta.doubleValue());
        ventaRepository.save(venta);

        List<DetalleVentaDTO> detalleDTOs = new ArrayList<>();
        for (DetalleVenta d : nuevosDetalles) {
            detalleDTOs.add(DetalleVentaDTO.builder()
                    .id(d.getId())
                    .productoId(d.getProductoId())
                    .descripcion(d.getDescripcion())
                    .cantidad(d.getCantidad())
                    .precioUnitario(d.getPrecioUnitario())
                    .subtotal(d.getSubtotal())
                    .impuesto(d.getImpuesto())
                    .descuento(d.getDescuento())
                    .totalItem(d.getTotalItem())
                    .build());
        }

        return VentaDTO.builder()
                .id(venta.getId())
                .clienteId(cliente.getId())
                .clienteNombre(cliente.getNombre())
                .clienteEmail(cliente.getEmail())
                .clienteTelefono(cliente.getTelefono())
                .fecha(venta.getFecha())
                .total(venta.getTotal())
                .detalles(detalleDTOs)
                .build();
    }
}