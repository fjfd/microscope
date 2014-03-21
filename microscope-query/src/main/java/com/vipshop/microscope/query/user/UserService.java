package com.vipshop.microscope.query.user;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AssertionHolder;
import org.jasig.cas.client.validation.Assertion;

public class UserService {

	public User fetchLogin() {
		User user = new User();
		Assertion assertion = AssertionHolder.getAssertion();
		AttributePrincipal principal = assertion.getPrincipal();
		String infoSnapshot = principal.getName();
		String[] info = infoSnapshot.split("\\|");
		user.setName(info[0]);

		try {
			Map<String, Object> attributes = principal.getAttributes();
			user.setNumber(Long.valueOf((String) attributes.get("UserNum")));
			user.setChineseName(URLDecoder.decode((String) attributes.get("UserName"), "utf-8"));
			user.setDepartmentName(URLDecoder.decode((String) attributes.get("DeptName"), "utf-8"));
			user.setDepartmentStruction(URLDecoder.decode((String) attributes.get("DeptFullName"), "utf-8"));
			user.setEmail(URLDecoder.decode((String) attributes.get("UserEmail"), "utf-8"));
			user.setMobile((String) attributes.get("UserMobile"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return user;
	}

	public boolean authenticate(User login) {
		return true;
	}

}