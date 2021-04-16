import React, { useContext } from 'react';
import app from '../firebase.js';

const DatabaseContext = React.createContext();
const DatabaseContextProvider = DatabaseContext.Provider;

export function useDatabase() {
    return useContext(DatabaseContext);
};

// Usage of Database Functions:
// call the function then couple it with then and catch because they are
// asynchronous

// Gives global variables of database utilities that can be called anywhere
// in the props
export function DatabaseProvider({ children }) {
    const userDatabase = app.firestore().collection("/users");
    const gameDatabase = app.firestore().collection("/games");
    const surveyDatabase = app.firestore().collection("/surveys");
    const notificationDatabase = app.firestore().collection("/notifications");
    const fireStorage = app.storage();

    // add an entry to the 
    // function addEntry(id, data) {
    //     let entry = {
    //         id: id,
    //         ...data
    //       };
    //     console.log(entry);
    //     // userDatabase.push(entry);
    // }

    // get the user entry from the firestore.
    function getEntry(id) {
        let userRef = userDatabase.doc(id);
        return userRef.get();
    }

    // get entry data from the firestore with the async part abstracted.
    function getEntryData(id) {
        let userRef = userDatabase.doc(id);
        return userRef.get().then((doc) => {
            const user = doc.data();
            user.matches = user.matches || [];
            return user;
        }).catch((error) => {});
    }

    // changes the user entry back in the database.
    function setEntry(id, data) {
        let userRef = userDatabase.doc(id);
        return userRef.set(data);
    }

    // Gets game data with the id given here.
    // if not such entry existed, returns the default value.
    function getGameData(id, gameType) {
        let defaultData = { 'blackjack-score': -1, 'blackjack-games-played': 0, 'id': id };
        let gameRef = gameDatabase.doc(id);

        return gameRef.get().then((doc) => {
            let gameData = doc.data();
            let fieldData = gameData.hasOwnProperty(gameType) ? gameData[gameType] : defaultData;
            console.log(fieldData);
            return fieldData;
        }).catch((error) => {
            console.log("Error getting document:", error);
            return defaultData;
        });
    }

    // Set game data whose type is specified by game type and
    // whose fieldname is now equal to the the new data.
    function setGameData(id, gameType, fieldname, newData) {
        let defaultData = { 'blackjack-score': -1, 'blackjack-games-played': 0, 'id': id };
        let gameRef = gameDatabase.doc(id);

        gameRef.get().then((doc) => {
            let gameData = doc.data();
            console.log(gameData);

            let updData = gameData.hasOwnProperty(gameType) ? gameData[gameType] : defaultData;
            updData[fieldname] = newData;
            updData.id = id;

            let dataSent = {};
            dataSent[gameType] = updData; 
            console.log(dataSent);

            return gameRef.update(dataSent);
        }).catch((error) => {
            console.log("Error getting document:", error);
        });
    }

    // Writes the survey entry to the database.
    function writeSurvey(id, fieldname, newData) {
        let surveyRef = surveyDatabase.doc(id);
        console.log(surveyRef);
        let dataSent = {};
        dataSent[fieldname] = newData;
        return surveyRef.update(dataSent);
    }

    // Upload the image to storage
    function uploadStorage(id, address, file) {
        let storageName = id + address;
        let storageRef = fireStorage.ref(storageName);
        return storageRef.put(file);
    }

    // Gets a file from the address and id
    function getFile(id, address) {
        let storageName = id + address;
        return fireStorage.ref(storageName).getDownloadURL();
    }

    // Realtime Listener to get Notification Update
    function listenNotification(id, handleSnapshot) {
        return notificationDatabase.doc(id).collection('/matches').orderBy('time').onSnapshot(handleSnapshot);
    }

    // The databaseInfo that will be passed down
    const databaseInfo = {
        setEntry,
        getEntry,
        getEntryData,
        getGameData,
        setGameData,
        writeSurvey,
        uploadStorage,
        getFile,
        listenNotification
    };

    return (
        <DatabaseContextProvider value={databaseInfo}>
            {children}
        </DatabaseContextProvider>
    );
};