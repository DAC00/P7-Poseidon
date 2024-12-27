package com.opcr.poseidon.configuration;

import com.opcr.poseidon.domain.PoseidonUserSecurity;
import com.opcr.poseidon.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class PoseidonUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(PoseidonUserSecurity::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username does not exist : %s".formatted(username)));
    }
}
