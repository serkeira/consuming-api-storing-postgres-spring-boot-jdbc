package br.serkeira.cats.client.service;

import java.util.List;

import br.serkeira.cats.model.entities.Cat;

public interface CatService {
	
	List<Cat> readFromApi();
	
	Cat readById(Long id);

	void create(Cat cat);
	
	boolean idExists(String id);
	
	List<Cat> readAllFromDB();

	void deleteAll();
	
	

}
