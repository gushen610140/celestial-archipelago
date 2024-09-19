<script lang="ts" setup>
import { resourcesUrl } from "@/utils/ResourcesUrlUtils.js";
</script>

<template>
  <div
    class="border-2 border-white ml-60 rounded-xl backdrop-blur-sm p-10"
    style="width: 60rem; height: 40rem"
  >
    <div class="text-white text-3xl">用户信息</div>
    <div class="flex">
      <img
        :src="resourcesUrl.default_avatar_image"
        alt="avatar"
        class="mt-10 w-25 h-25 rounded-xl"
      />
      <div class="flex flex-col gap-4 ml-10 mt-10">
        <div class="text-2xl text-white">用户昵称：</div>
        <div class="text-2xl text-white">邮箱：</div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
