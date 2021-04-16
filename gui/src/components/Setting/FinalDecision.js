import React, { useState } from 'react';
import { Button, Typography, TextField } from '@material-ui/core';
import { useAuth } from "../../context/AuthContext";
import { Link, useHistory } from "react-router-dom";
import Alert from '@material-ui/lab/Alert';

function FinalDecision() {
    const { currentUser, makeCredentials} = useAuth();
    const { history } = useHistory();
    const [credValue, setCred] = useState({
        email: "",
        password: ""
    });
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);

    function handleChange(e) {
        const { name, value } = e.target;
        setCred({
            ...credValue,
            [name]: value
        });
    }

    
    function handleDelete() {
        // Code referenced from how to authenaticate with Firebase
        // https://stackoverflow.com/questions/41108180/how-to-verify-users-current-password
        setLoading(true);
        setError('');

        try {
            let credential = makeCredentials(credValue.email, credValue.password);
            currentUser.reauthenticateWithCredential(credential).then(function() {
                // User re-authenticated.
                currentUser.delete().then(function() {
                    history.push('/');
                });
                
              }).catch(function(error) {
                // An error happened.
                setError(error.message);
                setLoading(false);
            });
        } catch (err) {
            setError(err.message);
            setLoading(false);
        }
    }


    return (
        <div>
        <Typography variant="h4">Are you absolutely sure that you want to?</Typography>
        <Typography variant="h6">Please enter your password and email to confirm deletion:</Typography>
        {error !== '' ? <Alert severity="warning">{error}</Alert> : <div />}
        <div>
        <TextField label="Confirm Email" name="email" value={credValue.email} onChange={handleChange}/>
        <br />
        <TextField label="Confirm Password" name="password" value={credValue.password} onChange={handleChange}/>
        </div>
        <br />
        <Link to="/profile">
        <Button variant="contained" color="primary">I changed my mind!</Button>
        </Link>
        <Button variant="contained" color="secondary" 
        onClick={handleDelete} disabled={loading}>
        Yes, I want to delete my account.</Button>
    </div>
    );
}

export default FinalDecision
