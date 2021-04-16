import {makeStyles} from "@material-ui/core";
import {useAuth} from "../../context/AuthContext";
import UserList from "./UserList";
import {useDatabase} from "../../context/DatabaseContext";
import {useEffect, useState} from "react";
import ChatPanel from "./ChatPanel";
import {useHistory} from "react-router-dom";
import {ChatProvider} from "../../context/ChatContext";

const useStyles = makeStyles(theme => ({
    outerRoot: {
        position: 'relative',
        flexGrow: 1,
        flexShrink: 1,
        flexBasis: 1,
    },
    root: {
        display: 'flex',
        flexDirection: 'row',
        position: 'absolute',
        top: 0,
        bottom: 0,
        left: 0,
        right: 0,
    },
    userList: {
        width: '25%',
        backgroundColor: '#00000020',
    },
    messagingArea: {
        flexGrow: 4,
        flexShrink: 4,
        flexBasis: 'auto',
        overflowY: 'scroll',
    }
}));

function Chat() {
    const {currentUser} = useAuth();
    const {getEntryData} = useDatabase();
    const history = useHistory();

    // Data-ful
    const [sender, setSender] = useState();
    const [recipientId, setRecipientId] = useState();

    useEffect(async () => {
        const self = await getEntryData(currentUser.uid);
        setSender(self);

        try {
            const id = history.location.hash.substring(1)
            || (history.location.state && history.location.state.data.id);
            if (self.matches.includes(id)) {
                try {
                    const user = await getEntryData(id);
                    setRecipientId(user.id);
                } catch (e) {
                    console.error(`${id} is not a user id`)
                }
            }
        } catch (e) {
            console.error(e);
        }
    }, []);

    // TODO this is somewhat unoptimized.
    //  There will be a second load as the panel loads the user's data.
    function openUser(user) {
        setRecipientId(user.id);
    }

    const classes = useStyles();

    return (
        <div className={classes.outerRoot}>
            <div className={classes.root}>
                <ChatProvider>
                    {sender
                    ? <UserList users={sender.matches} onOpenUser={openUser}
                        selectedUser={recipientId}
                        className={classes.userList}/>
                    : <div className={classes.userList}/>}
                    {recipientId
                    ? <ChatPanel to={recipientId} className={classes.messagingArea} />
                    : <div className={classes.messagingArea}></div>}
                </ChatProvider>
            </div>
        </div>
    );
}

export default Chat;