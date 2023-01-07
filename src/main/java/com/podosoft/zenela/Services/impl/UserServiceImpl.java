package com.podosoft.zenela.Services.impl;

import com.podosoft.zenela.Dto.UserRegistrationDto;
import com.podosoft.zenela.Models.Role;
import com.podosoft.zenela.Models.User;
import com.podosoft.zenela.Models.UsersRoles;
import com.podosoft.zenela.Repositories.RoleRepository;
import com.podosoft.zenela.Repositories.UserRepository;
import com.podosoft.zenela.Repositories.UsersRolesRepository;
import com.podosoft.zenela.Services.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository; RoleRepository roleRepository; UsersRolesRepository usersRolesRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UsersRolesRepository usersRolesRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.usersRolesRepository = usersRolesRepository;
    }

    @Transactional
    @Override
    public void save(UserRegistrationDto registrationDto) {


        User user = new User(registrationDto.getFirstName(), registrationDto.getLastName(),
                registrationDto.getEmail(), passwordEncoder.encode(registrationDto.getPassword()),true, true, null, "posts/profile3.png", "posts/cover1.jpg", new Date());

        User registeredUser = userRepository.save(user);

        Role roleAdmin = roleRepository.findByName("ROLE_ADMIN");
        Role roleUser = roleRepository.findByName("ROLE_USER");
        Role roleAgent = roleRepository.findByName("ROLE_AGENT");

        List<UsersRoles> usersRolesCollection = new ArrayList<>();

        UsersRoles usersRole = new UsersRoles();
        usersRole.setUserId(registeredUser.getId());

        if (registeredUser.getFirstName().equals("admin")) {
            usersRole.setRoleId(roleAdmin.getId());
            usersRolesCollection.add(new UsersRoles(usersRole.getUserId(), usersRole.getRoleId()));

            usersRole.setRoleId(roleAgent.getId());
            usersRolesCollection.add(new UsersRoles(usersRole.getUserId(), usersRole.getRoleId()));

            usersRole.setRoleId(roleUser.getId());
            usersRolesCollection.add(new UsersRoles(usersRole.getUserId(), usersRole.getRoleId()));
        }
        else if (registeredUser.getFirstName().equals("agent")){
            usersRole.setRoleId(roleAgent.getId());
            usersRolesCollection.add(new UsersRoles(usersRole.getUserId(), usersRole.getRoleId()));

            usersRole.setRoleId(roleUser.getId());
            usersRolesCollection.add(new UsersRoles(usersRole.getUserId(), usersRole.getRoleId()));
        }
        else{
            usersRole.setRoleId(roleUser.getId());
            usersRolesCollection.add(new UsersRoles(usersRole.getUserId(), usersRole.getRoleId()));
        }

        usersRolesRepository.saveAll(usersRolesCollection);

    }

    @Override
    public Object findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        Collection<Role> roles = null;
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        else if(!user.getAccountNonLocked()){
            throw new UsernameNotFoundException("This Account Is Locked, Check out Admins !");
        }
        else if(!user.getAccountEnabled()){
            throw new UsernameNotFoundException("User with this Account is not enabled");
        }
        else{
            roles = roleRepository.findRolesOfUser(user.getId());
        }

        user.setRoles(roles);


        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }


}
