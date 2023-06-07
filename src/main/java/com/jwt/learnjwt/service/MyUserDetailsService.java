package com.jwt.learnjwt.service;

import com.jwt.learnjwt.model.User;
import com.jwt.learnjwt.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user =  userRepo.findByEmail(username);

        if(user.isPresent()){
            User singleUser = user.get();
            return singleUser;
        }else {
            throw new UsernameNotFoundException("User does not exist!!!");
        }
    }
}
