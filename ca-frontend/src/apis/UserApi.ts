import http from "@/utils/HttpUtils";
import type { UserView } from "@/entity/user/UserView";
import type { UserLogin } from "@/entity/user/UserLogin";

export const ParseTokenAPI = () => {
  return http<UserView>({
    url: "/user/parse_token",
    method: "POST",
  });
};

export const CheckLoginAPI = () => {
  return http<null>({
    url: "/user/check_login",
    method: "GET",
  });
};

export const SendCodeAPI = (email: string) => {
  return http<null>({
    url: "/user/send_code",
    method: "GET",
    params: {
      email,
    },
  });
};

export const LoginAPI = (userLogin: UserLogin) => {
  return http<UserView>({
    url: "/user/login",
    method: "POST",
    data: userLogin,
  });
};

export const GetTokenAPI = (id: string) => {
  return http<string>({
    url: "/user/get_token",
    method: "GET",
    params: {
      id,
    },
  });
};
