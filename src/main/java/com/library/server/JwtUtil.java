package main.java.com.library.server;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Jwt 工具类，用于生成和解析 JWT 令牌，并包含权限管理逻辑。
 */
public class JwtUtil {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Generates a secure random key
    private static final long EXPIRATION_TIME = 3600_000; // 1 hour


    public static String generateToken(String userID, String role) {
        Map<String, Object> claims = Map.of("userId", userID, "role", role);
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


    public static boolean canPerform(String jwtToken, String action) {
        String role = extractRole(jwtToken);
        return getPermissionsByRole(role).contains(action);
    }

    public static boolean isaAdmin(String jwtToken) {
        String role = extractRole(jwtToken);
        return role.equals("admin");
    }

    private static Set<String> getPermissionsByRole(String role) {
        // 简单的权限映射，可以通过数据库或配置文件加载
        return switch (role) {
            case "admin" -> Set.of("add", "get", "update", "delete");
            default -> new HashSet<>();
        };
    }
}