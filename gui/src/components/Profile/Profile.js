import React, { useState, useEffect, useRef } from 'react';
import { Button, Typography, TextField, Tooltip } from '@material-ui/core';
import { useAuth } from "../../context/AuthContext";
import { useDatabase } from "../../context/DatabaseContext";

import MatchDisplay from './MatchDisplay';
import './Profile.css';

import Aos from 'aos';
import "aos/dist/aos.css";

function Profile({ history }) {
    const { currentUser } = useAuth();
    const { getEntry, setEntry, uploadStorage, getFile, getEntryData } = useDatabase();
    
    const [profileInfo, setProfileInfo] = useState({
        bio: "", name: "", age: "", id: "", matches: []});

    
    const selectFile = useRef(null);
    const [profilePic, setProfilePic] = useState("blank-profile.png");
    const [isEditing, setIsEditing] = useState(false);

    useEffect(async () => {
        Aos.init({});
        if (currentUser !== null) {
            getEntry(currentUser.uid).then((doc) => {
                if (doc.exists) {
                    console.log(doc.data());
                    let profileData = doc.data();
                    if (profileData['matches'] === null) {
                        profileData['matches'] = []
                    }
                    setProfileInfo(profileData);
                } else {
                    // doc.data() will be undefined in this case
                    console.log("No such document!");
                }
            }).catch((error) => {
                console.log("Error getting document:", error);
            });

            getFile(currentUser.uid, '/profilePicture').then((imageFile) => {
                setProfilePic(imageFile);
            }).catch((error) => {
                // console.log("Error getting document:", error);
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

    async function changeFile(event) {
        let fileAdded = event.target.files[0];
        if (fileAdded !== undefined) {
            if (fileAdded.size > 2097152) {
                alert("Image File is too big, please make sure it is under 2 MB");
            } else {
                let binaryData = [];
                binaryData.push(fileAdded);
                let newImageURL = URL.createObjectURL(new Blob(binaryData, {type: "application/zip"}));
                setProfilePic(newImageURL);
    
                try {
                    await uploadStorage(currentUser.uid, '/profilePicture', fileAdded);
                } catch (err) {
                    console.log(err.message);
                }

                window.location.reload();
            }
        }
    }

    function uploadImage() {
        selectFile.current.click();
    }

    return (
        <div className="profile-container" data-aos="fade-up" data-aos-duration="2000">
            <div className="profile">
            <Tooltip title={<h1>Change Profile Picture</h1>}>
                <div className="profile-picture-container" onClick={uploadImage}>
                    <img className="profile-picture" src={profilePic} alt="Avatar" />
                </div>
            </Tooltip>
            <input type="file" accept="image/*" onChange={changeFile}  ref={selectFile} style={{display: 'none'}} />
            Profile Page:
            <Typography variant="h5">Bio: <TextField fullWidth label="Bio"
            name="bio" value={profileInfo.bio} disabled={!isEditing} onChange={handleChange}/></Typography>
            <br />
            <Typography variant="h5">Name: <TextField fullWidth label="Name"
            name="name" value={profileInfo.name} disabled={!isEditing} onChange={handleChange}/></Typography>
            <br />
            <Typography variant="h5">Age: <TextField fullWidth label="Age"
            name="age" value={profileInfo.age} disabled={!isEditing} onChange={handleChange}/></Typography>
            <br />
            <div className="profile-control">
                <Button variant="contained" color="primary" disabled={isEditing} onClick={handleEdit}>EDIT</Button> 
                <Button variant="contained" color="secondary" disabled={!isEditing} onClick={handleSave}>SAVE</Button>
                <br />
                <br />
                <MatchDisplay matches={profileInfo.matches} />
            </div>
            </div>
        </div>
    )
}

export default Profile;
