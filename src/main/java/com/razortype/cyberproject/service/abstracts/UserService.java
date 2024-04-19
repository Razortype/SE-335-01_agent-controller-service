package com.razortype.cyberproject.service.abstracts;

import com.razortype.cyberproject.core.results.DataResult;
import com.razortype.cyberproject.entity.User;

public interface UserService {

    DataResult<User> getUserByEmail(String email);

}
