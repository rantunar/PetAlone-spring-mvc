package com.petsalone.service.impl;

import com.petsalone.entity.Role;
import com.petsalone.entity.User;
import com.petsalone.repository.UserRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired private UserRepository userRepository;

  Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> dbUser = userRepository.findById(username);

    if (!dbUser.isPresent()) {
      logger.info("username is not present in db, authentication unsuccessful");
      throw new UsernameNotFoundException(username);
    } else {
      logger.info("username is present in db, authenticating credentials");
    }

    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    for (Role role : dbUser.get().getRoles()) {
      grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
    }

    return new org.springframework.security.core.userdetails.User(
        dbUser.get().getUsername(), dbUser.get().getPassword(), grantedAuthorities);
  }
}
