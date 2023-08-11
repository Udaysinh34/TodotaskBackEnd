package com.niit.TodoList.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //type cast servlet request/response to Http request/response
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //expect the token
        String authHeader = request.getHeader("authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer_")) {

            //token missing

            throw new ServletException("Missing Or Invalid Token");
        }
        else {
            String jwtToken = authHeader.substring(7);
            Claims claims = Jwts.parser().setSigningKey("ourkey").parseClaimsJws(jwtToken).getBody();
            request.setAttribute("Current_user_emailId", claims.get("user_email"));

            filterChain.doFilter(request, response);

        }
    }
}
