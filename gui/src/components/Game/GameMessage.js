import './GameMessge.css';

// Displays game messages.
function GameMessage(props) {
    const styleText = {
        fontSize: 30
    }
    if (props.text !== "" && props.hidden === false) {
        return(
            <div className="gameMessage" style={styleText}>{props.text}</div>
        );
    } else {
        return null;
    }
}

export default GameMessage;