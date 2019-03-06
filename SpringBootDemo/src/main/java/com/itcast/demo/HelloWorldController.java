package com.itcast.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiuK
 * @date 2018/12/20 14:18
 * @description:
 */
@RestController
public class HelloWorldController {

    @Autowired
    private Environment env;

    @RequestMapping("/info")
    public String info(){
        return "HelloWorld!!"+env.getProperty("url");
    }
}
