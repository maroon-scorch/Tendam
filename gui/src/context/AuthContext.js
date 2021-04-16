import React, { useContext, useEffect, useState } from 'react';
import { auth, credentialAuth } from '../firebase';

// Procedures and Code Learned from Web Dev Simplified's tutorial:
// https://github.com/WebDevSimplified/React-Firebase-Auth

const AuthContext = React.createContext();
const AuthContextProvider = AuthContext.Provider;

export function useAuth() {
    return useContext(AuthContext);
}

export function AuthProvider({ children }) {
    const [currentUser, setCurrentUser] = useState();
    const [loadingUser, setLoading] = useState(true)

    function signup(email, password) {
        return auth.createUserWithEmailAndPassword(email, password);
    };

    function login(email, password) {
        return auth.signInWithEmailAndPassword(email, password);
    };

    function logout() {
        return auth.signOut();
    };

    function resetPassword(email) {
        return auth.sendPasswordResetEmail(email);
    };
    
    function updateEmail(email) {
        return currentUser.updateEmail(email);
    };
    
    function updatePassword(password) {
        return currentUser.updatePassword(password);
    }

    function makeCredentials(email, password) {
        return credentialAuth.credential(email, password);
    }


    useEffect(() => {
        let authState = auth.onAuthStateChanged(user => {
            setCurrentUser(user);
            setLoading(false);
        });

        return authState;
    }, []);

    const authInfo = {
        currentUser,
        makeCredentials,
        login,
        signup,
        logout,
        resetPassword,
        updateEmail,
        updatePassword
    };

    return (
        <AuthContextProvider value={authInfo}>
            {!loadingUser && children}
        </AuthContextProvider>
    );
};
