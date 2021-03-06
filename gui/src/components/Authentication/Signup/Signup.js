import React, {useState, useEffect} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { Button, Typography, FormControlLabel, Checkbox } from '@material-ui/core';
import neon from './neon.png';

import { Link, useHistory } from 'react-router-dom';
import './Signup.css';

import Aos from 'aos';
import "aos/dist/aos.css";

import formatForm from '../../../service/Form/createForm.js';
import Alert from '@material-ui/lab/Alert';

import { useAuth } from "../../../context/AuthContext";

// The Form Items that would be mapped into a Form
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

// The use style for the signUp Page
const useStyles = makeStyles((theme) => ({
    root: {
      '& .MuiTextField-root': {
        margin: theme.spacing(1),
        width: '100%',
        height: '100%'
      },
    },
  }));

  // Represents the sign up page of the App
function Signup() {

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [isChecked, setChecked] = useState(false);

    const initialValue = {
        email: '',
        userName: '',
        password: '',
        passwordConf: '',
    };

    const [formValues, setFormValues] = useState(initialValue);
    const classes = useStyles();

    const { signup } = useAuth();
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

    // Check if the entries are valid before procedding
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

        if (!isChecked) {
            setError('Please check the requirement!');
            return false;
        }

        return true;
    }

    // If the entries submitted are valid, creates an account with the email and password given.
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
                // If any error happens, its message will be displayed to the user.
                setError(err.message);
            }
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
            <FormControlLabel
                control={<Checkbox 
                    checked={isChecked}
                    onChange={()=>{setChecked(!isChecked)}}
                    name="checkedA" />}
                label="I confirm that I am of the Age of 18 or above."
            />
            <div>
                By creating this account, you agree to our <Link to="/terms-of-service" target="_blank" rel="noopener noreferrer">terms of service</Link> <br/>
                here that includes specifications on our privacy policies <br />
                and how we will collect and use data about you.
            <br />
            <Button id="signup-submit" variant="contained" color="secondary" onClick={handleSubmit} disabled={loading}>
                 Submit
            </Button>
            </div>
            </div>
            <div className="background-container">
                <img src={neon} alt={'Background of the App'} />
            </div>
        </div>
    )
};

export default Signup;