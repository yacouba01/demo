package com.malinov.demo.security;


import com.malinov.demo.enums.State;
import com.malinov.demo.models.Role;
import com.malinov.demo.models.Users;
import com.malinov.demo.repositories.RoleRepository;
import com.malinov.demo.repositories.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Transactional
@RequiredArgsConstructor
public class SetupLoaderData implements ApplicationListener<ContextRefreshedEvent> {

    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private boolean alreadySetup = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }

        final Role superAdminRole = createRoleIfNotFound("SUPER_ADMIN");
        final Role userRole = createRoleIfNotFound("USERS");
        final Role adminRole = createRoleIfNotFound("ADMIN");
        final List<Role> adminRoles = new ArrayList<>(Arrays.asList(
                superAdminRole, userRole, adminRole));
        createUsersIfNotFound(adminRoles);
        alreadySetup = true;
    }

    void createUsersIfNotFound(List<Role> role) {
        Users users = usersRepository.findByEmailAndState("admin@gmail.com", State.ACTIVATED);
        if (users == null) {
            users = new Users();
            users.setFirstName("Admin");
            users.setLastName("Demo");
            users.setEmail("admin@gmail.com");
            users.setPassword(passwordEncoder.encode("Demo2024#~!"));
            users.setPhoneNumber("22375900085");
        }
        users.setRoles(role);
        usersRepository.save(users);
    }

    Role createRoleIfNotFound(final String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role = roleRepository.save(role);
        }
        return role;
    }
}
