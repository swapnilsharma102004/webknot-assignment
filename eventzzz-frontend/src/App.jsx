import React, { useState, useEffect } from 'react'
import { BrowserRouter as Router, Routes, Route, Link, Navigate } from 'react-router-dom'
import Login from './components/Login'
import AdminDashboard from './components/AdminDashboard'
import StudentDashboard from './components/StudentDashboard'
import EventReports from './components/EventReports'
import './App.css'

function App() {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Check if user is already logged in
    const token = localStorage.getItem('token');
    const userData = localStorage.getItem('user');
    
    if (token && userData) {
      try {
        setUser(JSON.parse(userData));
      } catch (err) {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
      }
    }
    setLoading(false);
  }, []);

  const handleLogin = (userData) => {
    setUser(userData);
  };

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    setUser(null);
  };

  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  if (!user) {
    return <Login onLogin={handleLogin} />;
  }

  return (
    <Router>
      <div className="container">
        <header className="app-header">
          <h1>Eventzzz - Campus Event Management</h1>
          <div className="user-info">
            <span>Welcome, {user.fullName} ({user.role})</span>
            <button onClick={handleLogout} className="logout-btn">Logout</button>
          </div>
        </header>
        
        <nav className="nav">
          {user.role === 'ADMIN' ? (
            <>
              <Link to="/admin">
                <button>Admin Dashboard</button>
              </Link>
              <Link to="/reports">
                <button>Reports</button>
              </Link>
            </>
          ) : (
            <>
              <Link to="/student">
                <button>My Events</button>
              </Link>
              <Link to="/reports">
                <button>Reports</button>
              </Link>
            </>
          )}
        </nav>

        <Routes>
          <Route path="/" element={
            user.role === 'ADMIN' ? 
              <Navigate to="/admin" replace /> : 
              <Navigate to="/student" replace />
          } />
          <Route path="/admin" element={
            user.role === 'ADMIN' ? 
              <AdminDashboard /> : 
              <Navigate to="/student" replace />
          } />
          <Route path="/student" element={
            user.role === 'STUDENT' ? 
              <StudentDashboard user={user} /> : 
              <Navigate to="/admin" replace />
          } />
          <Route path="/reports" element={<EventReports />} />
        </Routes>
      </div>
    </Router>
  )
}

export default App
