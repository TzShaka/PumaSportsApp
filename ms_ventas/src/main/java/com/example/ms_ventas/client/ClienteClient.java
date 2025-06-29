package com.example.ms_ventas.client;

import com.example.ms_ventas.dto.ClienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ms-cliente-service")
public interface ClienteClient {

    // 1. Obtener cliente por ID
    @GetMapping("/clientes/{id}")
    ClienteDTO obtenerClientePorId(@PathVariable("id") Long id);

    // 2. Buscar cliente por nombre
    @GetMapping("/clientes/buscar-por-nombre/{nombre}")
    ClienteDTO buscarPorNombre(@PathVariable("nombre") String nombre);

    // 3. Crear nuevo cliente
    @PostMapping("/clientes")
    ClienteDTO crearCliente(@RequestBody ClienteDTO clienteDTO);
}
