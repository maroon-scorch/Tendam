import './GameMessge.css';

function GameMessage(props) {
    const styleText = {
        fontSize: 30
    }
    if (props.text !== "" && props.hidden === false) {
        // We only want game message to be rendered if there is a message to display!
        return(
            <div className="gameMessage" style={styleText}>{props.text}</div>
        );
    } else {
        return null;
    }
}

export default GameMessage;