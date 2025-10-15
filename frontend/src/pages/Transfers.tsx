import { useEffect, useState } from "react";
import { useAuth } from "../components/AuthContext";
import { Account } from "./Accounts";

export default function Transfers() {
    const { logout } = useAuth();
    const { token } = useAuth();
    const [accounts, setAccounts] = useState<Account[]>([]);
    const [accountId, setAccountId] = useState<number | string>("");
    const [amount, setAmount] = useState("");
    const [relatedAccountId, setRelatedAccountId] = useState<String | null>(null);
    const apiUrl = process.env.REACT_APP_API_URL;

    useEffect(() => {
            const getAccounts = async () => {
                try {
                    const response = await fetch(`${apiUrl}/api/accounts`, {
                        method: "GET",
                        headers: { 
                            "Content-Type": "application/json",
                            "Authorization": `Bearer ${token}`
                        }
                    });

                    if (!response.ok) {
                        const errorData = await response.json();
                        if (response.status === 401 || response.status === 403) {
                            localStorage.removeItem("jwt");
                            logout();
                        }
                        else if (response.status === 400) {
                            alert(errorData.error);
                        }
                        throw new Error(errorData.error);
                    } else {
                        const data: Account[] = await response.json();
                        data.sort((a, b) => a.id - b.id);
                        setAccounts(data);
                        if (data.length > 0) {
                            setAccountId(data[0].id);
                            // console.log(data[0].id);
                        }
                    }

                } catch (err) {
                    console.error(err);
                }
            };
    
            getAccounts();
        }, [logout, token, apiUrl]);


        // Helper to send request
        const sendRequest = async (url: string, body: any) => {
            try {
                const response = await fetch(url, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${token}`
                    },
                    body: JSON.stringify(body)
                });

                if (!response.ok) {
                    const errData = await response.json();
                    alert(errData.error);
                    return;
                }

                const data = await response.json();
                console.log("success: " + data);

            } catch (err) {
                console.log("Error: " + err);
            }
        }



        // Handlers for each transaction type
        const handleDeposit = async (e: React.FormEvent) => {
            e.preventDefault();
            sendRequest(`${apiUrl}/api/transactions/deposit`, {
                accountId,
                amount
            })
        }

        const handleWithdrawal = async (e: React.FormEvent) => {
            e.preventDefault();
            sendRequest(`${apiUrl}/api/transactions/withdraw`, {
                accountId,
                amount
            });
        }

        const handleTransfer = async (e: React.FormEvent) => {
            e.preventDefault();
            sendRequest(`${apiUrl}/api/transactions/transfer`, {
                fromId: accountId,
                toId: relatedAccountId,
                amount: amount
            });
        }


    return (
        <div className="transfer-page">
            <h1>Make A Transfer</h1>

            <div className="transfer-container">

                <div className="deposit">
                    <h3>Deposit</h3>
                    <form className="transfer-forms" onSubmit={handleDeposit}>
                        <label htmlFor="select-deposit-account">Select an account:</label><br />
                        <select name="account-list" id="select-deposit-account" onChange={(e) => setAccountId(e.target.value)}>
                            {
                                accounts.map((acc) => (
                                    <option key={acc.id} value={acc.id}>{acc.id}</option>
                                ))
                            }
                        </select><br />

                        <label htmlFor="deposit-amount">Enter the amount to deposit:</label><br />
                        <input type="text" name="amount" id="deposit-amount" placeholder="$ USD" required onChange={(e) => setAmount(e.target.value)} /><br />

                        <button type="submit">Deposit</button>
                    </form>
                </div>
                
                <div id="withdraw">
                    <h3>Withdrawal</h3>
                    <form className="transfer-forms" onSubmit={handleWithdrawal}>
                        <label htmlFor="select-withdrawal-account">Select an account:</label><br />
                        <select name="account-list" id="select-withdrawal-account" onChange={(e) => setAccountId(e.target.value)}>
                            {
                                accounts.map((acc) => (
                                    <option key={acc.id} value={acc.id}>{acc.id}</option>
                                ))
                            }
                        </select><br />

                        <label htmlFor="withdrawal-amount">Enter the amount to withdraw:</label><br />
                        <input type="text" name="amount" id="withdrawal-amount" placeholder="$ USD" required onChange={(e) => setAmount(e.target.value)} /><br />

                        <button type="submit">Withdraw</button>
                    </form>
                </div>

                <div id="transfer">
                    <h3>Transfer</h3>
                    <form className="transfer-forms" onSubmit={handleTransfer}>
                        <label htmlFor="select-transfer-account">Select a source account:</label><br />
                        <select name="account-list" id="select-transfer-account" onChange={(e) => setAccountId(e.target.value)}>
                            {
                                accounts.map((acc) => (
                                    <option key={acc.id} value={acc.id}>{acc.id}</option>
                                ))
                            }
                        </select><br />

                        <label htmlFor="transfer-destination">Enter a destination account:</label><br />
                        <input type="text" name="dst-account" id="transfer-destination" placeholder="Account #" required minLength={6} maxLength={6} 
                            onChange={(e) => setRelatedAccountId(e.target.value)} />
                        <br />

                        <label htmlFor="transfer-amount">Enter the amount to transfer:</label><br />
                        <input type="text" name="amount" id="transfer-amount" placeholder="$ USD" required onChange={(e) => setAmount(e.target.value)} /><br />

                        <button type="submit">Transfer</button>
                    </form>
                </div>
            </div>
        </div>
    )
}