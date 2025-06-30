package com.example.ms_cliente.repository;

import com.example.ms_cliente.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    // 🆕 Buscar clientes que contengan el nombre (case insensitive)
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);

    // 🆕 Verificar si existe un email
    boolean existsByEmail(String email);

    // 🆕 OPCIONAL: Buscar por email (case insensitive)
    List<Cliente> findByEmailContainingIgnoreCase(String email);
}