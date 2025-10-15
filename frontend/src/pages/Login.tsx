import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../components/AuthContext";

export default function Login() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();
    const [endpoint, setEndpoint] = useState("");
    const { login } = useAuth();
    const apiUrl = process.env.REACT_APP_API_URL;


    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault();

        var body;

        if (endpoint === "login") {
            body = {
                username: username,
                password: password
            }
        } else {
            body = {
                username: username,
                password: password,
                role: "CUSTOMER"
            }
        }

        try {
            const response = await fetch(`${apiUrl}/api/auth/${endpoint}`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(body)
            });

            if (!response.ok) {
                const errData = await response.json();
                throw new Error(errData.error);
            }

            const data = await response.json();
            login(data.token);
            navigate("/accounts");
        } catch (err) {
            console.error(err);
            alert(err);
        }
    }



    return (
        <div id="login-page">
            <div id="login-box">
                <h3>Login</h3>
                <form onSubmit={handleLogin}>

                    <input type="radio" className="new-account-button" name="type" value="register"  
                        onChange={(e) => setEndpoint(e.target.value)} required />
                    <label htmlFor="checking">New account</label><br />

                    <input type="radio" className="existing-account-button" name="type" value="login"
                        onChange={(e) => setEndpoint(e.target.value)} required />
                    <label htmlFor="savings">Existing account</label><br />


                    <input className="credential-box" type="text" placeholder="Username"
                        name="username" value={username} required minLength={3} maxLength={20}
                        onChange={(e) => setUsername(e.target.value)} /><br />

                    <input className="credential-box" type="password" placeholder="Password"
                        name="password" value={password} required minLength={7} maxLength={32}
                        onChange={(e) => setPassword(e.target.value)} /><br />
                    
                    <button type="submit">Login</button>
                </form>
            </div>
        </div>
    )
}