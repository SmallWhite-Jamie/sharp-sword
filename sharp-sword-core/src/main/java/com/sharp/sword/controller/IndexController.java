package com.sharp.sword.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * IndexController
 *
 * @author lizheng
 * @version 1.0
 * @date 2020/3/10 17:29
 */
@Controller
public class IndexController {

    @RequestMapping("doc")
    public String doc() {
        return "redirect:swagger-ui.html";
    }
}
