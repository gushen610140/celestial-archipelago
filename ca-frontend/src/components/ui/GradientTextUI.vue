<script lang="ts" setup>
const { text } = defineProps<{
  text?: string;
}>();
</script>

<template>
  <div
    class="text-transparent select-none bg-gradient-to-r from-slate-50 to-slate-400 bg-clip-text"
  >
    {{ text }}
    <slot></slot>
  </div>
</template>

<style scoped></style>
