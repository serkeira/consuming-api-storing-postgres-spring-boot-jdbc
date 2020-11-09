package br.serkeira.cats.dao.dao;

import java.util.List;

import br.serkeira.cats.model.entities.Cat;

public interface CatDao {
	
	List<Cat> readAll();
	
	void create(Cat entity);
	
	Cat readById(Long id);
	
	boolean update(Cat entity);
	
	boolean deleteById(Long id);

}
