package com.vipshop.microscope.query.action;

import com.vipshop.microscope.query.result.MapResult;
import com.vipshop.microscope.query.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Query home data API.
 *
 * @author Xu Fei
 * @version 1.0
 */
@Controller
public class HomeController {

    @Autowired
    private HomeService service;

    @RequestMapping("/home/index")
    @ResponseBody
    public MapResult index(HttpServletRequest request, String callback) {
        return null;
    }

    @RequestMapping("/home/systemInfo")
    @ResponseBody
    public MapResult systemInfo(HttpServletRequest request, String callback) {
        MapResult result = new MapResult();
        Map<String, Object> data = service.getSystemInfo(request);
        result.setResult(data);
        result.setCallback(callback);
        return result;
    }

}
