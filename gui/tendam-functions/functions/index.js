// index.js houses Firebase Functions that would be deployed
// to the Cloud Firebase to be triggered under specified circumstances.

// Setup the Firebase Functions Enviroment
const functions = require("firebase-functions");

// Initialize the admin
const admin = require('firebase-admin');
admin.initializeApp();


// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions

// initializeUserInDatabase:
// When a new user is created, this function is invoked to initialize its entry
// in the firestore, with default values in the User Collection and Games Collection.
exports.initializeUserInDatabase = functions.auth.user().onCreate(async (user) => {
  const userID = user.uid;
  let newUserRef = admin.firestore().collection('users').doc(userID);
  newUserRef.set({id: userID, name: '', age: '', bio: '', matches: []});

  let newGameRef = admin.firestore().collection('games').doc(userID);
  newGameRef.set({id: userID, 'BlackJack' : {
    'blackjack-score': -1, 'blackjack-games-played': 0, id: userID
  }});
});

// createNotification:
// A helper function that given a notification JSON, a field, and an id,
// accesses the Notification Collection and finds the entry with the id given,
// and inserts the notification under the subcollection (field) of that entry.
const createNotification = ((notification, field, id) => {
  return admin.firestore().collection('notifications').doc(id).collection(field)
  .add(notification).then(doc => console.log('Added New Notification!', doc))
  .catch(err => console.log('An error has occurred!'));
});

// createMatchNotification:
// Whenever a new Match is created, this is triggered to add a notification 
// to the user that there has been a match.
exports.createMatchNotification = functions.firestore.document('users/{userId}').onUpdate((change, context) => {
      // Finds the value before and after update.
      const previousValue = change.before.data();
      const newValue = change.after.data();
      // Finds the matches of the two values
      const prevMatch = previousValue.matches;
      const nextMatch = newValue.matches;

      // If the match has been updated:
      if (JSON.stringify(prevMatch)!=JSON.stringify(nextMatch)) {
        // check if exactly one new match is added
        let newMatch = nextMatch.filter(elt => !prevMatch.includes(elt));
        console.log(newMatch);

        if (newMatch !== undefined && newMatch.length == 1) {
          // Creates a notification to the user that the match was made.
          const notification = {
            content: 'A new match has been made for you!',
            user: newMatch[0],
            time: admin.firestore.FieldValue.serverTimestamp()
          };

          return createNotification(notification, 'matches', context.params.userId);
        }
      }
});

// createMessageNotification:
// Whenever the message collection is being written, check to see
// if a new message has been added. If so, add a notification to the user too.
// Only Creating Notification for One on One Messages Now
exports.createMessageNotification = functions.firestore.document('messages/{msgId}').onUpdate((change, context) => {
  // Finds the value before and after update.
  const previousValue = change.before.data();
  const newValue = change.after.data();
  // Finds the messages of the two values
  const prevMessages = previousValue.messages;
  const nextMessages = newValue.messages;

  // If the update is on messages
  if (JSON.stringify(prevMessages)!=JSON.stringify(nextMessages)
  && nextMessages.length > prevMessages.length) {
    // then notify the recipient of the message that this has been sent.
    let newestMsg = nextMessages[nextMessages.length - 1];
    console.log(newestMsg);
    let newestSender = newestMsg['sender'];
    let rest = newValue['participants'].filter(elt => elt != newestSender);

    if (rest.length == 1) {
      const notification = {
        content: 'You have a new message: ' + newestMsg['data'],
        user: newestSender,
        time: newestMsg['timestamp']
      };

      console.log(notification.content);
      console.log(notification.user);

      return createNotification(notification, 'messages', rest[0]);
    }
    }
  });

  
// Helper Function of deleteUserInfo, given two IDs, one being the
// id to delete, and a different id, goes into the matches that the second id
// contains and filters out the idToDelete.
const deleteMatch = (async (idToDelete, otherID) => {
  let otherUserRef = admin.firestore().collection('users').doc(otherID);
  let otherDataRef = await otherUserRef.get();
  if (otherDataRef.exists && otherDataRef.data()['matches'] !== null) {
    let otherData = otherDataRef.data();
    let otherMatches = otherData['matches'];
    let deleteMatch = otherMatches.filter((id) => id != idToDelete);
    // last line, no need to async?
    await otherUserRef.update({ matches : deleteMatch});
  }
});

// deleteUserInfo:
// When a user is deleted, also deleting the data associated with the user:
// its survey data, game data, notifications, and userInfo, etc.
exports.deleteUserInfo = functions.auth.user().onDelete(async (user) => {
  const batch = admin.firestore().batch();
  const gameInfo = admin.firestore().collection('games').doc(user.uid);
  const surveyInfo = admin.firestore().collection('surveys').doc(user.uid);
  const notifInfo = admin.firestore().collection('notifications').doc(user.uid);
  const userInfo = admin.firestore().collection('users').doc(user.uid);

  batch.delete(gameInfo);
  batch.delete(surveyInfo);
  batch.delete(notifInfo);

  let userDataRef = await userInfo.get();
  // For each match that the user deleted has, go to that id and delete the user's id
  // from their matches
  if (userDataRef.exists && userDataRef.data()['matches'] !== null) {
    let userData = userDataRef.data();
    await userData['matches'].forEach(async (match) => {
      await deleteMatch(user.uid, match);
    });
  }


  batch.delete(userInfo);
  await batch.commit();

  console.log(`Deleted user + ${user.email} !`);

});