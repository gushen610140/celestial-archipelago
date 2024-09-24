<script lang="ts" setup>
import { type Ref, ref } from "vue";
import { error, success } from "@/utils/ToastUtils";
import {
  FindPasswordAPI,
  LoginAPI,
  LoginPasswordAPI,
  ParseTokenAPI,
  RegisterAPI,
  SendCodeAPI,
} from "@/apis/UserApi";
import { useUserStore } from "@/stores/user";

const useForm = () => {
  enum FormType {
    email_code = "用户验证码登录",
    email_password = "用户密码登录",
    register = "用户注册",
    find_password = "忘记密码",
  }
  const formType = ref(FormType.email_code);

  const userLogin: Ref<{
    email: string;
    code: string;
  }> = ref({
    email: "",
    code: "",
  });

  const login = () => {
    if (!userLogin.value.code || !userLogin.value.email) {
      error("邮箱或验证码不能为空");
      return;
    }
    LoginAPI(userLogin.value.email, userLogin.value.code)
      .then((res) => {
        const token = res.data.token;
        ParseTokenAPI(token).then((res) => {
          const { data: user } = res;
          useUserStore().user = user;
          useUserStore().token = token;
          useUserStore().saveToken();
        });
      })
      .catch(() => {
        error("网络错误，请稍后再试");
      });
  };

  const userLoginPassword: Ref<{
    email: string;
    password: string;
  }> = ref({
    email: "",
    password: "",
  });
  const loginPassword = () => {
    if (!userLoginPassword.value.email || !userLoginPassword.value.password) {
      error("邮箱或密码不能为空");
      return;
    }
    LoginPasswordAPI(userLoginPassword.value.email, userLoginPassword.value.password)
      .then((res) => {
        const token = res.data.token;
        ParseTokenAPI(token).then((res) => {
          const { data: user } = res;
          useUserStore().user = user;
          useUserStore().token = token;
          useUserStore().saveToken();
        });
      })
      .catch(() => {
        error("网络错误，请稍后再试");
      });
  };

  const userRegister: Ref<{
    email: string;
    password: string;
    code: string;
    nickname: string;
  }> = ref({
    email: "",
    password: "",
    code: "",
    nickname: "",
  });
  const register = () => {
    if (
      !userRegister.value.email ||
      !userRegister.value.password ||
      !userRegister.value.code ||
      !userRegister.value.password
    ) {
      error("请填写完整的信息");
      return;
    }
    RegisterAPI(
      userRegister.value.email,
      userRegister.value.password,
      userRegister.value.nickname,
      userRegister.value.code,
    )
      .then((res) => {
        const { code, message } = res;
        if (code == 200) {
          success(message);
        } else {
          error(message);
        }
        formType.value = FormType.email_code;
      })
      .catch(() => {
        error("网络错误，请稍后再试");
      });
  };

  const userFindPassword: Ref<{
    email: string;
    code: string;
    password: string;
  }> = ref({
    email: "",
    code: "",
    password: "",
  });
  const find_password = () => {
    if (
      !userFindPassword.value.email ||
      !userFindPassword.value.code ||
      !userFindPassword.value.password
    ) {
      error("请填写完整的信息");
      return;
    }
    FindPasswordAPI(
      userFindPassword.value.email,
      userFindPassword.value.code,
      userFindPassword.value.password,
    )
      .then((res) => {
        success(res.message);
        formType.value = FormType.email_code;
      })
      .catch(() => {
        error("网络错误，请稍后再试");
      });
  };

  const pwdVisible = ref(false);

  return {
    userLogin,
    userLoginPassword,
    userRegister,
    userFindPassword,
    formType,
    FormType,
    pwdVisible,
    login,
    loginPassword,
    register,
    find_password,
  };
};

