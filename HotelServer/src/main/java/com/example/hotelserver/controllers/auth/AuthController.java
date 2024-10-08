package com.example.hotelserver.controllers.auth;

import com.example.hotelserver.dto.AuthenticationRequest;
import com.example.hotelserver.dto.AuthenticationResponse;
import com.example.hotelserver.dto.RequestDto;
import com.example.hotelserver.dto.UserDto;
import com.example.hotelserver.entity.User;
import com.example.hotelserver.mapper.UserMapper;
import com.example.hotelserver.repository.UserRepository;
import com.example.hotelserver.services.auth.AuthService;
import com.example.hotelserver.services.jwt.UserService;
import com.example.hotelserver.utils.JwtUtil;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody RequestDto dto){
        try{
            System.out.println(dto.toString());
            return new ResponseEntity<>(userMapper.toDto(authService.createUser(dto)), HttpStatus.CREATED);
        } catch (EntityExistsException exception) {
            return new ResponseEntity<>("User already exists", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest request){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()
                    ));

        }catch (BadCredentialsException ex){
            throw new BadCredentialsException("Incorrect credentions");
        }

        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(request.getEmail());
        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        AuthenticationResponse response = new AuthenticationResponse();
        if(optionalUser.isPresent()){
            response.setJwt(jwt);
            response.setUserRole(optionalUser.get().getUserRole());
            response.setUserId(optionalUser.get().getId());
        }

        return response;
    }
}
