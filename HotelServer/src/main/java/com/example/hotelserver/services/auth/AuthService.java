package com.example.hotelserver.services.auth;

import com.example.hotelserver.dto.RequestDto;
import com.example.hotelserver.dto.UserDto;
import com.example.hotelserver.entity.User;
import com.example.hotelserver.enums.UserRole;
import com.example.hotelserver.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @PostConstruct
    public void createAnAdminAccount(){
        Optional<User> adminAccount = userRepository.findUserByUserRole(UserRole.ADMIN);
        if(adminAccount.isEmpty()){
            User user = new User();
            user.setEmail("admin@test.com");
            user.setName("admin");
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            user.setUserRole(UserRole.ADMIN);
            userRepository.save(user);
            System.out.println("Admin created");
        }
        else
            System.out.println("Admin already exists");
    }

    public User createUser(RequestDto dto) {
        System.out.println("DTO " + dto.toString());
        if(userRepository.findFirstByEmail(dto.getUsername()).isPresent())
            throw new EntityExistsException("User alreay exists");

        User user = new User();
        user.setName(dto.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        user.setEmail(dto.getUsername());
        user.setUserRole(UserRole.CUSTOMER);
        return userRepository.save(user);
    }
}
