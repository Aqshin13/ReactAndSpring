import React, { useState } from "react";
import { signup, changeLanguage } from "../api/apiCalls";
import Input from "../components/Input";
import { withTranslation } from "react-i18next";
import ButtonWithProgress from "../components/ButtonWithProgress";
import { withApiProgress } from "../shared/ApiProgress";
import { connect } from "react-redux";
import { signupHandler } from "../redux/authActions";

const UserSignupPage = (props) => {
  const [username, setUsername] = useState();
  const [displayName, setDisplayName] = useState();
  const [password, setPassword] = useState();
  const [passwordRepeat, setPasswordRepeat] = useState();
  const [errors, setErrors] = useState({});
  // state = {
  //   username: null,
  //   displayName: null,
  //   password: null,
  //   passwordRepeat: null,
  //   errors: {},
  // };

  const onChange = (event) => {
    const { t } = props;

    const { name, value } = event.target;

    // const errors = { ...this.state.errors };

    errors[name] = undefined;

    if (name === "password" || name === "passwordRepeat") {
      if (name === "password" && value !== this.state.passwordRepeat) {
        errors.passwordRepeat = t("Password mismatch");
      } else if (name === "passwordRepeat" && value !== this.state.password) {
        errors.passwordRepeat = t("Password mismatch");
      } else {
        errors.passwordRepeat = undefined;
      }
    }

    // this.setState({ [name]: value, errors });
  };

  const onClickSignup = async (event) => {
    event.preventDefault();

    const { history, dispatch } = props;
    const { push } = history;

    // const { username, displayName, password } = this.state;

    const body = {
      username,
      displayName,
      password,
    };

    try {
      await dispatch(signupHandler(body));
      push("/");
    } catch (error) {
      console.log(error);
      if (error.response.data.validationErrors) {
        // this.setState({ errors: error.response.data.validationErrors });
      }
    }
  };

  // const { errors } = this.state;

  // const errors = {};
  const {
    username: usernameError,
    displayName: displayNameError,
    password: passwordError,
    passwordRepeat:passwordRepeatError,
  } = errors;

  const { pendingApiCall, t } = props;

  return (
    <div className="container">
      <form>
        <h1 className="text-center">{t("Sign Up")}</h1>
        <Input
          name="username"
          label={t("Username")}
          error={usernameError}
          onChange={onChange}
        />
        <br />
        <Input
          name="displayName"
          label={t("Display Name")}
          error={displayNameError}
          onChange={onChange}
        />
        <br />
        <Input
          name="password"
          label={t("Password")}
          error={passwordError}
          onChange={onChange}
          type="password"
        />

        <br />

        <Input
          name="passwordRepeat"
          label={t("Password Repeat")}
          error={passwordRepeatError}
          onChange={onChange}
          type="password"
        />

        <br />

        <div className="text-center">
          <ButtonWithProgress
            onClick={onClickSignup}
            disabled={pendingApiCall || passwordRepeatError !== undefined}
            pendingApiCall={pendingApiCall}
            text={t("Sign Up")}
          />
        </div>
      </form>
    </div>
  );
};

const UserSignupPageWithApiProgressForSignupRequest = withApiProgress(
  UserSignupPage,
  "/api/1.0/users"
);
const UserSignupPageWithApiProgressForAuthRequest = withApiProgress(
  UserSignupPageWithApiProgressForSignupRequest,
  "/api/1.0/auth"
);

const UserSignupPageWithTranslation = withTranslation()(
  UserSignupPageWithApiProgressForAuthRequest
);

export default connect()(UserSignupPageWithTranslation);
