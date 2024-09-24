import http from "@/utils/HttpUtils";

export const ParseTokenAPI = (token: string) => {
  return http<{
    id: string;
    nickname: string;
    avatar: string;
    email: string;
  }>({
    url: "/user/parse_token",
    method: "GET",
    params: {
      token,
    },
  });
};

export const CheckLoginAPI = (token: string) => {
  return http<null>({
    url: "/user/check_login",
    method: "GET",
    params: {
      token,
    },
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

export const LoginAPI = (email: string, code: string) => {
  return http<{
    token: string;
  }>({
    url: "/user/login",
    method: "POST",
    data: {
      email,
      code,
    },
  });
};

export const GetTokenAPI = (id: string) => {
  return http<{
    token: string;
  }>({
    url: "/user/get_token",
    method: "GET",
    params: {
      id,
    },
  });
};

export const LoginPasswordAPI = (email: string, password: string) => {
  return http<{
    token: string;
  }>({
    url: "/user/login_password",
    method: "POST",
    data: {
      email,
      password,
    },
  });
};

export const RegisterAPI = (email: string, password: string, nickname: string, code: string) => {
  return http<null>({
    url: "/user/create",
    method: "POST",
    data: {
      email,
      password,
      code,
      nickname,
    },
  });
};

export const FindPasswordAPI = (email: string, code: string, password: string) => {
  return http<null>({
    url: "/user/find_password",
    method: "POST",
    data: {
      email,
      code,
      password,
    },
  });
};
