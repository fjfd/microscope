package com.vipshop.microscope.sample.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vipshop.microscope.sample.domain.User;
import com.vipshop.microscope.sample.jsonp.MapResult;
import com.vipshop.microscope.sample.service.UserService;
import com.vipshop.microscope.sample.service.impl.UserServiceImpl;
import com.vipshop.microscope.trace.span.HTTPHeader;

@Controller
public class UserController {

	UserService service = new UserServiceImpl();

	@RequestMapping("/user/insert")
	@ResponseBody
	public MapResult insertUser(HttpServletRequest request, String callback) {
		
		MapResult result = new MapResult();
		
		User user = new User();

		user.setName("alex");
		user.setGender(1);
		user.setAdress("shanghai-pudong");
		user.setEducation("anhui");
		user.setHight(175);
		user.setWeight(148);
		user.setAge(25);

		service.insert(user);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("user", user);
		result.setResult(data);
		result.setCallback(callback);
		
		return result;
		
	}

	@RequestMapping("/user/find")
	@ResponseBody
	public MapResult findUser(HttpServletRequest request, String callback) {
		List<User> users = service.find();

		MapResult result = new MapResult();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("users", users);
		result.setResult(data);
		result.setCallback(callback);
		return result;
	}
	
	@RequestMapping("/user/update")
	@ResponseBody
	public void updateUser(HttpServletRequest request, String callback) {
		service.update();
	}
	
	@RequestMapping("/user/delete")
	@ResponseBody
	public void deleteUser(HttpServletRequest request, String callback) {
		service.delete();
	}
	
	@RequestMapping("/thrift/hand")
	@ResponseBody
	public void thriftHand(HttpServletRequest request, String callback) {
		String traceid = request.getHeader(HTTPHeader.X_B3_TRACE_ID);
		System.out.println(traceid);
	}


}
