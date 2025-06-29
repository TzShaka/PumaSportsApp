//    package com.example.ms.proveedor2.controller;
//
//    import com.example.ms.proveedor2.dto.ProveedorDTO;
//    import com.example.ms.proveedor2.service.ProveedorService;
//    import jakarta.validation.Valid;
//    import lombok.RequiredArgsConstructor;
//    import org.slf4j.Logger;
//    import org.slf4j.LoggerFactory;
//    import org.springframework.beans.factory.annotation.Autowired;
//    import org.springframework.http.HttpStatus;
//    import org.springframework.http.ResponseEntity;
//    import org.springframework.web.bind.annotation.*;
//
//    import java.util.List;
//
//    @RestController
//    @RequestMapping("/api/proveedores")
//    @RequiredArgsConstructor
//    @CrossOrigin(origins = "*")
//    public class ProveedorController {
//
//
//        @Autowired
//        private ProveedorService proveedorService;
//
//        private static final Logger log = LoggerFactory.getLogger(ProveedorController.class);
//
//        // GET /api/proveedores - Obtener todos los proveedores
//        @GetMapping
//        public ResponseEntity<List<ProveedorDTO>> getAllProveedores() {
//            List<ProveedorDTO> proveedores = proveedorService.getAllProveedores();
//            return ResponseEntity.ok(proveedores);
//        }
//
////         GET /api/proveedores/{id} - Obtener proveedor por ID
//        @GetMapping("/{id}")
//        public ResponseEntity<ProveedorDTO> getProveedorById(@PathVariable Long id) {
//            ProveedorDTO proveedor = proveedorService.getProveedorById(id);
//            return ResponseEntity.ok(proveedor);
//        }
//
//
////        @GetMapping("/{id}")
////        public ResponseEntity<?> getProveedorById(@PathVariable("id") Long id) {
////            try {
////                log.info("Buscando proveedor con ID: {}", id);
////
////                if (id == null || id <= 0) {
////                    log.warn("ID inválido: {}", id);
////                    return ResponseEntity.badRequest()
////                            .body(Map.of("error", "El ID debe ser un número positivo"));
////                }
////
////                ProveedorDTO proveedor = proveedorService.getProveedorById(id);
////                log.info("Proveedor encontrado: {}", proveedor.getNombreEmpresa());
////                return ResponseEntity.ok(proveedor);
////
////            } catch (Exception e) {
////                log.error("Error al buscar proveedor con ID {}: {}", id, e.getMessage());
////                return ResponseEntity.status(HttpStatus.NOT_FOUND)
////                        .body(Map.of("error", "Proveedor no encontrado con ID: " + id));
////            }
////        }
//
//
//
//
//
//        // POST /api/proveedores - Crear nuevo proveedor
//        @PostMapping
//        public ResponseEntity<ProveedorDTO> createProveedor(@Valid @RequestBody ProveedorDTO proveedorDTO) {
//            ProveedorDTO nuevoProveedor = proveedorService.createProveedor(proveedorDTO);
//            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProveedor);
//        }
//
//        // PUT /api/proveedores/{id} - Actualizar proveedor
//        @PutMapping("/{id}")
//        public ResponseEntity<ProveedorDTO> updateProveedor(@PathVariable Long id,
//                                                            @Valid @RequestBody ProveedorDTO proveedorDTO) {
//            ProveedorDTO proveedorActualizado = proveedorService.updateProveedor(id, proveedorDTO);
//            return ResponseEntity.ok(proveedorActualizado);
//        }
//
//        // DELETE /api/proveedores/{id} - Eliminar proveedor
//        @DeleteMapping("/{id}")
//        public ResponseEntity<Void> deleteProveedor(@PathVariable Long id) {
//            proveedorService.deleteProveedor(id);
//            return ResponseEntity.noContent().build();
//        }
//
//        // GET /api/proveedores/search?nombre={nombre} - Buscar por nombre de empresa
//        @GetMapping("/search")
//        public ResponseEntity<List<ProveedorDTO>> searchProveedores(@RequestParam String nombre) {
//            List<ProveedorDTO> proveedores = proveedorService.searchByNombreEmpresa(nombre);
//            return ResponseEntity.ok(proveedores);
//        }
//
//        // GET /api/proveedores/pais/{pais} - Obtener proveedores por país
//        @GetMapping("/pais/{pais}")
//        public ResponseEntity<List<ProveedorDTO>> getProveedoresByPais(@PathVariable String pais) {
//            List<ProveedorDTO> proveedores = proveedorService.getProveedoresByPais(pais);
//            return ResponseEntity.ok(proveedores);
//        }
//
//        // GET /api/proveedores/ciudad/{ciudad} - Obtener proveedores por ciudad
//        @GetMapping("/ciudad/{ciudad}")
//        public ResponseEntity<List<ProveedorDTO>> getProveedoresByCiudad(@PathVariable String ciudad) {
//            List<ProveedorDTO> proveedores = proveedorService.getProveedoresByCiudad(ciudad);
//            return ResponseEntity.ok(proveedores);
//        }
//
//        // GET /api/proveedores/health - Health check endpoint
//        @GetMapping("/health")
//        public ResponseEntity<String> healthCheck() {
//            return ResponseEntity.ok("Proveedor Service is running!");
//        }
//    }



package com.example.ms.proveedor2.controller;

import com.example.ms.proveedor2.dto.ProveedorDTO;
import com.example.ms.proveedor2.service.ProveedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/proveedores")
@CrossOrigin(origins = "*")
public class ProveedorController {

