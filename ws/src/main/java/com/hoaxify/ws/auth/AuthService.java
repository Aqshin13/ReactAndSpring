package com.hoaxify.ws.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import jakarta.transaction.Transactional;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hoaxify.ws.user.User;
import com.hoaxify.ws.user.UserService;
import com.hoaxify.ws.user.UserRepository;

import com.hoaxify.ws.user.vm.UserVM;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

//    UserService userService;

    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    TokenRepository tokenRepository;


    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenRepository tokenRepository) {
        super();
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;

    }

    public AuthResponse authenticate(Credentials credentials) {
        User inDB = userRepository.findByUsername(credentials.getUsername());
        if (inDB == null) {
            throw new AuthException();
        }
        boolean matches = passwordEncoder.matches(credentials.getPassword(), inDB.getPassword());
//        if(matches) {
//            UserVM userVM = new UserVM(inDB);
//            String token = Jwts.builder()
//                    .setSubject(""+inDB.getId())
//                    .signWith(SignatureAlgorithm.HS512, "my-app-secret")
//                    .compact();
//            AuthResponse response = new AuthResponse();
//            response.setUser(userVM);
//            response.setToken(token);
//            return response;
//        }
        if (!matches) {
            throw new AuthException();
        }
        UserVM userVM = new UserVM(inDB);
//        String token = Jwts.builder().setSubject("" + inDB.getId()).signWith(SignatureAlgorithm.HS512, "my-app-secret").compact();
        String token = generateRandomToken();
        Token tokenEntity = new Token();
        tokenEntity.setToken(token);
        tokenEntity.setUser(inDB);
        tokenRepository.save(tokenEntity);
        AuthResponse response = new AuthResponse();
        response.setUser(userVM);
        response.setToken(token);
        return response;
    }

    @Transactional
    public UserDetails getUserDetails(String token) {
//        JwtParser parser = Jwts.parser().setSigningKey("my-app-secret");
//        try {
//            parser.parse(token);
//            Claims claims = parser.parseClaimsJws(token).getBody();
//            long userId = new Long(claims.getSubject());
//            User user = userRepository.getOne(userId);
//            User actualUser = (User)((HibernateProxy)user).getHibernateLazyInitializer().getImplementation();
//            return actualUser;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        Optional<Token> optionalToken = tokenRepository.findById(token);
        if(!optionalToken.isPresent()) {
            return null;
        }
        return optionalToken.get().getUser();
    }

    public void clearToken(String token) {
        tokenRepository.deleteById(token);

    }
    public String generateRandomToken() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}