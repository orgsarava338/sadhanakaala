import api from "./api";

export const login = async (loginRequest: LoginDTO): Promise<User> => {
  const res = await api.post("/auth/me", loginRequest);
  return res.data;
}

export const getMe = async (): Promise<User> => {
  const res = await api.get("/auth/me");
  return res.data;
};
