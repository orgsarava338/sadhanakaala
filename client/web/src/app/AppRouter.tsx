import AppLayout from "@/layout/AppLayout";
import ProtectedLayout from "@/layout/ProtectedLayout";

import Dashboard from "@/pages/Dashboard";
import Home from "@/pages/Home";
import Login from "@/pages/Login";

import { BrowserRouter, Routes, Route } from "react-router-dom";

export default function AppRouter() {
  return (
    <BrowserRouter>
      <Routes>

        {/* Public */}
        <Route path="/login" element={<Login />} />

        {/* Protected */}
        <Route element={<ProtectedLayout />}>
          <Route element={<AppLayout />}>
            <Route path="/dashboard" element={<Dashboard />} />
          </Route>
        </Route>

        {/* Default */}
        <Route path="/" element={<Home />} />

        {/* Fallback */}
        <Route path="*" element={<div>404</div>} />

      </Routes>
    </BrowserRouter>
  );
};
