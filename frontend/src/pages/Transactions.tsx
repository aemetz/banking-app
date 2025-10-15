import { useAuth } from "../components/AuthContext"
import { useState } from "react"
import { useEffect } from "react"

export interface Transaction {
    amount: number,
    timestamp: string,
    type: "DEPOSIT" | "WITHDRAWAL" | "TRANSFER",
    accountId: number,
    relatedAccountId: number | null
}

export function sortTimestamps(timeA: string, timeB: string) {
    timeA = timeA.slice(0, 10) + " " + timeA.slice(11, 19);
    timeB = timeB.slice(0, 10) + " " + timeB.slice(11, 19);

    // Year
    if (Number(timeA.slice(0, 4)) < Number(timeB.slice(0, 4))) {
        return -1;
    } else if (Number(timeA.slice(0, 4)) > Number(timeB.slice(0, 4))) {
        return 1;
    }
    // Month
    if (Number(timeA.slice(5, 7)) < Number(timeB.slice(5, 7))) {
        return -1;
    } else if (Number(timeA.slice(5, 7)) > Number(timeB.slice(5, 7))) {
        return 1;
    }
    // Day
    if (Number(timeA.slice(9, 11)) < Number(timeB.slice(9, 11))) {
        return -1;
    } else if (Number(timeA.slice(9, 11)) > Number(timeB.slice(9, 11))) {
        return 1;
    }
    // Hour
    if (Number(timeA.slice(11, 13)) < Number(timeB.slice(11, 13))) {
        return -1;
    } else if (Number(timeA.slice(11, 13)) > Number(timeB.slice(11, 13))) {
        return 1;
    }
    // Minute
    if (Number(timeA.slice(14, 16)) < Number(timeB.slice(14, 16))) {
        return -1;
    } else if (Number(timeA.slice(14, 16)) > Number(timeB.slice(14, 16))) {
        return 1;
    }
    // Second
    if (Number(timeA.slice(17)) < Number(timeB.slice(17))) {
        return -1;
    } else if (Number(timeA.slice(17)) > Number(timeB.slice(17))) {
        return 1;
    }
    return -1;
}

export default function Transactions() {
    const [transactions, setTransactions] = useState<Transaction[]>([]);
    const { logout } = useAuth();
    const { token } = useAuth();
    const apiUrl = process.env.REACT_APP_API_URL;
    
    useEffect(() => {
        const getTransactions = async () => {
            try {
                const response = await fetch(`${apiUrl}/api/transactions`,{
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
                data.sort((a, b) => sortTimestamps(a.timestamp, b.timestamp));
                setTransactions(data);
            } catch(err) {
                console.error(err);
            }
        };
        getTransactions();
    }, [logout, token, apiUrl])
    
    
    return (
        <div className="transactions-page">
            <h1>Your Transactions</h1>

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