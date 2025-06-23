package org.vaadin.example.model;

public class MetodoPago {
    private long numeroTarjeta;
    private String nombreAsociado;

    // Getters y setters
    public long getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(long numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getNombreAsociado() {
        return nombreAsociado;
    }

    public void setNombreAsociado(String nombreAsociado) {
        this.nombreAsociado = nombreAsociado;
    }
}
