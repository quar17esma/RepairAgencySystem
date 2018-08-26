package com.quar17esma.filter;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {

    private String code;

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        code = fConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        String codeRequest = servletRequest.getCharacterEncoding();

        if (code != null && !code.equalsIgnoreCase(codeRequest)) {
            servletRequest.setCharacterEncoding(code);
            servletResponse.setCharacterEncoding(code);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        code = null;
    }

}
