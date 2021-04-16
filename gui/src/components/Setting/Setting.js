import React, { useState } from 'react';
import { Button, Typography, TextField } from '@material-ui/core';
import './Setting.css';
import { useAuth } from "../../context/AuthContext";
import { useModal } from "../../context/ModalContext";

import FinalDecision from './FinalDecision';

function Setting() {
    const { currentUser} = useAuth();
    const [show, setShow] = useState(true);
    const { close, open, setContent } = useModal();

    function handleYes() {
        setShow(false);
        close();
    }

    function displayDelete() {
        open();
        setContent(
            <div>
                <Typography variant="h4">Are you sure you want to delete your account?</Typography>
                <Typography variant="h6">Your account wouldn't be recoverable after you have deleted it,
                and making a new account with the same email won't restore your previous data.</Typography>
                <Button variant="contained" color="primary" onClick={close}>No</Button>
                <Button variant="contained" color="secondary" onClick={handleYes}>Yes</Button>
            </div>
        );
    }

    return (
        <div className="setting-container">
        <div className="setting">
            Setting
            {show ? <>
            <div>
            <Button variant="contained" color="primary">Reset Email:</Button> 
            <TextField label="Email" value={currentUser.email} disabled={true} />
            </div>
            <div>
            <Button variant="contained" color="primary">Reset Password:</Button>
            <TextField label="Password" value="********" disabled={true} />
            </div>
            <div>
            <Button variant="contained" color="secondary" onClick={displayDelete}>Delete Your Account</Button>
            </div></>
                :
                <FinalDecision />
            }
            </div>
        </div>
    )
}

export default Setting;