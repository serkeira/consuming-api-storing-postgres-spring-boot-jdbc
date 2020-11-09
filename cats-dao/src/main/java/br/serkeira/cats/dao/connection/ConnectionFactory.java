package br.serkeira.cats.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionFactory {
	
	private static Connection connection = null;

    private static final String url = "jdbc:postgresql://localhost:49199/catsdb";
    private static final String user = "postgres";
    private static final String password = "admin";

    public static Connection getConnection() throws ClassNotFoundException {

	try {
		//Class.forName("org.postgresql.Driver");

	    connection = DriverManager.getConnection(url, user, password);

	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}

	return connection;

    }

    public static void closeResultSet(ResultSet resultSet) {

	try {
	    resultSet.close();
	} catch (SQLException e) {

	    e.printStackTrace();
	}

    }

    public static void closePreparedStatement(PreparedStatement preparedStatement) {
	try {
	    preparedStatement.close();
	} catch (SQLException e) {

	    e.printStackTrace();
	}
    }

    public static void closeConnection(Connection connection) {

	try {
	    connection.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	}

    }

    public static void close(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {

	closeResultSet(resultSet);
	closePreparedStatement(preparedStatement);
	closeConnection(connection);

    }

    public static void close(PreparedStatement preparedStatement, Connection connection) {

	closePreparedStatement(preparedStatement);
	closeConnection(connection);

    }

}
