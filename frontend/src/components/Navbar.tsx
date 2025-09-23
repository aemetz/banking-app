import React from "react";
import { Link } from 'react-router-dom';

function Navbar() {
    return (
        <nav className="navbar">
            <ul className="navbar-options">
                <li><Link className="navbar-link" to="/">Home</Link></li>
                <li><Link className="navbar-link" to="/accounts">Accounts</Link></li>
                <li><Link className="navbar-link" to="/pay-transfer">Pay & Transfer</Link></li>
                <li><Link className="navbar-link" to="/profile">Profile</Link></li> {/* Make this a pfp icon? */}
                {/* Sign out button? */}
            </ul>
        </nav>
    )
}

export default Navbar;