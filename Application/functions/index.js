const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();
exports.setupLobbyTimeout = functions.database.ref('/lobby/{lobbyId}')
  .onCreate((snapshot, context) => {
    const lobbyId = context.params.lobbyId;
    const timeoutMillis = 5 * 60 * 1000;
    const expirationTime = snapshot.val().timestamp + timeoutMillis;
    setTimeout(() => {
      admin.database().ref(`/lobby/${lobbyId}`).once('value')
        .then((snapshot) => {
          if (snapshot.exists()) {
            return admin.database().ref(`/lobby/${lobbyId}`).remove();
          }
          return null;
        })
        .catch((error) => {
          console.error('Error checking/deleting lobby:', error);
          return null;
        });
    }, expirationTime);
    return null;
  });