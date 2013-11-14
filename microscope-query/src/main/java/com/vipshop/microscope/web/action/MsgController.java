package com.vipshop.microscope.web.action;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vipshop.microscope.web.result.ListResult;
import com.vipshop.microscope.web.service.MsgService;

public class MsgController {

	MsgService service = new MsgService();

	@RequestMapping("/msg/msgReport")
	@ResponseBody
	public ListResult msgReport(String callback) {
		ListResult result = new ListResult();
		List<Map<String, Object>> condition = service.getResult();
		result.setResult(condition);
		result.setCallback(callback);
		return result;
	}

}
