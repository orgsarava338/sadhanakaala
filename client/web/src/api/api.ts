import axios from "axios";
import { auth } from "../firebase/firebase";

const apiBaseUrl = import.meta.env.VITE_API_BASE_URL + "/api/v1"

const api = axios.create({
  baseURL: apiBaseUrl,
  headers: {
    "Content-Type": "application/json",
  },
});

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

export default api;
