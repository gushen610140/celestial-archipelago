import axios, { type AxiosRequestConfig } from "axios";
import { useUserStore } from "@/stores/user";

const instance = axios.create({
  baseURL: import.meta.env.VITE_DEV_ENV,
});

instance.interceptors.request.use((config) => {
  const token = useUserStore().token;
  if (token) {
    config.headers["token"] = token;
  }
  // 最后返回 config
  return config;
});

const http = async <T>(config: AxiosRequestConfig): Promise<Result<T>> => {
  const { data } = await instance.request<Result<T>>(config);
  return data;
};

export default http;
