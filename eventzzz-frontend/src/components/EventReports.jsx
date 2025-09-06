import React, { useState, useEffect } from 'react';
import { 
  getEventPopularity, 
  getActiveStudents, 
  getStudentParticipation,
  getEventSummary 
} from '../services/api';

const EventReports = () => {
  const [popularityData, setPopularityData] = useState([]);
  const [activeStudents, setActiveStudents] = useState([]);
  const [participationData, setParticipationData] = useState([]);
  const [eventSummary, setEventSummary] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [studentId, setStudentId] = useState('');
  const [eventId, setEventId] = useState('');
  const [activeReport, setActiveReport] = useState('popularity');

  useEffect(() => {
    fetchEventPopularity();
    fetchActiveStudents();
  }, []);

  const fetchEventPopularity = async () => {
    try {
      setLoading(true);
      const data = await getEventPopularity();
      setPopularityData(data);
      setError('');
    } catch (err) {
      setError('Failed to fetch event popularity: ' + err.message);
    } finally {
      setLoading(false);
    }
  };

  const fetchActiveStudents = async () => {
    try {
      const data = await getActiveStudents();
      setActiveStudents(data);
    } catch (err) {
      setError('Failed to fetch active students: ' + err.message);
    }
  };

  const fetchStudentParticipation = async () => {
    if (!studentId) {
      setError('Please enter a student ID');
      return;
    }

    try {
      setLoading(true);
      const data = await getStudentParticipation(parseInt(studentId));
      setParticipationData(data);
      setError('');
    } catch (err) {
      setError('Failed to fetch student participation: ' + (err.response?.data?.message || err.message));
    } finally {
      setLoading(false);
    }
  };

  const fetchEventSummary = async () => {
    if (!eventId) {
      setError('Please enter an event ID');
      return;
    }

    try {
      setLoading(true);
      const data = await getEventSummary(eventId);
      setEventSummary(data);
      setError('');
    } catch (err) {
      setError('Failed to fetch event summary: ' + (err.response?.data?.message || err.message));
    } finally {
      setLoading(false);
    }
  };

  const formatDate = (dateString) => {
    return new Date(dateString).toLocaleString();
  };

  return (
    <div>
      <h2>Event Reports</h2>
      
      {error && <div className="error">{error}</div>}

      <div className="report-controls">
        <button 
          onClick={() => setActiveReport('popularity')}
          style={{ backgroundColor: activeReport === 'popularity' ? '#535bf2' : '#646cff' }}
        >
          Event Popularity
        </button>
        <button 
          onClick={() => setActiveReport('active-students')}
          style={{ backgroundColor: activeReport === 'active-students' ? '#535bf2' : '#646cff' }}
        >
          Top Active Students
        </button>
        <button 
          onClick={() => setActiveReport('participation')}
          style={{ backgroundColor: activeReport === 'participation' ? '#535bf2' : '#646cff' }}
        >
          Student Participation
        </button>
        <button 
          onClick={() => setActiveReport('event-summary')}
          style={{ backgroundColor: activeReport === 'event-summary' ? '#535bf2' : '#646cff' }}
        >
          Event Summary
        </button>
      </div>

      {loading && <div>Loading...</div>}

      {activeReport === 'popularity' && (
        <div className="reports-section">
          <h3>Event Popularity Report</h3>
          <div className="report-data">
            {popularityData.length === 0 ? (
              <p>No popularity data available</p>
            ) : (
              popularityData.map((event, index) => (
                <div key={index} className="report-item">
                  <strong>{event.eventName}</strong>: {event.registrationCount} registrations
                </div>
              ))
            )}
          </div>
        </div>
      )}

      {activeReport === 'active-students' && (
        <div className="reports-section">
          <h3>Top 3 Most Active Students</h3>
          <div className="report-data">
            {activeStudents.length === 0 ? (
              <p>No active students data available</p>
            ) : (
              activeStudents.map((student, index) => (
                <div key={index} className="report-item">
                  <strong>#{index + 1} {student.studentName}</strong>: {student.attendedEventsCount} events attended
                </div>
              ))
            )}
          </div>
        </div>
      )}

      {activeReport === 'participation' && (
        <div className="reports-section">
          <h3>Student Participation Report</h3>
          <div className="report-controls">
            <input
              type="number"
              placeholder="Enter Student ID"
              value={studentId}
              onChange={(e) => setStudentId(e.target.value)}
            />
            <button onClick={fetchStudentParticipation}>
              Get Participation Report
            </button>
          </div>
          <div className="report-data">
            {participationData.length === 0 ? (
              <p>No participation data available. Enter a student ID and click the button above.</p>
            ) : (
              participationData.map((event, index) => (
                <div key={index} className="report-item">
                  <strong>{event.eventName}</strong> - {formatDate(event.eventDate)}
                </div>
              ))
            )}
          </div>
        </div>
      )}

      {activeReport === 'event-summary' && (
        <div className="reports-section">
          <h3>Event Summary Report</h3>
          <div className="report-controls">
            <input
              type="text"
              placeholder="Enter Event ID (UUID)"
              value={eventId}
              onChange={(e) => setEventId(e.target.value)}
            />
            <button onClick={fetchEventSummary}>
              Get Event Summary
            </button>
          </div>
          <div className="report-data">
            {eventSummary ? (
              <div>
                <div className="report-item">
                  <strong>Total Registrations:</strong> {eventSummary.totalRegistrations}
                </div>
                <div className="report-item">
                  <strong>Attendance Percentage:</strong> {eventSummary.attendancePercentage?.toFixed(2)}%
                </div>
                <div className="report-item">
                  <strong>Average Feedback Score:</strong> {
                    eventSummary.averageFeedbackScore 
                      ? eventSummary.averageFeedbackScore.toFixed(2) 
                      : 'No feedback yet'
                  }
                </div>
              </div>
            ) : (
              <p>No event summary available. Enter an event ID and click the button above.</p>
            )}
          </div>
        </div>
      )}
    </div>
  );
};

export default EventReports;
