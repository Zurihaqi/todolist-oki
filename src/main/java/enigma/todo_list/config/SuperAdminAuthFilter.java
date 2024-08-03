package enigma.todo_list.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SuperAdminAuthFilter extends OncePerRequestFilter {
    @Value("${X-Super-Admin-Secret-Key}")
    private String superAdminKey;

    @Value("${X-Admin-Secret-Key}")
    private String adminKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        AntPathMatcher antPathMatcher = new AntPathMatcher();

        boolean urlEditRole = antPathMatcher.match("/api/admin/users/**/role", request.getRequestURI());
        boolean urlCreateSuperAdmin = antPathMatcher.match("/api/admin/super-admin", request.getRequestURI());

        if (request.getMethod().equals("PATCH") && urlEditRole) {
            if (!request.getHeader("X-Admin-Secret-Key").equals(adminKey)){
                filterChain.doFilter(request, response);
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }
        if (request.getMethod().equals("POST") && urlCreateSuperAdmin) {
            if (!request.getHeader("X-Super-Admin-Secret-Key").equals(superAdminKey)){
                filterChain.doFilter(request, response);
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }
        filterChain.doFilter(request,response);
    }
}
