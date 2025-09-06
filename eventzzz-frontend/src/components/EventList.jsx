import React, { useState, useEffect } from 'react';
import { getEvents, registerForEvent } from '../services/api';

const EventList = () => {
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [registrationData, setRegistrationData] = useState({});

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
    const studentId = registrationData[eventId];
    if (!studentId) {
      setError('Please enter a student ID');
      return;
    }

    try {
      const response = await registerForEvent(eventId, parseInt(studentId));
      setSuccess(response.message);
      setError('');
      // Clear the input field
      setRegistrationData(prev => ({ ...prev, [eventId]: '' }));
    } catch (err) {
      setError('Registration failed: ' + (err.response?.data?.message || err.message));
      setSuccess('');
    }
  };

  const handleInputChange = (eventId, value) => {
    setRegistrationData(prev => ({ ...prev, [eventId]: value }));
  };

  const formatDate = (dateString) => {
    return new Date(dateString).toLocaleString();
  };

  if (loading) return <div>Loading events...</div>;

  return (
    <div>
      <h2>Campus Events</h2>
      
      {error && <div className="error">{error}</div>}
      {success && <div className="success">{success}</div>}

      <div className="event-list">
        {events.length === 0 ? (
          <p>No events available</p>
        ) : (
          events.map(event => (
            <div key={event.id} className="event-card">
              <h3>{event.name}</h3>
              <p><strong>Type:</strong> {event.eventType}</p>
              <p><strong>Date:</strong> {formatDate(event.date)}</p>
              <p><strong>College:</strong> {event.collegeName}</p>
              
              <div className="registration-form">
                <input
                  type="number"
                  placeholder="Student ID"
                  value={registrationData[event.id] || ''}
                  onChange={(e) => handleInputChange(event.id, e.target.value)}
                />
                <button onClick={() => handleRegistration(event.id)}>
                  Register
                </button>
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
};

export default EventList;
