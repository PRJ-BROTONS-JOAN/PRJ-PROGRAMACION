package es.progcipfpbatoi.classwork.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import es.progcipfpbatoi.classwork.models.entities.Pelicula;
import es.progcipfpbatoi.classwork.models.entities.Produccion;
import es.progcipfpbatoi.classwork.models.entities.Serie;
import es.progcipfpbatoi.classwork.models.repositories.ProduccionRepository;

@Controller
public class ContenidoController {
	
	 @Autowired
	    private ProduccionRepository produccionRepository;

	    @GetMapping("/peliculas")
	    public String mostrarPeliculas(Model model) {
	        ArrayList<Produccion> peliculas = (ArrayList<Produccion>) produccionRepository.findAllMovies();
	        model.addAttribute("movies", peliculas);
	        return "peliculas"; // Se espera que exista peliculas.html
	    }

	    @GetMapping("/series")
	    public String mostrarSeries(Model model) {
	        ArrayList<Produccion> series = (ArrayList<Produccion>) produccionRepository.findAllSeries();
	        model.addAttribute("series", series);
	        return "series"; // Se espera que exista series.html
	    }
	    
	    @GetMapping("/contenidoPelicula/{id}")
	    public String mostrarDetallePelicula(@PathVariable("id") int id, Model model) {
	        Pelicula contenido = produccionRepository.findPeliculaById(id);
	        
	        if (contenido == null) {
	            return "redirect:/dashboard";
	        }
	        model.addAttribute("contenido", contenido);
	        return "detalle"; // detalle.html
	    }
	    
	    @GetMapping("/contenidoSerie/{id}")
	    public String mostrarDetalleSerie(@PathVariable("id") int id, Model model) {
	        Serie contenido = produccionRepository.findSerieById(id);
	        
	        if (contenido == null) {
	            return "redirect:/dashboard";
	        }
	        model.addAttribute("contenido", contenido);
	        return "detalle_serie"; // detalle.html
	    }
	    

}
