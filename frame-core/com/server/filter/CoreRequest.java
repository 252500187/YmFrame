package com.server.filter;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lijunbo on 2016/1/12.
 */
public class CoreRequest implements Filter {

    private FilterConfig filterConfig;

    public FilterConfig getFilterConfig() {
        return filterConfig;
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy() {
        System.out.print("destroy");
    }

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        System.out.print("init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.getWriter().write("fffffffffffffff");
        ((HttpServletResponse) servletResponse).addCookie(new Cookie("a", ""));
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
