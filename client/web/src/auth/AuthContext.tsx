import { createContext } from "react";

export interface AuthUser {
  id: string;
  name: string;
  email: string;
  streak?: number;
}

export interface AuthContextType {
  user: AuthUser | null;
  loading: boolean;
  logout: () => Promise<void>;
}

export const AuthContext = createContext<AuthContextType | null>(null);
