package com.vipshop.microscope.query.action;

import com.vipshop.microscope.query.result.MapResult;
import com.vipshop.microscope.query.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Query report data API.
 *
 * @author Xu Fei
 * @version 1.0
 */
@Controller
public class ReportController {

    @Autowired
    private ReportService service;

    @RequestMapping("/report/metrics")
    @ResponseBody
    public MapResult metric(HttpServletRequest request, String callback) {
        MapResult result = new MapResult();
        Map<String, Object> data = service.metric(request);
        result.setResult(data);
        result.setCallback(callback);
        return result;
    }

    @RequestMapping("/report/topReport")
    @ResponseBody
    public MapResult topReport(HttpServletRequest request, String callback) {
        MapResult result = new MapResult();
        Map<String, Object> data = service.getTopReport();
        result.setResult(data);
        result.setCallback(callback);
        return result;
    }

}
