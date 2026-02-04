import { Outlet } from "react-router-dom";

export default function AppLayout() {
  return (
    <div>
      <header className="h-14 shadow flex items-center px-6">
        Sadhana Kaala
      </header>

      <main className="p-6">
        <Outlet />
      </main>
    </div>
  );
};
