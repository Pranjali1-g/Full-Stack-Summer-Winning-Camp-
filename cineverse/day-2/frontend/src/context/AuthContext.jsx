import { createContext, useState, useContext, useEffect } from 'react';

const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  // Load active session from localStorage on startup
  useEffect(() => {
    const activeUser = localStorage.getItem('activeUser');
    if (activeUser) {
      setUser(JSON.parse(activeUser));
    }
    setLoading(false);
  }, []);

  // Handle Signup Profile Creation
  const signup = (username, password, role) => {
    const existingUsers = JSON.parse(localStorage.getItem('users')) || [];
    
    // Check if username is already taken
    if (existingUsers.some(u => u.username.toLowerCase() === username.toLowerCase())) {
      return { success: false, message: "Username is already taken!" };
    }

    const newUser = { username, password, role };
    existingUsers.push(newUser);
    localStorage.setItem('users', JSON.stringify(existingUsers));
    return { success: true, message: "Account created successfully!" };
  };

  // Handle Login Credentials Check
  const login = (username, password) => {
    const existingUsers = JSON.parse(localStorage.getItem('users')) || [];
    
    // Search matching credentials
    const foundUser = existingUsers.find(
      u => u.username.toLowerCase() === username.toLowerCase() && u.password === password
    );

    if (foundUser) {
      const sessionData = { 
        isAuthenticated: true, 
        username: foundUser.username, 
        role: foundUser.role 
      };
      setUser(sessionData);
      localStorage.setItem('activeUser', JSON.stringify(sessionData));
      return { success: true };
    } else {
      return { success: false, message: "Invalid username or password!" };
    }
  };

  const logout = () => {
    setUser(null);
    localStorage.removeItem('activeUser');
  };

  return (
    <AuthContext.Provider value={{ user, login, signup, logout, loading }}>
      {!loading && children}
    </AuthContext.Provider>
  );
}

export const useAuth = () => useContext(AuthContext);