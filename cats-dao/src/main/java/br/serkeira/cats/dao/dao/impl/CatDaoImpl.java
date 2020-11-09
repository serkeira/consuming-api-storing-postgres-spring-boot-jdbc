package br.serkeira.cats.dao.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import br.serkeira.cats.dao.connection.ConnectionFactory;
import br.serkeira.cats.dao.dao.CatDao;
import br.serkeira.cats.model.entities.Cat;

@Repository

public class CatDaoImpl implements CatDao {

	@Override
	public List<Cat> readAll() {

		List<Cat> cats = new ArrayList<Cat>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = ConnectionFactory.getConnection();

			String sql = "SELECT * FROM cats; ";

			preparedStatement = connection.prepareStatement(sql);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Cat cat = new Cat();
				cat.setId(resultSet.getString("id"));
				cat.setUrl(resultSet.getString("url"));
				cat.setHeight(resultSet.getInt("height"));
				cat.setWidth(resultSet.getInt("width"));

				cats.add(cat);

			}

		} catch (Exception e) {

		} finally {

			ConnectionFactory.close(resultSet, preparedStatement, connection);

		}

		return cats;
	}

	@Override
	public void create(Cat entity) {
		
		System.out.println("iniciei o create");

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String sql = "INSERT INTO cats (id, url, height, width) ";
		sql += " VALUES (?, ?, ?, ?); ";

		//String id = "";

		
		try {

			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);

			preparedStatement = connection.prepareStatement(sql);
			System.out.println("CatDaoImpl tentando executar o SQL " + sql);

			preparedStatement.setString(1, entity.getId());
			preparedStatement.setString(2, entity.getUrl());
			preparedStatement.setInt(3, (int) entity.getHeight());
			preparedStatement.setInt(4, (int) entity.getWidth());

			preparedStatement.execute();
						
			connection.commit();


		} catch (Exception e) {
			System.out.println(e.getMessage());

			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}

	}

	@Override
	public Cat readById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Cat entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteById(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
