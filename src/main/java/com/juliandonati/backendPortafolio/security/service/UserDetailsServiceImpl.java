package com.juliandonati.backendPortafolio.security.service;

import com.juliandonati.backendPortafolio.security.domain.Role;
import com.juliandonati.backendPortafolio.security.domain.User;
import com.juliandonati.backendPortafolio.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No se encontró el usuario de nombre: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                mapRolesToGrantedAuthorities(user.getRoles())
        );
    }

    /**
     * Mapea un set de roles a una colección de una clase heredada de GrantedAuthority
     * @param roles Set de roles pertenecientes a un usuario
     * @return Colección de instancias que extienden GrantedAuthority
     */
    public Collection<? extends GrantedAuthority> mapRolesToGrantedAuthorities(Set<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(
                        role.getName()
                ))
                .collect(Collectors.toList());
    }
}