const useSendCode = () => {
  const isSendCode = ref(false);
  const sendBtnText = ref("发送验证码");
  const sendBtnDisabled = ref(false);
  const sendCode = (email) => {
    if (email == "") {
      error("请填写正确的邮箱格式!");
      return;
    }
    SendCodeAPI(email).catch(() => {
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

const {
  userLogin,
  userLoginPassword,
  userRegister,
  userFindPassword,
  formType,
  FormType,
  pwdVisible,
  login,
  loginPassword,
  register,
  find_password,
} = useForm();
const { isSendCode, sendBtnText, sendBtnDisabled, sendCode } = useSendCode();
</script>

<template>
  <div class="text-white text-3xl select-none">{{ formType }}</div>
  <div class="flex">
    <div class="flex flex-col gap-4 ml-10 mt-10 text-white">
      <div class="glass rounded-xl">
        <v-form v-if="formType == FormType.email_code" class="m-5">
          <v-text-field v-model="userLogin.email" label="邮箱" width="400"></v-text-field>
          <v-btn
            :disabled="sendBtnDisabled"
            block
            class="mb-5"
            variant="tonal"
            @click="sendCode(userLogin.email)"
            >{{ sendBtnText }}
          </v-btn>
          <v-expand-transition>
            <div v-show="isSendCode">
              <v-text-field v-model="userLogin.code" label="验证码" width="400"></v-text-field>
              <v-btn block variant="tonal" @click="login">登录</v-btn>
            </div>
          </v-expand-transition>
          <div class="flex justify-center mt-5">
            <v-btn variant="text" @click="formType = FormType.email_password">密码登录</v-btn>
            <v-btn variant="text" @click="formType = FormType.register">用户注册</v-btn>
            <v-btn variant="text" @click="formType = FormType.find_password">忘记密码</v-btn>
          </div>
        </v-form>

        <v-form v-if="formType == FormType.email_password" class="m-5">
          <v-text-field v-model="userLoginPassword.email" label="邮箱" width="400"></v-text-field>
          <v-text-field
            v-model="userLoginPassword.password"
            :append-inner-icon="pwdVisible ? 'mdi-eye-off' : 'mdi-eye'"
            :type="pwdVisible ? 'text' : 'password'"
            label="密码"
            width="400"
            @click:append-inner="pwdVisible = !pwdVisible"
          ></v-text-field>
          <v-btn block variant="tonal" @click="loginPassword">登录</v-btn>
          <div class="flex justify-center mt-5">
            <v-btn variant="text" @click="formType = FormType.email_code">验证码登录</v-btn>
            <v-btn variant="text" @click="formType = FormType.register">用户注册</v-btn>
            <v-btn variant="text" @click="formType = FormType.find_password">忘记密码</v-btn>
          </div>
        </v-form>

        <v-form v-if="formType == FormType.register" class="m-5">
          <v-text-field v-model="userRegister.email" label="邮箱" width="400"></v-text-field>
          <v-btn
            :disabled="sendBtnDisabled"
            block
            class="mb-5"
            variant="tonal"
            @click="sendCode(userRegister.email)"
            >{{ sendBtnText }}</v-btn
          >
          <v-expand-transition>
            <div v-show="isSendCode">
              <v-text-field v-model="userRegister.code" label="验证码" width="400"></v-text-field>
              <v-text-field v-model="userRegister.nickname" label="昵称" width="400"></v-text-field>
              <v-text-field
                v-model="userRegister.password"
                :append-inner-icon="pwdVisible ? 'mdi-eye-off' : 'mdi-eye'"
                :type="pwdVisible ? 'text' : 'password'"
                label="密码"
                width="400"
                @click:append-inner="pwdVisible = !pwdVisible"
              ></v-text-field>
              <v-btn block variant="tonal" @click="register">注册</v-btn>
            </div>
          </v-expand-transition>
          <div class="flex justify-center mt-5">
            <v-btn variant="text" @click="formType = FormType.email_code">验证码登录</v-btn>
            <v-btn variant="text" @click="formType = FormType.email_password">密码登录</v-btn>
            <v-btn variant="text" @click="formType = FormType.find_password">忘记密码</v-btn>
          </div>
        </v-form>

        <v-form v-if="formType == FormType.find_password" class="m-5">
          <v-text-field v-model="userFindPassword.email" label="邮箱" width="400"></v-text-field>
          <v-btn
            :disabled="sendBtnDisabled"
            block
            class="mb-5"
            variant="tonal"
            @click="sendCode(userFindPassword.email)"
            >{{ sendBtnText }}</v-btn
          >
          <v-expand-transition>
            <div v-show="isSendCode">
              <v-text-field
                v-model="userFindPassword.code"
                label="验证码"
                width="400"
              ></v-text-field>
              <v-text-field
                v-model="userFindPassword.password"
                :append-inner-icon="pwdVisible ? 'mdi-eye-off' : 'mdi-eye'"
                :type="pwdVisible ? 'text' : 'password'"
                label="新密码"
                width="400"
                @click:append-inner="pwdVisible = !pwdVisible"
              ></v-text-field>
              <v-btn block variant="tonal" @click="find_password">重设密码</v-btn>
            </div>
          </v-expand-transition>
          <div class="flex justify-center mt-5">
            <v-btn variant="text" @click="formType = FormType.email_code">验证码登录</v-btn>
            <v-btn variant="text" @click="formType = FormType.email_password">密码登录</v-btn>
            <v-btn variant="text" @click="formType = FormType.register">用户注册</v-btn>
          </div>
        </v-form>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
