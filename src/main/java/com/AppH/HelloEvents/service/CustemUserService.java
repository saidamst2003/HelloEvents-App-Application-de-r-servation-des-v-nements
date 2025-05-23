package com.AppH.HelloEvents.service;

import com.AppH.HelloEvents.model.User;
import com.AppH.HelloEvents.repository.UserReposetory;
import java.util.Collections;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CustemUserService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CustemUserService.class);

    private final UserReposetory userReposetory;

    public CustemUserService(UserReposetory userReposetory) {
        this.userReposetory = userReposetory;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userReposetory.findByUsername(username);

        if (user == null) {
            log.error("User not found: {}", username);
            throw new UsernameNotFoundException("User not found: " + username);
        }

        log.info("Loaded user: {}, password: {}", user.getUsername(), user.getPassword());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}
