import { defineStore } from "pinia";
import { CheckLoginAPI, GetTokenAPI } from "@/apis/UserApi";
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
  const token = ref("");

  const getToken = async () => {
    try {
      const { code, data }: { code: number; data: string } = await GetTokenAPI(user.value.id);
      if (code == 200) {
        token.value = data;
      } else {
        error("网络错误，请稍候再试");
      }
    } catch (e) {
      console.error("网络错误，请稍候再试");
    }
  };

  const checkLogin = async (): boolean => {
    if (!token) {
      return false;
    }
    const { code } = await CheckLoginAPI(token);
    return code == 200;
  };

  return {
    user,
    token,
    getToken,
    checkLogin,
  };
});
