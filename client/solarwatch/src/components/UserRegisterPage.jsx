import React, { useState } from 'react';
import {Navigate} from "react-router-dom";

function UserRegisterPage() {
    const [formData, setFormData] = useState({
        username: '',
        email: '',
        password: ''
    });
    const [isRegistered, setIsRegistered] = useState(false);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const token = localStorage.getItem('token');
            const response = await fetch('/api/user/register', {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            });
            if (response.ok) {
                setIsRegistered(true);
            } else {
                console.error('Failed to register user');
            }
        } catch (error) {
            console.error('Error registering user:', error);
        }
    };

    return (
        <div>
            <h2>User Registration</h2>
            <form onSubmit={handleSubmit}>
                <label>
                    Username:
                    <input type="text" name="username" value={formData.username} onChange={handleChange} />
                </label><br />
                <label>
                    Email:
                    <input type="email" name="email" value={formData.email} onChange={handleChange} />
                </label><br />
                <label>
                    Password:
                    <input type="password" name="password" value={formData.password} onChange={handleChange} />
                </label><br />
                <button type="submit">Register</button>
                {isRegistered && <Navigate to="/" />}
            </form>
        </div>
    );
}

export default UserRegisterPage;
