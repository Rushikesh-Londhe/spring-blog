package com.springblog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.springblog.config.AppConstants;
import com.springblog.entity.Role;
import com.springblog.repositories.RoleRepo;

@SpringBootApplication
public class SpringblogApplication implements CommandLineRunner {

	@Autowired
	private RoleRepo rr;
	@Autowired
	private PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(SpringblogApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		
		return new ModelMapper();
	}

	
	  @Override public void run(String... args) throws Exception {
	 // System.out.println(this.passwordEncoder.encode("xyz"));
	  
	  try {
		  Role role=new Role();
		  role.setId(AppConstants.ADMIN_USER);
		  role.setName("ROLE_ADMIN");
		  
		  
		  Role role1=new Role();
		  role1.setId(AppConstants.NORMAL_USER);
		  role1.setName("ROLE_NORMAL");
		  
		  List<Role> roles=List.of(role,role1);
		  
		  List<Role> result = this.rr.saveAll(roles);
		  result.forEach(r->{System.out.println(r.getName());
		  });
	  }catch(Exception e){
		  
	  }
	  }
	 
}
