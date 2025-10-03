import React from 'react';
import './App.css';
import Navbar from './components/Navbar';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import Accounts from './pages/Accounts';
// import Profile from './pages/Profile';
import Transactions from './pages/Transactions';
import Transfers from './pages/Transfers';
import PrivateRoute from './components/PrivateRoute';
import Login from './pages/Login';
import { AuthProvider } from './components/AuthContext';
import TransactionsByAccount from './pages/TransactionsByAccount';

function App() {
  return (
    <AuthProvider>
      <div className="App">
        <Router>
          <Navbar />
          <div style={{ height: '51px' }}></div> {/* Spacing */}
          <Routes>
            {/* Public routes */}
            <Route path='/login' element={<Login />} />
            <Route path='/' element={<Home />} />

            {/* Private routes */}
            <Route path='/accounts' element={
              <PrivateRoute>
                <Accounts />
              </PrivateRoute>
              }
            />

            <Route path='/transactions' element={
              <PrivateRoute>
                <Transactions />
              </PrivateRoute>
              }
            />

            <Route path='/transfers' element={
              <PrivateRoute>
                <Transfers />
              </PrivateRoute>
              }
            />

            <Route path="/transactions/:accountId" element={
              <PrivateRoute>
                <TransactionsByAccount />
              </PrivateRoute>
            }
            />

          </Routes>
        </Router>
      </div>
    </AuthProvider>
  );
}

export default App;
