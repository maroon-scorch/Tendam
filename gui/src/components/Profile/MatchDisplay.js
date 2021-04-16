import React from 'react';
import { Button, Typography } from '@material-ui/core';
import HighlightOffIcon from '@material-ui/icons/HighlightOff';

import { useAuth } from "../../context/AuthContext";
import { useDatabase } from "../../context/DatabaseContext";
import { useModal } from "../../context/ModalContext";

import { Link } from 'react-router-dom';
import BlankProfile from './blank-profile.png';

// The Toggle Button that displays the list of matches a user has.
function MatchDisplay({ matches }) {
    const { currentUser } = useAuth();
    const { getFile, getEntryData } = useDatabase();
    const { close, open, setContent } = useModal();

    async function displayMatch() {
        let matchItems = [];

        for (let ind in matches) {
            let userID = matches[ind];
            let userData = await getEntryData(userID);
            let userImage = await getFile(userID, '/profilePicture').then((imageFile) => {
                return imageFile
            }).catch((error) => {
                return BlankProfile;
            });

            matchItems.push({
                name: userData.name,
                picture: userImage,
                data: userData
            });
        }

        console.log(matchItems);

        let matchDisplay = matchItems.map((item, index) => {
            let isSelf = item.data.id === currentUser.uid;
            return(<Link to={{  
                pathname: isSelf ? '/profile' : `/profile/${item.name}`,
                state: {
                    data: item["data"] 
                    }}}>
                <div key={index} className="modal-profile-matches" onClick={close}>
                <div className="modal-matches-left">
                    <div className="modal-picture-wrapper">
                        <img className="modal-picture" src={item.picture} alt="User Avatar" />
                    </div>
                </div>
                <Typography variant="h4">{isSelf ? item.name + " (You)" : item.name}</Typography>
                <div className="modal-matches-right">
                    <HighlightOffIcon className="match-delete" color="secondary" fontSize="large" />
                </div>
                </div></Link>); 
        });

        open();
        setContent(matchDisplay);
    }

    return (
        <Button variant="contained" color="secondary" onClick={displayMatch}>
            <Typography variant="h5">Matches: {matches.length}</Typography>
        </Button>
    )
}

export default MatchDisplay
