package com.runke.filter;

import com.runke.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 存放Token的Header Key
     */
    public static final String HEADER_STRING = "Authorization";
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
       String token = httpServletRequest.getHeader(HEADER_STRING);
       if (null != token){
           String username = jwtTokenUtil.getUsernameFromToken(token);
           if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
               UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
               if (jwtTokenUtil.validateToken(token,userDetails)){
                   UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                   authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                   SecurityContextHolder.getContext().setAuthentication(authenticationToken);
               }
           }
       }
       filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
