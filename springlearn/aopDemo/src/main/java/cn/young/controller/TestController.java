package cn.young.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @RequestMapping(value="/test")
    @ResponseBody
    public String  test() {
        System.out.println("方法执行-----------");
        return "成功！";
    }

}
