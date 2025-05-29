package es.progcipfpbatoi.classwork.models.entities;


public class Director {
    private int id;
    private String nombre;

    public Director(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
