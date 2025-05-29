package es.progcipfpbatoi.classwork.models.entities;

public class Favorito {
    private Usuario usuario;
    private Produccion produccion;

    public Favorito(Usuario usuario, Produccion produccion) {
        this.usuario = usuario;
        this.produccion = produccion;
    }

    // Getters
    public Usuario getUsuario() {
        return usuario;
    }

    public Produccion getProduccion() {
        return produccion;
    }
}