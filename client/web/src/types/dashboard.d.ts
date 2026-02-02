interface DashboardResponse {
  streak: number;
  todayTotalSeconds: number;
  sessionsToday: number;
  activeSession?: {
    id: string;
    timerName: string;
    startedAt: string;
  };
}