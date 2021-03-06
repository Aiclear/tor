package com.tor.controller;


import com.tor.common.ServerResponse;
import com.tor.pojo.PageBean;
import com.tor.pojo.Traffic;
import com.tor.service.IFirstIdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class FirstIdentityController {

    @Autowired
    private IFirstIdentityService iFirstIdentityService;

    @RequestMapping(value = "/first/goto_identity")
    public String identity() {
        return "first_identity";
    }


    @RequestMapping(value = "/first/identity")
    public String identity(HttpSession session, Model model, String filePath) {

        if (StringUtils.isEmpty(filePath)) {
            ServerResponse response = ServerResponse.createByErrorMessage("文件路径为空");
            session.setAttribute("list", null);
            model.addAttribute("res", response);
        } else {
            ServerResponse<List<Traffic>> response = iFirstIdentityService.getIdentityList(filePath);
            session.setAttribute("list", response.getData());
            model.addAttribute("res", response);
        }
        return "first_identity";
    }

    @RequestMapping(value = "/first/identity_by_page")
    public String identityByPage(HttpSession session, Model model, Integer page) {
        List<Traffic> trafficList = (List<Traffic>) session.getAttribute("list");
        if (trafficList == null) {
            ServerResponse response = ServerResponse.createByErrorMessage("判断超时，请重新判断");
            model.addAttribute("res", response);
        } else if (StringUtils.isEmpty(page)) {
            ServerResponse response = ServerResponse.createByErrorMessage("参数错误");
            model.addAttribute("res", response);
//            model.addAttribute("status", 0);
//            model.addAttribute("msg", "文件路径为空");
        } else {
            ServerResponse<PageBean> response = iFirstIdentityService.queryForPage(20, page, trafficList);
            model.addAttribute("res", response);
        }
        return "first_identity";
    }


    @RequestMapping(value = "index")
    public String index(HttpSession session) {

        return "index";
    }

    @RequestMapping(value = "top")
    public String top() {
        return "top";
    }

    @RequestMapping(value = "left")
    public String left() {
        return "left";
    }

    @RequestMapping(value = "right")
    public String right() {
        return "right";
    }
}
