package com.emp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.emp.model.Manager;
import com.emp.repository.ManagerRepository;

public class UserDetailsServiceImplementation implements UserDetailsService {
	
	@Autowired 
	private ManagerRepository managerRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Manager manager = managerRepository.getManagerByManagerName(username);
		if(manager==null) {
			throw new UsernameNotFoundException("Could not found Manager !!");
		}
		CustomUserDetails customUserDetails = new CustomUserDetails(manager);
		return customUserDetails;
	}

}
