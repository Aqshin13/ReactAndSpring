import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import "./bootstraptest-override.scss";
import UserSignupPage from "./pages/UserSignupPage";
import LoginPage from "./pages/LoginPage";
import LanguageSelector from "./components/LanguageSelector";
import reportWebVitals from "./reportWebVitals";
import "./i18n";
import ApiProgress from "./shared/ApiProgress";
import App from "./container/App";
import { Provider } from "react-redux";
import { createStore } from "redux";

const loggedInState = {
  isLoggedIn: true,
  username: "user1",
  displayName: "display1",
  image: null,
  password: "P4ssword",
};



const defaultState = {
  isLoggedIn: false,
  username: undefined,
  displayName: undefined,
  image: undefined,
  password: undefined
};

const reducer = (state = { ...defaultState }, action) => {
  if (action.type === 'logout-success') {
    return defaultState;
  }
  return state;
};

const store = createStore(reducer, loggedInState);

const root = ReactDOM.createRoot(document.getElementById("root"));

root.render(
  <Provider store={store}>
    <App />
  </Provider>
);

reportWebVitals();
