import React from 'react';
import { useAuth } from "../context/AuthContext"
import { Route, Redirect } from 'react-router-dom';

// The opposite of Private Routes
// where as Private Route prevents unlogged in users from
// accessing certain pages, Public Route prevents logged-in users
// from accessing certain pages by redirecting them to the home page.

function PublicRoute({ component: Component, ...rest }) {
    const { currentUser } = useAuth();
    return (<Route
        {...rest}
        render={props => {
            return currentUser ? <Redirect to="/" /> : <Component {...props} />
        }}
    ></Route>);
};

export default PublicRoute;
