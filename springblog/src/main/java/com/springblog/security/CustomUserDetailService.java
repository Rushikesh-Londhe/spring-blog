package com.springblog.security;

import com.springblog.entity.User;
import com.springblog.exceptions.ResourceNotFoundException;
import com.springblog.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
@Autowired
private UserRepo ur;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        //loading user from db bt username
//     User user  = this.ur.findByEmail(username) ;//.orElseThrow(()->new ResourceNotFoundException("User","email"+username,0));
//
//return user;
//
//    }


@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
User user = this.ur.findByName(username);
if(user==null) {
	System.out.println("user not found");
	throw new UsernameNotFoundException("No user found !!");
}
	return user;
}
}
