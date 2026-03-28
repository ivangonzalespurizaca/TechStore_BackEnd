package com.techstore.techstore_backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component // Lo marcamos como componente para que Spring lo inyecte en el SecurityConfig
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain // La cadena de filtros que debe seguir la petición
    ) throws ServletException, IOException {

        // EXTRAER EL HEADER: Buscamos la cabecera 'Authorization'
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // VALIDAR FORMATO: Si no tiene el header o no empieza con 'Bearer ', ignoramos y seguimos
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Pasa la petición al siguiente filtro
            return;
        }

        // OBTENER EL TOKEN: Saltamos los primeros 7 caracteres ("Bearer ")
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt); // Extraemos el email del token usando el servicio JWT

        // Si hay email y el usuario aún NO está autenticado en el contexto de Spring...
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            // Buscamos al usuario en la base de datos para cargar sus detalles
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // Si el token es válido y no ha expirado...
            if(jwtService.isTokenValid(jwt, userDetails)){
                // Creamos el objeto de autenticación que Spring Security entiende
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // No necesitamos la contraseña
                        userDetails.getAuthorities() // Cargamos sus ROLES (ADMIN, CLIENTE)
                );
                // Añadimos detalles de la petición (IP, sesión, etc.)
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // Enviamos la petición al siguiente filtro o al Controller
        filterChain.doFilter(request, response);
    }
}
