package icu.sunway.causer.Controller;

import com.alibaba.fastjson2.JSONObject;
import icu.sunway.causer.Common.Api.Result;
import icu.sunway.causer.Service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/parse_token")
    public Result<JSONObject> parseToken(@RequestParam("token") String token) {
        return userService.parseToken(token);
    }

    @PostMapping("/create")
    public Result<JSONObject> createUser(@RequestBody String request) {
        return userService.createUser(request);
    }

    @GetMapping("/check_login")
    public Result<JSONObject> checkLogin(@RequestParam("token") String token) {
        return userService.checkLogin(token);
    }

    @GetMapping("/send_code")
    public Result<JSONObject> sendCode(@RequestParam String email) {
        return userService.sendCode(email);
    }

    @PostMapping("/login")
    public Result<JSONObject> login(@RequestBody String request) {
        return userService.login(request);
    }

    @PostMapping("/login_password")
    public Result<JSONObject> loginPassword(@RequestBody String request) {
        return userService.loginPassword(request);
    }

    @GetMapping("/get_token")
    public Result<JSONObject> getToken(@RequestParam String id) {
        return userService.getToken(id);
    }

    @PostMapping("/find_password")
    public Result<JSONObject> findPassword(@RequestBody String request) {
        return userService.findPassword(request);
    }
}
