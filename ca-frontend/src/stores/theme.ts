import { defineStore } from "pinia";

export const useThemeStore = defineStore("theme", () => {
  enum ThemeEnum {
    light = "light",
    dark = "dark",
  }

  const theme: string = ThemeEnum[ThemeEnum.light];

  return {
    ThemeEnum,
    theme,
  };
});
