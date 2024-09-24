<script lang="ts" setup>
import { resourcesUrl } from "@/utils/ResourcesUrlUtils";
import { changeRoute } from "@/utils/RouteUtils";
import { onMounted, ref } from "vue";
import UserLoginComp from "@/components/UserLoginComp.vue";
import { useUserStore } from "@/stores/user";
import UserInfoComp from "@/components/UserInfoComp.vue";
import GradientTextUI from "@/components/ui/GradientTextUI.vue";
import { CheckLoginAPI } from "@/apis/UserApi";

const useNavItems = () => {
  type item = {
    display_name: string;
    route: string;
  };

  const items: item[] = [
    {
      display_name: "破晓洲",
      route: "/dawn",
    },
    {
      display_name: "MMORPG",
      route: "/mmorpg",
    },
    {
      display_name: "金鼎洲",
      route: "/golden",
    },
    {
      display_name: "商城购物",
      route: "/shop",
    },
    {
      display_name: "设置",
      route: "/settings",
    },
    {
      display_name: "退出",
      route: "/exit",
    },
  ];

  return {
    items,
  };
};

const useCheckLogin = () => {
  const isLoginValid = ref(false);

  return {
    isLoginValid,
  };
};

const { items } = useNavItems();
const { isLoginValid } = useCheckLogin();

onMounted(async () => {
  const token = useUserStore().token;
  const { code } = await CheckLoginAPI(token);
  if (code == 200) {
    await useUserStore().setUser();
    isLoginValid.value = true;
  }
});
</script>

<template>
  <div
    :style="`background-image: url(${resourcesUrl.home_background_image})`"
    class="flex h-full items-center"
  >
    <div class="flex flex-col items-center ml-40">
      <GradientTextUI class="text-5xl font-bold mb-4">十四洲</GradientTextUI>
      <GradientTextUI class="text-xl font-bold mb-6">Celestial Archipelago</GradientTextUI>
      <div class="flex flex-col gap-6 p-4 glass">
        <v-btn
          v-for="item in items"
          height="50"
          rounded
          style="font-size: 1.5rem; color: white"
          variant="tonal"
          width="300"
          @click="changeRoute(item.route)"
        >
          {{ item.display_name }}
        </v-btn>
      </div>
    </div>
    <div class="flex items-center">
      <div class="ml-60 p-10 glass" style="width: 60rem; height: 40rem">
        <UserLoginComp v-if="!isLoginValid"></UserLoginComp>
        <UserInfoComp v-else></UserInfoComp>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
