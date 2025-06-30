package com.example.ms_cliente.service;

import com.example.ms_cliente.entity.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteService {
    List<Cliente> listar();
    Optional<Cliente> buscar(Integer id);
    Cliente guardar(Cliente cliente);
    Cliente actualizar(Integer id, Cliente cliente);
    void eliminar(Integer id);

    // 🆕 Nuevos métodos para búsqueda
    List<Cliente> buscarPorNombre(String nombre);
    boolean existeEmail(String email);
}