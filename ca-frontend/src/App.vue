<script lang="ts" setup>
import { RouterView } from "vue-router";
import { useThemeStore } from "@/stores/theme";
</script>

<template>
  <v-app :theme="useThemeStore().theme" class="w-full h-full">
    <RouterView />
  </v-app>
</template>

<style scoped></style>
