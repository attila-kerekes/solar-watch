import React, { useState } from 'react';
import { Navigate, useRoutes } from 'react-router-dom';
import HomePage from './components/HomePage';
import UserRegisterPage from './components/UserRegisterPage';
import AdminRegisterPage from './components/AdminRegisterPage';
import LoginPage from './components/LoginPage';
import SolarWatchPage from './components/SolarWatchPage';
import AdminSolarWatchPage from './components/AdminSolarWatchPage';

function App() {
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [isAdmin, setIsAdmin] = useState(false);

    const routes = useRoutes([
        {
            element: isAuthenticated ?
                (isAdmin ? <Navigate to="/solar-watch-admin" /> : <SolarWatchPage />)
                : <Navigate to="/login" />,
            path: '/solar-watch',
        },
        {
            element: isAuthenticated ?
                (isAdmin ? <AdminSolarWatchPage /> : <Navigate to="/solar-watch" />)
                : <Navigate to="/login" />,
            path: '/solar-watch-admin',
        },
        {
            element: <HomePage />,
            path: '/'
        },
        {
            element: <UserRegisterPage />,
            path: '/user/register'
        },
        {
            element: <AdminRegisterPage />,
            path: '/admin/register'
        },
        {
            element: <LoginPage setIsAuthenticated={setIsAuthenticated} setIsAdmin={setIsAdmin} />,
            path: '/login'
        }
    ]);

    return routes;
}

export default App;
