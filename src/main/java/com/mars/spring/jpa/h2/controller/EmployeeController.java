package com.mars.spring.jpa.h2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mars.spring.jpa.h2.dto.ResponseDTO;
import com.mars.spring.jpa.h2.exception.EmployeeDataCreationException;
import com.mars.spring.jpa.h2.exception.NoEmployeeDataException;
import com.mars.spring.jpa.h2.model.Employee;
import com.mars.spring.jpa.h2.repository.EmployeeRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class EmployeeController {
	
	private static final Logger logger = LogManager.getLogger(EmployeeController.class);

	@Autowired
	EmployeeRepository employeeRepository;


	/**
	 * (non-Javadoc)
	 * 10-Jun-2021 
	 * @see com.mars.spring.jpa.h2.controller.EmployeeContoller#getAllEmployees()
	 *      This method is used to get all employee details available
	 * @return  ResponseDTO<Employee>
	 */
	
	@GetMapping("/getAllEmployees")
	public ResponseDTO<Employee> getAllEmployees() throws NoEmployeeDataException {
		List<Employee> employees = new ArrayList<>();
		ResponseDTO<Employee> responseObject = new ResponseDTO<>();
		try {
			employeeRepository.findAll().forEach(employees::add);
			if (employeeRepository.count()<=0) {
				responseObject.setStatusCode("500");
				responseObject.setStatusMessage("No Employee Data Available.");
				responseObject.setResult(employees);
				throw new NoEmployeeDataException("No Employee Data Available.");
			}
			else if(employeeRepository.count()>0) {
				responseObject.setStatusCode("200");
				responseObject.setStatusMessage("Success");
				responseObject.setResult(employees);
			}
		} catch (Exception e) {
			logger.error("EmployeeController : getAllEmployees : ", e);
		}
		return responseObject;
	}

	/**
	 * (non-Javadoc)
	 * 10-Jun-2021 
	 * @see com.mars.spring.jpa.h2.controller.EmployeeContoller#getEmployeeByID(java.lang.Integer)
	 *      This method is used to get employee details of EmployeeID
	 * @return  ResponseDTO<Employee>
	 */
	@GetMapping(path = "/getEmployeesById")
	public ResponseDTO<Employee> getEmployeesById(@RequestParam(value = "id") Integer id) throws NoEmployeeDataException {
		ResponseDTO<Employee> responseObject = new ResponseDTO<>();
		final List<Employee> list = new ArrayList<>();
		try {
			Long eID = Long.valueOf(id);
			Optional<Employee> employeeData = employeeRepository.findById(eID);
			if (!employeeData.isPresent()) {
				responseObject.setStatusCode("500");
				responseObject.setStatusMessage("No Record Found for the Employee ID : " + id + ".");
				responseObject.setResult(list);
				throw new NoEmployeeDataException("No Record Found for the Employee ID : " +id + ".");
			} else if(employeeRepository.count()>0) {
				employeeData.ifPresent(list :: add);
				responseObject.setStatusCode("200");
				responseObject.setStatusMessage("Success");
				responseObject.setResult(list);
			}
		} catch(Exception e) {
			logger.error("EmployeeController : getEmployeesById : {} ", id, e);
		}
		return responseObject;
	}

	/**
	 * (non-Javadoc)
	 * 10-Jun-2021 
	 * @see com.mars.spring.jpa.h2.controller.EmployeeContoller#createEmployee(com.mars.spring.jpa.h2.model.Employee)
	 *      This method is used to save the employee details in DB
	 * @return  ResponseDTO<Employee>
	 */
	@PostMapping(path = "/createEmployees")
	public ResponseDTO<Employee> createEmployee(@RequestBody Employee employee) throws EmployeeDataCreationException {
		List<Employee> employeeObj = new ArrayList<>();
		ResponseDTO<Employee> responseObject = new ResponseDTO<>();
		try {
			Employee _employee = employeeRepository
					.save(new Employee(employee.getFirstname(), employee.getSurname()));
			employeeObj.add(_employee);
			if(employeeObj.isEmpty()) {
				responseObject.setStatusCode("500");
				responseObject.setStatusMessage("Employee Creation Failed.");
				responseObject.setResult(employeeObj);
				throw new EmployeeDataCreationException("Employee Creation Failed.");
			} else {
				responseObject.setStatusCode("200");
				responseObject.setStatusMessage("Employee Creation Successfull.");
				responseObject.setResult(employeeObj);
			}
		} catch (Exception e) {
			logger.error("EmployeeController : createEmployee : ", e);
		}
		return responseObject;
	}

	/**
	 * (non-Javadoc)
	 * 10-Jun-2021 
	 * @see com.mars.spring.jpa.h2.controller.EmployeeContoller#updateEmployee(java.lang.Integer, com.mars.spring.jpa.h2.model.Employee)
	 *      This method is used to update the employee data which is already present in DB
	 * @return  ResponseDTO<Employee>
	 */
	@PutMapping(path = "/updateEmployeeById")
	public ResponseDTO<Employee> updateEmployee(@RequestParam(value = "id") Integer id, @RequestBody Employee employee) {
		Long eID = Long.valueOf(id);
		ResponseDTO<Employee> responseObj = new ResponseDTO<>();
		List<Employee> objList = new ArrayList<>();
		try {
			Optional<Employee> employeeData = employeeRepository.findById(eID);
			if (employeeData.isPresent()) {
				Employee _employee = employeeData.get();
				_employee.setFirstname(employee.getFirstname());
				_employee.setSurname(employee.getSurname());
				Employee empObj = employeeRepository.save(_employee);
				objList.add(empObj);
				if(objList != null && !objList.isEmpty()) {
					responseObj.setStatusCode("200");
					responseObj.setStatusMessage("Employee Data Corrected/Updated Successfully.");
					responseObj.setResult(objList);
				}
			} else {
				responseObj.setStatusCode("500");
				responseObj.setStatusMessage("No Record Found for the Employee ID : " + id + ".");
				responseObj.setResult(objList);
				throw new NoEmployeeDataException("No Record Found for the Employee ID : " +id + ".");
			}
		} catch(Exception e) {
			logger.error("EmployeeController : updateEmployee : ", e);
		}
		return responseObj;
	}

	/**
	 * (non-Javadoc)
	 * 10-Jun-2021 
	 * @see com.mars.spring.jpa.h2.controller.EmployeeContoller#deleteEmployee(java.lang.Integer)
	 *      This method is used to delete the employee data from DB based on employeeID
	 * @return  ResponseDTO<Employee>
	 */
	@DeleteMapping(path = "/deleteEmployee")
	public ResponseDTO<Employee> deleteEmployee(@RequestParam(value = "id") Integer id) {
		ResponseDTO<Employee> responseObj = new ResponseDTO<>();
		try {
			Long eID = Long.valueOf(id);
			ResponseDTO<Employee> empObj = this.getEmployeesById(id);
			if(empObj.getStatusCode().equalsIgnoreCase("200")) {
				employeeRepository.deleteById(eID);
				responseObj.setStatusCode("200");
				responseObj.setStatusMessage("Employee Data Deleted Successfully.");
			} else {
				responseObj.setStatusCode("500");
				responseObj.setStatusMessage("No Record Found for the Employee ID : " + id + ".");
				throw new NoEmployeeDataException("No Record Found for the Employee ID : " +id + ".");
			}
		} catch (Exception e) {
			responseObj.setStatusCode("500");
			responseObj.setStatusMessage("Employee Data Not Found.");
			logger.error("EmployeeController : deleteEmployee : ", e);
		}
		return responseObj;
	}
}
