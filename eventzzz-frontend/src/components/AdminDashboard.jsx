import React, { useState, useEffect } from 'react';
import { createEvent, deleteEvent, getAllEventsAdmin } from '../services/api';

const AdminDashboard = () => {
  const [events, setEvents] = useState([]);
  const [showCreateForm, setShowCreateForm] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [formData, setFormData] = useState({
    name: '',
    eventType: 'WORKSHOP',
    date: '',
    collegeId: 1
  });

  useEffect(() => {
    fetchEvents();
  }, []);

  const fetchEvents = async () => {
    try {
      setLoading(true);
      const data = await getAllEventsAdmin();
      setEvents(data);
      setError('');
    } catch (err) {
      setError('Failed to fetch events: ' + err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleCreateEvent = async (e) => {
    e.preventDefault();
    try {
      setLoading(true);
      await createEvent({
        ...formData,
        date: new Date(formData.date).toISOString()
      });
      setSuccess('Event created successfully');
      setError('');
      setShowCreateForm(false);
      setFormData({
        name: '',
        eventType: 'WORKSHOP',
        date: '',
        collegeId: 1
      });
      fetchEvents();
    } catch (err) {
      setError('Failed to create event: ' + (err.response?.data?.message || err.message));
      setSuccess('');
    } finally {
      setLoading(false);
    }
  };

  const handleDeleteEvent = async (eventId) => {
    if (!window.confirm('Are you sure you want to delete this event?')) {
      return;
    }

    try {
      await deleteEvent(eventId);
      setSuccess('Event deleted successfully');
      setError('');
      fetchEvents();
    } catch (err) {
      setError('Failed to delete event: ' + (err.response?.data?.message || err.message));
      setSuccess('');
    }
  };

  const handleInputChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const formatDate = (dateString) => {
    return new Date(dateString).toLocaleString();
  };

  return (
    <div>
      <div className="admin-header">
        <h2>Admin Dashboard</h2>
        <button onClick={() => setShowCreateForm(!showCreateForm)}>
          {showCreateForm ? 'Cancel' : 'Create New Event'}
        </button>
      </div>

      {error && <div className="error">{error}</div>}
      {success && <div className="success">{success}</div>}

      {showCreateForm && (
        <div className="create-event-form">
          <h3>Create New Event</h3>
          <form onSubmit={handleCreateEvent}>
            <div className="form-group">
              <label>Event Name:</label>
              <input
                type="text"
                name="name"
                value={formData.name}
                onChange={handleInputChange}
                required
              />
            </div>

            <div className="form-group">
              <label>Event Type:</label>
              <select
                name="eventType"
                value={formData.eventType}
                onChange={handleInputChange}
                required
              >
                <option value="WORKSHOP">Workshop</option>
                <option value="FEST">Fest</option>
                <option value="SEMINAR">Seminar</option>
                <option value="TECH_TALK">Tech Talk</option>
              </select>
            </div>

            <div className="form-group">
              <label>Date & Time:</label>
              <input
                type="datetime-local"
                name="date"
                value={formData.date}
                onChange={handleInputChange}
                required
              />
            </div>

            <div className="form-group">
              <label>College:</label>
              <select
                name="collegeId"
                value={formData.collegeId}
                onChange={handleInputChange}
                required
              >
                <option value={1}>MIT College of Engineering</option>
                <option value={2}>Stanford University</option>
              </select>
            </div>

            <button type="submit" disabled={loading}>
              {loading ? 'Creating...' : 'Create Event'}
            </button>
          </form>
        </div>
      )}

      <div className="events-management">
        <h3>Manage Events</h3>
        {loading && <div>Loading events...</div>}
        
        <div className="event-list">
          {events.length === 0 ? (
            <p>No events available</p>
          ) : (
            events.map(event => (
              <div key={event.id} className="event-card admin-event-card">
                <h4>{event.name}</h4>
                <p><strong>Type:</strong> {event.eventType}</p>
                <p><strong>Date:</strong> {formatDate(event.date)}</p>
                <p><strong>College:</strong> {event.collegeName}</p>
                <p><strong>ID:</strong> {event.id}</p>
                
                <div className="admin-actions">
                  <button 
                    onClick={() => handleDeleteEvent(event.id)}
                    className="delete-btn"
                  >
                    Delete Event
                  </button>
                </div>
              </div>
            ))
          )}
        </div>
      </div>
    </div>
  );
};

export default AdminDashboard;
