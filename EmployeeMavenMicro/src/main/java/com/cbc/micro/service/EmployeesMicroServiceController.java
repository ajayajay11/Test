package com.cbc.micro.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cbc.jdbc.EmployeeDAO;
import com.cbc.micro.service.entities.Employee;

@RestController
@RequestMapping(value = "/EmployeeService")
public class EmployeesMicroServiceController {

	@RequestMapping(value = "/getEmployeeInfoById/{empId}", method = RequestMethod.GET, produces = {
			"application/json" })
	public Employee getEmployeeInfoById(@PathVariable("empId") String empId) {

		System.out.println("Micro service invoked... " + empId);

		EmployeeDAO dao = new EmployeeDAO();

		Employee emp = dao.getEmployeeInfo(empId);

		return emp;
	}

	@RequestMapping(value = "/getAllEmployees", 
			       method = RequestMethod.GET, 
			       produces = {"application/json" })
	public List getAllEmployees() {

		System.out.println("Micro service invoked...getAllEmployees " );

		EmployeeDAO dao = new EmployeeDAO();

		List empList = dao.getAllEmployeeInfo();

		return empList;
	}

}
