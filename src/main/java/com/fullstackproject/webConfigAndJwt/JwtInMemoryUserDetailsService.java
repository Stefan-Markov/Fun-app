package com.fullstackproject.webConfigAndJwt;

import com.fullstackproject.models.User;
import com.fullstackproject.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JwtInMemoryUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JwtInMemoryUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepository
                .findByUsername(username);

        return userOpt
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("No user " + username));
    }

    private UserDetails map(User user) {
        List<GrantedAuthority> authorities = user.
                getAuthorities().
                stream().
                map(r -> new SimpleGrantedAuthority(r.getAuthority())).
                collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword() != null ? user.getPassword() : "",
                authorities);
    }

}


