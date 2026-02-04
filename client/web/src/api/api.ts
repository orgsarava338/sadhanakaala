import axios from "axios";
import { auth } from "../firebase/firebase";
import CONFIG from "@/app/config";

const apiBaseUrl = CONFIG.API.BASE_URL + "/api/v1"

const api = axios.create({ baseURL: apiBaseUrl, headers: { "Content-Type": "application/json" }});
const publicApi = axios.create({ baseURL: apiBaseUrl, headers: { "Content-Type": "application/json" }});

// Attach Firebase token
api.interceptors.request.use(async (config) => {
  const user = auth.currentUser;

  if (user) {
    const token = await user.getIdToken();
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

// Handle auth errors globally
api.interceptors.response.use(
  (res) => res,
  (error) => {
    if (error.response?.status === 401) {
      console.warn("Unauthorized – token expired?");
    }
    return Promise.reject(error);
  }
);

export { api, publicApi };


