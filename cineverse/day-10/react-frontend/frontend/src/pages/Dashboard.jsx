import Navbar from '../components/Navbar';
import { useAuth } from '../context/AuthContext';

export default function Dashboard() {
  const { user } = useAuth();

  return (
    <div>
      <Navbar />
      <div style={{ padding: '20px' }}>
        <h2>Welcome to your Dashboard, {user.username}!</h2>
        <p>Your current access tier is: <strong>{user.role}</strong></p>
        
        {/* Conditional Management Content based on Matrix */}
        {user.role === 'Admin' && (
          <div style={{ background: '#ffe6e6', padding: '15px', marginTop: '20px' }}>
            <h3>🛠️ Master System Admin Panel</h3>
            <button>Manage System Users</button>
          </div>
        )}
      </div>
    </div>
  );
}
