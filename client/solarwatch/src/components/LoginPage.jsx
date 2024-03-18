import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { jwtDecode } from "jwt-decode";

const LoginPage = ({ setIsAuthenticated, setIsAdmin }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleLogin = async () => {
        try {
            const response = await fetch('/api/user/signin', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ username, password }),
            });
            const data = await response.json();

            localStorage.setItem('token', data.jwt);

            const role = data.role;

            if (role === 'ROLE_ADMIN') {
                setIsAdmin(true);
                navigate('/solar-watch-admin');
            } else {
                setIsAdmin(false);
                navigate('/solar-watch');
            }

            setIsAuthenticated(true);

        } catch (error) {
            console.error('Error logging in:', error);
        }
    };

    return (
        <div>
            <h2>Login</h2>
            <input
                type="text"
                placeholder="Username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
            />
            <input
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <button onClick={handleLogin}>Login</button>
        </div>
    );
};

export default LoginPage;
