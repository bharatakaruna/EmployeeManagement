package com.mars.spring.jpa.h2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.mars.spring.jpa.h2.exception.NoEmployeeDataException;
import com.mars.spring.jpa.h2.model.Employee;
import com.mars.spring.jpa.h2.repository.EmployeeRepository;

@Configuration
@Component
public class Runner implements CommandLineRunner {
	
	private static final Logger logger = LogManager.getLogger(Runner.class);
	
	@Autowired
	EmployeeRepository repository;
	
	public void run(String... args) throws Exception {
		if(args.length > 0 ) {
			System.out.println("length : " + args.length);
			if(args[0].equalsIgnoreCase("save")) {
				repository.save(new Employee(args[1], args[2]));
			} else if(args[0].equalsIgnoreCase("findByID")) {
				Optional<Employee> employeeData = repository.findById(Long.parseLong(args[1]));
				List<Employee> employees = new ArrayList<>();
				if (!employeeData.isPresent()) {
					logger.info("No Employee Data Available for the ID : {}", args[1]);
				}
				else if(repository.count()>0) {
					 Iterator < Employee > iterator = employees.iterator();
				        while (iterator.hasNext()) {
				            logger.info("{}", iterator.next());
				     }
				}
			} else if(args[0].equalsIgnoreCase("findAll")) {
				List<Employee> employees = new ArrayList<>();
				repository.findAll().forEach(employees::add);
				if (repository.count()<=0) {
					logger.info("No Employee Data Available.");
				}
				else if(repository.count()>0) {
					 Iterator < Employee > iterator = employees.iterator();
				        while (iterator.hasNext()) {
				            logger.info("{}", iterator.next());
				     }
				}
			} else if(args[0].equalsIgnoreCase("update")) {
				List<Employee> objList = new ArrayList<>();
				Optional<Employee> employeeData = repository.findById(Long.parseLong(args[1]));
				if (employeeData.isPresent()) {
					Employee _employee = employeeData.get();
					_employee.setFirstname(args[2]);
					_employee.setSurname(args[2]);
					Employee empObj = repository.save(_employee);
					objList.add(empObj);
					if(objList != null && !objList.isEmpty()) {
						 Iterator < Employee > iterator = objList.iterator();
					        while (iterator.hasNext()) {
					            logger.info("{}", iterator.next());
					     }
					}
				} else {
					logger.info("No Record Found for the Employee ID : " + args[1] + ".");
					throw new NoEmployeeDataException("No Record Found for the Employee ID : " +args[1] + ".");
				}
			} else if(args[0].equalsIgnoreCase("delete")) {
				repository.deleteById(Long.parseLong(args[1]));
				logger.info("Record Deleted for the Employee ID : " + args[1] + ".");
			}
			logger.info("Arguments passed.");
		} else {
			logger.info("No Arguments passed.");
		}
	}

}
