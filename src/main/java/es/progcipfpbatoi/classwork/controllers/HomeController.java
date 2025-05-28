package es.progcipfpbatoi.classwork.controllers;

import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.progcipfpbatoi.classwork.controllers.services.MariaDBConnection;

@Controller
public class HomeController {

    @Autowired
    private MariaDBConnection mariaDBConnection;

    @GetMapping("/home")
    public String mostrarHome(Model model) {
        try (Connection conn = mariaDBConnection.getConnection()) {
            // Obtener películas recomendadas
        	ArrayList<Map<String, Object>> peliculas = obtenerProducciones(conn, "pelicula");
            model.addAttribute("peliculas", peliculas);

            // Obtener series recomendadas
            ArrayList<Map<String, Object>> series = obtenerProducciones(conn, "serie");
            model.addAttribute("series", series);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "home";
    }

    private ArrayList<Map<String, Object>> obtenerProducciones(Connection conn, String tipo) throws SQLException {
        ArrayList<Map<String, Object>> producciones = new ArrayList<>();
        String sql = "SELECT titulo, poster FROM producciones WHERE tipo = ? ORDER BY valoracion DESC LIMIT 30";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tipo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> produccion = new HashMap<>();
                    produccion.put("titulo", rs.getString("titulo"));
                    produccion.put("poster", rs.getString("poster"));
                    producciones.add(produccion);
                }
            }
        }
        return producciones;
    }
}