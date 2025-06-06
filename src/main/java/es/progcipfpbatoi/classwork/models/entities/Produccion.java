package es.progcipfpbatoi.classwork.models.entities;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Produccion {
    private int idproduccion;
    private String titulo;
    private Tipo tipo;
    private String calificacion;
    private LocalDate fechaLanzamiento;
    private int duracion;
    private String genero;
    private String guion;
    private String productora;
    private String urlTrailer;
    private String poster;
    private float valoracionTotal;
    
    // Relaciones
    private List<Actor> actores;
    private List<Director> directores;

    public enum Tipo {
        pelicula, serie
    }

    public Produccion(int idproduccion, String titulo, Tipo tipo, String calificacion, 
                    LocalDate fechaLanzamiento, int duracion, String genero, 
                    String guion, String productora, String urlTrailer, 
                    String poster, float valoracionTotal) {
        this.idproduccion = idproduccion;
        this.titulo = titulo;
        this.tipo = tipo;
        this.calificacion = calificacion;
        this.fechaLanzamiento = fechaLanzamiento;
        this.duracion = duracion;
        this.genero = genero;
        this.guion = guion;
        this.productora = productora;
        this.urlTrailer = urlTrailer;
        this.poster = poster;
        this.valoracionTotal = valoracionTotal;
    }

    // Getters y setters
    public int getId() {
        return idproduccion;
    }

    public String getTitulo() {
        return titulo;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public int getDuracion() {
        return duracion;
    }

    public String getGenero() {
        return genero;
    }

    public String getGuion() {
        return guion;
    }

    public String getProductora() {
        return productora;
    }

    public String getUrlTrailer() {
        return urlTrailer;
    }

    public String getPoster() {
        return poster;
    }

    public float getValoracionTotal() {
        return valoracionTotal;
    }

    public List<Actor> getActores() {
        return actores;
    }

    public List<Director> getDirectores() {
        return directores;
    }

    public void setActores(List<Actor> actores) {
        this.actores = actores;
    }

    public void setDirectores(List<Director> directores) {
        this.directores = directores;
    }
}