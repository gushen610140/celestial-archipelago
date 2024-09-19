import { defineStore } from "pinia";
import { CheckLoginAPI } from "@/apis/UserApi";

export const useUserStore = defineStore("user", () => {
  const user: UserView = null;
  const token: string | null = null;

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
    checkLogin,
  };
});
