package br.com.danilo.forum.controller;

import br.com.danilo.forum.config.security.TokenService;
import br.com.danilo.forum.controller.dto.LoginDto;
import br.com.danilo.forum.controller.dto.TokenDto;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {


    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;


    @PostMapping
    public ResponseEntity<?> autenticar(@RequestBody @Valid LoginDto loginDto){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("crip 123456 "+encoder.encode("123456"));

        try {
            UsernamePasswordAuthenticationToken dadosLogin = loginDto.converter();
            Authentication authentication = authManager.authenticate(dadosLogin);
            String token = tokenService.gerarToken(authentication);
            System.out.println(token);


            return ResponseEntity.ok( new TokenDto(token, "Bearer"));

        }catch ( AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }

    }

}
