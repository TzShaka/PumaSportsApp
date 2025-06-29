package com.example.ms.proveedor2.util;

import com.example.ms.proveedor2.entity.Proveedor;
import com.example.ms.proveedor2.repository.ProveedorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class ProveedorSeeder implements CommandLineRunner {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Override
    public void run(String... args) throws Exception {
        if (proveedorRepository.count() == 0) {
            Proveedor proveedor1 = new Proveedor();
            proveedor1.setNombreEmpresa("Tech Global S.A.");
            proveedor1.setContacto("Juan Pérez");
            proveedor1.setCorreoElectronico("contacto@techglobal.com");
            proveedor1.setTelefono("+51 999 888 777");
            proveedor1.setDireccion("Av. Siempre Viva 123, Lima");
            proveedor1.setCiudad("Lima");
            proveedor1.setPais("Perú");

            Proveedor proveedor2 = new Proveedor();
            proveedor2.setNombreEmpresa("Soluciones TI S.R.L.");
            proveedor2.setContacto("Ana Gómez");
            proveedor2.setCorreoElectronico("ana.gomez@solucionesti.com");
            proveedor2.setTelefono("+51 988 777 666");
            proveedor2.setDireccion("Calle Falsa 456, Arequipa");
            proveedor2.setCiudad("Arequipa");
            proveedor2.setPais("Perú");

            proveedorRepository.save(proveedor1);
            proveedorRepository.save(proveedor2);

            System.out.println("Seeder de proveedores ejecutado correctamente.");
        }
    }
}
