import AuthProvider from "@/auth/AuthProvider";
import { StrictMode } from "react";
import AppRouter from "./AppRouter";

export default function App() {
  return <>
    <StrictMode>
      <AuthProvider>
        <AppRouter />
      </AuthProvider>
    </StrictMode>
  </>
}