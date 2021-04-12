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
        wordBreak: 'break-all',
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
        setMessages([]);
        const participants = {};
        participants[currentUser.uid] = await getUser(currentUser.uid);
        participants[to] = await getUser(to);
        setParticipants(participants);
        setThread(await getThread(to));
    }, [to]);

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

    function Message({message}) {
        const participant = participants[message.sender];
        if (!participant) {
            return <ListItem></ListItem>
        }
        return (
            <ListItem className={classes.message}>
                {/* TODO substitute avatar */}
                <ListItemAvatar><Avatar src={participants[message.sender].profilePicture}/></ListItemAvatar>
                <ListItemText>{participants[message.sender].name || 'Tendam User'}: {message.data}</ListItemText>
            </ListItem>
        );
    }

    return (
        <div className={`${classes.root} ${className}`}>
            <List className={classes.messageList}>
                {messages && messages.length ? messages.map(message => (
                    <Message message={message} />
                )) : undefined}
            </List>
            <ChatBar onSubmit={sendTextMessage} />
        </div>
    );
}

export default ChatPanel;