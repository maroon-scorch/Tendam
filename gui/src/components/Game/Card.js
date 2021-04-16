import cardBack from './depositphotos_92457980-stock-illustration-playing-cards-back.jpeg';
import './Card.css';
import images from "./CardPics";

function Card(props) {
    // if (props.text !== "") {
    //     // We only want game message to be rendered if there is a message to display!
    //     return(
    //         <div className={"gameMessage"}>{props.text}</div>
    //     );
    // } else {
    //     return null;
    // }

//
    console.log(props.text);
        if (props.text === -1) {
            return (
            <div className="HiddenCard">
                <img src={cardBack}
                     alt={"plz work"} className={"plz work"} height={200} width={150}/>
                {/*<img src={'https://images.app.goo.gl/meSznKbMDasJ3YFA8'} />*/}
            </div>)
        }
        else {
            console.log(images);
            const path = images.get(props.text);
            console.log(path);
            return (
                <div className="Card">
                    <img src={process.env.PUBLIC_URL + path}
                         alt={"plz work"} className={"plz work"} height={200} width={150}/>
                    {/*<img src={'https://images.app.goo.gl/meSznKbMDasJ3YFA8'} />*/}
                </div>)
            //     let key = props.text;
            //     let cardNumber = key % 13 + 1;
            //     if (cardNumber === 1) {
            //         card += "Ace ";
            //     } else if (cardNumber === 11) {
            //         card += "Jack ";
            //     } else if (cardNumber === 12) {
            //         card += "Queen ";
            //     } else if (cardNumber === 13) {
            //         card += "King ";
            //     } else {
            //         card += cardNumber + " ";
            //     }
            //
            //     // Spades
            //     if (key < 13) {
            //         card += "Spades ";
            //     }
            //     // Hearts
            //     else if (key < 26) {
            //         card += "Hearts ";
            //     }
            //     // Clubs
            //     else if (key < 39) {
            //         card += "Clubs ";
            //     }
            //     // Diamonds
            //     else {
            //         card += "Diamonds ";
            //     }
            // }
        }
}

export default Card;