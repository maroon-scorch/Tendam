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
    //todo change these initial values
    //change to zero?
    let [playerHand, setPlayerHand] = useState([-1, -1]);
    // let [dealerHand, setDealerHand] = useState([-1, -1]);

    const [highestRiskScore, setHighestRiskScore] = useState(-1);
    // const [numGamesPlayed, setNumGamesPlayed] = useState(0);

    useEffect(() => {
        if (deck.length === 52) {
            setUpBoard();
        }
    }, [deck])

    async function play() {
        setDeck(Array.from(Array(52).keys()));
        setWhoWon("");
        setGameEnded(false);
        // let currentScore = await getGameData(currentUser.uid, "blackjack-score");
        setHighestRiskScore(0);
        let currentNumGamesPlayed = await getGameData(currentUser.uid, "blackjack-games-played");
        setGameData(currentUser.uid, "blackjack-games-played", currentNumGamesPlayed + 1);
        // setNumGamesPlayed(currentNumGamesPlayed + 1);
    }

    function setUpBoard() {
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

    function setUpCards(numCards) {
        let cardsToAdd = [];
        let cardsOfPerson = [];
        let cardsValuesOfPerson = [];
        let chosenKeys = [];

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

    // TODO: Currently coded for the purpose of displaying as text message, but should change it
    // to make it easier to find the right graphics to display
    function cardFromKey(key) {
        console.log(key);
        let card = "";

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

    useEffect(() => {
        checkBust();
    }, [cardValuesOfPlayer])

    function checkBust() {
        const player = calculateScore(cardValuesOfPlayer);
        if (player > 21) {
            // update database for user's risk propensity score with final risk propensity score
            setAverageRiskPropensityScore(null);
            setGameEnded(true);
            setWhoWon("You Lose :(");
        }
    }

    function calculateScore(cards) {
        let possibleScores = [0];
        for (let i = 0; i < cards.length; i++) {
            if (cards[i] === 11 || cards[i] === 12 || cards[i] === 13) {
                for (let j = 0; j < possibleScores.length; j++) {
                    possibleScores[j] = possibleScores[j] + 10;
                }
            } else if (cards[i] === 1) {
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
        let score = bestScore(possibleScores);
        return score;
    }

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
        let remainingSum = sumOfValues;
        const maxBeforeBust = 21 - calculateScore(cardValuesOfPlayer);
        let count = 0;
        for (let i = 0; i < deck.length; i++) {
            let cardValue = deck[i] % 13 + 1;
            if (cardValue === 11 || cardValue === 12 || cardValue === 13) {
                cardValue = 10;
            }
            if (cardValue > maxBeforeBust) {
                count++;
            }
        }
        return 100 * count / deck.length;
    }

    async function setAverageRiskPropensityScore(riskScore) {
        let currentScore = await getGameData(currentUser.uid, "blackjack-score");
        let currentNumGamesPlayed = await getGameData(currentUser.uid, "blackjack-games-played");
        if (riskScore) {
            if (currentNumGamesPlayed !== 0) {
                if (currentScore === -1) {
                    setGameData(currentUser.uid, "blackjack-score", riskScore);
                } else {
                    let newScore = (currentScore * (currentNumGamesPlayed - 1) + riskScore) / (currentNumGamesPlayed);
                    setGameData(currentUser.uid, "blackjack-score", newScore);
                }
            }
        } else {
            if (currentNumGamesPlayed !== 0) {
                if (currentScore === -1) {
                    setGameData(currentUser.uid, "blackjack-score", highestRiskScore);
                } else {
                    let newScore = (currentScore * (currentNumGamesPlayed - 1) + highestRiskScore) / (currentNumGamesPlayed);
                    setGameData(currentUser.uid, "blackjack-score", newScore);
                }
            }
        }
    }

    function checkGameResults() {
        const dealer = calculateScore(cardValuesOfDealer);
        const player = calculateScore(cardValuesOfPlayer);
        setGameEnded(true);

        if (player > 21 || (dealer < 22 && dealer >= player)) {
            // update database for user's risk propensity score with final risk propensity score
            setWhoWon("You Lose :(");
        } else {
            //display "you win!"
            // update database for user's risk propensity score with final risk propensity score
            setWhoWon("You Win!");
        }
    }

    function stand() {
        // setPlayerStand(true);
        // setDealerScore(calculateScore(cardValuesOfDealer));
        // setDealerHand(realDealerHand);
        if (gameEnded) {
            setWhoWon("Press play first");
        } else {
            let riskScore = calculateRiskScore();
            setAverageRiskPropensityScore(riskScore);
            dealerHand = realDealerHand;
            while (calculateScore(cardValuesOfDealer) < 17) {
                let dealerCardsAndValues = setUpCards(1);
                cardsOfDealer = cardsOfDealer.concat(dealerCardsAndValues[0]);
                cardValuesOfDealer = cardValuesOfDealer.concat(dealerCardsAndValues[1]);
                // setDealerHand(dealerHand.concat(dealerCardsAndValues[2]));
                // setDealerHand(realDealerHand.concat(dealerCardsAndValues[2]));
                // realDealerHand = realDealerHand.concat(dealerCardsAndValues[2]);
                dealerHand = dealerHand.concat(dealerCardsAndValues[2]);
                // setCardsOfDealer(cardsOfDealer.concat(dealerCardsAndValues[0]));
                // setCardValuesOfDealer(cardValuesOfDealer.concat(dealerCardsAndValues[1]));
            }
            checkGameResults();
        }
    }

    /**
     * Gets key longitude
     * @param lon
     * @param lambda
     * @returns {JSX.Element}
     */
    //function convertKeytoCard(num) {
    //     return Math.floor(lon / lambda) * lambda;

    //}
    // function Button() {
    //     return <AwesomeButton type="primary"> Button</AwesomeButton>;
    // }
    const Button = styled.button`
  background-color: GoldenRod;
  color: black;
  font-size: 20px;
  padding: 10px 60px;
  border-radius: 5px;
  margin: 10px 0px;
  cursor: pointer;
`;


    // For debugging purposes
    // async function handleScore(e) {
    //     e.preventDefault();
    //     let score = await getGameData(currentUser.uid, "blackjack-score");
    //     console.log("score is: " + score);
    // }

    // async function handleNumGamesPlayed(e) {
    //     e.preventDefault();
    //     let num = await getGameData(currentUser.uid, "blackjack-games-played")
    //     console.log("num games played is: " + num);
    // }

    // async function resetScoreData(e) {
    //     e.preventDefault();
    //     await setGameData(currentUser.uid, "blackjack-score", -1);
    // }

    // async function resetNumberData(e) {
    //     e.preventDefault();
    //     await setGameData(currentUser.uid, "blackjack-games-played", 0);
    // }

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
            
            {/* For debugging purposes
            <Button variant="contained" color="primary" onClick={handleScore}>get score</Button>
            <Button variant="contained" color="primary" onClick={handleNumGamesPlayed}>get games played</Button>
            <Button variant="contained" color="primary" onClick={resetScoreData}>reset score data</Button>
            <Button variant="contained" color="primary" onClick={resetNumberData}>reset number data</Button> */}
        </div>

    )
}


export default BlackJack;
