<script lang="ts" setup>
import { resourcesUrl } from "@/utils/ResourcesUrlUtils";
import { changeRoute } from "@/utils/RouteUtils";
import UserLoginComp from "@/components/UserLoginComp.vue";

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

const { items } = useNavItems();
</script>

<template>
  <div
    :style="`background-image: url(${resourcesUrl.home_background_image})`"
    class="flex h-full items-center"
  >
    <div class="flex flex-col items-center ml-40">
      <div class="text-5xl mb-4 text-white select-none">十四洲</div>
      <div class="text-xl mb-4 text-white select-none">Celestial Archipelago</div>
      <div class="flex flex-col gap-4 p-4">
        <v-btn
          v-for="item in items"
          height="50"
          rounded
          style="font-size: 1.5rem; color: white"
          variant="outlined"
          width="300"
          @click="changeRoute(item.route)"
        >
          {{ item.display_name }}
        </v-btn>
      </div>
    </div>
    <div class="flex items-center">
      <UserLoginComp></UserLoginComp>
    </div>
  </div>
</template>

<style scoped></style>
