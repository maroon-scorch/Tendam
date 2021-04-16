import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import GameMessage from "./GameMessage";
import './BlackJack.css';
import Hand from "./Hand.js";
import { useAuth } from "../../context/AuthContext";
import { useDatabase } from "../../context/DatabaseContext";

let cardsOfDealer = [];
let cardValuesOfDealer = [];
let realDealerHand = [];
let dealerHand = [-1, -1];
function BlackJack() {
    const { currentUser } = useAuth();
    const { getGameData, setGameData } = useDatabase();

    // Sum of all values in a deck of card 4(1 + 2 + ... + 13).
    const sumOfValues = 364;

    const [deck, setDeck] = useState([]);
    const [gameEnded, setGameEnded] = useState(true);

    const [whoWon, setWhoWon] = useState("");

    const [cardsOfPlayer, setCardsOfPlayer] = useState([]);
    const [cardValuesOfPlayer, setCardValuesOfPlayer] = useState([]);

    // -1 refers to showing the back of a card and not the face.
    let [playerHand, setPlayerHand] = useState([-1, -1]);
    const [highestRiskScore, setHighestRiskScore] = useState(-1);

    // Sets up board after each round.
    useEffect(() => {
        if (deck.length === 52) {
            setUpBoard();
        }
    }, [deck])

    // User presses play; resets values to default.
    async function play() {
        // Resets cards to all 52 cards
        setDeck(Array.from(Array(52).keys()));
        setWhoWon("");

        setGameEnded(false);
        setHighestRiskScore(0);

        // Gets user data so risk propensity score can be updated and set later
        let currentGameData = await getGameData(currentUser.uid, "BlackJack");
        let currentNumGamesPlayed = currentGameData['blackjack-games-played'];
        setGameData(currentUser.uid, "BlackJack", "blackjack-games-played", currentNumGamesPlayed + 1);
    }

    // Sets up board.
    function setUpBoard() {
        // Deals 2 cards to dealer and player.
        let dealerCardsAndValues = setUpCards(2);
        let playerCardsAndValues = setUpCards(2);

        cardsOfDealer = dealerCardsAndValues[0];
        cardValuesOfDealer = dealerCardsAndValues[1];
        realDealerHand = dealerCardsAndValues[2];
        dealerHand = [dealerCardsAndValues[2][0], -1];

        setCardsOfPlayer(playerCardsAndValues[0]);
        setCardValuesOfPlayer(playerCardsAndValues[1]);
        setPlayerHand(playerCardsAndValues[2]);
    }

    // Deals numCards number of cards.
    function setUpCards(numCards) {
        let cardsToAdd = [];
        let cardsOfPerson = [];
        let cardsValuesOfPerson = [];
        let chosenKeys = [];

        // Gets numCards number of cards randomly
        for (let i = 0; i < numCards; i++) {
            let randIndex = Math.floor(Math.random() * deck.length);
            let chosenCardKey = deck.splice(randIndex, 1);
            setDeck(deck);
            let chosenCard = cardFromKey(chosenCardKey);

            cardsValuesOfPerson.push(chosenCardKey % 13 + 1);
            cardsOfPerson.push(chosenCard);
            chosenKeys.push(parseInt(chosenCardKey));
        }

        cardsToAdd.push(cardsOfPerson);
        cardsToAdd.push(cardsValuesOfPerson);
        cardsToAdd.push(chosenKeys);

        return cardsToAdd;
    }

    // Outputs a card from a key; in order of Spades, Hearts, Clubs, Diamonds
    // Eg) key = 0 => card = Ace Spade, while key = 13 => card = Ace Hearts
    function cardFromKey(key) {
        console.log(key);
        let card = "";

        // Gets the value of the card.
        let cardNumber = key % 13 + 1;
        if (cardNumber === 1) {
            card += "Ace ";
        } else if (cardNumber === 11) {
            card += "Jack ";
        } else if (cardNumber === 12) {
            card += "Queen ";
        } else if (cardNumber === 13) {
            card += "King ";
        } else {
            card += cardNumber + " ";
        }

        // Gets the suit of the card.
        // Spades
        if (key < 13) {
            card += "Spades ";
        }
        // Hearts
        else if (key < 26) {
            card += "Hearts ";
        }
        // Clubs
        else if (key < 39) {
            card += "Clubs ";
        }
        // Diamonds
        else {
            card += "Diamonds ";
        }

        return card;
    }

    // Checks whether player busts
    useEffect(() => {
        checkBust();
    }, [cardValuesOfPlayer])

    // Function to check whether player has busted
    function checkBust() {
        const playerScore = calculateScore(cardValuesOfPlayer);
        if (playerScore > 21) {
            setAverageRiskPropensityScore(null);
            setGameEnded(true);
            setWhoWon("You Lose :(");
        }
    }

    // Function that calculates score of given list of cards.
    function calculateScore(cards) {
        let possibleScores = [0];

        for (let i = 0; i < cards.length; i++) {
            // If statement to check whether we have Jack, Queen, or King.
            if (cards[i] === 11 || cards[i] === 12 || cards[i] === 13) {
                for (let j = 0; j < possibleScores.length; j++) {
                    possibleScores[j] = possibleScores[j] + 10;
                }
            } 
            // Checks whether we have Ace.
            else if (cards[i] === 1) {
                let tentativeScores = [];
                for (let j = 0; j < possibleScores.length; j++) {
                    tentativeScores.push(possibleScores[j] + 1);
                    tentativeScores.push(possibleScores[j] + 11);
                }
                possibleScores = tentativeScores;
            } else {
                for (let j = 0; j < possibleScores.length; j++) {
                    possibleScores[j] = possibleScores[j] + cards[i];
                }
            }
        }

        // Finds the best score from all possible scores.
        let score = bestScore(possibleScores);
        return score;
    }

    // Finds the best score (since some values like Ace can be both 1 and 11).
    function bestScore(scores) {
        if (scores.length === 0) return 0;
        let best = scores[0];
        for (let i = 0; i < scores.length; i++) {
            if (scores[i] > best && scores[i] <= 21) {
                best = scores[i];
            }
        }
        return best;
    }

    // Function to hit for player.
    function hit() {
        if (gameEnded) {
            setWhoWon("Press play first");
        } else {
            let playerCardsAndValues = setUpCards(1);
            setCardsOfPlayer(cardsOfPlayer.concat(playerCardsAndValues[0]));
            setCardValuesOfPlayer(cardValuesOfPlayer.concat(playerCardsAndValues[1]));
            console.log(playerCardsAndValues[0][0])
            setPlayerHand(playerHand.concat(playerCardsAndValues[2]));
        }
        setHighestRiskScore(calculateRiskScore());
    }

    // Calculates probability that a new card will make the hand go over 21.
    function calculateRiskScore() {
        const maxBeforeBust = 21 - calculateScore(cardValuesOfPlayer);
        let count = 0;
        for (let i = 0; i < deck.length; i++) {
            let cardValue = deck[i] % 13 + 1;
            // Checking whether we have Jack, Queen, or Kings, since the values are 10.
            if (cardValue === 11 || cardValue === 12 || cardValue === 13) {
                cardValue = 10;
            }
            if (cardValue > maxBeforeBust) {
                count++;
            }
        }
        return 100 * count / deck.length;
    }

    // Sets average risk propensity score for user.
    async function setAverageRiskPropensityScore(riskScore) {
        // Gests user data.
        let currentGameData = await getGameData(currentUser.uid, "BlackJack");
        let currentScore = currentGameData["blackjack-score"];
        let currentNumGamesPlayed = currentGameData["blackjack-games-played"];

        // Updates user data.
        if (riskScore) {
            if (currentNumGamesPlayed !== 0) {
                if (currentScore === -1) {
                    setGameData(currentUser.uid, "BlackJack", "blackjack-score", riskScore);
                } else {
                    let newScore = (currentScore * (currentNumGamesPlayed - 1) + riskScore) / (currentNumGamesPlayed);
                    setGameData(currentUser.uid, "BlackJack", "blackjack-score", newScore);
                }
            }
        } else {
            if (currentNumGamesPlayed !== 0) {
                if (currentScore === -1) {
                    setGameData(currentUser.uid, "BlackJack", "blackjack-score", highestRiskScore);
                } else {
                    let newScore = (currentScore * (currentNumGamesPlayed - 1) + highestRiskScore) / (currentNumGamesPlayed);
                    setGameData(currentUser.uid, "BlackJack", "blackjack-score", newScore);
                }
            }
        }
    }

    // Checks game results.
    function checkGameResults() {
        const dealer = calculateScore(cardValuesOfDealer);
        const player = calculateScore(cardValuesOfPlayer);
        setGameEnded(true);

        if (player > 21 || (dealer < 22 && dealer >= player)) {
            setWhoWon("You Lose :(");
        } else {
            setWhoWon("You Win!");
        }
    }

    // Function for player to stand.
    function stand() {
        if (gameEnded) {
            setWhoWon("Press play first");
        } else {
            let riskScore = calculateRiskScore();
            setAverageRiskPropensityScore(riskScore);
            dealerHand = realDealerHand;
            // Dealer deals for themself until they reach 17 or above.
            while (calculateScore(cardValuesOfDealer) < 17) {
                let dealerCardsAndValues = setUpCards(1);
                cardsOfDealer = cardsOfDealer.concat(dealerCardsAndValues[0]);
                cardValuesOfDealer = cardValuesOfDealer.concat(dealerCardsAndValues[1]);
                dealerHand = dealerHand.concat(dealerCardsAndValues[2]);
            }
            checkGameResults();
        }
    }

    // Styling of buttons.
    const Button = styled.button`
  background-color: GoldenRod;
  color: black;
  font-size: 20px;
  padding: 10px 60px;
  border-radius: 5px;
  margin: 10px 0px;
  cursor: pointer;
`;

    return (
        <div className="body">
            <div className="cards">
                <GameMessage text={"Dealer's cards: "} hidden={gameEnded} />
                <GameMessage text={cardsOfDealer} />
                <Hand cards={dealerHand} />
                <br />
                <GameMessage text={"Player's cards: "} hidden={gameEnded} />
                <GameMessage text={"Player total " + calculateScore(cardValuesOfPlayer)} hidden={gameEnded} />
                <Hand cards={playerHand} />
            </div>
            <div className="buttons">
                <Button onClick={play} hidden={!gameEnded}>Play</Button>
                <br />
                <Button
                    onClick={hit} hidden={gameEnded}>Hit</Button>
                <br />
                <Button onClick={stand} hidden={gameEnded}>Stand</Button>
                <br />
                <GameMessage text={whoWon} hidden={!gameEnded} />
            </div>
        </div>

    )
}


export default BlackJack;
