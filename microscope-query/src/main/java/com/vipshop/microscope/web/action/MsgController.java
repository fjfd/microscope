package com.vipshop.microscope.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vipshop.microscope.mysql.condition.MsgReportCondition;
import com.vipshop.microscope.web.condition.QueryConditionBuilder;
import com.vipshop.microscope.web.result.MapResult;
import com.vipshop.microscope.web.service.MsgService;

@Controller
public class MsgController {

	MsgService service = new MsgService();

	@RequestMapping("/report/msgReport")
	@ResponseBody
	public MapResult msgReport(HttpServletRequest request, String callback) {
		MapResult result = new MapResult();
		
		MsgReportCondition query = QueryConditionBuilder.buildMsgReport(request);
		Map<String, Object> condition = service.getMsgReport(query);
		result.setResult(condition);
		result.setCallback(callback);
		return result;
	}

}
