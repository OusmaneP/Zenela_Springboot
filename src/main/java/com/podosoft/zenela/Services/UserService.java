package com.podosoft.zenela.Services;


import com.podosoft.zenela.Dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void save(UserRegistrationDto registrationDto);

    Object findByEmail(String email);
}
