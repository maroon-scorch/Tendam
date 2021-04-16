import React from 'react';
import { useAuth } from "../context/AuthContext"
import { Route, Redirect } from 'react-router-dom';

// Procedures and Code Learned from Web Dev Simplified's tutorial:
// https://github.com/WebDevSimplified/React-Firebase-Auth

// A Private Route prevents the user from accessing the page it is directed to
// unless the user is logged in.
function PrivateRoute({ component: Component, ...rest }) {
    const { currentUser } = useAuth();
    return (<Route
        {...rest}
        render={props => {
            return currentUser ? <Component {...props} /> : <Redirect to="/login" />
        }}
    ></Route>);
};

export default PrivateRoute;
