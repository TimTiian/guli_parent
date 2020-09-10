package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin
public class EduLoginController {

    // login
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }


    // logout
    @GetMapping("info")
    public R info(){
        return R.ok().data("name","admin").data("avatar","http://scimg.jianbihuadq.com/202004/2020040920112033.jpg");
    }
}
