package com.tramon.userservice.filter;


import com.tramon.userservice.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

// Клас позначено як @Component, щоб Spring міг автоматично
// створювати його екземпляр.
@Component
// JwtAuthenticationFilter наслідується від OncePerRequestFilter,
// що гарантує виконання фільтра тільки один раз на запит.
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // Оголошується поле для роботи з токенами через JwtUtil.
    private final JwtUtil jwtUtil;
    // Конструктор для ін’єкції залежності JwtUtil.
    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    @Override
// Перевизначення методу doFilterInternal, який виконує перевірку
// JWT токена у кожному HTTP-запиті.
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws
            ServletException, IOException {
        // Отримується значення заголовка Authorization з вхідного запиту.
        final String authHeader = request.getHeader("Authorization");
        // Перевіряється, чи існує заголовок і
        // чи починається він зі слова "Bearer " (стандарт для токенів).
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Вирізається сам токен, без префікса "Bearer ".
            final String token = authHeader.substring(7);
            // Використовується jwtUtil для витягнення даних (claims) з токена.
            Claims claims = jwtUtil.extractClaims(token);
            // Отримуються ім'я користувача (subject) і
            // роль користувача з claims токена.
            String username = claims.getSubject();
            String role = (String) claims.get("role");
            // Створюється об'єкт UsernamePasswordAuthenticationToken,
            // який представляє автентифікованого користувача.
            // Тут Collections.emptyList() вказує,
// що жодних ролей/прав (authorities) явно не призначено
// (можна додати при потребі).
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            new User(username, "", Collections.emptyList()),
                            null,
                            Collections.emptyList()
                    );
            // Додаються додаткові дані про HTTP-запит
// до токена автентифікації (наприклад, IP-адреса, сесія).
            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );
// Установлюється автентифікація користувача
            // в контексті Spring Security.
            // Після цього запит вважається автентифікованим.
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
// Продовжується ланцюжок обробки запиту
// (передається далі до наступного фільтра або контролера).
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/actuator"); // або ще: || path.startsWith("/auth")
    }
}