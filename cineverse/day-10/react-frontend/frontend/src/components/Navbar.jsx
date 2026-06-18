import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function Navbar() {
  const { user, logout } = useAuth();

  return (
    <nav style={{ display: 'flex', gap: '20px', padding: '15px', background: '#222', color: '#fff' }}>
      <strong>CineVerse</strong>
      <Link to="/dashboard" style={{ color: '#fff' }}>Dashboard</Link>
      <Link to="/catalog" style={{ color: '#fff' }}>Movie Catalog</Link>
      
      {/* Structural UI Modification based on RBAC Rules */}
      {(user.role === 'Admin' || user.role === 'Theatre Owner') && (
        <span style={{ color: '#gold', fontWeight: 'bold' }}>👑 Management Panel</span>
      )}

      <span style={{ marginLeft: 'auto' }}>Hello, {user.username} ({user.role})</span>
      <button onClick={logout} style={{ marginLeft: '10px' }}>Logout</button>
    </nav>
  );
}