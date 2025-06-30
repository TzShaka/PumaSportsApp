package com.example.ms.proveedor2.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProveedorDTO {

    @Id
    private Long id;

    // ✅ Código del proveedor
    @Size(max = 100, message = "El código del proveedor no puede exceder 100 caracteres")
    private String codigoProveedor;

    @NotBlank(message = "El nombre de la empresa es obligatorio")
    @Size(max = 255, message = "El nombre de la empresa no puede exceder 255 caracteres")
    private String nombreEmpresa;

    @Size(max = 255, message = "El contacto no puede exceder 255 caracteres")
    private String contacto;

    @Email(message = "El formato del correo electrónico no es válido")
    @Size(max = 255, message = "El correo electrónico no puede exceder 255 caracteres")
    private String correoElectronico;

    @Size(max = 50, message = "El teléfono no puede exceder 50 caracteres")
    private String telefono;

    private String direccion;

    @Size(max = 100, message = "La ciudad no puede exceder 100 caracteres")
    private String ciudad;

    @Size(max = 100, message = "El país no puede exceder 100 caracteres")
    private String pais;

    // 🔥 CAMPO FALTANTE - Fecha de creación
    private LocalDateTime fechaCreacion;

    // ✅ Campo activo
    private Boolean activo;

    // ✅ Constructor vacío
    public ProveedorDTO() {
    }

    // ✅ Constructor con todos los parámetros (ACTUALIZADO)
    public ProveedorDTO(Long id, String codigoProveedor, String nombreEmpresa, String contacto,
                        String correoElectronico, String telefono, String direccion,
                        String ciudad, String pais, LocalDateTime fechaCreacion, Boolean activo) {
        this.id = id;
        this.codigoProveedor = codigoProveedor;
        this.nombreEmpresa = nombreEmpresa;
        this.contacto = contacto;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.pais = pais;
        this.fechaCreacion = fechaCreacion;
        this.activo = activo;
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

    // ✅ Getter y Setter para activo
    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}