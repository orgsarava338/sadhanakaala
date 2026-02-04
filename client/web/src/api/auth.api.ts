import { api } from "./api";
import Log from "@/decorators/log";

class AuthService {

  @Log
  async login(): Promise<User> {
    try {
      const res = await api.post("/auth/login");
      return res.data;
    } catch (error) {
      throw error;
    }
  }

  @Log
  async logout(): Promise<void> {
    try {
      await api.post("/auth/logout");
    } catch (error) {
      throw error;
    }
  }

  @Log
  async getMe(): Promise<User> {
    try {
      const res = await api.get("/auth/me");
      return res.data;
    } catch (error) {
      throw error;
    }
  };
}

const authService = new AuthService();
export default authService;
