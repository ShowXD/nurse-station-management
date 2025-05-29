package com.showxd.nurse_management;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NurseManagementController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
