package es.progcipfpbatoi.classwork.models.entities;

import java.util.Date;

public class Visualizacion {
    private int id;
    private Usuario usuario;
    private Produccion produccion;
    private Date fecha;
    private int contador;

    public Visualizacion(int id, Usuario usuario, Produccion produccion, Date fecha, int contador) {
        this.id = id;
        this.usuario = usuario;
        this.produccion = produccion;
        this.fecha = fecha;
        this.contador = contador;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Produccion getProduccion() {
        return produccion;
    }

    public Date getFecha() {
        return fecha;
    }

    public int getContador() {
        return contador;
    }

    public void incrementarContador() {
        this.contador++;
    }
}
