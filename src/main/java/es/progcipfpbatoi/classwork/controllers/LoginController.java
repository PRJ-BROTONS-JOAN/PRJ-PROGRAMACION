package es.progcipfpbatoi.classwork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.progcipfpbatoi.classwork.models.entities.Usuario;
import es.progcipfpbatoi.classwork.models.repositories.UsuarioRepository;

@Controller
public class LoginController {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String username,
                               @RequestParam String password,
                               Model model) {
        
        Usuario usuario = usuarioRepository.findByUsername(username);
        
        if (usuario == null || !usuario.checkPassword(password)) {
            model.addAttribute("error", true);
            return "login";
        }
        
        return "redirect:/dashboard";
    }

    @GetMapping("/logout")
    public String cerrarSesion() {
    	
        return "redirect:/login";
    }
}