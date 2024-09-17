package icu.sunway.causer.Entity.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateView {
    private String nickname;
    private String email;
    private String password;
    private String validateCode;
}
