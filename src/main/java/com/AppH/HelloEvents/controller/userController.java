
package com.AppH.HelloEvents.controller;

import com.AppH.HelloEvents.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/client")
public class userController {


















    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/test")
    public String test() {
        return "test";
    }

//
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/count")
//    public int countUsersByRole(@RequestParam String role) {
//        return userService.countUsersByRole("admin");
//    }

}
