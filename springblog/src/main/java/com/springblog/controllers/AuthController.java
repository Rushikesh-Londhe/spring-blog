package com.springblog.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springblog.exceptions.ApiException;
import com.springblog.payloads.JwtAuthRequest;
import com.springblog.payloads.JwtAuthResponse;
import com.springblog.payloads.UserDto;
import com.springblog.security.CustomUserDetailService;
import com.springblog.security.JwtTokenHelper;
import com.springblog.services.UserService;


@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

	@Autowired
	private UserService us;
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private CustomUserDetailService uds;
	
	@Autowired
	private AuthenticationManager am;
	
	/*
	 * @PostMapping("/login") public ResponseEntity<JwtAuthResponse> createToken(
	 * 
	 * @RequestBody JwtAuthRequest req ) throws Exception{
	 * 
	 * this.authenticate(req.getUsername(),req.getPassword()); UserDetails
	 * userDetails = this.uds.loadUserByUsername(req.getUsername()); String token =
	 * this.jwtTokenHelper.generateToken(userDetails); JwtAuthResponse res=new
	 * JwtAuthResponse(); System.out.println(res.getToken()); return new
	 * ResponseEntity<JwtAuthResponse>(res,HttpStatus.OK); }
	 * 
	 * private void authenticate(String username,String password) throws Exception {
	 * UsernamePasswordAuthenticationToken token=new
	 * UsernamePasswordAuthenticationToken(username, password);
	 * 
	 * try { this.am.authenticate(token); } catch (BadCredentialsException e) {
	 * System.out.println("Invalid details"); throw new
	 * ApiException("Invalid details"); }
	 * 
	 * }
	 */
	

	//generate token
	@PostMapping("/login")
	public ResponseEntity<?> generateToken(@RequestBody JwtAuthRequest jwtRequest) throws Exception{
		
		try {
			this.authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
		}catch(UsernameNotFoundException e) {
			e.printStackTrace();
			throw new Exception("user not found");
		}
		//authentication done
		UserDetails userDetails = this.uds.loadUserByUsername(jwtRequest.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);
		return ResponseEntity.ok(new JwtAuthResponse(token));
	}
	
	
	private void authenticate(String username,String password) throws Exception {
		try {
			am.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		}
		catch(DisabledException e) {
			throw new Exception("USER DISABLED");
		}
		catch(BadCredentialsException e) {
			throw new Exception("Invalid Credentials");
		}
	}
	
	
	//register new user api
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
		UserDto registerNewUser = this.us.registerNewUser(userDto);
		return new ResponseEntity<UserDto>(registerNewUser,HttpStatus.CREATED);
	}
		
}
