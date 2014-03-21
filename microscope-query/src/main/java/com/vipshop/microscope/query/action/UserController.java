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
	
	@RequestMapping("/user/userlist")
	@ResponseBody
	public ListResult queryExcepCondition(String callback) {
		ListResult result = new ListResult();
		List<Map<String, Object>> data = userService.findUseHistory();
		result.setResult(data);
		result.setCallback(callback);
		return result;
	}

}
