package com.vipshop.microscope.test.app.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vipshop.microscope.test.app.service.UserService;
import com.vipshop.microscope.test.app.service.UserServiceImpl;
import com.vipshop.microscope.web.result.MapResult;

@Controller
public class UserController {
	
	UserService service = new UserServiceImpl();
	
	@RequestMapping("/user/insert")
	@ResponseBody
	public MapResult marketReport(HttpServletRequest request, String callback) {
		MapResult result = new MapResult();
		result.setCallback(callback);
		return result;
	}	

}
