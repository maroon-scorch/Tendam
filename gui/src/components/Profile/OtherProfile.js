import React, { useState, useEffect } from 'react';
import { Button, Typography, TextField, Tooltip } from '@material-ui/core';
import { useDatabase } from "../../context/DatabaseContext";

import MatchDisplay from './MatchDisplay';
import './Profile.css';

import { Link } from 'react-router-dom';
import Aos from 'aos';
import "aos/dist/aos.css";

function OtherProfile({ history }) {
    const { getFile } = useDatabase();
    
    const [profileInfo, setProfileInfo] = useState({
        bio: "", name: "", age: "", id: "", matches: []});

    const [profilePic, setProfilePic] = useState("blank-profile.png");

    useEffect(async () => {
        Aos.init({});

        let userData = history.location.state;
        console.log(userData);
        if (typeof userData !== "undefined") {
            setProfileInfo(userData.data);

            getFile(userData.data.id, '/profilePicture').then((imageFile) => {
                setProfilePic(imageFile);
            }).catch((error) => {
                // console.log("Error getting document:", error);
            });
        }
    }, []);

    return (
        <div className="profile-container" data-aos="fade-up" data-aos-duration="2000">
            <div className="profile">
            <Tooltip title="Profile Picture">
                <div className="profile-picture-container">
                    <img className="profile-picture" src={profilePic} alt="Avatar" />
                </div>
            </Tooltip>
            Profile Page:
            <Typography variant="h5">Bio: <TextField fullWidth label="Bio"
            name="bio" value={profileInfo.bio} disabled={true}/></Typography>
            <br />
            <Typography variant="h5">Name: <TextField fullWidth label="Name"
            name="name" value={profileInfo.name} disabled={true}/></Typography>
            <br />
            <Typography variant="h5">Age: <TextField fullWidth label="Age"
            name="age" value={profileInfo.age} disabled={true}/></Typography>
            <br />
            <div className="profile-control">
                <MatchDisplay matches={profileInfo.matches} />
                <Link to={{  
                    pathname: `/message#${profileInfo.id}`,
                    state: {
                    data: profileInfo
                }}}>
                    <Button variant="contained" color="primary">
                        <Typography variant="h5">Message</Typography>
                    </Button>
                </Link>
            </div>
            </div>
        </div>
    )
}

export default OtherProfile