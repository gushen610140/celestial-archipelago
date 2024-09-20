<script lang="ts" setup>
import { useUserStore } from "@/stores/user";
import { computed } from "vue";
import type { UserView } from "@/entity/user/UserView";

const useUserInfo = () => {
  const user: UserView = computed(() => useUserStore().user);

  return {
    user,
  };
};

const { user } = useUserInfo();
</script>

<template>
  <div class="text-3xl text-white">个人信息</div>
  <div class="flex mt-10">
    <v-avatar :image="user.avatar" size="200"></v-avatar>
    <div class="flex flex-col gap-6 ml-20">
      <div class="text-2xl text-white">{{ user.nickname }}</div>
      <div class="text-2xl text-white">{{ user.email }}</div>
    </div>
  </div>
</template>

<style scoped></style>
