import React from "react";
import UserSignupPage from "../pages/UserSignupPage";
import LoginPage from "../pages/LoginPage";
import LanguageSelector from "../components/LanguageSelector";
import HomePage from "../pages/HomePage";
import UserPage from "../pages/UserPage";
import { HashRouter, Route, Redirect, Switch } from "react-router-dom";

function App() {
  return (
    <div>
      <HashRouter>
        {/* Http requestleri aradan qaldirmaq ucun ve url deyisir # elave edir meselen root url #/ kimi olur */}
        <Switch>

        <Route exact path="/" component={HomePage} />
        <Route path="/login" component={LoginPage} />
        <Route path="/signup" component={UserSignupPage} />
        <Route path="/user/:username" component={UserPage} />
        {/* Burda dinamik olur.Yeni /user/agshin  /user/veli ve s */}
        <Redirect to="/" />
        {/* Yanlis url olsa /-urle yonlendirsin */}

        </Switch>

      </HashRouter>

      <LanguageSelector />
    </div>
  );
}

export default App;
