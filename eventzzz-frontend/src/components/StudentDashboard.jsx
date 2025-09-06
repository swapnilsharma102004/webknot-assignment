import React, { useState, useEffect } from 'react';
import { getEvents, registerForEvent, markAttendance, submitFeedback } from '../services/api';

const StudentDashboard = ({ user }) => {
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [feedbackData, setFeedbackData] = useState({});

  useEffect(() => {
    fetchEvents();
  }, []);

  const fetchEvents = async () => {
    try {
      setLoading(true);
      const data = await getEvents();
      setEvents(data);
      setError('');
    } catch (err) {
      setError('Failed to fetch events: ' + err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleRegistration = async (eventId) => {
    try {
      // For simplicity, we'll use the user's ID as student ID
      // In a real app, you'd have a proper student-user mapping
      const response = await registerForEvent(eventId, 1); // Using student ID 1 for demo
      setSuccess(response.message);
      setError('');
    } catch (err) {
      setError('Registration failed: ' + (err.response?.data?.message || err.message));
      setSuccess('');
    }
  };

  const handleAttendance = async (eventId) => {
    try {
      const response = await markAttendance(eventId, 1); // Using student ID 1 for demo
      setSuccess(response.message);
      setError('');
    } catch (err) {
      setError('Attendance marking failed: ' + (err.response?.data?.message || err.message));
      setSuccess('');
    }
  };

  const handleFeedback = async (eventId) => {
    const score = feedbackData[eventId];
    if (!score) {
      setError('Please select a feedback score');
      return;
    }

    try {
      const response = await submitFeedback(eventId, 1, parseInt(score)); // Using student ID 1 for demo
      setSuccess(response.message);
      setError('');
      setFeedbackData(prev => ({ ...prev, [eventId]: '' }));
    } catch (err) {
      setError('Feedback submission failed: ' + (err.response?.data?.message || err.message));
      setSuccess('');
    }
  };

  const handleFeedbackChange = (eventId, value) => {
    setFeedbackData(prev => ({ ...prev, [eventId]: value }));
  };

  const formatDate = (dateString) => {
    return new Date(dateString).toLocaleString();
  };

  if (loading) return <div>Loading events...</div>;

  return (
    <div>
      <div className="student-header">
        <h2>Welcome, {user.fullName}!</h2>
        <p>Student Dashboard</p>
      </div>

      {error && <div className="error">{error}</div>}
      {success && <div className="success">{success}</div>}

      <div className="student-events">
        <h3>Available Events</h3>
        <div className="event-list">
          {events.length === 0 ? (
            <p>No events available</p>
          ) : (
            events.map(event => (
              <div key={event.id} className="event-card student-event-card">
                <h4>{event.name}</h4>
                <p><strong>Type:</strong> {event.eventType}</p>
                <p><strong>Date:</strong> {formatDate(event.date)}</p>
                <p><strong>College:</strong> {event.collegeName}</p>
                
                <div className="student-actions">
                  <button onClick={() => handleRegistration(event.id)}>
                    Register
                  </button>
                  
                  <button onClick={() => handleAttendance(event.id)}>
                    Mark Attendance
                  </button>
                  
                  <div className="feedback-section">
                    <select
                      value={feedbackData[event.id] || ''}
                      onChange={(e) => handleFeedbackChange(event.id, e.target.value)}
                    >
                      <option value="">Select Rating</option>
                      <option value="1">1 - Poor</option>
                      <option value="2">2 - Fair</option>
                      <option value="3">3 - Good</option>
                      <option value="4">4 - Very Good</option>
                      <option value="5">5 - Excellent</option>
                    </select>
                    <button onClick={() => handleFeedback(event.id)}>
                      Submit Feedback
                    </button>
                  </div>
                </div>
              </div>
            ))
          )}
        </div>
      </div>
    </div>
  );
};

export default StudentDashboard;
