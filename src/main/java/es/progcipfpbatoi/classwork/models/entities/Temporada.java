package es.progcipfpbatoi.classwork.models.entities;

import java.util.Date;

public class Temporada {
    private int id;
    private int numero;
    private int numCapitulos;
    private String plot;
    private Date fechaLanzamiento;

    public Temporada(int id, int numero, int numCapitulos, String plot, Date fechaLanzamiento) {
        this.id = id;
        this.numero = numero;
        this.numCapitulos = numCapitulos;
        this.plot = plot;
        this.fechaLanzamiento = fechaLanzamiento;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public int getNumero() {
        return numero;
    }

    public int getNumCapitulos() {
        return numCapitulos;
    }

    public String getPlot() {
        return plot;
    }

    public Date getFechaLanzamiento() {
        return fechaLanzamiento;
    }
}