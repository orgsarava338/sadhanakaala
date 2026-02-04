import authService from "@/api/auth.api";
import { auth, googleProvider } from "@/firebase/firebase";
import { onAuthStateChanged, signInWithPopup, signOut, type User as FirebaseUser } from "firebase/auth";
import { createContext, useEffect, useState } from "react";

interface AuthContext {
  user: User | null;
  loading: boolean;
  loginWithGoogle: () => Promise<void>;
  logout: () => Promise<void>;
}

interface AuthProviderProps {
    children: React.ReactNode;
}

export const AuthContext = createContext<AuthContext | null>(null);

export default function AuthProvider({children}: AuthProviderProps) {

    const [user, setUser] = useState<User | null>(null);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        return onAuthStateChanged(auth, async (firebaseUser) => {
            try {
                if (!firebaseUser || firebaseUser.uid === user?.uid) return;                

                setLoading(true);
                await loadUser(firebaseUser);
            } catch (error) {
                throw error;
            } finally {
                setLoading(false);
            }
        })
    }, [])

    const loginWithGoogle = async () => {
        try {
            setLoading(true);
            const result = await signInWithPopup(auth, googleProvider);
            await loadUser(result.user);
        } catch (error) {
            throw error
        } finally {
            setLoading(false);
        }
    }
    
    const logout = async () => {
        await signOut(auth);
        await clearUser();
    }
    
    const loadUser = async (user?: FirebaseUser | null) => {
        if(!user) return;
        const backendUser = await authService.login();
        setUser(backendUser);
    }
    
    const clearUser = async () => {
        await authService.logout();
        setUser(null);
    }
    
    return <AuthContext.Provider value={{ user, loading, loginWithGoogle, logout }}>
        {children}
    </AuthContext.Provider>
}
