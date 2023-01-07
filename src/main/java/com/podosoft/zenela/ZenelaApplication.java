package com.podosoft.zenela;

import com.podosoft.zenela.Models.Role;
import com.podosoft.zenela.Repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class ZenelaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZenelaApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner dataLoader(RoleRepository roleRepository){
//        return args -> {
//            roleRepository.saveAll(
//                    Arrays.asList(
//                            new Role("ROLE_ADMIN"),
//                            new Role("ROLE_AGENT"),
//                            new Role("ROLE_USER"))
//            );
//        };
//    }

}
