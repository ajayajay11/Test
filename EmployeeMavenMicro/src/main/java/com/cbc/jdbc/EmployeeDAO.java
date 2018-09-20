package com.cbc.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.cbc.micro.service.entities.Employee;

public class EmployeeDAO {

	public Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "hr");
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return con;

	}

	public Employee getEmployeeInfo(String pEmpId) {
		
		Employee emp = null;
		try {
			
			Connection con =getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from employees where employee_id ="+pEmpId);

			while (rs.next()) {

				String empId = rs.getString("EMPLOYEE_ID");
				String firstName = rs.getString("FIRST_NAME");
				String lastName = rs.getString("LAST_NAME");
				String email = rs.getString("EMAIL");
				
				 emp = new Employee();
				 emp.setEmpId(empId);
				 emp.setLastName(lastName);
				 emp.setFirstName(firstName);
				 emp.setEmail(email);
				
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return emp;

	}
	
	
public List getAllEmployeeInfo() {
		
		Employee emp = null;
		List empList = new ArrayList();
		try {
			
			Connection con =getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from employees");

			while (rs.next()) {

				String empId = rs.getString("EMPLOYEE_ID");
				String firstName = rs.getString("FIRST_NAME");
				String lastName = rs.getString("LAST_NAME");
				String email = rs.getString("EMAIL");
				
				 emp = new Employee();
				 emp.setEmpId(empId);
				 emp.setLastName(lastName);
				 emp.setFirstName(firstName);
				 emp.setEmail(email);
				 
				 empList.add(emp);
				
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return empList;

	}

	public void getEmployeeInfoUsingPreparedStmt(Connection con) {
		try {
			PreparedStatement pstmt = con.prepareStatement("select * from employees where employee_id =? and first_name=?");
			pstmt.setInt(1, 100);
			pstmt.setString(2, "Steven");
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				String empId = rs.getString("EMPLOYEE_ID");
				String firstName = rs.getString("FIRST_NAME");
				String lastName = rs.getString("LAST_NAME");
				String email = rs.getString("EMAIL");
				System.out.println(empId + " : " + firstName + " : " + lastName + " : " + email);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	public void addEmployeeInfo(Connection con) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(
					"insert into employees(EMPLOYEE_ID,LAST_NAME,FIRST_NAME,EMAIL) values(1001,'abc','xyz','abc.xyz@gmail.com')");
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addEmployeeInfoUsingCallableStmetn(Connection con) {
		try {
			CallableStatement cStmt = con.prepareCall("{call add_employee_info(?,?,?,?)}");
			//cStmt.setInt(1, 1003);
			cStmt.setString(1, "hello");
			cStmt.setString(2, "Ram");
			cStmt.setString(3, "Ram.hello@gmail.com");
			
			cStmt.registerOutParameter(4, Types.VARCHAR);
			cStmt.execute();
			
			String result =cStmt.getString(4);
			System.out.println(" Result from stored procedure : "+result);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateEmployeeInfo(Connection con) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("update employees set LAST_NAME='john' where employee_id=1001");
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteEmployeeInfo(Connection con) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("delete from employees where employee_id=1001");
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	
}
