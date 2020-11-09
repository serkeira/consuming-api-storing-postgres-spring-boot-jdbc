package br.serkeira.cats.client.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.serkeira.cats.client.service.CatService;
import br.serkeira.cats.dao.connection.ConnectionFactory;
import br.serkeira.cats.model.entities.Cat;

@Service
public class CatServiceImpl implements CatService {

	@Override
	public List<Cat> readFromApi() {

		Cat novoGato = new Cat();

		List<Cat> response = null;

		String endpoint = "https://api.thecatapi.com/v1/images/search";

		RestTemplate restTemplate = new RestTemplate();

		try {

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.TEXT_PLAIN);
			headers.set("x-api-key", "04c3ec97-40c6-4114-9b29-1ff7295a5ad6");

			HttpEntity<String> requestEntity = new HttpEntity<>("", headers);

			ResponseEntity<Cat[]> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET, requestEntity,
					Cat[].class);

			Cat[] cats = requestResponse.getBody();
			response = Arrays.asList(cats);

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}

		return response;
	}

	@Override
	public Cat readById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(Cat cat) {

		System.out.println("iniciei o create");

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		
		String sql = "INSERT INTO cats (id, url, height, width) ";
		sql += " VALUES (?, ?, ?, ?); ";

	
		try {

			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);

			preparedStatement = connection.prepareStatement(sql);
			System.out.println("CatServiceImpl tentando executar o SQL " + sql);

			preparedStatement.setString(1, cat.getId());
			preparedStatement.setString(2, cat.getUrl());
			preparedStatement.setInt(3, (int) cat.getHeight());
			preparedStatement.setInt(4, (int) cat.getWidth());

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
			ConnectionFactory.close(preparedStatement, connection);
		}

	}

	@Override
	public boolean idExists(String id) {

		System.out.println("iniciei a checagem com idExists pra ver se já existe no banco");

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String sql = "SELECT * FROM cats WHERE id = ?";

		try {

			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);

			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, id);

			resultSet = preparedStatement.executeQuery();
			connection.commit();

			if (resultSet.next()) {
				System.out.println("já existe esse id");
				return true;
			} else {
				System.out.println("ainda não existe esse id");
				System.out.println("criando o id no banco... " + id);
				return false;
			}

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

		return false;

	}

	@Override
	public List<Cat> readAllFromDB() {

		List<Cat> cats = new ArrayList<>();

		System.out.println("iniciei o readAllFromDB");

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String sql = "SELECT * FROM cats; ";

		try {

			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);

			preparedStatement = connection.prepareStatement(sql);

			resultSet = preparedStatement.executeQuery();

			connection.commit();

			while (resultSet.next()) {

				Cat novoGato = new Cat();

				novoGato.setId(resultSet.getString("id"));
				novoGato.setUrl(resultSet.getString("url"));
				novoGato.setHeight(resultSet.getInt("height"));
				novoGato.setWidth(resultSet.getInt("width"));
				
				cats.add(novoGato);
				System.out.println("O novo gato pego do banco foi: " + novoGato);

			}

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

		return cats;
	}

	@Override
	public void deleteAll() {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;


		String sql = "TRUNCATE TABLE cats; ";
		
		try {

			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);

			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.execute();
			

			
			connection.commit();

			System.out.println("CONTEUDO DA TABELA DELETADO ");
		
		} catch (Exception e) {
			System.out.println(e.getMessage());

			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		} finally {
			ConnectionFactory.close(preparedStatement, connection);
		}

		
		
	}

}
