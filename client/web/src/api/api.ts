import axios from "axios";
import { auth } from "../firebase/firebase";
import CONFIG from "@/app/config";

const apiBaseUrl = CONFIG.API.BASE_URL + "/api/v1"

const api = axios.create({ 
  baseURL: apiBaseUrl, 
  headers: { "Content-Type": "application/json" },
  withCredentials: true,
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
  (res) => {
    const body = res.data;

    if (body?.errors?.length) {
      return Promise.reject({ type: "BUSINESS", errors: body.errors })
    }

    return body;
  },
  (error) => {
   if (!error.response) {
      return Promise.reject({type: "NETWORK", message: "Network error. Check connection"});
    }

    const status = error.response.status;
    const body = error.response.data;

    return Promise.reject({type: "HTTP", status, errors: body?.errors || body});
  }
);

export { api };


