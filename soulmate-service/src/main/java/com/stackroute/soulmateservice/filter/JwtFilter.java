package com.stackroute.soulmateservice.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Siva
 * @Date 10/29/2021 4:11 PM
 */
@Slf4j
@Component
public class JwtFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.debug("Inside doFilter()");
        final HttpServletRequest request=(HttpServletRequest) servletRequest;
        final HttpServletResponse response=(HttpServletResponse) servletResponse;
        final String authHeader=request.getHeader("Authorization");
        String url = ((HttpServletRequest)request).getRequestURL().toString();
        if (url.equals("http://localhost:8088/api/v1/user")&&"POST".equals(request.getMethod()))
        {
            log.debug("registration request. No token varification is required");
            filterChain.doFilter(request,response);
            return;
        }
        if("OPTIONS".equals(request.getMethod()))
        {
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(request,response);
        }
        else{
            if(authHeader==null || !authHeader.startsWith("Bearer "))
            {
                throw new ServletException("Missing or invalid authorization header");
            }
            final String token=authHeader.substring(7);
            System.out.println("token" + token);
            Claims claims= Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
            request.setAttribute("claims",claims);
            request.setAttribute("profile", servletRequest.getParameter("email"));
            filterChain.doFilter(request,response);
        }

    }
}
