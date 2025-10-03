import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../components/AuthContext";

export default function Login() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const { login } = useAuth();

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault();    

        try {
            const response = await fetch("http://localhost:8080/api/auth/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ "username":username, "password":password })
            });

            if (!response.ok) {
                throw new Error("Invalid login!");
            }

            const data = await response.json();
            login(data.token);
            navigate("/accounts");
        } catch (err) {
            console.error(err);
            alert("Login failed!");
        }
    }


    return (
        <div id="login-page">
            <div id="login-box">
                <h3>Login</h3>
                <form onSubmit={handleLogin}>
                    <input className="credential-box" type="text" placeholder="Username"
                        name="username" value={username} required minLength={3} maxLength={20}
                        onChange={(e) => setUsername(e.target.value)} /><br/>

                    <input className="credential-box" type="password" placeholder="Password"
                        name="password" value={password} required minLength={7} maxLength={32} 
                        onChange={(e) => setPassword(e.target.value)} /><br/>
                    
                    <button type="submit">Login</button>
                </form>
            </div>
        </div>
    )
}