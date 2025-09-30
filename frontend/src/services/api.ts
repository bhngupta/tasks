import { auth, googleProvider } from './firebase';
import { signInWithPopup } from 'firebase/auth';
import db from './db';

export const openOAuthWindow = async (): Promise<string> => {
  try {
    const result = await signInWithPopup(auth, googleProvider);
    const idToken = await result.user.getIdToken();

    // Store in IndexedDB
    await db.tokens.put({ key: 'taskmanager-token', value: idToken });

    return idToken;
  } catch (err) {
    console.error('Firebase sign‑in failed', err);
    throw err;
  }
};