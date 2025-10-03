import { createContext, useContext, useState, ReactNode } from "react";
import { Navigate } from "react-router-dom";

interface AuthContextType {
    token: string | null;
    login: (token: string) => void;
    logout: () => void;
    isAuthenticated: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
    const [token, setToken] = useState<string | null>(localStorage.getItem("jwt"));

    const login = (newToken: string) => {
        localStorage.setItem("jwt", newToken);
        setToken(newToken);
    };

    const logout = () => {
        localStorage.removeItem("jwt");
        setToken(null);
        return <Navigate to={"/login"} replace />;
    };

    const isAuthenticated = !!token;

    return (
        <AuthContext.Provider value={{ token, login, logout, isAuthenticated }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error("useAuth must be used within AuthProvider");
    }
    return context;
}