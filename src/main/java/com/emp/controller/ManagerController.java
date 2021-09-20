package com.emp.controller;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.emp.message.Message;
import com.emp.model.Employee;
import com.emp.model.Manager;
import com.emp.repository.EmployeeRepository;
import com.emp.repository.ManagerRepository;
import com.emp.service.EmployeeService;

@Controller
@RequestMapping("/manager")
public class ManagerController {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private ManagerRepository managerRepository;

	@Autowired
	private EmployeeService employeeService;

	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String managerName = principal.getName();
		System.out.println("Username " + managerName);

		Manager manager = this.managerRepository.getManagerByManagerName(managerName);
		System.out.println("Manager " + manager);

		model.addAttribute("manager", manager);
	}

	@RequestMapping("/index")
	public String managerDashboard(Model model, Principal principal) {

		String managerName = principal.getName();
		System.out.println("ManagerName " + managerName);

		Manager manager = this.managerRepository.getManagerByManagerName(managerName);
		System.out.println("USER " + manager);

		model.addAttribute("user", manager);
		model.addAttribute("listEmployees", manager.getEmployee());

		return "managerHomepage";
	}

	@RequestMapping("/addEmployee")
	public String addEmployee(Model model) {
		model.addAttribute("employee", new Employee());
		return "newEmployeeAdd";
	}

	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee, Principal principal,
			HttpSession session) {
		try {
			String name = principal.getName();
			Manager manager = managerRepository.getManagerByManagerName(name);
			employee.setManager(manager);
			manager.getEmployee().add(employee);
			managerRepository.save(manager);
			session.setAttribute("message", new Message("Your contact is added!!", "success"));
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("message", new Message("Something went wrong!!", "danger"));
		}
		return "redirect:/manager/index";
	}

	@GetMapping("/deleteEmployee/{employeeId}")
	public String deleteContact(@PathVariable("employeeId") Long employeeId, Model model, Principal principal,
			HttpSession session) {

		Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
		Employee employee = employeeOptional.get();

		String managerName = principal.getName();
		Manager manager = this.managerRepository.getManagerByManagerName(managerName);

		if (manager.getManagerId() == employee.getManager().getManagerId()) {
			Manager manager1 = managerRepository.getManagerByManagerName(principal.getName());
			manager1.getEmployee().remove(employee);
			managerRepository.save(manager);
		}

		session.setAttribute("message", new Message("Contact Deleted Successfully..", "success"));

		return "redirect:/manager/index";
	}

	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {

		Employee employee = employeeService.getEmployeeById(id);
		model.addAttribute("employee", employee);

		return "updateEmployee";
	}

	@PostMapping("/processUpdate")
	public String updateEmployee(@ModelAttribute Employee employee, Model model, HttpSession session,
			Principal principal) {
		Manager manager = managerRepository.getManagerByManagerName(principal.getName());
		employee.setManager(manager);
		employeeRepository.save(employee);
		return "redirect:/manager/index";
	}
}
