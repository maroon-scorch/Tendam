import React, {useState, useEffect} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { Button, TextField, Typography } from '@material-ui/core';
import neon from './neon.png';

import { Link, useHistory } from 'react-router-dom';
import './Login.css';

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
    },
    {
        type: 'password',
        label: "Password",
        className: 'signup-field',
        key: "password"
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

function Login() {

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');

    const initialValue = {
        email: '',
        password: ''
    };

    const [formValues, setFormValues] = useState(initialValue);
    const classes = useStyles();

    const { login } = useAuth();
    const history = useHistory();

    useEffect(() => {
        Aos.init({});
    }, []);

    function entriesAreValid() {
        return true;
    }

    async function handleSubmit(e) {
        e.preventDefault();
        console.log('Logging In');
        console.log(formValues);

        setError('');

        if (entriesAreValid()) {
            setLoading(true);
            try {
                await login(formValues.email, formValues.password);
                history.push('/dashboard');
            } catch(err) {
                setError(err.message);
            }
        }

        setLoading(false);
    }

    return (
        <div className="login-form-container">
            <div className="login-form" data-aos="fade-up" data-aos-duration="2250">
            <Typography variant="h3" className="signup-title">Log In</Typography>
            {error !== '' ? <Alert severity="warning">{error}</Alert> : <div />}
            <form className={classes.root} autoComplete="off">
                {formatForm(formItems, formValues, setFormValues)}
                <Button id="login-submit" variant="contained" color="secondary" onClick={handleSubmit} disabled={loading}>
                 Submit
                </Button>
            </form>
            <br />
            <div>
                <Link to="/forgot-password">I forgot my password.</Link>
            </div>
            <div>
                Haven't made an account yet? Sign up <Link to="/signup">here</Link>!
            </div>
            </div>
            <div className="background-container">
                <img src={neon} />
            </div>
        </div>
    )
};

export default Login;