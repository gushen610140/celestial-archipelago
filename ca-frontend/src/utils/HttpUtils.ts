import axios, { type AxiosRequestConfig } from "axios";
import type { Result } from "@/entity/api/Result";

const instance = axios.create({
  baseURL: import.meta.env.VITE_DEV_ENV,
});

const http = async <T>(config: AxiosRequestConfig): Promise<Result<T>> => {
  const { data } = await instance.request<Result<T>>(config);
  return data;
};

export default http;
