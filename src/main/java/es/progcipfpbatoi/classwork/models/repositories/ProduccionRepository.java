package es.progcipfpbatoi.classwork.models.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.progcipfpbatoi.classwork.controllers.services.MariaDBConnection;
import es.progcipfpbatoi.classwork.models.entities.Actor;
import es.progcipfpbatoi.classwork.models.entities.Director;
import es.progcipfpbatoi.classwork.models.entities.Pelicula;
import es.progcipfpbatoi.classwork.models.entities.Produccion;
import es.progcipfpbatoi.classwork.models.entities.Serie;
import es.progcipfpbatoi.classwork.models.entities.Temporada;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProduccionRepository {

    @Autowired
    private MariaDBConnection mariaDBConnection;

    public List<Produccion> findAllRecommendedProductions() {
        return getContent(
            "SELECT p.* FROM PRODUCCION p " +
            "JOIN VALORACION v ON p.idproduccion = v.idproduccion " +
            "GROUP BY p.idproduccion ORDER BY AVG(v.puntuacion) DESC LIMIT 4");
    }

    public List<Produccion> findAllMovies() {
        return getContent(
            "SELECT p.* FROM PRODUCCION p " +
            "JOIN PELICULAS m ON p.idproduccion = m.idproduccion " +
            "ORDER BY p.fecha_lanzamiento DESC ");
    }

    public List<Produccion>findAllSeries() {
        return getContent(
            "SELECT p.* FROM PRODUCCION p " +
            "JOIN SERIES s ON p.idproduccion = s.idproduccion " +
            "ORDER BY p.fecha_lanzamiento DESC");
    }

    private List<Produccion> getContent(String query) {
        List<Produccion> contentList = new ArrayList<>();
        
        Connection conn = mariaDBConnection.getConnection();
        
        try (
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
                        rs.getDate("fecha_lanzamiento").toLocalDate(),
                        rs.getInt("duracion"), rs.getString("genero"), rs.getString("guion"), rs.getString("productora"), rs.getString("url_trailer"), rs.getString("poster"),
                        rs.getFloat("valoracion_total"), rs.getString("plataforma")

                    );
                    
                    
                    
                } else {
                    produccion = new Serie(
                    		rs.getInt("idproduccion"),
                    	    rs.getString("titulo"),
                    	    rs.getString("calificacion"),
                    	    rs.getDate("fecha_lanzamiento").toLocalDate(),
                    	    rs.getInt("duracion"),
                    	    rs.getString("genero"),
                    	    rs.getString("guion"),
                    	    rs.getString("productora"),
                    	    rs.getString("url_trailer"),
                    	    rs.getString("poster"),
                    	    rs.getFloat("valoracion_total"),
                    	    rs.getString("plataforma") // <- NUEVO CAMPO
                    	);
                }
                
                produccion.setActores(getActoresDeProduccion(produccion));
                produccion.setDirectores(getDirectoresDeProduccion(produccion));
                
                contentList.add(produccion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contentList;
    }
    
    private ArrayList<Actor> getActoresDeProduccion(Produccion produccion) {
        ArrayList<Actor> actores = new ArrayList<>();
        
        String sql = "SELECT a.idactor, a.nombre " +
                     "FROM ACTOR a " +
                     "JOIN PRODUCCION_ACTOR pa ON a.idactor = pa.idactor " +
                     "WHERE pa.idproduccion = ?";
        
        Connection conn = mariaDBConnection.getConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, produccion.getId()); 
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Actor actor = new Actor(
                        rs.getInt("idactor"),
                        rs.getString("nombre")
                    );
                    actores.add(actor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        
        return actores;
    }
    
    private ArrayList<Director> getDirectoresDeProduccion(Produccion produccion) {
        ArrayList<Director> directores = new ArrayList<>();

        String sql = "SELECT d.iddirector, d.nombre " +
                     "FROM DIRECTOR d " +
                     "JOIN PRODUCCION_DIRECTOR pd ON d.iddirector = pd.iddirector " +
                     "WHERE pd.idproduccion = ?";
        
        Connection conn = mariaDBConnection.getConnection();
        
        try (
            
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, produccion.getId());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Director director = new Director(
                        rs.getInt("iddirector"),
                        rs.getString("nombre")
                    );
                    directores.add(director);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return directores;
    }
    
    public Pelicula findPeliculaById(int id) {
    	return (Pelicula)findById(id, "pelicula");
    }
    
    public Serie findSerieById(int id) {
    	Serie serie = (Serie) findById(id, "serie");
    	serie.setTemporadas(getTemporadasBySerie(id));
		return serie;
    }
    
    private List<Temporada> getTemporadasBySerie(int id) {
    	List<Temporada> temporadas = new ArrayList<>();
        String sql = "SELECT * FROM TEMPORADAS WHERE idproduccion = ? ORDER BY numero";

        try (Connection conn = mariaDBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Temporada temporada = new Temporada(
                        rs.getInt("idtemporada"),
                        rs.getInt("numero"),
                        rs.getInt("num_capitulos"),
                        rs.getString("plot"),
                        rs.getDate("fecha_lanzamiento").toLocalDate()
                    );
                    temporadas.add(temporada);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return temporadas;
    }
    
    private Produccion findById(int id, String tipo) {
        String query = "SELECT * FROM PRODUCCION WHERE idproduccion = ?";
        Connection conn = mariaDBConnection.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Produccion produccion;
                    
                    if ("pelicula".equalsIgnoreCase(tipo)) {
                        produccion = new Pelicula(
                            rs.getInt("idproduccion"),
                            rs.getString("titulo"),
                            rs.getString("calificacion"),
                            rs.getDate("fecha_lanzamiento").toLocalDate(),
                            rs.getInt("duracion"),
                            rs.getString("genero"),
                            rs.getString("guion"),
                            rs.getString("productora"),
                            rs.getString("url_trailer"),
                            rs.getString("poster"),
                            rs.getFloat("valoracion_total"),
                            rs.getString("plataforma")
                        );
                    } else {
                        produccion = new Serie(
                        		rs.getInt("idproduccion"),
                        	    rs.getString("titulo"),
                        	    rs.getString("calificacion"),
                        	    rs.getDate("fecha_lanzamiento").toLocalDate(),
                        	    rs.getInt("duracion"),
                        	    rs.getString("genero"),
                        	    rs.getString("guion"),
                        	    rs.getString("productora"),
                        	    rs.getString("url_trailer"),
                        	    rs.getString("poster"),
                        	    rs.getFloat("valoracion_total"),
                        	    rs.getString("plataforma") // <- NUEVO CAMPO
                        	);
                    }

                    produccion.setActores(getActoresDeProduccion(produccion));
                    produccion.setDirectores(getDirectoresDeProduccion(produccion));
                    return produccion;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
