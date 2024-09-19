/// <reference types="vite/client" />

// Typescript 类型声明
interface ImportMetaEnv {
  readonly VITE_DEV_ENV: string;
  readonly VITE_PRODUCTION_ENV: string;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}
