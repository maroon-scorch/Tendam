const functions = require("firebase-functions");

// Initialize the admin
const admin = require('firebase-admin');
admin.initializeApp();


// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//   functions.logger.info("Hello logs!", {structuredData: true});
//   response.send("Hello World!");
// });

// Finds the id of the new user and initialize them in the Firestore
exports.initializeUserInDatabase = functions.auth.user().onCreate(async (user) => {
  const userID = user.uid;
  let newUserRef = admin.firestore().collection('users').doc(userID);
  newUserRef.set({name: '', age: '', bio: '', matches: []});
});