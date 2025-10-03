import { useEffect } from "react";
import { useState } from "react";
import { useAuth } from "../components/AuthContext";

export interface Account {
    id: number,
    balance: number,
    type: "CHECKING" | "SAVINGS",
    status: "ACTIVE" | "PENDING" | "REJECTED"
}


export default function Accounts() {
    const [accounts, setAccounts] = useState<Account[]>([]);
    const { logout } = useAuth();
    const { token } = useAuth();
    const [type, setType] = useState("");

    useEffect(() => {
        const getAccounts = async () => {
            try {
                const response = await fetch("http://localhost:8080/api/accounts", {
                    method: "GET",
                    headers: { 
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${token}`
                    }
                });

                if (response.status === 401 || response.status === 403) {
                    localStorage.removeItem("jwt");
                    logout();
                    // window.location.href = "/login";
                    throw new Error("Token expired or invalid");
                }

                const data: Account[] = await response.json();
                data.sort((a, b) => a.id - b.id);
                setAccounts(data);
            } catch (err) {
                console.error(err);
            }
        };

        getAccounts();
    }, [logout, token]);


    const handleAccountCreation = async (e: React.FormEvent) => {
        e.preventDefault();

        try {
            const response = await fetch("http://localhost:8080/api/accounts", {
                method: "POST",
                headers: { 
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                },
                body: JSON.stringify({ "type": type })
            });

            if (response.status === 401 || response.status === 403) {
                localStorage.removeItem("jwt");
                logout();
                throw new Error("Token expired");
            }

            const data: Account = await response.json();
            setAccounts(accounts => [...accounts, data]);
        } catch (err) {
            console.error(err);
        }

    }

    return (
        <div id="accounts-page">
            <div id="account-create-div">
                <h1>Create An Account</h1>

                {/* Put this form in a details tag? (expandable) */}
                <form id="account-create-form" onSubmit={handleAccountCreation}>
                    <h4>To create an account, select an account type below:</h4>
                    
                    <input type="radio" id="checking" name="type" value="CHECKING"  
                        onChange={(e) => setType(e.target.value)} required />
                    <label htmlFor="checking">Checking</label><br/>

                    <input type="radio" id="savings" name="type" value="SAVINGS"
                        onChange={(e) => setType(e.target.value)} required />
                    <label htmlFor="savings">Savings</label><br/>

                    <button type="submit">Create</button>
                </form>
            </div>

            <div id="your-accounts-div">
                <h1>Your Accounts</h1>

                <ul id="account-list">
                    {
                        accounts.map((acc) => (
                            <li key={acc.id}>
                                Account #{acc.id}<br/>
                                Type: {acc.type} | Balance: ${acc.balance}
                            </li>
                        ))
                    }
                    {/* Make each account expandable with transactions component inside? */}
                </ul>
            </div>
            
            
        </div>
    )
}