package br.com.danilo.forum.config.security;

import br.com.danilo.forum.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
public class SecurityConfiguration  {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests() //
                .antMatchers(HttpMethod.GET, "/topicos").permitAll() //
                .antMatchers(HttpMethod.GET, "/topicos/*").permitAll() //
                .antMatchers(HttpMethod.POST, "/auth").permitAll() //
                .antMatchers(HttpMethod.GET, "/actuator/**").permitAll() //
                .antMatchers(HttpMethod.GET, "/").permitAll() //
                .antMatchers(HttpMethod.DELETE, "/topicos/*").hasAnyRole("ADMINISTRADOR", "MODERADOR") //
                .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/**/api-docs").permitAll() //
                .antMatchers("**/favicon.ico", "/css/**", "/images/**", "/js/**", "/webjars/**").permitAll() //
                .anyRequest().authenticated() //
                .and().csrf().disable() //
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(autenticacaoService).passwordEncoder(passwordEncoder());
    }

}
