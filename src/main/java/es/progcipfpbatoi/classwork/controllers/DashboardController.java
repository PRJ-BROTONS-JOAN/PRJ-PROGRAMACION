package es.progcipfpbatoi.classwork.controllers;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.progcipfpbatoi.classwork.models.repositories.ProduccionRepository;

@Controller
public class DashboardController {

    @Autowired
    private ProduccionRepository produccionRepository;

    @GetMapping("/dashboard")
    public String mostrarDashboard(Model model) {
        model.addAttribute("recommended", produccionRepository.findAllRecommendedProductions());
        model.addAttribute("movies", produccionRepository.findAllMovies());
        model.addAttribute("series", produccionRepository.findAllSeries());
        return "dashboard";
    }
}
