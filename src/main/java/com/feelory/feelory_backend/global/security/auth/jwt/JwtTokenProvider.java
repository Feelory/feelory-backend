package com.feelory.feelory_backend.global.security.auth.jwt;

import com.feelory.feelory_backend.global.exception.exceptions.auth.AdminAccessDeniedException;
import com.feelory.feelory_backend.global.exception.exceptions.auth.IllegalUserTypeException;
import com.feelory.feelory_backend.global.exception.exceptions.auth.UserAccessDeniedException;
import com.feelory.feelory_backend.global.exception.exceptions.auth.UserIdNotFoundException;
import com.feelory.feelory_backend.users.model.UserRole;
import com.feelory.feelory_backend.users.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;
    private final UserService userService;

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(Long userId, UserRole role) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("role", role.name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenExpiration()))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(Long userId) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshTokenExpiration()))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            Long userId = getUserIdFromToken(token);
            return userService.isActiveUser(userId);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Long getUserIdFromToken(String token) {
        return Long.parseLong(parseClaims(token).getSubject());
    }

    public String getRoleFromToken(String token) {
        return (String) parseClaims(token).get("role");
    }

    public LocalDateTime getExpirationLocalDateTimeFromToken(String token) {
        Date expiration = parseClaims(token).getExpiration();

        // UTC -> 시스템 시간대 변환
        return expiration.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public Authentication getAuthenticationFromContext() {

        return SecurityContextHolder.getContext().getAuthentication();
    }

    public List<UserRole> getRolesFromAuthentication() {
        Authentication authentication = getAuthenticationFromContext();

        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(role -> role.replace("ROLE_", "")) // 접두사 제거
                .map(UserRole::valueOf)
                .collect(Collectors.toList());
    }

    public void checkUserOrAdmin() {
        List<UserRole> roles = getRolesFromAuthentication();

        boolean isUser = roles.stream()
                .anyMatch(hasRole -> UserRole.USER.getLevel() <= hasRole.getLevel());

        if(!isUser) {
            throw new UserAccessDeniedException();
        }
    }

    public void checkAdmin() {
        List<UserRole> roles = getRolesFromAuthentication();

        boolean isAdmin = roles.stream()
                .anyMatch(hasRole -> UserRole.ADMIN.getLevel() <= hasRole.getLevel());

        if(!isAdmin) {
            throw new AdminAccessDeniedException();
        }
    }

    public UserDetails getUserDetailsFromAuthentication() {
        Authentication authentication = getAuthenticationFromContext();

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            return (UserDetails) principal;
        } else {
            throw new IllegalUserTypeException();
        }
    }

    public Long getUserIdFromUserDetails() {

        UserDetails userDetails = getUserDetailsFromAuthentication();

        String userId = userDetails.getUsername();

        if(userId == null) {
            throw new UserIdNotFoundException();
        }

        return Long.parseLong(userId);
    }

    public Long getUserIdFromAuthentication() {
        Authentication authentication = getAuthenticationFromContext();

        Object principal = authentication.getPrincipal();

        return (Long) principal;
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}