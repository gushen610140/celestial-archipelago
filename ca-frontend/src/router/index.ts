import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      component: () => import("@/pages/HomePage.vue"),
    },
    {
      path: "/dawn",
      component: () => import("@/pages/DawnPage.vue"),
    },
  ],
});

export default router;
