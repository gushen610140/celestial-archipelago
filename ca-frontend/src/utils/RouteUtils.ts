import router from "@/router/index";

export async function changeRoute(route: string): void {
  await router.push(route);
}
