package br.serkeira.cats.client.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.serkeira.cats.client.service.CatService;
import br.serkeira.cats.model.entities.Cat;

@Controller
public class HomeController {

	@Autowired
	private CatService catService;



	@GetMapping("/")
	public String getHomePage(Model model) {

		List<Cat> cats = catService.readFromApi();

		if (cats != null && cats.size() != 0) {
			model.addAttribute("cats", cats);
			System.out.println("foram encontrados " + cats.size());

			String novaUrl = cats.get(0).getUrl();
			String novoId = cats.get(0).getId();
			Integer novoHeight = cats.get(0).getHeight();
			Integer novoWidth = cats.get(0).getWidth();
			
			Cat novoGato = new Cat(novoId, novaUrl, novoWidth,  novoHeight);
			
			System.out.println(novoGato);
			
			//catService.create(novoGato);
			
			try {
				System.out.println("tendando chamar o create");
				
				// checar se id ja existe...
				
				if (catService.idExists(novoGato.getId())) {
					System.out.println("HomeController: id ja existe, skipando...");
				} else {
					catService.create(novoGato);	
				}
				
				
					
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println("caiu no catch");
			}
			
		
			
			

		} else {

			model.addAttribute("cats", new ArrayList<Cat>());
			System.out.println("nenhum gato encontrado.");
		}

		return "home";

	}
	
	
	@GetMapping("/history")
	public String getHistoryPage(Model model) {
		
		List<Cat> cats = catService.readAllFromDB();
		
		if (cats != null && cats.size() != 0) {
			model.addAttribute("cats", cats);
			System.out.println("foram encontrados " + cats.size());
			
		} else {
			model.addAttribute("cats", new ArrayList<Cat>());
		}
		
		
		return "history";
	}
	
	
	@GetMapping("/delete/{id}")
	public String deleteById(@PathVariable String id) {
	  
		
		
	    return "redirect:/history";
	}
	
	@PostMapping("/delete")
	public String deleteAll() {
		
		catService.deleteAll();
		
		
		return "redirect:/history";
	}
	
	
	@GetMapping("/about")
	public String getAboutPage() {
		
		return "about";
	}

}
