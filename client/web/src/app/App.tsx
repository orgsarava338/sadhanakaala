import AuthProvider from "@/auth/AuthProvider";
import { StrictMode } from "react";
import AppRouter from "./AppRouter";
import { Toaster } from "react-hot-toast";

export default function App() {
  return <>
    <StrictMode>
      <AuthProvider>
        <AppRouter />
      </AuthProvider>
      <Toaster position="top-right" />
    </StrictMode>
  </>
}