import React from "react";
import { Link } from 'react-router-dom';
// import { useState } from "react";
// import { Navigate } from "react-router-dom";
import { useAuth } from "./AuthContext";

function Navbar() {
    const { isAuthenticated } = useAuth();
    const { logout } = useAuth();

    const handleLogout = (e: React.FormEvent) => {
        e.preventDefault();
        logout();
    }

    return (
        <nav className="navbar">
            <ul className="navbar-options">
                <li><Link className="navbar-link" to="/">Home</Link></li>
                <li><Link className="navbar-link" to="/accounts">Accounts</Link></li>
                <li><Link className="navbar-link" to="/transactions">Transactions</Link></li>
                <li><Link className="navbar-link" to="/transfers">Transfers</Link></li>
                {isAuthenticated ? <li><Link className="navbar-link" to="/login" id="logout-button" onClick={handleLogout}>Log out</Link></li> : null}
                {isAuthenticated ? null : <li><Link className="navbar-link" to="/login" id="logout-button">Log in</Link></li>}
            </ul>
        </nav>
    )
}

export default Navbar;