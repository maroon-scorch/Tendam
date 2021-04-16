import Card from './Card.js';
import './Hand.css';

// Displays cards.
function Hand(props) {
    function cards() {
        let cardSet = Array.from(props.cards)
        return (cardSet.map(x =>
            <div className='column' >
                <Card text={x} />
            </div>))
    }
    return (
        <div className="row">
            {cards()}
        </div>
    );
}

export default Hand;