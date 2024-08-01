package enigma.todo_list.config;

import enigma.todo_list.config.advisers.CustomAccessDeniedException;
import enigma.todo_list.config.advisers.CustomAuthenticationEntryPoint;
import enigma.todo_list.model.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Autowired
    private CustomAccessDeniedException accessDeniedException;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authz -> authz
                        //Auth Endpoint
                        .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/refresh").permitAll()

                        //Todos Endpoint
                        .requestMatchers(HttpMethod.POST, "/api/todos").hasAuthority(Role.USER.name())
                        .requestMatchers(HttpMethod.GET, "/api/todos").hasAuthority(Role.USER.name())
                        .requestMatchers(HttpMethod.GET, "/api/todos/**").hasAuthority(Role.USER.name())
                        .requestMatchers(HttpMethod.PUT, "/api/todos/**").hasAuthority(Role.USER.name())
                        .requestMatchers(HttpMethod.PATCH, "/api/todos/**").hasAuthority(Role.USER.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/todos/**").hasAuthority(Role.USER.name())

                        //User Management API
                        .requestMatchers(HttpMethod.GET, "/api/admin/users").hasAnyAuthority(Role.ADMIN.name(), Role.SUPER_ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/api/admin/users/**").hasAnyAuthority(Role.ADMIN.name(), Role.SUPER_ADMIN.name())
                        .requestMatchers(HttpMethod.PATCH, "/api/admin/users/**").hasAuthority(Role.SUPER_ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/api/admin/super-admin").permitAll()

                        //Todo Item Management EndPoint
                        .requestMatchers(HttpMethod.GET, "/api/admin/todos").hasAnyAuthority(Role.ADMIN.name(), Role.SUPER_ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/api/admin/todos/**").hasAnyAuthority(Role.ADMIN.name(), Role.SUPER_ADMIN.name())

                        .anyRequest().authenticated()
                )
                .exceptionHandling(entryPoint -> entryPoint
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedException))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout.logoutUrl("/api/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext())));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));  // Be careful with this in production
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
