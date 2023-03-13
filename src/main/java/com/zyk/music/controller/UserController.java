package com.zyk.music.controller;

import com.zyk.music.base.ReturnRes;
import com.zyk.music.entity.User;
import com.zyk.music.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ReturnRes<Object> login(@RequestBody User user) throws InterruptedException {
        Thread.sleep(1000);
        return userService.login(user);
    }
}
