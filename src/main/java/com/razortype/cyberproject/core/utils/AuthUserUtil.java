package com.razortype.cyberproject.core.utils;

import com.razortype.cyberproject.config.JwtService;
import com.razortype.cyberproject.core.results.DataResult;
import com.razortype.cyberproject.entity.User;
import com.razortype.cyberproject.service.abstracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class AuthUserUtil {

    private final UserService userService;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Autowired
    public AuthUserUtil(UserService userService, JwtService jwtService, UserDetailsService userDetailsService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    public User getAuthenticatedUser() {

        Authentication auth = this.getAuthentication();
        if (auth == null) { return null; }

        DataResult result = userService.getUserByEmail(auth.getName());
        return (User) result.getData();

    }

    public boolean IsRequestAuthenticated() {
        Authentication auth = this.getAuthentication();
        return auth.isAuthenticated();
    }

    public User getUserFromSocketSession(WebSocketSession session) {

        String token = session.getHandshakeHeaders().getFirst("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }

        String jwt = token.substring(7);
        String userEmail = jwtService.extractUsername(jwt);

        if (userEmail == null) {
            return null;
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
        if (!jwtService.isTokenValid(jwt)) {
            return null;
        }

        return userService.getUserByEmail(userDetails.getUsername()).getData();

    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
