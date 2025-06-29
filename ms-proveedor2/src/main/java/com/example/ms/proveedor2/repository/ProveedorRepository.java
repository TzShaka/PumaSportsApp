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

    // ==================== BÚSQUEDAS POR CÓDIGO DE PROVEEDOR ====================

    // ✅ NUEVO - Buscar por código de proveedor
    Optional<Proveedor> findByCodigoProveedor(String codigoProveedor);

    // ✅ NUEVO - Verificar si existe un proveedor con ese código
    boolean existsByCodigoProveedor(String codigoProveedor);

    // ✅ NUEVO - Buscar por código de proveedor (ignora mayúsculas/minúsculas)
    Optional<Proveedor> findByCodigoProveedorIgnoreCase(String codigoProveedor);

    // ==================== BÚSQUEDAS POR NOMBRE DE EMPRESA ====================

    // Buscar por nombre de empresa (contiene texto)
    List<Proveedor> findByNombreEmpresaContainingIgnoreCase(String nombreEmpresa);

    // ✅ NUEVO - Buscar por nombre exacto de empresa
    Optional<Proveedor> findByNombreEmpresaIgnoreCase(String nombreEmpresa);

    // ==================== BÚSQUEDAS POR UBICACIÓN ====================

    // Buscar por país
    List<Proveedor> findByPaisIgnoreCase(String pais);

    // Buscar por ciudad
    List<Proveedor> findByCiudadIgnoreCase(String ciudad);

    // Buscar proveedores por país y ciudad
    @Query("SELECT p FROM Proveedor p WHERE LOWER(p.pais) = LOWER(:pais) AND LOWER(p.ciudad) = LOWER(:ciudad)")
    List<Proveedor> findByPaisAndCiudad(@Param("pais") String pais, @Param("ciudad") String ciudad);

    // ✅ NUEVO - Buscar por país y ciudad (solo activos)
    @Query("SELECT p FROM Proveedor p WHERE LOWER(p.pais) = LOWER(:pais) AND LOWER(p.ciudad) = LOWER(:ciudad) AND p.activo = true")
    List<Proveedor> findByPaisAndCiudadAndActivoTrue(@Param("pais") String pais, @Param("ciudad") String ciudad);

    // ==================== BÚSQUEDAS POR CORREO ELECTRÓNICO ====================

    // Buscar por correo electrónico
    Optional<Proveedor> findByCorreoElectronico(String correoElectronico);

    // Verificar si existe un proveedor con ese correo
    boolean existsByCorreoElectronico(String correoElectronico);

    // ✅ NUEVO - Buscar por correo (ignora mayúsculas/minúsculas)
    Optional<Proveedor> findByCorreoElectronicoIgnoreCase(String correoElectronico);

    // ==================== BÚSQUEDAS POR ESTADO (ACTIVO/INACTIVO) ====================

    // Buscar proveedores activos
    List<Proveedor> findByActivoTrue();

    // ✅ NUEVO - Buscar proveedores inactivos
    List<Proveedor> findByActivoFalse();

    // ✅ NUEVO - Buscar por ID solo si está activo
    @Query("SELECT p FROM Proveedor p WHERE p.id = :id AND p.activo = true")
    Optional<Proveedor> findByIdAndActivoTrue(@Param("id") Long id);

    // ✅ NUEVO - Buscar por ID solo si está inactivo
    @Query("SELECT p FROM Proveedor p WHERE p.id = :id AND p.activo = false")
    Optional<Proveedor> findByIdAndActivoFalse(@Param("id") Long id);

    // ==================== BÚSQUEDAS COMBINADAS ====================

    // ✅ NUEVO - Buscar por nombre de empresa (solo activos)
    List<Proveedor> findByNombreEmpresaContainingIgnoreCaseAndActivoTrue(String nombreEmpresa);

    // ✅ NUEVO - Buscar por país (solo activos)
    List<Proveedor> findByPaisIgnoreCaseAndActivoTrue(String pais);

    // ✅ NUEVO - Buscar por ciudad (solo activos)
    List<Proveedor> findByCiudadIgnoreCaseAndActivoTrue(String ciudad);

    // ==================== BÚSQUEDAS POR TELÉFONO ====================

    // ✅ NUEVO - Buscar por teléfono
    Optional<Proveedor> findByTelefono(String telefono);

    // ✅ NUEVO - Buscar por teléfono (contiene)
    List<Proveedor> findByTelefonoContaining(String telefono);

    // ==================== QUERIES PERSONALIZADAS ====================

    // ✅ CORREGIDO - Query mejorada para proveedores activos
    @Query("SELECT p FROM Proveedor p WHERE p.activo = true ORDER BY p.nombreEmpresa ASC")
    List<Proveedor> findAllActiveProveedoresOrderedByName();

    // ✅ NUEVO - Buscar proveedores por múltiples criterios
    @Query("SELECT p FROM Proveedor p WHERE " +
            "(:nombreEmpresa IS NULL OR LOWER(p.nombreEmpresa) LIKE LOWER(CONCAT('%', :nombreEmpresa, '%'))) AND " +
            "(:pais IS NULL OR LOWER(p.pais) = LOWER(:pais)) AND " +
            "(:ciudad IS NULL OR LOWER(p.ciudad) = LOWER(:ciudad)) AND " +
            "(:activo IS NULL OR p.activo = :activo)")
    List<Proveedor> findByMultipleCriteria(@Param("nombreEmpresa") String nombreEmpresa,
                                           @Param("pais") String pais,
                                           @Param("ciudad") String ciudad,
                                           @Param("activo") Boolean activo);

    // ✅ NUEVO - Contar proveedores activos
    @Query("SELECT COUNT(p) FROM Proveedor p WHERE p.activo = true")
    Long countActiveProveedores();

    // ✅ NUEVO - Contar proveedores inactivos
    @Query("SELECT COUNT(p) FROM Proveedor p WHERE p.activo = false")
    Long countInactiveProveedores();

    // ✅ NUEVO - Obtener proveedores agregados recientemente (últimos N)
    @Query("SELECT p FROM Proveedor p WHERE p.activo = true ORDER BY p.id DESC")
    List<Proveedor> findRecentActiveProveedores();

    // ✅ NUEVO - Buscar proveedores sin correo electrónico
    @Query("SELECT p FROM Proveedor p WHERE p.correoElectronico IS NULL AND p.activo = true")
    List<Proveedor> findActiveProveedoresWithoutEmail();

    // ✅ NUEVO - Buscar proveedores sin teléfono
    @Query("SELECT p FROM Proveedor p WHERE p.telefono IS NULL AND p.activo = true")
    List<Proveedor> findActiveProveedoresWithoutPhone();
}