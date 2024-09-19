import http from "@/utils/HttpUtils";

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
