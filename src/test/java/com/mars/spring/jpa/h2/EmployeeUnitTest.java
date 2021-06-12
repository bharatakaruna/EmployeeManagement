package com.mars.spring.jpa.h2;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.mars.spring.jpa.h2.model.Employee;
import com.mars.spring.jpa.h2.repository.EmployeeRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeUnitTest {
	
	@Autowired
	  private TestEntityManager entityManager;

	  @Autowired
	  EmployeeRepository repository;

	  @Test
	  public void should_find_no_employees_if_repository_is_empty() {
	    Iterable<Employee> employees = repository.findAll();

	    assertThat(employees).isEmpty();
	  }

	  @Test
	  public void should_store_a_employee() {
	    Employee employee = repository.save(new Employee("Karuna", "Bharata"));

	    assertThat(employee).hasFieldOrPropertyWithValue("firstname", "Karuna");
	    assertThat(employee).hasFieldOrPropertyWithValue("surname", "Bharata");
	  }

	  @Test
	  public void should_find_all_employees() {
		Employee emp1 = new Employee("Karuna#1", "Bharata#1");
	    entityManager.persist(emp1);

	    Employee emp2 = new Employee("Raaj#2", "Kandakatla#2");
	    entityManager.persist(emp2);

	    Employee emp3 = new Employee("Ravi#3", "Kandakatla#3");
	    entityManager.persist(emp3);

	    Iterable<Employee> employees = repository.findAll();

	    assertThat(employees).hasSize(3).contains(emp1, emp2, emp3);
	  }

	  @Test
	  public void should_find_employee_by_id() {
	    Employee emp1 = new Employee("Karuna#1", "Bharata#1");
	    entityManager.persist(emp1);

	    Employee emp2 = new Employee("Raj#2", "Kandakatla#2");
	    entityManager.persist(emp2);

	    Employee employeeObj = repository.findById(emp2.getId()).get();

	    assertThat(employeeObj).isEqualTo(emp2);
	  }

	  @Test
	  public void should_update_employee_by_id() {
	    Employee emp1 = new Employee("Karuna#1", "Bharata#1");
	    entityManager.persist(emp1);

	    Employee emp2 = new Employee("Raj#2", "Kandakatla#2");
	    entityManager.persist(emp2);

	    Employee updatedEmp = new Employee("updated emp#2", "Karuna#2");

	    Employee emp = repository.findById(emp2.getId()).get();
	    emp.setFirstname(updatedEmp.getFirstname());
	    emp.setSurname(updatedEmp.getSurname());
	    repository.save(emp);

	    Employee checkEmployee = repository.findById(emp2.getId()).get();
	    
	    assertThat(checkEmployee.getId()).isEqualTo(emp2.getId());
	    assertThat(checkEmployee.getFirstname()).isEqualTo(updatedEmp.getFirstname());
	    assertThat(checkEmployee.getSurname()).isEqualTo(updatedEmp.getSurname());
	  }

	  @Test
	  public void should_delete_employee_by_id() {
		Employee emp1 = new Employee("Karuna#1", "Bharata#1");
	    entityManager.persist(emp1);

	    Employee emp2 = new Employee("Raj#2", "Kandakatla#2");
	    entityManager.persist(emp2);

	    Employee emp3 = new Employee("Ravi#3", "Kandakatla#3");
	    entityManager.persist(emp3);

	    repository.deleteById(emp3.getId());

	    Iterable<Employee> employees = repository.findAll();

	    assertThat(employees).hasSize(2).contains(emp1, emp2);
	  }
}
