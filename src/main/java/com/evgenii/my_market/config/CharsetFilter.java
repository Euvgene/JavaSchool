package com.evgenii.my_market.config;

import javax.servlet.*;
import java.io.IOException;

public class CharsetFilter implements Filter {
    private String code;

    @Override
    public void init(FilterConfig fConfig) {
        code = fConfig.getInitParameter("character-encoding");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String codeRequest = request.getCharacterEncoding();
        if (code != null && !code.equalsIgnoreCase(codeRequest)) {
            request.setCharacterEncoding(code);
            response.setCharacterEncoding(code);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        code = null;
    }
}