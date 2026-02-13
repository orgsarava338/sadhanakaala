import authService from "@/api/auth.api";
import { auth, googleProvider } from "@/firebase/firebase";
import { getRedirectResult, onIdTokenChanged, signInWithRedirect, signOut } from "firebase/auth";
import { createContext, useEffect, useState } from "react";

interface AuthContextType {
  user: User | null;
  loading: boolean;
  loginWithGoogle: () => Promise<boolean>;
  logout: () => Promise<void>;
}

interface AuthProviderProps {
  children: React.ReactNode;
}

export const AuthContext = createContext<AuthContextType | null>(null);

export default function AuthProvider({ children }: AuthProviderProps) {
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getRedirectResult(auth)
        .then(result => {
          console.log("Redirect result:", result);
        })
        .catch(error => {
          console.error("Redirect error:", error);
        });

    const unsubscribe = onIdTokenChanged(auth, async (firebaseUser) => {
        console.log("on token changed", firebaseUser);

      if (!firebaseUser) {
        localStorage.removeItem("auth_uid");
        setUser(null);
        setLoading(false);
        return;
      }

      const storedUid = localStorage.getItem("auth_uid");

      try {
        if (storedUid === firebaseUser.uid) {
          const me = await authService.getMe();
          setUser({
            ...me, 
            name: firebaseUser.displayName ?? "",
            email: firebaseUser.email ?? "",
            photoURL: firebaseUser.photoURL ?? "",
          });
        } 

        else {
          const loggedInUser = await authService.login();
          localStorage.setItem("auth_uid", firebaseUser.uid);
          setUser({
            ...loggedInUser,
            name: firebaseUser.displayName ?? "",
            email: firebaseUser.email ?? "",
            photoURL: firebaseUser.photoURL ?? "",
          });
        }
      } catch (error) {
        console.error("Auth sync failed:", error);
        setUser(null);
      } finally {
        setLoading(false);
      }
    });

    return unsubscribe;
  }, []);

  const loginWithGoogle = async (): Promise<boolean> => {
    try {
      await signInWithRedirect(auth, googleProvider);
      return true;
    } catch (error) {
      console.error("Login failed:", error);
      return false;
    }
  };

  const logout = async () => {
    await signOut(auth);
    localStorage.removeItem("auth_uid");
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, loading, loginWithGoogle, logout }}>
      {children}
    </AuthContext.Provider>
  );
}
