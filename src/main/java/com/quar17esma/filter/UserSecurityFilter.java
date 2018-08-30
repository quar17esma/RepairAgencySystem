package com.quar17esma.filter;

import com.quar17esma.controller.manager.ConfigurationManager;
import com.quar17esma.entity.User;
import com.quar17esma.enums.Role;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// restricts access for user
public class UserSecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");
        if (user != null) {
            if (Role.USER.equals(user.getRole())) {
                String page = ConfigurationManager.getProperty("path.page.welcome");
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
                dispatcher.forward(request, response);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}