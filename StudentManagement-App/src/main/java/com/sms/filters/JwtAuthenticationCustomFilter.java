package com.sms.filters;

import com.sms.config.AuthenticationUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationCustomFilter extends OncePerRequestFilter {

    private final JwtAuthenticationUtils jwtAuthenticationUtils;
    private final AuthenticationUserDetailsService authenticationUserDetailsService;


    @Autowired
    public JwtAuthenticationCustomFilter(JwtAuthenticationUtils jwtAuthenticationUtils, AuthenticationUserDetailsService authenticationUserDetailsService) {
        this.jwtAuthenticationUtils = jwtAuthenticationUtils;
        this.authenticationUserDetailsService = authenticationUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = jwtAuthenticationUtils.getJwtTokenFromRequest(request);

        if( StringUtils.hasText(token) && jwtAuthenticationUtils.validateToken( token ) ){

            String username = jwtAuthenticationUtils.getUserName( token );
            UserDetails userDetails =  authenticationUserDetailsService.loadUserByUsername( username );

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails , null , userDetails.getAuthorities());

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }

        filterChain.doFilter( request , response );


    }

}
