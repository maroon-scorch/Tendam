import React from 'react';
import { Button, Typography, TextField } from '@material-ui/core';
import './Setting.css';
import { useAuth } from "../../context/AuthContext";

function Setting() {
    const { currentUser } = useAuth();

    return (
        <div className="setting-container">
            <div className="setting">
            Setting
            <div>
            <Button variant="contained" color="primary">Reset Email:</Button> 
            <TextField label="Email" value={currentUser.email} disabled={true} />
            </div>
            <div>
            <Button variant="contained" color="primary">Reset Password:</Button>
            <TextField label="Password" value="********" disabled={true} />
            </div>
            </div>
        </div>
    )
}

export default Setting;