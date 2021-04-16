import React from 'react'
import neon from './neon.mp4';
import light from './light.mp4';
import './Home.css';
import { Typography, Button } from '@material-ui/core';
import { Link } from 'react-router-dom';
import { useAuth } from "../../context/AuthContext.js";

function Home() {
    const { currentUser } = useAuth();

    return (
        <div className="home-page">
            <div className="home-text">
                <Typography variant="h1">TƎNDAM</Typography>
                <Typography variant="h5" className="home-description">The Youngest Social Media Matching App in the World</Typography>
                <Link to='/signup'>
                    <Button variant="contained" color="primary"  disabled={currentUser !== null} size="large">Create Account</Button>
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
