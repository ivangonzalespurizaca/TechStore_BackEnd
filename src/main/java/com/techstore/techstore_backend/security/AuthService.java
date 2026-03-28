package com.techstore.techstore_backend.security;

import com.techstore.techstore_backend.dto.AuthResponse;
import com.techstore.techstore_backend.dto.LoginRequest;
import com.techstore.techstore_backend.dto.RegisterRequest;
import com.techstore.techstore_backend.dto.UsuarioResponseDTO;
import com.techstore.techstore_backend.entity.Usuario;
import com.techstore.techstore_backend.entity.enums.Genero;
import com.techstore.techstore_backend.entity.enums.Rol;
import com.techstore.techstore_backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder; // Herramienta para ocultar contrasenas
    private final JwtService jwtService; // Herramienta para generar tokens JWT
    private final AuthenticationManager authenticationManager; // Herramienta para validar email/password

    public AuthResponse register(RegisterRequest request){

        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Error: El correo electrónico ya está registrado");
        }

        // Convertimos los datos que llegan de Angular al objeto de Base de Datos
        var user = Usuario.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                // Encriptamos la clave antes de que toque la DB
                .contrasenia(passwordEncoder.encode(request.getContrasenia()))
                .genero(Genero.valueOf(request.getGenero().toUpperCase()))
                .rol(Rol.CLIENTE)
                .activo(true)
                .build();

        // Guardamos el usuario en la DB
        repository.save(user);

        // Generamos un token JWT para el usuario registrado
        var jwtToken = jwtService.generateToken(user);

        UsuarioResponseDTO userDto = UsuarioResponseDTO.builder()
                .email(user.getEmail())
                .nombre(user.getNombre())
                .rol(user.getRol().name())
                .build();

        return AuthResponse.builder()
                .token(jwtToken)
                .usuario(userDto)
                .build();
    }

    // Verifica que el email y contraseña sean correctos, y si es así, devuelve un nuevo token JWT
    public AuthResponse login(LoginRequest request){
        // El manager compara el email y la clave (usando BCrypt internamente)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getContrasenia()
                )
        );

        // Si la clave fue correcta, buscamos al usuario completo para saber su Rol
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        var jwtToken = jwtService.generateToken(user);

        UsuarioResponseDTO userDto = UsuarioResponseDTO.builder()
                .email(user.getEmail())
                .nombre(user.getNombre())
                .rol(user.getRol().name())
                .build();

        return AuthResponse.builder()
                .token(jwtToken)
                .usuario(userDto)
                .build();
    }
}
