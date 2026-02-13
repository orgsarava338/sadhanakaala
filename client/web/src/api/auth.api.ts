import { api } from "./api";
import Log from "@/decorators/log";
import { handleApiError } from "./error.handler";

class AuthService {

  @Log
  async login(): Promise<ApiUser> {
    try {
      const res = await api.post("/user/login");
      return res.data;
    } catch (error) {
      handleApiError(error);
      throw error;
    }
  }

  @Log
  async getMe(): Promise<ApiUser> {
    try {
      const res = await api.get(`/user/me`);
      return res.data;
    } catch (error) {
      handleApiError(error);
      throw error;
    }
  };
}

const authService = new AuthService();
export default authService;
