package com.emp.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="MANAGER")
public class Manager {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int managerId;
	private String managerName;
	private String managerEmail;
	private String managerPassword;
	private String role;
	
	@OneToMany(cascade = CascadeType.ALL , mappedBy = "manager", orphanRemoval = true)
	private List<Employee> employee =  new ArrayList<>();

	//Default constructor
	public Manager() {
		super();
		// TODO Auto-generated constructor stub
	}
	//Parameterized constructor
	public Manager(int managerId, String managerName, String managerEmail, String managerPassword, String role,
			List<Employee> employee) {
		super();
		this.managerId = managerId;
		this.managerName = managerName;
		this.managerEmail = managerEmail;
		this.managerPassword = managerPassword;
		this.role = role;
		this.employee = employee;
	}
	//Getter and Setter method
	public int getManagerId() {
		return managerId;
	}
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getManagerEmail() {
		return managerEmail;
	}
	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}
	public String getManagerPassword() {
		return managerPassword;
	}
	public void setManagerPassword(String managerPassword) {
		this.managerPassword = managerPassword;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public List<Employee> getEmployee() {
		return employee;
	}
	public void setEmployee(List<Employee> employee) {
		this.employee = employee;
	}
	
	//toString method
	@Override
	public String toString() {
		return "Manager [managerId=" + managerId + ", managerName=" + managerName + ", managerEmail=" + managerEmail
				+ ", managerPassword=" + managerPassword + ", role=" + role + ", employee=" + employee + "]";
	}
	

}
