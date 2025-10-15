import { useEffect } from "react";
import { useAuth } from "../components/AuthContext";
import { Transaction } from "./Transactions";
import { useState } from "react";
import { useParams } from "react-router-dom";
import { sortTimestamps } from "./Transactions";

export default function TransactionsByAccount() {
    const { logout } = useAuth();
    const { token } = useAuth();
    const [transactions, setTransactions] = useState<Transaction[]>([]);
    const { accountId } = useParams<{ accountId: string }>();
    const [error, setError] = useState<string | null>(null);
    const apiUrl = process.env.REACT_APP_API_URL;


    useEffect(() => {
            const getTransactions = async () => {
                try {
                    const response = await fetch(`${apiUrl}/api/transactions/account/${accountId}`,{
                        method: "GET",
                        headers: {
                            "Content-Type": "application/json",
                            "Authorization": `Bearer ${token}`
                        }
                    });
    
                    if (!response.ok) {
                        if (response.status === 401 || response.status === 403) {
                            logout();
                        } else {
                            setError("Something went wrong while fetching transactions");
                        }
                        return;
                    }
    
                    const data: Transaction[] = await response.json();
                    data.sort((a, b) => sortTimestamps(a.timestamp, b.timestamp));
                    setTransactions(data);
                } catch(err) {
                    console.error(err);
                    setError("Unable to fetch transactions");
                }
            };
            getTransactions();
    }, [accountId, logout, token, apiUrl])

    if (error) {
        return <div><h2>{error}</h2></div>;
    }


    return (
        <div className="transactions-page">
            <h1>Transactions for Account {accountId}</h1>

            <ul className="transaction-list">
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