//package com.example.ms.proveedor.repository;
//
//
//import com.example.ms.proveedor.entity.Proveedor;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
//
//    // Buscar por nombre de empresa
//    List<Proveedor> findByNombreEmpresaContainingIgnoreCase(String nombreEmpresa);
//
//    // Buscar por país
//    List<Proveedor> findByPaisIgnoreCase(String pais);
//
//    // Buscar por ciudad
//    List<Proveedor> findByCiudadIgnoreCase(String ciudad);
//
//    // Buscar por correo electrónico
//    Optional<Proveedor> findByCorreoElectronico(String correoElectronico);
//
//    // Verificar si existe un proveedor con ese correo
//    boolean existsByCorreoElectronico(String correoElectronico);
//
//    // Buscar proveedores por país y ciudad
//    @Query("SELECT p FROM Proveedor p WHERE p.pais = :pais AND p.ciudad = :ciudad")
//    List<Proveedor> findByPaisAndCiudad(@Param("pais") String pais, @Param("ciudad") String ciudad);
//
//    // Buscar proveedores activos (puedes agregar un campo estado más adelante)
//    @Query("SELECT p FROM Proveedor p WHERE p.nombreEmpresa IS NOT NULL AND p.correoElectronico IS NOT NULL")
//    List<Proveedor> findProveedoresActivos();
//}



package com.example.ms.proveedor2.repository;

import com.example.ms.proveedor2.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    // Buscar por nombre de empresa
    List<Proveedor> findByNombreEmpresaContainingIgnoreCase(String nombreEmpresa);

    // CORREGIDO: Método para buscar por ID (no debería devolver List)
    // Este método ya existe en JpaRepository como findById(Long id)
    // Si necesitas un método custom:
    @Query("SELECT p FROM Proveedor p WHERE p.id = :id AND p.activo = true")
    Optional<Proveedor> findByIdAndActivo(@Param("id") Long id);

    // Buscar por país
    List<Proveedor> findByPaisIgnoreCase(String pais);

    // Buscar por ciudad
    List<Proveedor> findByCiudadIgnoreCase(String ciudad);

    // Buscar por correo electrónico
    Optional<Proveedor> findByCorreoElectronico(String correoElectronico);

    // Verificar si existe un proveedor con ese correo
    boolean existsByCorreoElectronico(String correoElectronico);



    // Buscar proveedores por país y ciudad
    @Query("SELECT p FROM Proveedor p WHERE p.pais = :pais AND p.ciudad = :ciudad")
    List<Proveedor> findByPaisAndCiudad(@Param("pais") String pais, @Param("ciudad") String ciudad);

    // Buscar proveedores activos
    @Query("SELECT p FROM Proveedor p WHERE p.activo = true")
    List<Proveedor> findProveedoresActivos();

    // Buscar todos los proveedores activos (método adicional)
    List<Proveedor> findByActivoTrue();
}