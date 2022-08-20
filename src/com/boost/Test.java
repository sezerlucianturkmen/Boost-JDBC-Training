package com.boost;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {

	public static void main(String[] args) {

		Test test = new Test();
		String url = "jdbc:postgresql://localhost:5432/school";
		String username = "postgres";
		String password = "root";
		Connection connect = null;

		try {
			Driver.class.forName("org.postgresql.Driver");
			connect = DriverManager.getConnection(url, username, password);
			System.out.println("Connection is successful");

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

	public void howManyCity(Connection connection, String cityString) {

		String sqlString = "SELECT count(city) FROM student WHERE city='" + cityString + "'";

		try {
			PreparedStatement ps = connection.prepareCall(sqlString);
			ResultSet rs = ps.executeQuery();
			rs.next();
			System.out.println(rs.getInt(1));

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void create(Connection connection, Student student) {

		// try {
		// String sqlString = "INSERT INTO student(name,age,city) VALUES ('" +
		// student.getName() + "', '"
		// + student.getAge() + "','" + student.getCity() + "')";
		// ExecutiveQuery(connection, sqlString);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }

		String sqlString = "INSERT INTO student(name,age,city) VALUES (?,?,?)";
		try {
			PreparedStatement pStatement = connection.prepareStatement(sqlString);
			pStatement.setString(1, student.getName());
			pStatement.setInt(2, student.getAge());
			pStatement.setString(3, student.getCity());
			pStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void update(Connection connection, Student student, int id) {

		String sqlString = "UPDATE student SET name=?, age=?,city=? WHERE id=?";
		try {
			PreparedStatement pStatement = connection.prepareStatement(sqlString);
			pStatement.setString(1, student.getName());
			pStatement.setInt(2, student.getAge());
			pStatement.setString(3, student.getCity());
			pStatement.setInt(4, id);
			pStatement.executeUpdate();

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
