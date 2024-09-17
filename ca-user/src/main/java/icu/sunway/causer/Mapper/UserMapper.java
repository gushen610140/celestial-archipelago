package icu.sunway.causer.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import icu.sunway.causer.Entity.DO.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