    private static final Logger log = LoggerFactory.getLogger(ProveedorController.class);

    @Autowired
    private ProveedorService proveedorService;

    // Health Check - SIEMPRE debe funcionar
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        log.info("Health check called");
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Proveedor Service");
        response.put("timestamp", java.time.LocalDateTime.now().toString());
        return ResponseEntity.ok(response);
    }

    // GET /api/proveedores - Obtener todos los proveedores
    @GetMapping
    public ResponseEntity<List<ProveedorDTO>> getAllProveedores() {
        try {
            log.info("Obteniendo todos los proveedores");
            List<ProveedorDTO> proveedores = proveedorService.getAllProveedores();
            log.info("Se encontraron {} proveedores", proveedores.size());
            return ResponseEntity.ok(proveedores);
        } catch (Exception e) {
            log.error("Error al obtener proveedores: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // GET /api/proveedores/{id} - Obtener proveedor por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getProveedorById(@PathVariable("id") Long id) {
        try {
            log.info("Buscando proveedor con ID: {}", id);

            // Validación básica
            if (id == null || id <= 0) {
                log.warn("ID inválido: {}", id);
                Map<String, Object> error = new HashMap<>();
                error.put("error", "El ID debe ser un número positivo");
                error.put("id", id);
                return ResponseEntity.badRequest().body(error);
            }

            ProveedorDTO proveedor = proveedorService.getProveedorById(id);
            log.info("Proveedor encontrado: {}", proveedor.getNombreEmpresa());
            return ResponseEntity.ok(proveedor);

        } catch (Exception e) {
            log.error("Error al buscar proveedor con ID {}: {}", id, e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Proveedor no encontrado");
            error.put("id", id);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    // POST /api/proveedores - Crear nuevo proveedor
    @PostMapping
    public ResponseEntity<?> createProveedor(@Valid @RequestBody ProveedorDTO proveedorDTO) {
        try {
            log.info("Creando nuevo proveedor: {}", proveedorDTO.getNombreEmpresa());
            ProveedorDTO nuevoProveedor = proveedorService.createProveedor(proveedorDTO);
            log.info("Proveedor creado con ID: {}", nuevoProveedor.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProveedor);
        } catch (Exception e) {
            log.error("Error al crear proveedor: {}", e.getMessage(), e);
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    // PUT /api/proveedores/{id} - Actualizar proveedor
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProveedor(@PathVariable Long id, @Valid @RequestBody ProveedorDTO proveedorDTO) {
        try {
            log.info("Actualizando proveedor con ID: {}", id);
            ProveedorDTO proveedorActualizado = proveedorService.updateProveedor(id, proveedorDTO);
            log.info("Proveedor actualizado: {}", proveedorActualizado.getNombreEmpresa());
            return ResponseEntity.ok(proveedorActualizado);
        } catch (Exception e) {
            log.error("Error al actualizar proveedor con ID {}: {}", id, e.getMessage(), e);
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    // DELETE /api/proveedores/{id} - Eliminar proveedor
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProveedor(@PathVariable Long id) {
        try {
            log.info("Eliminando proveedor con ID: {}", id);
            proveedorService.deleteProveedor(id);
            log.info("Proveedor eliminado con ID: {}", id);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Proveedor eliminado correctamente");
            response.put("id", id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al eliminar proveedor con ID {}: {}", id, e.getMessage(), e);
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    // GET /api/proveedores/search?nombre={nombre} - Buscar por nombre de empresa
    @GetMapping("/search")
    public ResponseEntity<List<ProveedorDTO>> searchProveedores(@RequestParam String nombre) {
        try {
            log.info("Buscando proveedores por nombre: {}", nombre);
            List<ProveedorDTO> proveedores = proveedorService.searchByNombreEmpresa(nombre);
            log.info("Se encontraron {} proveedores con nombre: {}", proveedores.size(), nombre);
            return ResponseEntity.ok(proveedores);
        } catch (Exception e) {
            log.error("Error al buscar proveedores por nombre {}: {}", nombre, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // GET /api/proveedores/pais/{pais} - Obtener proveedores por país
    @GetMapping("/pais/{pais}")
    public ResponseEntity<List<ProveedorDTO>> getProveedoresByPais(@PathVariable String pais) {
        try {
            log.info("Buscando proveedores por país: {}", pais);
            List<ProveedorDTO> proveedores = proveedorService.getProveedoresByPais(pais);
            log.info("Se encontraron {} proveedores en {}", proveedores.size(), pais);
            return ResponseEntity.ok(proveedores);
        } catch (Exception e) {
            log.error("Error al buscar proveedores por país {}: {}", pais, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // GET /api/proveedores/ciudad/{ciudad} - Obtener proveedores por ciudad
    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<List<ProveedorDTO>> getProveedoresByCiudad(@PathVariable String ciudad) {
        try {
            log.info("Buscando proveedores por ciudad: {}", ciudad);
            List<ProveedorDTO> proveedores = proveedorService.getProveedoresByCiudad(ciudad);
            log.info("Se encontraron {} proveedores en {}", proveedores.size(), ciudad);
            return ResponseEntity.ok(proveedores);
        } catch (Exception e) {
            log.error("Error al buscar proveedores por ciudad {}: {}", ciudad, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/activos")
    public ResponseEntity<List<ProveedorDTO>> obtenerProveedoresActivos() {
        List<ProveedorDTO> proveedores = proveedorService.obtenerActivos();
        return ResponseEntity.ok(proveedores);
    }
}
