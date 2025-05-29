package es.progcipfpbatoi.classwork.models.entities;


import java.util.Date;

public class Valoracion {
    private int id;
    private Usuario usuario;
    private Produccion produccion;
    private int puntuacion;
    private String comentario;
    private Date fecha;

    public Valoracion(int id, Usuario usuario, Produccion produccion, 
                     int puntuacion, String comentario, Date fecha) {
        this.id = id;
        this.usuario = usuario;
        this.produccion = produccion;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
        this.fecha = fecha;
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

    public int getPuntuacion() {
        return puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public Date getFecha() {
        return fecha;
    }
}
