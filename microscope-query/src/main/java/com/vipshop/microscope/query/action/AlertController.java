package com.vipshop.microscope.query.action;

import com.vipshop.microscope.query.result.MapResult;
import com.vipshop.microscope.query.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AlertController {

    @Autowired
    private AlertService service;

    @RequestMapping("/alert/index")
    @ResponseBody
    public MapResult index(HttpServletRequest request, String callback) {
        return null;
    }

}
