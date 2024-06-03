package com.zawadzkia.springtodo.auth;

import com.zawadzkia.springtodo.user.UserModel;
import com.zawadzkia.springtodo.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found!", username)));
        return new AppUserDetails(user);
    }
}
