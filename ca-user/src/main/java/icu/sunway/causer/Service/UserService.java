package icu.sunway.causer.Service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.sunway.causer.Common.Api.Result;
import icu.sunway.causer.Common.Utils.JwtUtils;
import icu.sunway.causer.Entity.User;
import icu.sunway.causer.Mapper.UserMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.Objects;
import java.util.Random;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    private final RedisTemplate<String, Object> redisTemplate;
    private final JavaMailSender javaMailSender;
    @Value("${server.static}")
    private String staticServer;

    // 通过 Nacos 配置管理注入相关参数
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    UserService(RedisTemplate<String, Object> redisTemplate, JavaMailSender javaMailSender) {
        this.redisTemplate = redisTemplate;
        this.javaMailSender = javaMailSender;
    }

    public Result<JSONObject> parseToken(String token) {
        String id = JwtUtils.parseToken(token);
        if (id == null) {
            return new Result<>(400, "解析token失败", null);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        User user = getOne(queryWrapper);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("nickname", user.getNickname());
        jsonObject.put("avatar", user.getAvatar());
        jsonObject.put("email", user.getEmail());
        return new Result<>(200, "解析token成功", jsonObject);
    }

    public Result<JSONObject> createUser(String request) {
        JSONObject req = JSON.parseObject(request);
        String email = req.getString("email");
        String password = req.getString("password");
        String code = req.getString("code");
        String nickname = req.getString("nickname");
        if (!Objects.equals(code, redisTemplate.opsForValue().get("code_" + email))) {
            return new Result<>(400, "验证码错误", null);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        User user = getOne(queryWrapper);
        if (user != null) {
            return new Result<>(401, "用户已存在", null);
        }
        save(new User(null, nickname, staticServer + "/common/default_avatar.jpg", email, password));
        return new Result<>(200, "创建用户成功", null);
    }

    public Result<JSONObject> checkLogin(String token) {
        if (JwtUtils.parseToken(token) == null) {
            return new Result<>(400, "登录无效", null);
        }
        return new Result<>(200, "登录有效", null);
    }

    public Result<JSONObject> sendCode(String email) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, false);
            helper.setFrom("56038009@qq.com", "十四洲");
            helper.setTo(email);
            helper.setSubject("十四洲邮箱验证码");
            Random randomGenerator = new Random();
            StringBuilder code = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                code.append(randomGenerator.nextInt(10));
            }
            redisTemplate.opsForValue().set("code_" + email, code.toString());
            redisTemplate.expire("code_" + email, Duration.ofMinutes(5));
            helper.setText("您的验证码是 <strong>" + code + "</strong>", true);
            javaMailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            return new Result<>(400, "验证码发送失败", null);
        }
        return new Result<>(200, "验证码发送成功", null);
    }

    public Result<JSONObject> login(String request) {
        JSONObject req = JSON.parseObject(request);
        String email = req.getString("email");
        String code = req.getString("code");
        if (!Objects.equals(code, redisTemplate.opsForValue().get("code_" + email))) {
            return new Result<>(400, "登录失败", null);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        User user = getOne(queryWrapper);
        String token = JwtUtils.generateToken(user.getId());
        JSONObject resp = new JSONObject();
        resp.put("token", token);
        return new Result<>(200, "登录成功", resp);
    }

    public Result<JSONObject> loginPassword(String request) {
        JSONObject req = JSON.parseObject(request);
        String email = req.getString("email");
        String password = req.getString("password");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        queryWrapper.eq("password", password);
        User user = getOne(queryWrapper);
        if (user == null) {
            return new Result<>(400, "登录失败", null);
        }
        String token = JwtUtils.generateToken(user.getId());
        JSONObject resp = new JSONObject();
        resp.put("token", token);
        return new Result<>(200, "登录成功", resp);
    }

    public Result<JSONObject> getToken(String id) {
        String token = JwtUtils.generateToken(id);
        JSONObject resp = new JSONObject();
        resp.put("token", token);
        return new Result<>(200, "获取token成功", resp);
    }

    public Result<JSONObject> findPassword(String request) {
        JSONObject req = JSON.parseObject(request);
        String email = req.getString("email");
        String code = req.getString("code");
        String password = req.getString("password");
        if (!Objects.equals(code, redisTemplate.opsForValue().get("code_" + email))) {
            return new Result<>(400, "验证码错误", null);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        User user = getOne(queryWrapper);
        if (user == null) {
            return new Result<>(401, "用户不存在", null);
        }
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("password", password);
        updateWrapper.eq("id", user.getId());
        update(updateWrapper);
        return new Result<>(200, "密码重置成功", null);
    }
}
