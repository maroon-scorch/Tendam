import React, { useContext, useEffect, useState } from 'react';
import app from '../firebase.js';

const DatabaseContext = React.createContext();
const DatabaseContextProvider = DatabaseContext.Provider;

export function useDatabase() {
    return useContext(DatabaseContext);
};

export function DatabaseProvider({ children }) {
    const userDatabase = app.firestore().collection("/users");

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

    function setEntry(id, data) {
        // let userRef = userDatabase.doc(id);
       // return userRef.set(data);
    }

    const databaseInfo = {
        addEntry,
        userDatabase,
        getEntry
    };

    return (
        <DatabaseContextProvider value={databaseInfo}>
            {children}
        </DatabaseContextProvider>
    );
};