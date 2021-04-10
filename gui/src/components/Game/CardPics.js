
    let images = new Map();
    let c = 52;
    for (let i = 0; i < c; i++) {
        let card = "";
        let cardNumber = i % 13 + 1;
        if (cardNumber === 1) {
            card += "A";
        } else if (cardNumber === 11) {
            card += "J";
        } else if (cardNumber === 12) {
            card += "Q";
        } else if (cardNumber === 13) {
            card += "K";
        } else {
            card += cardNumber;
        }

        // Spades
        if (i < 13) {
            card += "S";
        }
        // Hearts
        else if (i < 26) {
            card += "H";
        }
        // Clubs
        else if (i < 39) {
            card += "C";
        }
        // Diamonds
        else {
            card += "D";
        }
        images.set(i, 'CardImages/' + card + '.jpg');
    }
    console.log(images);


export default images;