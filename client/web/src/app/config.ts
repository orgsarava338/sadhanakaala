const CONFIG = {
    ENV: import.meta.env.VITE_APP_ENV as string || import.meta.env.MODE as string,
    MODE: import.meta.env.MODE as string,

    API: {
        BASE_URL: import.meta.env.VITE_API_BASE_URL as string
    } as const,

    FIREBASE: {
        API_KEY: import.meta.env.VITE_FIREBASE_API_KEY as string,
        AUTH_DOMAIN : import.meta.env.VITE_FIREBASE_AUTH_DOMAIN as string,
        PROJECT_ID: import.meta.env.VITE_FIREBASE_PROJECT_ID as string,
        STORAGE_BUCKET: import.meta.env.STORAGE_BUCKET as string,
        MESSAGING_SENDER_ID: import.meta.env.VITE_FIREBASE_MESSAGING_SENDER_ID as string,
        APP_ID: import.meta.env.VITE_FIREBASE_APP_ID as string,
        MEASUREMENT_ID: import.meta.env.VITE_FIREBASE_MEASUREMENT_ID as string,
    } as const,
} as const

export default CONFIG;