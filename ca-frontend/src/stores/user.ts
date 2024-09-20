import { defineStore } from "pinia";
import { CheckLoginAPI, GetTokenAPI } from "@/apis/UserApi";
import { UserView } from "@/entity/user/UserView";
import { ref } from "vue";
import { error } from "@/utils/ToastUtils";

export const useUserStore = defineStore("user", () => {
  const user = ref(new UserView());
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
    const { code } = await CheckLoginAPI();
    return code == 200;
  };

  return {
    user,
    token,
    getToken,
    checkLogin,
  };
});
