import React from 'react'
import neon from './neon.mp4';
import './Home.css';
import { Typography, Button } from '@material-ui/core';
import { Link } from 'react-router-dom';

function Home() {
    return (
        <div className="home-page">
            <div className="home-text">
                <Typography variant="h1">TƎNDAM</Typography>
                <Typography variant="h5" className="home-description">The Youngest Social Media Matching App in the World</Typography>
                <Link to='/signup'>
                    <Button variant="contained" color="primary" size="large">Create Account</Button>
                </Link>
            </div>
            <div className="background-container">
                <video className='home-video' autoPlay loop muted>
                    <source src={neon} type='video/mp4' />
                </video>
            </div>
        </div>
    )
}

export default Home
