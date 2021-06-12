package com.mars.spring.jpa.h2.model;

import javax.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "firstname")
	private String firstname;

	@Column(name = "surname")
	private String surname;

	public Employee() {

	}

	public Employee(String firstname, String surname) {
		this.firstname = firstname;
		this.surname = surname;
	}

	public long getId() {
		return id;
	}


	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstname=" + firstname + ", surname=" + surname + "]";
	}

}
