import axios from 'axios';

const API_BASE_URL = 'http://localhost:8081/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add token to requests if available
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Handle token expiration
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

// Event registration
export const registerForEvent = async (eventId, studentId) => {
  const response = await api.post(`/events/${eventId}/register`, { studentId });
  return response.data;
};

// Mark attendance
export const markAttendance = async (eventId, studentId) => {
  const response = await api.post(`/events/${eventId}/attendance`, { studentId });
  return response.data;
};

// Submit feedback
export const submitFeedback = async (eventId, studentId, score) => {
  const response = await api.post(`/events/${eventId}/feedback`, { studentId, score });
  return response.data;
};

// Get all events
export const getEvents = async (eventType = null) => {
  const params = eventType ? { type: eventType } : {};
  const response = await api.get('/reports/events', { params });
  return response.data;
};

// Get event summary
export const getEventSummary = async (eventId) => {
  const response = await api.get(`/reports/events/${eventId}/summary`);
  return response.data;
};

// Get event popularity
export const getEventPopularity = async () => {
  const response = await api.get('/reports/popularity');
  return response.data;
};

// Get student participation
export const getStudentParticipation = async (studentId) => {
  const response = await api.get(`/reports/students/${studentId}/participation`);
  return response.data;
};

// Get active students
export const getActiveStudents = async () => {
  const response = await api.get('/reports/active-students');
  return response.data;
};

// Authentication APIs
export const login = async (username, password) => {
  const response = await api.post('/auth/login', { username, password });
  return response.data;
};

export const register = async (userData) => {
  const response = await api.post('/auth/register', userData);
  return response.data;
};

// Admin APIs
export const createEvent = async (eventData) => {
  const response = await api.post('/admin/events', eventData);
  return response.data;
};

export const deleteEvent = async (eventId) => {
  const response = await api.delete(`/admin/events/${eventId}`);
  return response.data;
};

export const getAllEventsAdmin = async () => {
  const response = await api.get('/admin/events');
  return response.data;
};

export default api;
