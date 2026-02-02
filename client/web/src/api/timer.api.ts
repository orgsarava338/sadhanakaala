import api from "./api";

export const getTimers = async (): Promise<Timer[]> => {
  const res = await api.get("/timer");
  return res.data;
};

export const createTimer = async (data: {
  name: string;
  defaultDuration: number;
}) => {
  const res = await api.post("/timer", data);
  return res.data;
};

export const updateTimer = async (
  id: string,
  data: Partial<{ name: string; defaultDuration: number }>
) => {
  const res = await api.put(`/timer/${id}`, data);
  return res.data;
};

export const deleteTimer = async (id: string) => {
  await api.delete(`/timer/${id}`);
};
