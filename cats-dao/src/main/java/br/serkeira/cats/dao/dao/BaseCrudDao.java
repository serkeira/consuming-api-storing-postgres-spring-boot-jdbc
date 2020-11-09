package br.serkeira.cats.dao.dao;

import java.util.List;

public interface BaseCrudDao<T> {
	
	List<T> readAll();
	
	Long create(T entity);
	
	T readById(Long id);
	
	boolean update(T entity);
	
	boolean deleteById(Long id);

}
