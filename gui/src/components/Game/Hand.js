import Card from './Card.js';
import './Hand.css';

function Hand(props) {
    // if (props.text !== "") {
    //     // We only want game message to be rendered if there is a message to display!
    //     return(
    //         <div className={"gameMessage"}>{props.text}</div>
    //     );
    // } else {
    //     return null;
    // }

//
    function cards() {
        let cardSet = Array.from(props.cards)
        return (cardSet.map(x =>
            <div className='column' >
                <Card text={x} />
            </div>))
    }
    return (
        <div className="row">
            {/*<div className='column'>*/}
            {/*    <Card text={0} />*/}
            {/*</div>*/}
            {/*<div className='column'>*/}
            {/*    <Card text={0} />*/}
            {/*</div>*/}
            {/*<div className='column'>*/}
            {/*    <Card text={0} />*/}
            {/*</div>*/}
            {cards()}
        </div>
    );
}

export default Hand;