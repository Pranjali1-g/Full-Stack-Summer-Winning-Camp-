import { useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { useNavigate, Link } from 'react-router-dom';

export default function Signup() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [role, setRole] = useState('User');
  const [message, setMessage] = useState({ type: '', text: '' });
  
  const { signup } = useAuth();
  const navigate = useNavigate();

  const handleSignupSubmit = (e) => {
    e.preventDefault();
    setMessage({ type: '', text: '' });

    if (!username || !password) {
      setMessage({ type: 'error', text: 'All fields are required!' });
      return;
    }

    const result = signup(username, password, role);
    if (result.success) {
      setMessage({ type: 'success', text: result.message + ' Redirecting to login...' });
      setTimeout(() => {
        navigate('/login');
      }, 2000);
    } else {
      setMessage({ type: 'error', text: result.message });
    }
  };

  return (
    <div style={{ maxWidth: '400px', margin: '80px auto', padding: '30px', border: '1px solid #ddd', borderRadius: '8px', textAlign: 'center', fontFamily: 'sans-serif' }}>
      <h2>Create CineVerse Account</h2>
      
      {message.text && (
        <p style={{ color: message.type === 'error' ? 'red' : 'green', fontSize: '14px' }}>
          {message.text}
        </p>
      )}

      <form onSubmit={handleSignupSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '15px', textAlign: 'left' }}>
        <div>
          <label style={{ fontWeight: 'bold' }}>Choose Username</label>
          <input 
            type="text" 
            value={username} 
            onChange={(e) => setUsername(e.target.value)}
            style={{ width: '100%', padding: '8px', marginTop: '5px', boxSizing: 'border-box' }}
            placeholder="e.g., Pranjali123"
          />
        </div>

        <div>
          <label style={{ fontWeight: 'bold' }}>Password</label>
          <input 
            type="password" 
            value={password} 
            onChange={(e) => setPassword(e.target.value)}
            style={{ width: '100%', padding: '8px', marginTop: '5px', boxSizing: 'border-box' }}
            placeholder="Create secure password"
          />
        </div>

        <div>
          <label style={{ fontWeight: 'bold' }}>Assign Platform Account Role</label>
          <select 
            value={role} 
            onChange={(e) => setRole(e.target.value)}
            style={{ width: '100%', padding: '8px', marginTop: '5px', boxSizing: 'border-box' }}
          >
            <option value="User">User (Standard App Access)</option>
            <option value="Theatre Owner">Theatre Owner (Can Manage Shows)</option>
            <option value="Admin">Admin (Full Control Panel Access)</option>
          </select>
        </div>

        <button type="submit" style={{ padding: '10px', background: '#28a745', color: '#fff', border: 'none', borderRadius: '4px', cursor: 'pointer', fontWeight: 'bold' }}>
          Register Account
        </button>
      </form>

      <p style={{ marginTop: '20px', fontSize: '14px' }}>
        Already have an account? <Link to="/login" style={{ color: '#007bff', textDecoration: 'none' }}>Sign In</Link>
      </p>
    </div>
  );
}