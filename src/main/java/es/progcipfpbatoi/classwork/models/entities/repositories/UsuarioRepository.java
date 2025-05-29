package es.progcipfpbatoi.classwork.models.entities.repositories;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.progcipfpbatoi.classwork.controllers.services.MariaDBConnection;
import es.progcipfpbatoi.classwork.models.entities.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UsuarioRepository {

    @Autowired
    private MariaDBConnection mariaDBConnection;

    public Usuario findByUsername(String username) {
        String sql = "SELECT * FROM USUARIO WHERE username = ?";
        
        try (Connection conn = mariaDBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                        rs.getInt("idusuario"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password"),
                        Usuario.Rol.valueOf(rs.getString("rol"))
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}