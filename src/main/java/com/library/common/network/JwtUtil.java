package main.java.com.library.common.network;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author upteka
 */
public class JwtUtil {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Generates a secure random key
    private static final long EXPIRATION_TIME = 3600_000; // 1 hour

    private static final Map<String, Map<String, Boolean>> PERMISSIONS = new HashMap<>();

    static {
        Map<String, Boolean> adminPermissions = new HashMap<>();
        adminPermissions.put("add", true);
        adminPermissions.put("get", true);
        adminPermissions.put("update", true);
        adminPermissions.put("delete", true);

        Map<String, Boolean> userPermissions = new HashMap<>();
        userPermissions.put("add", false);
        userPermissions.put("get", true);
        userPermissions.put("update", false);
        userPermissions.put("delete", false);

        PERMISSIONS.put("admin", adminPermissions);
        PERMISSIONS.put("user", userPermissions);
    }

    public static String generateToken(String userID, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userID);
        claims.put("role", role);

        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    public static Claims extractClaims(String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }

        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public static boolean isTokenExpired(String token) {
        if (token == null || token.isEmpty()) {
            return true; // Treat null or empty token as expired
        }

        return extractClaims(token).getExpiration().before(new Date());
    }

    public static String extractUserId(String token) {
        return extractClaims(token).get("userId", String.class);
    }

    public static String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    public static boolean isAdmin(String token) {
        return "admin".equals(extractRole(token));
    }

    public static boolean canPerform(String jwtToken, String action, String entity) {
        String role = extractRole(jwtToken);
        return PERMISSIONS.getOrDefault(role, new HashMap<>(4)).getOrDefault(action, false);
    }
}