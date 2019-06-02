package security;

import datatypes.User;
import enums.UserType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AdminAuthenticationFilter", urlPatterns = {"/admin", "/announcements"})
public class AdminAuthenticationFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) response.sendError(404, "You're not allowed to view this page. login please");
        if (user.getUserType() != UserType.Admin) response.sendError(404, "You don't have permission to view this page");
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config)  {

    }

}
