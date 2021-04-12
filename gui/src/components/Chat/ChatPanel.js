import {Avatar, Button, List, ListItem, ListItemAvatar, ListItemText, makeStyles, TextField} from "@material-ui/core";
import {useEffect, useRef, useState} from "react";
import {useAuth} from "../../context/AuthContext";
import {useChat} from "../../context/ChatContext";
import { useDatabase } from "../../context/DatabaseContext";
import ChatBar from "./ChatBar";

const MESSAGE_CHUNK_SIZE = 20;

const useStyles = makeStyles(theme => ({
    root: {
        display: 'flex',
        flexDirection: 'column',
        padding: theme.spacing(1),
    },
    messageList: {
        height: '100%',
        overflowY: 'scroll',
    },
}));

function ChatPanel({to, className}) {
    const {currentUser} = useAuth();
    const {getThread, getUser} = useChat();
    const [thread, setThread] = useState();

    const [messages, setMessages] = useState([]);

    const [participants, setParticipants] = useState();

    useEffect(async () => {
        const participants = {};
        participants[currentUser.uid] = await getUser(currentUser.uid);
        participants[to] = await getUser(to);
        setParticipants(participants)
        setThread(await getThread(to));
    }, []);

    useEffect(() => {
        if (!thread) return;
        //thread.getAllMessages().then(messages => setMessages(messages));
        thread.onReceiveMessages(setMessages);
    }, [thread]);

    function sendTextMessage(text) {
        const messageObj = {
            type: 'message',
            sender: currentUser.uid,
            data: text,
        };
        thread.sendMessage(messageObj);
    }

    const classes = useStyles();

    return (
        <div className={`${classes.root} ${className}`}>
            <List className={classes.messageList}>
                {messages.map(message => (
                    <ListItem>
                        {/* TODO substitute avatar */}
                        <ListItemAvatar><Avatar src={participants[message.sender].profilePicture}/></ListItemAvatar>
                        <ListItemText>{participants[message.sender].name || 'Tendam User'}: {message.data}</ListItemText>
                    </ListItem>
                ))}
            </List>
            <ChatBar onSubmit={sendTextMessage} />
        </div>
    );
}

export default ChatPanel;