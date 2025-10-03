import { useAuth } from "../components/AuthContext"
import { useState } from "react"
import { useEffect } from "react"

interface Transaction {
    amount: number,
    timestamp: string,
    type: "DEPOSIT" | "WITHDRAWAL" | "TRANSFER",
    accountId: number,
    relatedAccountId: number | null
}

export default function Transactions() {
    const [transactions, setTransactions] = useState<Transaction[]>([]);
    const { logout } = useAuth();
    const { token } = useAuth();
    
    useEffect(() => {
        const getTransactions = async () => {
            try {
                const response = await fetch("http://localhost:8080/api/transactions",{
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${token}`
                    }
                });

                if (response.status === 401 || response.status === 403) {
                    localStorage.removeItem("jwt");
                    logout();
                    throw new Error("Token expired or invalid");
                }

                const data: Transaction[] = await response.json();
                data.sort((a, b) => a.accountId - b.accountId);
                setTransactions(data);
            } catch(err) {
                console.error(err);
            }
        };
        getTransactions();
    }, [logout, token])
    
    
    return (
        <div id="transactions-page">
            <h1>Your Transactions</h1>

            <ul id="transaction-list">
                {
                    transactions.map((t) => (
                        <li key={t.timestamp}>
                            {t.type === "DEPOSIT" ? <p>Deposit to {t.accountId}</p> : null}
                            {t.type === "WITHDRAWAL" ? <p>Withdrawal from {t.accountId}</p> : null}
                            {t.type === "TRANSFER" ? <p>Transfer from {t.accountId} to {t.relatedAccountId}</p> : null}
                            <p>Amount: ${t.amount}</p>
                            <p>Time: {t.timestamp.slice(0, 10)} {t.timestamp.slice(11, 19)} CT</p>
                        </li>
                    ))
                }
            </ul>
        </div>
    )
}