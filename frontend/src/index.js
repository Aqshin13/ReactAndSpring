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
import configureStore from "./redux/configureStore";







const store = configureStore()

const root = ReactDOM.createRoot(document.getElementById("root"));

root.render(
  <Provider store={store}>
    <App />
  </Provider>
);

reportWebVitals();
