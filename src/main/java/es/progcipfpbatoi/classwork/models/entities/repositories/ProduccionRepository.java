package es.progcipfpbatoi.classwork.models.entities.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.progcipfpbatoi.classwork.controllers.services.MariaDBConnection;
import es.progcipfpbatoi.classwork.models.entities.Pelicula;
import es.progcipfpbatoi.classwork.models.entities.Produccion;
import es.progcipfpbatoi.classwork.models.entities.Serie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProduccionRepository {

    @Autowired
    private MariaDBConnection mariaDBConnection;

    public List<Produccion> getRecommendedContent() {
        return getContent(
            "SELECT p.* FROM PRODUCCION p " +
            "JOIN VALORACION v ON p.idproduccion = v.idproduccion " +
            "GROUP BY p.idproduccion ORDER BY AVG(v.puntuacion) DESC LIMIT 4");
    }

    public List<Produccion> getMovies() {
        return getContent(
            "SELECT p.* FROM PRODUCCION p " +
            "JOIN PELICULAS m ON p.idproduccion = m.idproduccion " +
            "ORDER BY p.fecha_lanzamiento DESC LIMIT 4");
    }

    public List<Produccion> getSeries() {
        return getContent(
            "SELECT p.* FROM PRODUCCION p " +
            "JOIN SERIES s ON p.idproduccion = s.idproduccion " +
            "ORDER BY p.fecha_lanzamiento DESC LIMIT 4");
    }

    private List<Produccion> getContent(String query) {
        List<Produccion> contentList = new ArrayList<>();
        
        try (Connection conn = mariaDBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Produccion produccion;
                String tipo = rs.getString("tipo");
                
                if (tipo.equalsIgnoreCase("pelicula")) {
                    produccion = new Pelicula(
                        rs.getInt("idproduccion"),
                        rs.getString("titulo"),
                        rs.getString("calificacion"),
                        rs.getDate("fecha_lanzamiento"),
                        0, rs.getString("poster"), tipo, tipo, tipo, tipo, 0
                    );
                } else {
                    produccion = new Serie(
                        rs.getInt("idproduccion"),
                        rs.getString("titulo"),
                        rs.getString("calificacion"),
                        rs.getDate("fecha_lanzamiento"),
                        0, rs.getString("poster"), tipo, tipo, tipo, tipo, 0
                    );
                }
                contentList.add(produccion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contentList;
    }
}
