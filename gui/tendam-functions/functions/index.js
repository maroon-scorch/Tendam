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
  newUserRef.set({id: userID, name: '', age: '', bio: '', matches: []});

  let newGameRef = admin.firestore().collection('games').doc(userID);
  newGameRef.set({id: userID, 'blackjack-score': -1, 'blackjack-games-played': 0});
});

const createNotification = ((notification, id) => {
  return admin.firestore().collection('notifications').doc(id).collection('matches')
  .add(notification).then(doc => console.log('Added New Match!', doc))
  .catch(err => console.log('An error has occurred!'));
});


exports.createMatchNotification = functions.firestore.document('users/{userId}').onUpdate((change, context) => {
      const previousValue = change.before.data();
      const newValue = change.after.data();

      const prevMatch = previousValue.matches;
      const nextMatch = newValue.matches;

      if (JSON.stringify(prevMatch)!=JSON.stringify(nextMatch)) {
        // matches
        let newMatch = nextMatch.filter(elt => !prevMatch.includes(elt));
        console.log(newMatch);
        if (newMatch !== undefined && newMatch.length == 1) {
          const notification = {
            content: 'A new match has been made for you!',
            user: newMatch[0],
            time: admin.firestore.FieldValue.serverTimestamp()
          };

          return createNotification(notification, context.params.userId);

        }
      }
});
