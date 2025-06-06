package es.progcipfpbatoi.classwork.models.entities;


import java.time.LocalDate;
import java.util.List;

public class Serie extends Produccion {
    private List<Temporada> temporadas;

    public Serie(int idserie, String titulo, String calificacion, 
    		LocalDate fechaLanzamiento, int duracion, String genero, 
                String guion, String productora, String urlTrailer, 
                String poster, float valoracionTotal) {
        super(idserie, titulo, Tipo.serie, calificacion, fechaLanzamiento, 
              duracion, genero, guion, productora, urlTrailer, poster, 
              valoracionTotal);
    }

    public List<Temporada> getTemporadas() {
        return temporadas;
    }

    public void setTemporadas(List<Temporada> temporadas) {
        this.temporadas = temporadas;
    }
}
