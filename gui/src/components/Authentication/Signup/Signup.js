import React, {useState, useEffect} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { Button, TextField, Typography } from '@material-ui/core';
import neon from './neon.png';

import { Link, useHistory } from 'react-router-dom';
import './Signup.css';

import Aos from 'aos';
import "aos/dist/aos.css";

import formatForm from '../../../service/Form/createForm.js';
import Alert from '@material-ui/lab/Alert';

import { useAuth } from "../../../context/AuthContext";
import { useDatabase } from "../../../context/DatabaseContext";

const formItems = [
    {
        type: 'text',
        label: "Email",
        className: 'signup-field',
        key: "email"
    },
    {
        type: 'text',
        label: "Username",
        className: 'signup-field',
        key: "userName"
    },
    {
        type: 'password',
        label: "Password",
        className: 'signup-field',
        key: "password"
    },
    {
        type: 'password',
        label: "Confirm Password",
        className: 'signup-field',
        key: "passwordConf"
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

function Signup() {

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');

    const initialValue = {
        email: '',
        userName: '',
        password: '',
        passwordConf: '',
    };

    const [formValues, setFormValues] = useState(initialValue);
    const classes = useStyles();

    const { currentUser, signup } = useAuth();
    const history = useHistory();

    useEffect(() => {
        Aos.init({});
    }, []);

    // Regex taken from the top rated answer of the post:
    // https://stackoverflow.com/questions/46155/how-to-validate-an-email-address-in-javascript
    // provided by 22 users' cumulative revision as of 4/12/2021
    function validateEmail(email) {
        const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(String(email).toLowerCase());
    }

    function entriesAreValid() {
        if (formValues.email === '') {
            setError('Email Shouldn\'t be Blank');
            return false;
        }

        if (!validateEmail(formValues.email)) {
            setError('Email Address is invalid, please check if you have entered it correctly');
            return false;
        }

        if (formValues.password !== formValues.passwordConf) {
            setError('Passwords do not match!');
            return false;
        }

        if (formValues.password.length < 6) {
            setError('Password must be greater than or equal to 6 characters');
            return false;
        }

        return true;
    }

    async function handleSubmit(e) {
        e.preventDefault();
        setError('');

        if (entriesAreValid()) {
            setLoading(true)
            try {
                await signup(formValues.email, formValues.password, formValues.userName);
                window.location.reload();
                history.push('/dashboard');
            } catch (err) {
                setError(err.message);
            }

            // signup(formValues.email, formValues.password, formValues.userName)
            // .then(function(result) {
            //     result.user.updateProfile({
            //       displayName: formValues.userName
            //     });
            //     history.push('/dashboard');
            //   }).catch(function(error) {
            //     setError(error.message);
            //   });
        }

        setLoading(false);
    }

    return (
        <div className="signup-form-container">
            <div className="signup-form" data-aos="fade-up" data-aos-duration="2250">
            <Typography variant="h3" className="signup-title">Sign Up</Typography>
            {error !== '' ? <Alert severity="warning">{error}</Alert> : <div />}
            <form className={classes.root} autoComplete="off">
                {formatForm(formItems, formValues, setFormValues)}
            </form>
            <div>
            <h4>
                By creating this account, you agree to our <Link to="/terms-of-service" target="_blank" rel="noopener noreferrer">terms of service</Link> <br/>
                here that includes specifications on our privacy policies <br />
                and how we will collect and use data about you.
            </h4>
            <Button id="signup-submit" variant="contained" color="secondary" onClick={handleSubmit} disabled={loading}>
                 Submit
            </Button>
            </div>
            {/* <Button onClick={(e) => {console.log(currentUser);}}>Test</Button> */}
            </div>
            <div className="background-container">
                <img src={neon} />
            </div>
        </div>
    )
};

export default Signup;