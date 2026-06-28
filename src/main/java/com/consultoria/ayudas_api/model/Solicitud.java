package com.consultoria.ayudas_api.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "solicitudes")

public class Solicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Esta linea autoincrementa el ID al añadir una solicitud
    private Long id;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private Double importe;

    @Column(nullable = false)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false) //Crea automáticamente una columna con Foreign Key llamada usuario_id
    private Usuario usuario;

    public Solicitud(String descripcion, Double importe, String estado, Usuario usuario) {
        this.descripcion = descripcion;
        this.importe = importe;
        this.estado = estado;
        this.usuario = usuario;
    }

    public Solicitud() {
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getImporte() {
        return importe;
    }


    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
