package es.progcipfpbatoi.classwork.models.entities;


import java.util.Date;

public class Historial {
    private Usuario usuario;
    private Produccion produccion;
    private Date fechaVisualizacion;

    public Historial(Usuario usuario, Produccion produccion, Date fechaVisualizacion) {
        this.usuario = usuario;
        this.produccion = produccion;
        this.fechaVisualizacion = fechaVisualizacion;
    }

    // Getters
    public Usuario getUsuario() {
        return usuario;
    }

    public Produccion getProduccion() {
        return produccion;
    }

    public Date getFechaVisualizacion() {
        return fechaVisualizacion;
    }
}
