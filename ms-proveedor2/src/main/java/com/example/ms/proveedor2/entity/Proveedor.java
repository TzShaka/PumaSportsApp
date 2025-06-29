//package com.example.ms.proveedor.entity;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;
//import jdk.jshell.Snippet;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
////@Table(name = "proveedores")
//@Data
////@NoArgsConstructor
////@AllArgsConstructor
//public class Proveedor {
//
////    @Id
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
////    private Long id;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
////    @Column(name = "codigo_proveedor")
//    private Long id;
//
//
//    @Column(name = "activo")
//    private Boolean activo;
//
//
//
//    @PrePersist
//    public void prePersist() {
//        if (activo == null) {
//            activo = true;
//        }
//    }
//
//
//    @Column(name = "nombre_empresa")
//    @NotBlank(message = "El nombre de la empresa es obligatorio")
//    @Size(max = 255, message = "El nombre de la empresa no puede exceder 255 caracteres")
//    private String nombreEmpresa;
//
//    @Column(name = "contacto")
//    @Size(max = 255, message = "El contacto no puede exceder 255 caracteres")
//    private String contacto;
//
//    @Column(name = "correo_electronico")
////    @Email(message = "El formato del correo electrónico no es válido")
////    @Size(max = 255, message = "El correo electrónico no puede exceder 255 caracteres")
//    private String correoElectronico;
//
//    @Column(name = "telefono")
//    @Size(max = 50, message = "El teléfono no puede exceder 50 caracteres")
//    private String telefono;
//
//    @Column(name = "direccion", columnDefinition = "TEXT")
//    private String direccion;
//
//    @Column(name = "ciudad")
//    @Size(max = 100, message = "La ciudad no puede exceder 100 caracteres")
//    private String ciudad;
//
//    @Column(name = "pais")
//    @Size(max = 100, message = "El país no puede exceder 100 caracteres")
//    private String pais;
//
//    public Proveedor() {
//    }
//
//    public Proveedor(Long id, String nombreEmpresa, String correoElectronico, String direccion, String ciudad, String pais, String telefono, String contacto) {
//        this.id = id;
//        this.nombreEmpresa = nombreEmpresa;
//        this.correoElectronico = correoElectronico;
//        this.direccion = direccion;
//        this.ciudad = ciudad;
//        this.pais = pais;
//        this.telefono = telefono;
//        this.contacto = contacto;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getNombreEmpresa() {
//        return nombreEmpresa;
//    }
//
//    public void setNombreEmpresa(String nombreEmpresa) {
//        this.nombreEmpresa = nombreEmpresa;
//    }
//
//    public String getContacto() {
//        return contacto;
//    }
//
//    public void setContacto(String contacto) {
//        this.contacto = contacto;
//    }
//
//    public String getCorreoElectronico() {
//        return correoElectronico;
//    }
//
//    public void setCorreoElectronico(String correoElectronico) {
//        this.correoElectronico = correoElectronico;
//    }
//
//    public String getTelefono() {
//        return telefono;
//    }
//
//    public void setTelefono(String telefono) {
//        this.telefono = telefono;
//    }
//
//    public String getDireccion() {
//        return direccion;
//    }
//
//    public void setDireccion(String direccion) {
//        this.direccion = direccion;
//    }
//
//    public String getCiudad() {
//        return ciudad;
//    }
//
//    public void setCiudad(String ciudad) {
//        this.ciudad = ciudad;
//    }
//
//    public String getPais() {
//        return pais;
//    }
//
//    public void setPais(String pais) {
//        this.pais = pais;
//    }
//}



package com.example.ms.proveedor2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "proveedores") // Agregado nombre de tabla
@Data
//@NoArgsConstructor
@AllArgsConstructor
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "activo")
    private Boolean activo;

    @PrePersist
    public void prePersist() {
        if (activo == null) {
            activo = true;
        }
    }

    @Column(name = "nombre_empresa", nullable = false)
    @NotBlank(message = "El nombre de la empresa es obligatorio")
    @Size(max = 255, message = "El nombre de la empresa no puede exceder 255 caracteres")
    private String nombreEmpresa;

    @Column(name = "contacto")
    @Size(max = 255, message = "El contacto no puede exceder 255 caracteres")
    private String contacto;

    @Email(message = "El formato del correo electrónico no es válido")
    @Column(name = "correo_electronico", unique = true) // Agregado unique constraint
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

    // Constructor personalizado que coincida con el uso en el servicio
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

    public Proveedor() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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