import axios from 'axios';

// Ready-configured instance pointing to your upcoming local API Gateway port
const api = axios.create({
  baseURL: 'http://localhost:8080/api', 
  headers: {
    'Content-Type': 'application/json',
  }
});

// Automatically inject JWT token into header requests when backend integration happens
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default api;