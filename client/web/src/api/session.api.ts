import api from "./api";

export const startSession = async (timerId: string) => {
  const res = await api.post("/sessions/start", { timerId });
  return res.data;
};

export const stopSession = async (
  sessionId: string,
  actualDuration: number
) => {
  const res = await api.post(`/sessions/${sessionId}/stop`, {
    actualDuration,
  });
  return res.data;
};

export const cancelSession = async (sessionId: string) => {
  await api.post(`/sessions/${sessionId}/cancel`);
};

export const getSessions = async (from: string, to: string) => {
  const res = await api.get("/sessions", {
    params: { from, to },
  });
  return res.data;
};
