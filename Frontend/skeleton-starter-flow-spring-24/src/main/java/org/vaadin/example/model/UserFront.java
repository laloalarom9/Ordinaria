package org.vaadin.example.model;

import java.util.UUID;
import org.vaadin.example.model.DireccionFront;
import org.vaadin.example.model.MetodoPagoFront;

public class UserFront {
    private UUID id;
    private String nombre;
    private String apellidos;
    private String nif;
    private DireccionFront direccion;
    private String email;
    private MetodoPagoFront metodoPago;

    // Getters y setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public DireccionFront getDireccion() {
        return direccion;
    }

    public void setDireccion(DireccionFront direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MetodoPagoFront getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPagoFront metodoPago) {
        this.metodoPago = metodoPago;
    }
}
