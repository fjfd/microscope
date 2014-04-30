package com.vipshop.microscope.query.action;

import com.vipshop.microscope.query.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

}
