import { useState, useEffect } from 'react';
import './App.css';
import { openOAuthWindow } from './services/api';
import db from './services/db';

function App() {
  const [token, setToken] = useState<string | null>(null);

  useEffect(() => {
    const loadToken = async () => {
      const stored = await db.tokens.get('taskmanager-token');
      if (stored) setToken(stored.value);
    };
    loadToken();
  }, []);

  const handleLogin = async () => {
    const t = await openOAuthWindow();
    setToken(t);
  };

  const handleLogout = async () => {
  await db.tokens.delete('taskmanager-token');
  setToken(null);
  };

  return (
    <>
      <h1>Task Manager!</h1>
      <div className="auth-section">
        {token ? (
          <>
            <p>Logged in! Token: {token}</p>
            <button onClick={handleLogout}>Logout</button>
          </>
        ) : (
          <button onClick={handleLogin}>Login with Google (Firebase)</button>
        )}
      </div>
    </>
  );
}

export default App;