package cn.young.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @RequestMapping(value="/test/{id}")
    @ResponseBody
    public String  test(@PathVariable String id) {
        System.out.println("方法执行-----------");
        return "成功！"+id;
    }

}
