import React, {useState, useEffect} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { Button, TextField, Typography } from '@material-ui/core';
import neon from './neon.png';

import './ForgotPassword.css';

import Aos from 'aos';
import "aos/dist/aos.css";

import formatForm from '../../../service/Form/createForm.js';
import Alert from '@material-ui/lab/Alert';

import { useAuth } from "../../../context/AuthContext";

const formItems = [
    {
        type: 'text',
        label: "Email",
        className: 'signup-field',
        key: "email"
    }
];

const useStyles = makeStyles((theme) => ({
    root: {
      '& .MuiTextField-root': {
        margin: theme.spacing(1),
        width: '100%',
        height: '100%'
      },
    },
  }));

function ForgotPassword() {

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');

    const initialValue = {
        email: ''
    };

    const [formValues, setFormValues] = useState(initialValue);
    const classes = useStyles();

    const { resetPassword } = useAuth();

    useEffect(() => {
        Aos.init({});
    }, []);

    function entriesAreValid() {
        return true;
    }

    async function handleSubmit(e) {
        e.preventDefault();

        setError('');

        if (entriesAreValid()) {
            setLoading(true);
            try {
                await resetPassword(formValues.email);
                alert('Success! Check your inbox for further instructions!');
            } catch(err) {
                setError(err.message);
            }
        }

        setLoading(false);
    }

    return (
        <div className="fp-form-container">
            <div className="fp-form" data-aos="fade-up" data-aos-duration="2250">
            <Typography variant="h3" className="signup-title">Reset Password</Typography>
            {error !== '' ? <Alert severity="warning">{error}</Alert> : <div />}
            <form className={classes.root} autoComplete="off">
                {formatForm(formItems, formValues, setFormValues)}
                <div>
                <Typography variant="h6" className="signup-title">An Email will be sent to your inbox for reset!</Typography>
                </div>
                <Button id="fp-submit" variant="contained" color="secondary" onClick={handleSubmit} disabled={loading}>
                 Submit
                </Button>
            </form>
            <br />
            </div>
            <div className="background-container">
                <img src={neon} />
            </div>
        </div>
    )
};

export default ForgotPassword;