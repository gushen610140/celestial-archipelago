package icu.sunway.causer.Controller;

import icu.sunway.causer.Common.Api.Result;
import icu.sunway.causer.Entity.VO.UserCreateView;
import icu.sunway.causer.Service.UserService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public Result<Boolean> createUser(@RequestBody UserCreateView userCreateView) {
        return userService.createUser(userCreateView);
    }

    @GetMapping("/send_code")
    public Result<Null> sendCode(@RequestParam String email) {
        return userService.sendCode(email);
    }
}
