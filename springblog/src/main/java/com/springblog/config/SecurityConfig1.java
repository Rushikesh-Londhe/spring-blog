package com.springblog.config;

import com.springblog.security.CustomUserDetailService;
import com.springblog.security.JwtAuthenticationEntryPoint;
import com.springblog.security.JwtAuthenticationFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig1  {

	public static final String[] PUBLIC_URLS= {"/api/v1/auth/**"
			,"/v3/api-docs"
			,"/v2/api-docs"
			,"/swagger-resources/**"
			,"/swagger-ui/**"
			,"/webjars/**"};
@Autowired
private CustomUserDetailService cud;

@Autowired
private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

@Autowired
private JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean public SecurityFilterChain securityFilterChain(HttpSecurity http)
  throws Exception{
      http. csrf(csrf -> csrf .disable()) .authorizeHttpRequests()
              .requestMatchers(PUBLIC_URLS).permitAll()
              .requestMatchers(HttpMethod.GET) .permitAll()
              .anyRequest() .authenticated()
              .and().exceptionHandling(handling -> handling
              .authenticationEntryPoint(this.jwtAuthenticationEntryPoint))
              .sessionManagement(management -> management .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
  http.addFilterBefore(this.jwtAuthenticationFilter,
  UsernamePasswordAuthenticationFilter.class);
  
  http.authenticationProvider(daoAuthenticationProvider());
  DefaultSecurityFilterChain build=http.build(); return build; 
  }
 

/*
	 * @Bean public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
	 * Exception { http .csrf().disable() .authorizeRequests(authorizeRequests ->
	 * authorizeRequests .requestMatchers("/api/v1/auth/login", "/**").permitAll()
	 * .anyRequest().authenticated() ) .exceptionHandling(exceptionHandling ->
	 * exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint) )
	 * .sessionManagement(sessionManagement ->
	 * sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) );
	 * 
	 * http.addFilterBefore(jwtAuthenticationFilter,
	 * UsernamePasswordAuthenticationFilter.class);
	 * http.authenticationProvider(daoAuthenticationProvider());
	 * 
	 * return http.build(); }
	 */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
    	DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
    	provider.setUserDetailsService(this.cud);
    	provider.setPasswordEncoder(passwordEncoder());
    	return provider;
    }
    
    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration)throws Exception{

        return authenticationConfiguration.getAuthenticationManager();
    }
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.userDetailsService(this.cud).passwordEncoder(passwordEncoder());
//
//    }
    
    






}
