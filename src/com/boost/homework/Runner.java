package com.boost.homework;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Runner {

	public static void main(String[] args) {

		String url = "jdbc:postgresql://localhost:5432/etrade2";
		String username = "postgres";
		String password = "root";
		Connection connect = null;
		Runner r = new Runner();

		try {
			Driver.class.forName("org.postgresql.Driver");
			connect = DriverManager.getConnection(url, username, password);
			System.out.println("Connection is successful");
			r.queryMethod1(connect);
			r.queryMethod2(connect, "a");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		} catch (SQLException e) {
			System.out.println("Connection is unsuccessful");
			e.printStackTrace();

		} finally {
			try {
				connect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Girilen Harfe başayan isimlerin göre kadın ve erkek sayılarını veren method
	 * 
	 * @param connection
	 * @param letter
	 */
	public void queryMethod2(Connection connection, String letter) {

		String sqlString = "SELECT u.gender, COUNT(*) FROM users AS u WHERE u.namesurname ILIKE '" + letter
				+ "%' GROUP BY u.gender ;";

		try {

			PreparedStatement ps = connection.prepareCall(sqlString);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				System.out.println(rs.getString(1) + "->" + rs.getInt(2));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Bu method çalıştığında doğumyıllarına göre kullanıcıların yaptıları toplam
	 * ödemeleri göstermektedir.
	 * 
	 * @param connection
	 */
	public void queryMethod1(Connection connection) {

		String sqlString = "SELECT EXTRACT(YEAR FROM u.birthdate ) AS \"BirthYear\",SUM(p.paymenttotal) FROM payments AS p\r\n"
				+ "INNER JOIN orders AS o ON p.orderid=o.id\r\n" + "INNER JOIN users AS u ON u.id=o.userid\r\n"
				+ "GROUP BY EXTRACT(YEAR FROM u.birthdate ) ORDER BY EXTRACT(YEAR FROM u.birthdate )";

		try {
			PreparedStatement ps = connection.prepareCall(sqlString);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getInt(1) + "->" + rs.getDouble(2));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void ExecutiveQuery(Connection connection, String sql) throws SQLException {

		try {
			PreparedStatement ps = connection.prepareCall(sql);
			ps.executeUpdate();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
