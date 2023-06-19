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
import AuthenticationContext from "./shared/AuthenticationContext";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <AuthenticationContext>
   <App/>
  </AuthenticationContext>
);

reportWebVitals();
