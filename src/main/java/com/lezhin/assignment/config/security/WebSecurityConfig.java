package com.lezhin.assignment.config.security;

import com.lezhin.assignment.common.jwt.JwtAccessDeniedHandler;
import com.lezhin.assignment.common.jwt.JwtAuthenticationEntryPoint;
import com.lezhin.assignment.common.jwt.JwtFilter;
import com.lezhin.assignment.common.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

// refer spring documentation
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig  {
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http    .csrf(AbstractHttpConfigurer::disable)  // CSRF 비활성화
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api/auth/**", "/api/content/**", "/api/purchase/**").permitAll()
                        )
                .sessionManagement(
                    session -> session.sessionCreationPolicy
                        (SessionCreationPolicy.STATELESS) // 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정
                ).exceptionHandling((exceptionHandling) ->
                        exceptionHandling
                                .accessDeniedHandler(jwtAccessDeniedHandler)
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // H2 DB, Swagger Spring Security ignoring
    @Bean
    public WebSecurityCustomizer configureH2ConsoleAndSwaggerEnable() {
        return web -> web.ignoring()
                .requestMatchers("/h2-console/**", // JUnit 테스트 시 오류로 경로 하드코딩 (기존 : PathRequest.toH2Console())
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/v3/api-docs/**");
    }

}