package com.showxd.nurse_management;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NurseManagementController {

    @RequestMapping("/index")
    public String index() {
        return "Nurse Management System is running!";
    }
}
