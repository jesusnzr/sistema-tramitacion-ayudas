package com.consultoria.ayudas_api.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String rol; // Ejemplo: "SOLICITANTE" o "FUNCIONARIO"

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    //mappedby indica que la relación ya está configurada por el campo usuario de la otra clase, evitando crear una tabla intermedia innecesaria
    //cascade = CascadeType.ALL indica que al eliminar un usuario sus solicitudes se eliminan en cascada para no dejar datos huerfanos.
    private List<Solicitud> solicitudes;

    public Usuario() {
    }

    public Usuario(String email, String nombre, String rol) {
        this.email = email;
        this.nombre = nombre;
        this.rol = rol;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
