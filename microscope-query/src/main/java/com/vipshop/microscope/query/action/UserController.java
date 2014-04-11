package com.vipshop.microscope.query.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vipshop.microscope.query.result.ListResult;
import com.vipshop.microscope.query.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
}
