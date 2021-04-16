import React, { useContext, useEffect, useState } from 'react';
import { auth, credentialAuth } from '../firebase';

// Procedures and Code Learned from Web Dev Simplified's tutorial:
// https://github.com/WebDevSimplified/React-Firebase-Auth

const AuthContext = React.createContext();
const AuthContextProvider = AuthContext.Provider;

export function useAuth() {
    return useContext(AuthContext);
}

// The Provider Context around the Content
// Gives global variables of auth utilities that can be called anywhere
// in the props
export function AuthProvider({ children }) {
    const [currentUser, setCurrentUser] = useState();
    const [loadingUser, setLoading] = useState(true)

    // Signup An Account
    function signup(email, password) {
        return auth.createUserWithEmailAndPassword(email, password);
    };

    // Login with the Account
    function login(email, password) {
        return auth.signInWithEmailAndPassword(email, password);
    };

    // Logout with the Account
    function logout() {
        return auth.signOut();
    };

    // Resets the Password of the User
    function resetPassword(email) {
        return auth.sendPasswordResetEmail(email);
    };
    
    // Update the Email of the User
    function updateEmail(email) {
        return currentUser.updateEmail(email);
    };
    
    // Update the Password of the User
    function updatePassword(password) {
        return currentUser.updatePassword(password);
    }

    // Returns the credential part of the Authentication so it can
    // authenticate it.
    function makeCredentials(email, password) {
        return credentialAuth.credential(email, password);
    }

    // On Mount, the currentUser is set to the user.
    useEffect(() => {
        let authState = auth.onAuthStateChanged(user => {
            setCurrentUser(user);
            setLoading(false);
        });

        return authState;
    }, []);

    // Pass the authInfo down
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
