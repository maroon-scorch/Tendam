import {Avatar, List, ListItem, ListItemAvatar, makeStyles, Typography} from "@material-ui/core";
import { useEffect, useState } from "react";
import { useChat } from "../../context/ChatContext";

const useStyles = makeStyles(theme => ({
    root: {
    },
}));

function UserList({users, onOpenUser, selectedUser, className}) {
    const {getUser} = useChat();
    const [userProfiles, setUserProfiles] = useState([]);
    const classes = useStyles();

    useEffect(() => {
        Promise.all(users.map(getUser)).then(setUserProfiles);
    }, []);

    function openUser(profile) {
        onOpenUser(profile);
    }

    return (
        <List className={`${className} ${classes.root}`}>
            {userProfiles.map(profile => (
                <ListItem button onClick={() => openUser(profile)}
                selected={profile.id === selectedUser} >
                    <ListItemAvatar><Avatar src={profile.profilePicture}/></ListItemAvatar>
                    <Typography>{profile.name || 'Tendam User'}</Typography>
                </ListItem>
            ))}
        </List>
    );
}

export default UserList;