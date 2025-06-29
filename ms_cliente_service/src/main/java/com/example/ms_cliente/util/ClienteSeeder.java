package com.example.ms_cliente.util;

import com.example.ms_cliente.entity.Cliente;
import com.example.ms_cliente.repository.ClienteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ClienteSeeder implements CommandLineRunner {

    private final ClienteRepository clienteRepository;

    public ClienteSeeder(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void run(String... args) {
        if (clienteRepository.count() == 0) {
            Cliente cli1 = new Cliente(null, "Juan Pérez", "juan.perez@gmail.com", "987654321");
            Cliente cli2 = new Cliente(null, "María García", "maria.garcia@hotmail.com", "912345678");
            Cliente cli3 = new Cliente(null, "Luis Torres", "luis.torres@yahoo.com", "998877665");

            clienteRepository.save(cli1);
            clienteRepository.save(cli2);
            clienteRepository.save(cli3);

            System.out.println("Datos de clientes insertados correctamente.");
        } else {
            System.out.println("Los clientes ya existen, no se insertaron datos.");
        }
    }

}
