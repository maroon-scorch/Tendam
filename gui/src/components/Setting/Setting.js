import React, {useEffect, useState} from 'react';
import {Button, Typography, TextField} from '@material-ui/core';
import './Setting.css';
import {useAuth} from "../../context/AuthContext";
import {useModal} from "../../context/ModalContext";
import {useDatabase} from "../../context/DatabaseContext";

import FinalDecision from './FinalDecision';
import createForm from "../../service/Form/createForm";

function getSetting(settings, defaultValue, ...paths) {
    if (settings === undefined) {
        return defaultValue;
    }
    if (paths.length === 0) {
        return settings;
    }
    return getSetting(settings[paths[0]], defaultValue, ...paths.slice(1));
}

function setSetting(settings, newValue, paths) {
    const firstPath = paths[0];
    if (paths.length === 1) {
        settings[firstPath] = newValue;
        return;
    }
    if (settings[firstPath] === undefined) {
        settings[firstPath] = {};
    }
    setSetting(settings[firstPath], newValue, paths.slice(1));
    return settings;
}

function Setting() {
    const {currentUser} = useAuth();
    const {getEntryData, setEntry} = useDatabase();
    const [show, setShow] = useState(true);
    const {close, open, setContent} = useModal();

    const [userData, setUserData] = useState();
    const [changedUserData, setChangedUserData] = useState();

    useEffect(() => {
        getEntryData(currentUser.uid).then(setUserData);
    }, []);

    function handleYes() {
        setShow(false);
        close();
    }

    function displayDelete() {
        open();
        setContent(
            <div>
                <Typography variant="h4">Are you sure you want to delete your account?</Typography>
                <Typography variant="h6">Your account wouldn't be recoverable after you have deleted it,
                    and making a new account with the same email won't restore your previous data.</Typography>
                <Button variant="contained" color="primary" onClick={close}>No</Button>
                <Button variant="contained" color="secondary" onClick={handleYes}>Yes</Button>
            </div>
        );
    }

    function changeSetting(event) {
        console.log(event);
        setChangedUserData(data => {
            data = data || userData;
            data = Object.assign({}, data);
            data.settings = data.settings || {};
            for (const key of Object.keys(event)) {
                setSetting(data.settings, event[key], key.split('.'));
            }
            console.log(data);
            return data;
        });
    }

    useEffect(() => {
        console.log(changedUserData);
        if (changedUserData === undefined) {
            return;
        }
        setEntry(currentUser.uid, changedUserData);
    }, [changedUserData]);

    return (
        <div className="setting-container">
            <div className="setting">
                <Typography variant="h1">Settings</Typography>
                {show ? <>
                        <Typography variant="h2">Account</Typography>
                        <div>
                            <Button variant="contained" color="primary">Reset Email:</Button>
                            <TextField label="Email" value={currentUser.email} disabled={true}/>
                        </div>
                        <div>
                            <Button variant="contained" color="primary">Reset Password:</Button>
                            <TextField label="Password" value="********" disabled={true}/>
                        </div>
                        <div>
                            <Button variant="contained" color="secondary" onClick={displayDelete}>Delete Your
                                Account</Button>
                        </div>
                        {userData ? createForm([
                                {
                                    type: 'label',
                                    textType: 'h2',
                                    label: 'Matching',
                                },
                                {
                                    type: 'switch',
                                    textType: 'h2',
                                    label: 'Hidden (will not receive new matches)',
                                    key: 'matching.hidden',
                                },
                            ], {
                                'matching.hidden': getSetting(userData.settings, false, 'matching', 'hidden'),
                            }, changeSetting)
                            : undefined}
                    </>
                    : <FinalDecision/>
                }
            </div>

        </div>
    )
}

export default Setting;