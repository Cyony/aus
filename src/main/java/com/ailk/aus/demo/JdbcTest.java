package com.ailk.aus.demo;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcTest {

	public static void main(String[] args) throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection(args[0], args[1], args[2]);
		System.out.println(connection.isValid(5000));
	}

}
