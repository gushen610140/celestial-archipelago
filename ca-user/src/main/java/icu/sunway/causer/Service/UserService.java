package icu.sunway.causer.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.sunway.causer.Common.Api.Result;
import icu.sunway.causer.Entity.DO.User;
import icu.sunway.causer.Entity.VO.UserCreateView;
import icu.sunway.causer.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Value("${server.static}")
    private String staticServer;

    public Result<Boolean> createUser(UserCreateView userCreateView) {
        save(new User(null, userCreateView.getNickname(), staticServer + "/default_avatar.jpg" , userCreateView.getEmail(), userCreateView.getPassword(), null));
        return Result.success(true);
    }
}
