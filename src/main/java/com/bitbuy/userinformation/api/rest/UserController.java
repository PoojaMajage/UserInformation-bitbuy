package com.bitbuy.userinformation.api.rest;

import com.bitbuy.userinformation.domain.User;
import com.bitbuy.userinformation.domain.UserInformation;
import com.bitbuy.userinformation.exception.UserAlreadyExistsException;
import com.bitbuy.userinformation.exception.UserNotFoundException;
import com.bitbuy.userinformation.repository.UserInformationRepository;
import com.bitbuy.userinformation.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private final UserInformationRepository userInfoRepository;

    public UserController(UserRepository userRepository, UserInformationRepository userInfoRepository) {
        this.userRepository = userRepository;
        this.userInfoRepository = userInfoRepository;
    }

    @PostMapping("/api/register")
    User createUser(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistsException(user.getUsername());
        }
        user.setUserInfo(new UserInformation());
        return userRepository.save(user);
    }

    @PostMapping("/api/login")
    public User login(@RequestBody User user) {
        User userExists = userRepository.findByUsername(user.getUsername());
        if (userExists == null) {
            throw new UserNotFoundException(user.getUsername());
        }
        user.setToken(getJWTToken(user.getUsername()));
        return user;
    }

    @GetMapping("/api/users/{id}")
    UserInformation getUser(@PathVariable UUID id) {
        UserInformation userInfo = userInfoRepository.findOneByUuid(id);
        if (userInfo == null) {
            throw new UserNotFoundException(id.toString());
        }
        return userInfo;
    }

    @PutMapping("/api/users/{id}")
    UserInformation updateUser(@RequestBody UserInformation user, @PathVariable UUID id) {
        UserInformation userInfo = userInfoRepository.findOneByUuid(id);
        if (userInfo != null) {
            userInfo.setPhone(user.getPhone());
            userInfo.setName(user.getName());
            userInfo.setEmail(user.getEmail());
            return userInfoRepository.save(userInfo);
        } else {
            throw new UserNotFoundException(id.toString());
        }
    }

    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");
        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                       grantedAuthorities.stream()
                                         .map(GrantedAuthority::getAuthority)
                                         .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                          secretKey.getBytes()).compact();
        return "Bearer " + token;
    }

}