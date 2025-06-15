//package com.scholarship.config;
//
//import java.util.HashSet;
//
//import com.scholarship.entities.Role;
//import com.scholarship.entities.User;
//import com.scholarship.repositories.RoleRepository;
//import com.scholarship.repositories.UserRepository;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import lombok.experimental.NonFinal;
//import lombok.extern.slf4j.Slf4j;
//
//@Configuration
//@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//@Slf4j
//public class ApplicationInitConfig {
//
//    PasswordEncoder passwordEncoder;
//    String USER_ROLE = "USER";
//    String ADMIN_ROLE = "ADMIN";
//    @NonFinal
//    static final String ADMIN_USER_NAM = "longdeptrai";
//
//    @NonFinal
//    static final String ADMIN_PASSWORD = "longdeptrai";
//
//    @Bean
//    @ConditionalOnProperty(
//            prefix = "spring",
//            value = "datasource.driverClassName",
//            havingValue = "com.mysql.cj.jdbc.Driver")
//    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
//        log.info("Initializing application.....");
//        return args -> {
//            if (userRepository.findByEmail(ADMIN_USER_NAME).isEmpty()) {
//                roleRepository.save(Role.builder()
//                        .name(USER_ROLE)
//                        .build());
//
//                Role adminRole = roleRepository.save(Role.builder()
//                        .name(ADMIN_ROLE)
//                        .build());
//
//                User user = User.builder()
//                        .userName(ADMIN_USER_NAME)
//                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
//                        .role(adminRole)
//                        .build();
//
//                userRepository.save(user);
//                log.warn("admin user has been created with default password: admin, please change it");
//            }
//            log.info("Application initialization completed .....");
//        };
//    }
//}
