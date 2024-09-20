import { type PluginOptions, useToast } from "vue-toastification";

const toast = useToast();

const globalOptions: PluginOptions = {
  timeout: 3000,
};

export const success = (message: string) => {
  toast.success(message, globalOptions);
};

export const error = (message: string) => {
  toast.error(message, globalOptions);
};
