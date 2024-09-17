package icu.sunway.causer.Common.Const;

import lombok.Getter;

@Getter
public enum Const {
    UserAvatarDefault("/default_avatar.jpg");

    private final String value;

    Const(String value) {
        this.value = value;
    }
}
