import cardBack from './depositphotos_92457980-stock-illustration-playing-cards-back.jpeg';
import './Card.css';
import images from "./CardPics";

function Card(props) {
    if (props.text === -1) {
        return (
            <div className="HiddenCard">
                <img src={cardBack}
                    alt={"plz work"} className={"plz work"} height={200} width={150} />
            </div>)
    }
    else {
        const path = images.get(props.text);
        return (
            <div className="Card">
                <img src={process.env.PUBLIC_URL + path}
                    alt={"plz work"} className={"plz work"} height={200} width={150} />
            </div>)
    }
}

export default Card;