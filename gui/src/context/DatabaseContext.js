import React, { useContext, useEffect, useState } from 'react';
import app from '../firebase.js';

const DatabaseContext = React.createContext();
const DatabaseContextProvider = DatabaseContext.Provider;

export function useDatabase() {
    return useContext(DatabaseContext);
};

// Usage of Database Functions:
// call the function then couple it with then and catch because they are
// asynchronous

export function DatabaseProvider({ children }) {
    const userDatabase = app.firestore().collection("/users");
    const gameDatabase = app.firestore().collection("/games");
    const surveyDatabase = app.firestore().collection("/surveys");
    const notificationDatabase = app.firestore().collection("/notifications");
    const fireStorage = app.storage();

    function addEntry(id, data) {
        let entry = {
            id: id,
            ...data
          };
        console.log(entry);
        // userDatabase.push(entry);
    }

    function getEntry(id) {
        let userRef = userDatabase.doc(id);
        return userRef.get();
    }

    function getEntryData(id) {
        let userRef = userDatabase.doc(id);
        return userRef.get().then((doc) => {
            return doc.data();
        }).catch((error) => {});
    }

    function setEntry(id, data) {
        let userRef = userDatabase.doc(id);
        return userRef.set(data);
    }

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

    // function getGame(id, fieldname) {
    //     let gameRef = gameDatabase.doc(id);
    //     return gameRef.get().then((doc) => {
    //         console.log(doc.data());
    //         let fieldData = doc.data()[fieldname];
    //         console.log(fieldData);
    //         return fieldData;
    //     }).catch((error) => {
    //         console.log("Error getting document:", error);
    //     });
    // }

    // function writeGame(id, fieldname, newData) {
    //     let surveyRef = gameDatabase.doc(id);
    //     let dataSent = {};
    //     dataSent[fieldname] = newData;
    //     return surveyRef.update(dataSent);
    // }

    function writeSurvey(id, fieldname, newData) {
        let surveyRef = surveyDatabase.doc(id);
        let dataSent = {};
        dataSent[fieldname] = newData;
        return surveyRef.update(dataSent);
    }

    function uploadStorage(id, address, file) {
        let storageName = id + address;
        let storageRef = fireStorage.ref(storageName);
        return storageRef.put(file);
    }

    function getFile(id, address) {
        let storageName = id + address;
        return fireStorage.ref(storageName).getDownloadURL();
    }

    // Realtime Listener to get Notification Update
    function listenNotification(id, handleSnapshot) {
        return notificationDatabase.doc(id).collection('/matches').orderBy('time').onSnapshot(handleSnapshot);
    }

    const databaseInfo = {
        addEntry,
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