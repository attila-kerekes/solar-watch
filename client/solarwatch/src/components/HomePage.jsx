import React from 'react';
import { Link } from 'react-router-dom';

function HomePage() {
    return (
        <div>
            <h1>Welcome to SolarWatch</h1>
            <Link to="/user/register">Register as User</Link><br />
            <Link to="/admin/register">Register as Admin</Link><br />
            <Link to="/login">Login</Link>
        </div>
    );
}

export default HomePage;
