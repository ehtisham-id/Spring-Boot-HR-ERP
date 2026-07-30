package com.spring.practice1.modules.auth.config;

import com.spring.practice1.modules.auth.entity.Permission;
import com.spring.practice1.modules.auth.entity.Role;
import com.spring.practice1.modules.auth.entity.User;
import com.spring.practice1.modules.auth.enums.PermissionType;
import com.spring.practice1.modules.auth.enums.RoleType;
import com.spring.practice1.modules.auth.repository.PermissionRepository;
import com.spring.practice1.modules.auth.repository.RoleRepository;
import com.spring.practice1.modules.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class BootstrapData {


    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;


    @Bean
    CommandLineRunner bootstrap() {

        return args -> {
            //Permissions
            for (PermissionType permissionType : PermissionType.values()) {
                permissionRepository.findByName(permissionType)
                        .orElseGet(() -> permissionRepository.save(
                                new Permission(permissionType)
                        ));
            }

            //Roles
            for (RoleType roleType : RoleType.values()) {
                Role role = roleRepository.findByName(roleType)
                        .orElseGet(() -> {
                            Role newRole = new Role();
                            newRole.setName(roleType);
                            return roleRepository.save(newRole);
                        });

                Set<Permission> permissions = Arrays.stream(roleType.getPermissions())
                        .map(permissionType ->
                                permissionRepository.findByName(permissionType)
                                        .orElseThrow(() ->
                                                new RuntimeException(
                                                        "Permission not found: "
                                                                + permissionType
                                                )))
                        .collect(Collectors.toSet());
                role.setPermissions(permissions);
                roleRepository.save(role);
            }

            // Create  Admin User
            if (userRepository.count() == 0) {
                Role adminRole = roleRepository.findByName(RoleType.SUPER_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Super Admin role not found"));

                User admin = new User();
                admin.setEmail("admin@admin.com");
                admin.setPassword(encoder.encode("Admin@123"));
                admin.setEmailVerified(true);
                admin.setRole(adminRole);
                admin.setFirstName("System");
                admin.setLastName("Administrator");
                userRepository.save(admin);

                System.out.println("Default admin user created.");
            }
        };
    }
}