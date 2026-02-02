import api from "./api";

export const getDashboard = async (): Promise<DashboardResponse> => {
  const res = await api.get("/dashboard");
  return res.data;
};
