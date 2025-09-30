import axios from 'axios';
import db from './db';

const api = axios.create({
  baseURL: 'http://localhost:8080', 
  withCredentials: true,
});

// Async interceptor for IndexedDB token
api.interceptors.request.use(async (config) => {
  const stored = await db.tokens.get('taskmanager-token');
  if (stored?.value) {
    config.headers.Authorization = `Bearer ${stored.value}`;
  }
  return config;
});

export default api;