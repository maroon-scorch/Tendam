import React, { useState, useEffect } from 'react';
import { Button, Typography, TextField } from '@material-ui/core';
import { useAuth } from "../../context/AuthContext";
import { useDatabase } from "../../context/DatabaseContext";

import { Link, useHistory } from 'react-router-dom';

import './Profile.css';

import Aos from 'aos';
import "aos/dist/aos.css";

function Profile() {
    const { currentUser } = useAuth();
    const { getEntry, setEntry } = useDatabase();
    
    const [profileInfo, setProfileInfo] = useState({
        bio: "", name: "", age: "", matches: []});
    
    const [isEditing, setIsEditing] = useState(false);

    useEffect(() => {
        Aos.init({});

        if (currentUser !== null) {
            getEntry(currentUser.uid).then((doc) => {
                if (doc.exists) {
                    console.log(doc.data());
                    setProfileInfo(doc.data());
                } else {
                    // doc.data() will be undefined in this case
                    console.log("No such document!");
                }
            }).catch((error) => {
                console.log("Error getting document:", error);
            });
        }
    }, []);

    function handleEdit(e){
        e.preventDefault();
        setIsEditing(true);
    }
    
    function handleSave(e){
        e.preventDefault();
        setIsEditing(false);
        // setEntry('1', '2');
        let data = profileInfo;
        setEntry(currentUser.uid, data).then(() => {
                console.log("Document successfully written!");
        })
            .catch((error) => {
                console.error("Error writing document: ", error);
        });
    }

    function handleChange(e) {
        e.preventDefault();
        const { name, value } = e.target;
        setProfileInfo({
            ...profileInfo,
            [name]: value
        });
    }

    return (
        <div className="profile-container" data-aos="fade-up" data-aos-duration="2000">
            <div className="profile">
            <Link>
                <img className="profile-picture" src="blank-profile.png" alt="Avatar" />
            </Link>
            Profile Page:
            <Typography variant="h5">Bio: <TextField label="Bio"
            name="bio" value={profileInfo.bio} disabled={!isEditing} onChange={handleChange}/></Typography>
            <br />
            <Typography variant="h5">Name: <TextField label="Name"
            name="name" value={profileInfo.name} disabled={!isEditing} onChange={handleChange}/></Typography>
            <br />
            <Typography variant="h5">Age: <TextField label="Age"
            name="age" value={profileInfo.age} disabled={!isEditing} onChange={handleChange}/></Typography>
            <br />
            <Button variant="contained" color="primary" disabled={isEditing} onClick={handleEdit}>EDIT</Button> 
            <Button variant="contained" color="secondary" disabled={!isEditing} onClick={handleSave}>SAVE</Button>
            <br />
            <br />
            <Button variant="contained" color="secondary">
                <Typography variant="h5">Matches: {profileInfo.matches.length}</Typography>
            </Button>
            </div>
        </div>
    )
}

export default Profile
