import React, { useState, useEffect } from 'react'
import { Button, Typography } from '@material-ui/core';
import AccessTimeIcon from '@material-ui/icons/AccessTime';
import Moment from 'react-moment';

import { useAuth } from "../../context/AuthContext.js";
import { useDatabase } from "../../context/DatabaseContext";
import { Link, useHistory } from 'react-router-dom';
import './Match.css';

function Match() {
    const { currentUser } = useAuth();
    const { listenNotification, getEntryData} = useDatabase();
    const history = useHistory();
    const [notification, setNotification] = useState([]);
    
    // async function handleSnapshot(snapshot) {
    //     let changes = snapshot.docChanges();
    //     let base = [];
    //     await changes.forEach(async (item) => {
    //         base.push(item.doc.data());
    //         // let notifData = item.doc.data();
    //         // let userData = await getEntryData(notifData.user);
    //         // notifData['name'] = userData['name'];
    //         // base.push(notifData);
    //     });
    //     console.log(base);
    //     setNotification(base);
    // }

    function handleSnapshot(snapshot) {
        let changes = snapshot.docChanges();
        let base = notification;
        let idList = base.map((item) => item.id);

        changes.forEach((item) => {
            let notifID = item.doc.id;
            let notifData = item.doc.data();
            notifData['id'] = notifID;
            
            if (item.type == 'added' && !idList.includes(notifID)) {
                base.unshift(notifData);
            } else if (item.type == 'removed') {
                base = base.filter(data => data.id != notifID);
            }

            // let notifData = item.doc.data();
            // // let userData = await getEntryData(notifData.user);
            // // notifData['name'] = userData['name'];
            // base.push(notifData);
        });
        // console.log(base);
        setNotification([...base]);
    }



    function showNotif() {
        console.log(notification);
    }

    async function visitProfile(userID) {
        console.log(userID);
        try {
            let userData = await getEntryData(userID);
            console.log(userData);
            history.push({
            pathname: userData['name'].length == 0 ? `/profile/${userData['id']}` : `/profile/${userData['name']}`,
            state: {
                data: userData
            }
            });
        } catch (err) {
            console.log(err);
        }
        

    }

    // useEffect(() => {
    //     // const unsubscribe = listenNotification(currentUser.uid, handleSnapshot);
    //     // return () => unsubscribe();
    // }, []);

    listenNotification(currentUser.uid, handleSnapshot);

    return (
        <div className='match-container'>
            <div className='matching-template'>
            <Typography variant="h3" className="match-title">Matched!</Typography>
            <ul className="match-notification-list">
                {notification.map((item, index) => {
                    return (<li key={index} className="notification-item" onClick={() => visitProfile(item.user)}>
                        <div>
                           <span>
                               <Button variant="outlined" size="large">
                                <AccessTimeIcon color="outlined"/><Moment fromNow>{item.time.toDate()}</Moment>
                               </Button>
                            </span>
                        </div>
                        <div className="notification-message">
                        <Button variant="contained" color="secondary" size="medium">
                            From {item.name}: {item.content}
                        </Button>
                        </div>
                    </li>);
                })}
            </ul>
            <Button onClick={showNotif}>Test</Button>
            {/* {JSON.stringify(notification)} */}
            </div>
        </div>
    );
}

export default Match
