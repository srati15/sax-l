package security;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "UserAuthenticationFilter", urlPatterns = {"/profile", "/users-list"})
public class UserAuthenticationFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;
        if (request.getSession().getAttribute("user") == null) response.sendError(404, "You're not allowed to view this page. login please");
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config)  {

    }

}
