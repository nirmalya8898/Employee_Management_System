package com.emp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emp.message.Message;
import com.emp.model.Manager;
import com.emp.repository.ManagerRepository;

/**
 * @author Nirmalya
 *
 */
@Controller
public class HomeController {
	
	@Autowired
	private ManagerRepository managerRepository;
	
	@Autowired(required=true)
	private BCryptPasswordEncoder passwordEncoder;
	
	
	@RequestMapping("/")
	public String homePageDashboard(Model model) {
		return "home";
	}

	@RequestMapping("/about")
	public String aboutPage(Model model) {
		return "about";
	}

	@RequestMapping("/signin")
	public String managerLogin(Model model) {
		return "login";
	}

	@RequestMapping("/signup")
	public String managerSignUp(Model model) {
		model.addAttribute("manager", new Manager());
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String registerManager(@ModelAttribute("user") Manager manager, Model model, HttpSession session) {

		try {
			manager.setRole("ROLE_MANAGER");
			manager.setManagerPassword(passwordEncoder.encode(manager.getManagerPassword()));

			this.managerRepository.save(manager);

			model.addAttribute("manager", new Manager());
			session.setAttribute("message", new Message("Successfully Registred!!", "alert-success"));
			return "signup";

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("manager", manager);
			session.setAttribute("message", new Message("Somthing went Wrong!!" + e.getMessage(), "alert-danger"));
			return "signup";
		}

	}

}
