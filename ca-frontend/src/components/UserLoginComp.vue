<script lang="ts" setup>
import { type Ref, ref } from "vue";
import { UserLogin } from "@/entity/user/UserLogin";
import { error } from "@/utils/ToastUtils";
import { LoginAPI, SendCodeAPI } from "@/apis/UserApi";
import { useUserStore } from "@/stores/user";
import type { UserView } from "@/entity/user/UserView";
import type { Result } from "@/entity/api/Result";

const useForm = () => {
  const userLogin: Ref<UserLogin> = ref(new UserLogin());
  const login = () => {
    LoginAPI(userLogin)
      .then((res: Result<UserView>) => {
        useUserStore().user = res.data;
        useUserStore().getToken();
      })
      .catch(() => {
        error("网络错误，请稍后再试");
      });
  };

  return {
    userLogin,
    login,
  };
};

const useSendCode = () => {
  const isSendCode = ref(false);
  const sendBtnText = ref("发送验证码");
  const sendBtnDisabled = ref(false);
  const sendCode = () => {
    if (userLogin.value.email == "") {
      error("请填写正确的邮箱格式!");
      return;
    }
    SendCodeAPI(userLogin.value.email).catch(() => {
      error("网络错误，请稍候再试");
    });
    isSendCode.value = true;
    sendBtnDisabled.value = true;
    let restTime = 60;
    sendBtnText.value = `剩余 ${restTime} 秒后可以再次发送验证码`;
    let interval = setInterval(() => {
      restTime--;
      sendBtnText.value = `剩余 ${restTime} 秒后可以再次发送验证码`;
      if (restTime == 0) {
        clearInterval(interval);
        sendBtnDisabled.value = false;
        sendBtnText.value = "重发验证码";
      }
    }, 1000);
  };

  return {
    isSendCode,
    sendBtnText,
    sendBtnDisabled,
    sendCode,
  };
};

const { userLogin, login } = useForm();
const { isSendCode, sendBtnText, sendBtnDisabled, sendCode } = useSendCode();
</script>

<template>
  <div class="text-white text-3xl select-none">用户登录</div>
  <div class="flex">
    <div class="flex flex-col gap-4 ml-10 mt-10 text-white">
      <div class="glass rounded-xl">
        <v-form class="m-5">
          <v-text-field v-model="userLogin.email" label="邮箱" width="400"></v-text-field>
          <v-btn :disabled="sendBtnDisabled" block class="mb-5" variant="tonal" @click="sendCode">{{
            sendBtnText
          }}</v-btn>
          <v-expand-transition>
            <div v-show="isSendCode">
              <v-text-field
                v-model="userLogin.code"
                class="text-white"
                label="验证码"
                width="400"
              ></v-text-field>
              <v-btn block variant="tonal" @click="login">登录</v-btn>
            </div>
          </v-expand-transition>
          <div class="flex justify-center mt-5">
            <v-btn variant="text">密码登录</v-btn>
            <v-btn variant="text">用户注册</v-btn>
            <v-btn variant="text">密码找回</v-btn>
          </div>
        </v-form>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
