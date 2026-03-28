package com.techstore.techstore_backend.config;

import com.techstore.techstore_backend.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                //Desactiva CSRF porque no usamos sesiones ni cookies, y el token JWT ya protege contra ataques CSRF.
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/uploads/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/categorias/**").hasAnyAuthority("CLIENTE", "ADMINISTRADOR")

                        .requestMatchers(HttpMethod.GET, "/api/productos/**").hasAnyAuthority("CLIENTE", "ADMINISTRADOR")
                        .requestMatchers("/api/productos/**").hasAuthority("ADMINISTRADOR")

                        .requestMatchers("/api/carrito/**").hasAnyAuthority("CLIENTE")

                        .requestMatchers(HttpMethod.POST, "/api/pedidos").hasAuthority("CLIENTE")
                        .requestMatchers("/api/pedidos/mis-pedidos").hasAnyAuthority("CLIENTE")
                        .requestMatchers("/api/pedidos/**").hasAnyAuthority("ADMINISTRADOR")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
