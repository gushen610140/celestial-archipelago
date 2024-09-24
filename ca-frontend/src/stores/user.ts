import { defineStore } from "pinia";
import { CheckLoginAPI, GetTokenAPI, ParseTokenAPI } from "@/apis/UserApi";
import { ref } from "vue";
import { error } from "@/utils/ToastUtils";

export const useUserStore = defineStore("user", () => {
  const user = ref<{
    id: string;
    nickname: string;
    avatar: string;
    email: string;
  }>({
    id: "",
    nickname: "",
    avatar: "",
    email: "",
  });
  const token = ref(localStorage.getItem("token") || "");

  const getToken = async () => {
    if (!user.value.id) {
      error("用户未登录");
      return;
    }
    try {
      const { code, data }: { code: number; data: string } = await GetTokenAPI(user.value.id);
      if (code == 200) {
        token.value = data;
      } else {
        error("网络错误，请稍候再试");
      }
    } catch (e) {
      error("网络错误，请稍候再试");
    }
  };

  const checkLogin = async (): boolean => {
    if (!token) {
      return false;
    }
    const { code } = await CheckLoginAPI(token);
    return code == 200;
  };

  const saveToken = () => {
    if (!token.value) {
      error("token不存在");
      return;
    }
    localStorage.setItem("token", token.value);
  };

  const setUser = async () => {
    const { data } = await ParseTokenAPI(token.value);
    user.value.id = data.id;
    user.value.nickname = data.nickname;
    user.value.avatar = data.avatar;
    user.value.email = data.email;
  };

  return {
    user,
    token,
    getToken,
    checkLogin,
    saveToken,
    setUser,
  };
});
