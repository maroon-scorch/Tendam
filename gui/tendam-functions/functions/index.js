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
  newGameRef.set({id: userID, 'BlackJack' : {
    'blackjack-score': -1, 'blackjack-games-played': 0, id: userID
  }});
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

const deleteMatch = (async (idToDelete, otherID) => {
  let otherData = await admin.firestore().collection('users').doc(otherID).data();
  let otherMatches = otherData['matches'];
  let deleteMatch = otherMatches.filter((id) => id != idToDelete);
  // last line, no need to async?
  await otherUserInfo.update({ matches : deleteMatch});
});

// Deleting data associated with the userID
exports.deleteUserInfo = functions.auth.user().onDelete(async (user) => {
  const batch = database.batch();
  const gameInfo = admin.firestore().collection('games').doc(user.uid);
  const surveyInfo = admin.firestore().collection('surveys').doc(user.uid);
  const notifInfo = admin.firestore().collection('notifications').doc(user.uid);
  const userInfo = admin.firestore().collection('users').doc(user.uid);

  batch.delete(gameInfo);
  batch.delete(surveyInfo);
  batch.delete(notifInfo);

  let userData = userInfo.data();
  await userData['matches'].forEach(async (match) => {
    await deleteMatch(user.uid, match);
  });

  batch.delete(userInfo);
  await batch.commit();

  console.log(`Deleted user + ${user.email} !`);

});