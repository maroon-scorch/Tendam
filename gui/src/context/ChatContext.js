import firebase from "firebase";
import "firebase/firestore";

import app from "../firebase";
import {useAuth} from "./AuthContext";
import {createContext, useContext, useEffect, useState} from "react";
import { useDatabase } from "./DatabaseContext";

function now() {
    // Can't use FieldValue.serverTime because of update()
    return firebase.firestore.Timestamp.now();
}

/**
 * Returns a reference to the document representing the thread with the given
 * participants.
 */
async function getOrMakeThread(collection, from, to) {
    if (collection === undefined || from === undefined || to === undefined) {
        throw new Error('Collection, from, and to required');
    }
    if (from === to) {
        throw new Error('Threads must have two participants');
    }
    const query = collection.where('participants', 'array-contains', from);
    const snapshot = await query.get();
    const docs = snapshot.docs;

    let thread = undefined;
    // First try to find the thread. If we don't return anything, then we should
    //  create the thread manually.
    for (const doc of docs) {
        const thread = doc.data();
        // TODO fix these
        if (!thread.participants) {
            continue;
        }
        if (thread.participants.includes(to)) {
            return doc.ref;
        }
    }

    // Make the thread
    const docRef = collection.add({
        participants: [from, to],
        messages: [],
    });
    return docRef;
}

class MessageListener {
    constructor(thread) {
        if (!thread) {
            throw new Error('Thread must exist');
        }
        this._callbacks = [];
        this._messages = [];
        
        let res;
        let rej;
        this._setupPromise = new Promise((_res, _rej) => {
            res = _res;
            rej = _rej;
        });

        thread.onSnapshot({complete: res, error: rej, next: snapshot => {
            const changedDoc = snapshot.data();
            console.log(changedDoc);
            const messages = changedDoc.messages;
            this._messages = messages;

            for (const callback of this._callbacks) {
                callback.call(undefined, messages);
            }
        }});
    }

    async getMessages() {
        await this._setupPromise;
        return this._messages;
    }

    addListener(callback) {
        this._callbacks.push(callback);
    }
}

class Thread {
    constructor(collection, from, to) {
        this.sender = from;
        this.recipient = to;

        // TODO create if does not exist
        /** @data Promise<DocumentReference<DocumentData>> */
        this.threadRef = getOrMakeThread(collection, from, to);
        /** @data Promise<CollectionReference<DocumentData>> */

        // Undefined until it is required
        this._threadListener = undefined;
    }

    /**
     * Ensures that a _threadListener exists.
     */
    async _ensureListener() {
        if (this._threadListener === undefined) {
            const thread = await this.threadRef;
            this._threadListener = new MessageListener(thread);
        }
    }

    async getAllMessages() {
        await this._ensureListener();
        return await this._threadListener.getMessages();
    }

    /**
     * Sends a message to Firebase.
     *
     * @param {Message} message a message to send
     * @returns {Promise<DocumentReference<DocumentData>>} a reference to the added message
     */
    async sendMessage(message) {
        const messageCopy = Object.assign({}, message);
        messageCopy.timestamp = now();
        messageCopy.readBy = [this.sender];
        const ref = await this.threadRef;
        const threadData = (await ref.get()).data();
        console.log(threadData);
        const messages = threadData.messages;
        return await ref.update('messages', [...messages, messageCopy]);
    }

    async onReceiveMessages(callback) {
        await this._ensureListener();
        this._threadListener.addListener(callback);
    }
}

export const ChatContext = createContext();

export function useChat() {
    return useContext(ChatContext);
}

export function ChatProvider({children}) {
    const {currentUser} = useAuth();
    const {getEntryData, getFile} = useDatabase();
    const [senderInfo, setSenderInfo] = useState();
    const chatCollection = app.firestore().collection("/messages");

    const [threads, setThreads] = useState({});

    useEffect(() => {
        setSenderInfo(currentUser.data);
        setThreads()
    });

    // TODO consider one thread object to avoid this query
    async function getThread(to) {
        // Hope there is one
        return new Thread(chatCollection, currentUser.uid, to);
    }

    async function getUser(id) {
        const user = await getEntryData(id);
        try {
            user.profilePicture = await getFile(id, '/profilePicture');
        } catch (e) {
            user.profilePicture = 'blank-profile.png';
        }

        return user;
    }

    const chatInfo = {
        getThread: getThread,
        getUser: getUser,
    };

    return (
        <ChatContext.Provider value={chatInfo}>
            {children}
        </ChatContext.Provider>
    );
}