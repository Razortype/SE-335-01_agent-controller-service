package com.razortype.cyberproject.core.utils;

import com.razortype.cyberproject.core.results.DataResult;
import com.razortype.cyberproject.entity.User;
import com.razortype.cyberproject.service.abstracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUserUtil {

    private final UserService userService;

    @Autowired
    public AuthUserUtil(UserService userService) {
        this.userService = userService;
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

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
