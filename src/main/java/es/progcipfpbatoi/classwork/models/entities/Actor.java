package es.progcipfpbatoi.classwork.models.entities;

public class Actor {
    private int id;
    private String nombre;

    public Actor(int id, String nombre) {
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
