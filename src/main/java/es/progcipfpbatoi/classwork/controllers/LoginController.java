package es.progcipfpbatoi.classwork.controllers;

import java.awt.List;
import java.security.MessageDigest;
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
        try (Connection conn = mariaDBConnection.getConnection()) {
            String sql = "SELECT * FROM USUARIO WHERE username = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) {
                        model.addAttribute("error", true);
                        return "login";
                    }

                    String hashPasswordDB = rs.getString("password");
                    String passwordHash = sha256(password);

                    if (!passwordHash.equalsIgnoreCase(hashPasswordDB)) {
                        model.addAttribute("error", true);
                        return "login";
                    }

                    // Usuario y contraseña correctos, redirigir a página éxito
                    model.addAttribute("username", username);
                    return "loginSuccess";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", true);
            return "login";
        }
    }

    private String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
