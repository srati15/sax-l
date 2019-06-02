package security;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "GuestAuthenticationFilter", urlPatterns = {"/login", "/register"})
public class GuestAuthenticationFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;
        if (request.getSession().getAttribute("user") != null) response.sendError(404, "You are already logged in");
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config)  {

    }

}
