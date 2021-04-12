import { Button, makeStyles, TextField } from "@material-ui/core";
import { useRef, useState } from "react";

const useStyles = makeStyles(theme => ({
    root: {
        display: 'flex',
        flexDirection: 'row',
    },
    input: {
        flexGrow: 1,
        flexShrink: 1,
        flexBasis: 'auto',
    },
    submit: {
    }
}))

function ChatBar({onSubmit}) {
    const [text, setText] = useState('');
    const textRef = useRef();

    const classes = useStyles();

    function changeText(event) {
        setText(event.target.value || '');
    }

    function submit(event) {
        event.preventDefault();
        if (text.length === 0) {
            return;
        }
        if (onSubmit) {
            onSubmit.call(onSubmit, text);
        }
        textRef.current.value = '';
    }

    return (
        <form onSubmit={submit} className={classes.root}>
            <TextField onChange={changeText} inputRef={textRef}
                className={classes.input} />
            <Button type="submit" className={classes.submit}>Send</Button>
        </form>
    );
}

export default ChatBar;