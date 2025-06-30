package com.example.ms.proveedor2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "proveedores")
@Data
@AllArgsConstructor
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // ✅ NUEVO CAMPO - Código del proveedor
    @Column(name = "codigo_proveedor", nullable = false, unique = true)
    @Size(max = 100, message = "El código del proveedor no puede exceder 100 caracteres")
    private String codigoProveedor;

    @Column(name = "activo")
    private Boolean activo;

    // 🔥 CAMPO FALTANTE - Este era el problema
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    public void prePersist() {
        if (activo == null) {
            activo = true;
        }
        // ✅ Generar código automáticamente si no existe
        if (codigoProveedor == null || codigoProveedor.isEmpty()) {
            codigoProveedor = generarCodigoProveedor();
        }
        // 🔥 ESTABLECER FECHA DE CREACIÓN AUTOMÁTICAMENTE
        if (fechaCreacion == null) {
            fechaCreacion = LocalDateTime.now();
        }
    }

    // ✅ Método para generar código automáticamente
    private String generarCodigoProveedor() {
        long timestamp = System.currentTimeMillis();
        int random = (int) (Math.random() * 1000);
        return "PROV_" + timestamp + "_" + random;
    }

    @Column(name = "nombre_empresa", nullable = false)
    @NotBlank(message = "El nombre de la empresa es obligatorio")
    @Size(max = 255, message = "El nombre de la empresa no puede exceder 255 caracteres")
    private String nombreEmpresa;

    @Column(name = "contacto")
    @Size(max = 255, message = "El contacto no puede exceder 255 caracteres")
    private String contacto;

    @Email(message = "El formato del correo electrónico no es válido")
    @Column(name = "correo_electronico", unique = true)
    private String correoElectronico;

    @Column(name = "telefono")
    @Size(max = 50, message = "El teléfono no puede exceder 50 caracteres")
    private String telefono;

    @Column(name = "direccion", columnDefinition = "TEXT")
    private String direccion;

    @Column(name = "ciudad")
    @Size(max = 100, message = "La ciudad no puede exceder 100 caracteres")
    private String ciudad;

    @Column(name = "pais")
    @Size(max = 100, message = "El país no puede exceder 100 caracteres")
    private String pais;

    // ✅ Constructor vacío
    public Proveedor() {
    }

    // ✅ Constructor personalizado ACTUALIZADO (incluye fechaCreacion)
    public Proveedor(String codigoProveedor, String nombreEmpresa, String contacto,
                     String correoElectronico, String telefono, String direccion,
                     String ciudad, String pais, LocalDateTime fechaCreacion) {
        this.codigoProveedor = codigoProveedor;
        this.nombreEmpresa = nombreEmpresa;
        this.contacto = contacto;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.pais = pais;
        this.fechaCreacion = fechaCreacion;
    }

    // ✅ Constructor SIN codigoProveedor ni fechaCreacion (se generan automático)
    public Proveedor(String nombreEmpresa, String contacto, String correoElectronico,
                     String telefono, String direccion, String ciudad, String pais) {
        this.nombreEmpresa = nombreEmpresa;
        this.contacto = contacto;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.pais = pais;
    }

    // ✅ Getters y Setters existentes...
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // ✅ Getter y Setter para codigoProveedor
    public String getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(String codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    // 🔥 NUEVOS - Getter y Setter para fechaCreacion
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }
}