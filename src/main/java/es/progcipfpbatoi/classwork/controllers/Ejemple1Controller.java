package es.progcipfpbatoi.classwork.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Ejemple1Controller {
	@GetMapping("/example1")
    @ResponseBody
    public String connectionEjemple() {
        
        try {
            // Cargar driver (opcional en versiones recientes)
                        
            String dbURL = "jdbc:mariadb://192.168.56.101/batoiflix";
                        
            Connection connection = DriverManager.getConnection(dbURL, "batoi", "1234");
            return "Conexión establecida con éxito";
        } catch (SQLException e) {
            return e.getMessage();
        } 
    }

}
