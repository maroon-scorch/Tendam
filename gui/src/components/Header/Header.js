import React, {useState, useEffect} from 'react';
import { Link, useHistory } from 'react-router-dom';
// import headerItems from './HeaderItems.js';
import { Typography } from '@material-ui/core';
import { useAuth } from "../../context/AuthContext.js";
import { useDatabase } from "../../context/DatabaseContext";

import { Button, Tooltip } from '@material-ui/core';
import logo from './tendam-logo.png';
import './Header.css';

function Header() {
    const { currentUser, logout } = useAuth();
    const { getFile } = useDatabase();
    const [profilePic, setProfilePic] = useState("blank-profile.png");
    const history = useHistory();

    useEffect(async () => {
        if (currentUser !== null) {
            getFile(currentUser.uid, '/profilePicture').then((imageFile) => {
                setProfilePic(imageFile);
            }).catch((error) => {
                // console.log("Error getting document:", error);
            });
        }
    }, []);

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
            title: <Button variant="outlined" size="large">MATCH NOTIFICATION</Button>,
            address: '/match'
        },
        {
            title: <Button variant="outlined" size="large">CHAT</Button>,
            address: '/message'
        },
        {
            title: <Button variant="outlined" size="large">PROFILE</Button>,
            address: '/profile'
        }
        // ,
        // {
        //     title: <Button variant="outlined" size="large">SETTING</Button>,
        //     address: '/setting'
        // }
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

    function displayNotification() {

    }

    return (
        <div className={"header"}>
            <nav className={"header-bar"}>
                <Link to="/" className="header-logo">
                <Typography variant="h3">TƎNDAM</Typography>
                <img src={logo} className="header-logo-img" alt="Tendam Logo"/>
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
                <Link to="/match">
                    <Tooltip title={<h1>View Notifications</h1>}>
                        <div className="header-avatar-container" onClick={displayNotification}
                        style={{display: currentUser ? 'inline' : 'none'}}>
                                <img src={profilePic} alt='Header Avatar' className="header-avatar" />
                        </div>
                    </Tooltip>
                </Link>
                <div className="red-dot-notification" style={{display: currentUser ? 'inline' : 'none'}}></div>
            </nav>
        </div>
    )
}

export default Header;
