import React, {useState} from 'react';
import { Link, useHistory } from 'react-router-dom';
// import headerItems from './HeaderItems.js';
import { Typography } from '@material-ui/core';
import { useAuth } from "../../context/AuthContext.js";
import Button from '@material-ui/core/Button';
import './Header.css';

function Header() {
    const { currentUser, logout } = useAuth();
    const history = useHistory();

    const headerItems = [
        {
            title: <Button variant="outlined" size="large">DASHBOARD</Button>,
            address: '/dashboard'
        },
        {
            title: <Button variant="outlined" size="large">QUIZZES</Button>,
            address: '/quizzes'
        },
        {
            title: <Button variant="outlined" size="large">GAMES</Button>,
            address: '/games'
        },
        {
            title: <Button variant="outlined" size="large">MATCH!</Button>,
            address: '/match'
        },
        {
            title: <Button variant="outlined" size="large">PROFILE</Button>,
            address: '/profile'
        },
        {
            title: <Button variant="outlined" size="large">SETTING</Button>,
            address: '/setting'
        }
    ];

    async function handleLogOut(e) {
        e.preventDefault();
        try {
            await logout();
            history.push("/");
          } catch {
            alert('Failed to log out.');
        }
    }

    function handleLogIn(e) {
        e.preventDefault();
        history.push('/login');
    }

    return (
        <div className={"header"}>
            <nav className={"header-bar"}>
                <Link to="/" className="header-logo">
                <Typography variant="h3">TƎNDAM</Typography>
                    <img src="./logo.png" className="header-logo-img"/>
                </Link>
                <ul className="header-menu">
                {headerItems.map((item, index) => {
                     return (<li className='header-items' key={index}>
                         <Link to={item['address']} className='header-links'>
                             {item['title']}
                         </Link>
                     </li>)
                 })}
                 <li className="header-items" key={headerItems.length}>
                     {
                        currentUser ? <Button className="header-auth" variant="contained"
                        color="secondary" size="large" onClick={handleLogOut}>
                            LOG OUT</Button> : <Button className="header-auth" 
                            variant="contained" color="secondary" size="large" onClick={handleLogIn}>LOG IN</Button>
                     }
                 </li>
                </ul>
            </nav>
        </div>
    )
}

export default Header;
