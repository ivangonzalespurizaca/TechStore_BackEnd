package com.techstore.techstore_backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    // Extrae la llave secreta desde application.properties por seguridad
    @Value("${application.security.jwt.secret-key}")
    private String SECRET_KEY;

    // Extrae el 'subject' del token, que en nuestro caso es el email del usuario
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Metodo genérico para extraer cualquier claim del token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Creacion del token: Aqui se crea el JWT
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername()) // Guardamos el email como identificador principal
                .issuedAt(new Date(System.currentTimeMillis())) // Fecha de emisión (ahora)
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // Expira en 24 horas
                .signWith(getSignInKey()) // Firma el token con la llave secreta
                .compact(); // Lo serializa a String
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        // El token es valido si el email coincide y el reloj no ha pasado la fecha de expiracion
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extrae todos los claims del token usando la llave secreta para verificar la firma
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey()) // Verifica la firma del token con la llave secreta
                .build()
                .parseSignedClaims(token) // Lee el token "sellado"
                .getPayload(); // Obtiene el contenido (el JSON interno del token)
    }

    // Convierte tu clave de texto (Base64) en una llave secreta HMAC SHA-256
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}