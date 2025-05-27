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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.progcipfpbatoi.classwork.controllers.services.MariaDBConnection;


@Controller
public class LoginController {
	@Autowired
    private MariaDBConnection mariaDBConnection;

    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("error", false);
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String username,
                               @RequestParam String password,
                               Model model) {
        try {
            Connection conn = mariaDBConnection.getConnection();

            // Buscar usuario con contraseña cifrada (SHA-256 como requerido)
            String sql = "SELECT * FROM USUARIO WHERE username = ? AND password = SHA2(?, 256)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                // Usuario o contraseña incorrectos
                model.addAttribute("error", true);
                return "login";
            }

            // Autenticación exitosa - redirigir a la página principal
            return "redirect:/main";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", true);
            return "login";
        }
    }

    @GetMapping("/main")
    public String mostrarMain(Model model) {
        try {
            Connection conn = mariaDBConnection.getConnection();
            
            // Obtener películas más recomendadas
            String peliculasSql = "SELECT * FROM PRODUCCION WHERE tipo = 'pelicula' ORDER BY valoracion_total DESC LIMIT 4";
            PreparedStatement peliculasStmt = conn.prepareStatement(peliculasSql);
            ResultSet peliculasRs = peliculasStmt.executeQuery();
            
            ArrayList<Map<String, Object>> peliculas = new ArrayList<>();
            while (peliculasRs.next()) {
                Map<String, Object> pelicula = new HashMap<>();
                pelicula.put("id", peliculasRs.getInt("id"));
                pelicula.put("titulo", peliculasRs.getString("titulo"));
                pelicula.put("poster", peliculasRs.getString("poster"));
                pelicula.put("valoracion", peliculasRs.getFloat("valoracion_total"));
                peliculas.add(pelicula);
            }
            
            // Obtener series más recomendadas
            String seriesSql = "SELECT * FROM PRODUCCION WHERE tipo = 'serie' ORDER BY valoracion_total DESC LIMIT 4";
            PreparedStatement seriesStmt = conn.prepareStatement(seriesSql);
            ResultSet seriesRs = seriesStmt.executeQuery();
            
            ArrayList<Map<String, Object>> series = new ArrayList<>();
            while (seriesRs.next()) {
                Map<String, Object> serie = new HashMap<>();
                serie.put("id", seriesRs.getInt("id"));
                serie.put("titulo", seriesRs.getString("titulo"));
                serie.put("poster", seriesRs.getString("poster"));
                serie.put("valoracion", seriesRs.getFloat("valoracion_total"));
                series.add(serie);
            }
            
            model.addAttribute("peliculas", peliculas);
            model.addAttribute("series", series);
            
            return "main";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login";
        }
    }

}
