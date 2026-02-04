// Import the functions you need from the SDKs you need
import CONFIG from "@/app/config";
import { initializeApp } from "firebase/app";
import { getAuth, GoogleAuthProvider } from "firebase/auth";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries
// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: CONFIG.FIREBASE.API_KEY,
  authDomain: CONFIG.FIREBASE.AUTH_DOMAIN,
  projectId: CONFIG.FIREBASE.PROJECT_ID,
  // storageBucket: CONFIG.FIREBASE.STORAGE_BUCKET,
  // messagingSenderId: CONFIG.FIREBASE.MESSAGING_SENDER_ID,
  // appId: CONFIG.FIREBASE.APP_ID,
  // measurementId: CONFIG.FIREBASE.MEASUREMENT_ID,
};

// Initialize Firebase
export const app = initializeApp(firebaseConfig);

export const auth = getAuth(app);
export const googleProvider = new GoogleAuthProvider();