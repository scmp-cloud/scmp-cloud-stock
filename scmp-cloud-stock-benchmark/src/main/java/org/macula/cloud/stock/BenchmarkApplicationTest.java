package org.macula.cloud.stock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BenchmarkApplicationTest {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection connetcion = DriverManager.getConnection(
				"jdbc:mysql://localhost/macula-cloud?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=CTT",
				"root", "macula@^cloud$")) {
			try (Statement statement = connetcion.createStatement()) {
				statement.addBatch("update user set name = '劉允嵩' where id = 1;");
				statement.executeBatch();
				System.out.println("OK");
			}
		}

	}

}
