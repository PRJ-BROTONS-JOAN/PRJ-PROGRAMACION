package es.progcipfpbatoi.classwork.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;

public class PeliculaController {
	@PostMapping("/peliculas/editar")
	public String editarPelicula(HttpServletRequest request) {
	    int id = Integer.parseInt(request.getParameter("id"));
	    String titulo = request.getParameter("titulo");
	    int duracion = Integer.parseInt(request.getParameter("duracion"));
	    String genero = request.getParameter("genero");
	    String calificacion = request.getParameter("calificacion");

	    try (Connection conn = Connection.z()) {
	        String sql = "UPDATE peliculas SET titulo = ?, duracion = ?, genero = ?, calificacion = ? WHERE id = ?";
	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, titulo);
	            stmt.setInt(2, duracion);
	            stmt.setString(3, genero);
	            stmt.setString(4, calificacion);
	            stmt.setInt(5, id);
	            stmt.executeUpdate();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return "redirect:/peliculas";
	}
}
