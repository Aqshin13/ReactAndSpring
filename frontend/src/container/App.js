import React from "react";
import UserSignupPage from "../pages/UserSignupPage";
import LoginPage from "../pages/LoginPage";
import LanguageSelector from "../components/LanguageSelector";
import HomePage from "../pages/HomePage";
import UserPage from "../pages/UserPage";
import {
  HashRouter as Router,
  Route,
  Redirect,
  Switch,
} from "react-router-dom";
import TopBar from "../components/TopBar";

class App extends React.Component {
  state = {
    isLoggedIn: false,
    username: undefined,
  };

  onLoginSuccess = username => {
    this.setState({
      username,
      isLoggedIn: true
    });
  };

  onLogoutSuccess = () => {
    this.setState({
      isLoggedIn: false,
      username: undefined
    });
  };

  render() {
    const { isLoggedIn, username } = this.state;

    return (
      <div>
        <Router>
          {/* Http requestleri aradan qaldirmaq ucun ve url deyisir # elave edir meselen root url #/ kimi olur */}
          <TopBar username={username} isLoggedIn={isLoggedIn} onLogoutSuccess={this.onLogoutSuccess} />
          <Switch>
            <Route exact path="/" component={HomePage} />
            {!isLoggedIn && (
              <Route
                path="/login"
                component={props => {
                  return <LoginPage {...props} onLoginSuccess={this.onLoginSuccess} />;
                }}
              />
            )}
            <Route path="/signup" component={UserSignupPage} />
            <Route path="/user/:username" component={UserPage} />
            {/* Burda dinamik olur.Yeni /user/agshin  /user/veli ve s */}
            <Redirect to="/" />
            {/* Yanlis url olsa /-urle yonlendirsin */}
          </Switch>
        </Router>

        <LanguageSelector />
      </div>
    );
  }
}

export default App;
