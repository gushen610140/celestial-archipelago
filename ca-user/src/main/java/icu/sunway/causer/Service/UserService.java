package icu.sunway.causer.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.sunway.causer.Common.Api.Result;
import icu.sunway.causer.Entity.DO.User;
import icu.sunway.causer.Entity.VO.UserCreateView;
import icu.sunway.causer.Mapper.UserMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.Random;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Value("${server.static}")
    private String staticServer;
    private final RedisTemplate<String, Object> redisTemplate;
    private final JavaMailSender javaMailSender;

    UserService(RedisTemplate<String, Object> redisTemplate, JavaMailSender javaMailSender) {
        this.redisTemplate = redisTemplate;
        this.javaMailSender = javaMailSender;
    }

    public Result<Boolean> createUser(UserCreateView userCreateView) {
        save(new User(null, userCreateView.getNickname(), staticServer + "/default_avatar.jpg" , userCreateView.getEmail(), userCreateView.getPassword(), null));
        return Result.success(true);
    }

    public Result<Null> sendCode(String email) {
        // 创建一个邮件消息
        MimeMessage message = javaMailSender.createMimeMessage();
        // 创建 MimeMessageHelper
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, false);
            // 发件人邮箱和名称
            helper.setFrom("56038009@qq.com", "十四洲");
            // 收件人邮箱
            helper.setTo(email);
            // 邮件标题
            helper.setSubject("十四洲邮箱验证码");
            // 生成验证码
            Random randomGenerator = new Random();
            StringBuilder code = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                code.append(randomGenerator.nextInt(10));
            }
            redisTemplate.opsForValue().set("code_" + email, code.toString());
            redisTemplate.expire("code_" + email, Duration.ofMinutes(5));
            // 邮件正文，第二个参数表示是否是HTML正文
            helper.setText("您的验证码是 <strong>" + code + "</strong>", true);
            // 发送
            javaMailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return new Result<>(200, "success", null);
    }
}
