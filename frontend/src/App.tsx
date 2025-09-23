import React from 'react';
import './App.css';
import Navbar from './components/Navbar';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import Accounts from './pages/Accounts';
import PayTransfer from './pages/PayTransfer';
import Profile from './pages/Profile';

function App() {
  return (
    <div className="App">
      <Router>
        <Navbar />
        <div style={{ height: '51px' }}></div> {/* Spacing */}
        <Routes>
          <Route path='/' element={<Home />}/>
          <Route path='/accounts' element={<Accounts />}/>
          <Route path='/pay-transfer' element={<PayTransfer />}/>
          <Route path='/profile' element={<Profile />}/>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
