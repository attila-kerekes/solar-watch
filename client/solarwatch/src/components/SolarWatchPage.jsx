import React, { useState } from 'react';

const SolarWatchPage = () => {
    const [city, setCity] = useState('');
    const [date, setDate] = useState('');
    const [sunrise, setSunrise] = useState('');
    const [sunset, setSunset] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch(`/api/sunrise-sunset?city=${city}&date=${date}`);
            const data = await response.json();
            setSunrise(data.results.sunrise);
            setSunset(data.results.sunset);
        } catch (error) {
            console.error('Error fetching sunrise/sunset data:', error);
        }
    };

    return (
        <div>
            <h2>Solar Watch</h2>
            <form onSubmit={handleSubmit}>
                <label>
                    City:
                    <input
                        type="text"
                        value={city}
                        onChange={(e) => setCity(e.target.value)}
                        required
                    />
                </label>
                <label>
                    Date:
                    <input
                        type="date"
                        value={date}
                        onChange={(e) => setDate(e.target.value)}
                        required
                    />
                </label>
                <button type="submit">Get Sunrise/Sunset</button>
            </form>
            {sunrise && sunset && (
                <div>
                    <h3>Sunrise: {sunrise}</h3>
                    <h3>Sunset: {sunset}</h3>
                </div>
            )}
        </div>
    );
};

export default SolarWatchPage;
