package es.progcipfpbatoi.classwork.models.entities;

import java.sql.Date;

public class Pelicula extends Produccion {
    public Pelicula(int id, String titulo, String calificacion, 
                   Date fechaLanzamiento, int duracion, String genero, 
                   String guion, String productora, String urlTrailer, 
                   String poster, float valoracionTotal) {
        super(id, titulo, Tipo.pelicula, calificacion, fechaLanzamiento, 
              duracion, genero, guion, productora, urlTrailer, poster, 
              valoracionTotal);
    }
}