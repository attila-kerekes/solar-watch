import React, { useState } from 'react';

const AdminSolarWatchPage = () => {
    const [city, setCity] = useState('');
    const [date, setDate] = useState('');
    const [sunrise, setSunrise] = useState('');
    const [sunset, setSunset] = useState('');
    const [formData, setFormData] = useState({
        cityName: '',
        cityLat: '',
        cityLon: '',
        cityCountry: '',
        cityState: '',
        sunriseTime: '',
        sunsetTime: ''
    });

    const handleCitySubmit = async (e) => {
        e.preventDefault();
        try {
            const token = localStorage.getItem('token');
            const response = await fetch(`/api/admin/cities/${formData.cityName}`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    lat: formData.cityLat,
                    lon: formData.cityLon,
                    country: formData.cityCountry,
                    state: formData.cityState
                })
            });
            if (response.ok) {
                console.log('City added successfully');
            } else {
                console.error('Failed to add city');
            }
        } catch (error) {
            console.error('Error adding city:', error);
        }
    };

    const handleSunriseSunsetSubmit = async (e) => {
        e.preventDefault();
        try {
            const token = localStorage.getItem('token');
            const response = await fetch(`/api/admin/sunrisesunset/${formData.cityName}/${date}`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    sunrise: formData.sunriseTime,
                    sunset: formData.sunsetTime
                })
            });
            if (response.ok) {
                console.log('Sunrise/sunset data added successfully');
            } else {
                console.error('Failed to add sunrise/sunset data');
            }
        } catch (error) {
            console.error('Error adding sunrise/sunset data:', error);
        }
    };

    return (
        <div>
            <h2>Solar Watch</h2>
            <form onSubmit={handleCitySubmit}>
                <h3>Add City</h3>
                <label>
                    Name:
                    <input type="text" value={formData.cityName} onChange={(e) => setFormData({ ...formData, cityName: e.target.value })} required />
                </label><br />
                <label>
                    Latitude:
                    <input type="text" value={formData.cityLat} onChange={(e) => setFormData({ ...formData, cityLat: e.target.value })} required />
                </label><br />
                <label>
                    Longitude:
                    <input type="text" value={formData.cityLon} onChange={(e) => setFormData({ ...formData, cityLon: e.target.value })} required />
                </label><br />
                <label>
                    Country:
                    <input type="text" value={formData.cityCountry} onChange={(e) => setFormData({ ...formData, cityCountry: e.target.value })} required />
                </label><br />
                <label>
                    State:
                    <input type="text" value={formData.cityState} onChange={(e) => setFormData({ ...formData, cityState: e.target.value })} required />
                </label><br />
                <button type="submit">Add City</button>
            </form>

            <form onSubmit={handleSunriseSunsetSubmit}>
                <h3>Add Sunrise/Sunset Data</h3>
                <label>
                    City:
                    <input type="text" value={formData.cityName} onChange={(e) => setFormData({ ...formData, cityName: e.target.value })} required />
                </label><br />
                <label>
                    Date:
                    <input type="date" value={date} onChange={(e) => setDate(e.target.value)} required />
                </label><br />
                <label>
                    Sunrise Time:
                    <input type="time" value={formData.sunriseTime} onChange={(e) => setFormData({ ...formData, sunriseTime: e.target.value })} required />
                </label><br />
                <label>
                    Sunset Time:
                    <input type="time" value={formData.sunsetTime} onChange={(e) => setFormData({ ...formData, sunsetTime: e.target.value })} required />
                </label><br />
                <button type="submit">Add Sunrise/Sunset Data</button>
            </form>
        </div>
    );
};

export default AdminSolarWatchPage;
