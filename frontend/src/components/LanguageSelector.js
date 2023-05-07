import React from "react";
import { withTranslation } from "react-i18next";
import { changeLanguage } from "../api/apiCalls";

const LanguageSelector = (props) => {
  const onChangeLanguage = (language) => {
    const { i18n } = props;
    i18n.changeLanguage(language);
    changeLanguage(language);
  };

  return (
    <div className="container">
      <button
        onClick={(e) => {
          e.preventDefault();
          onChangeLanguage("tr");
        }}
        style={{ cursor: "pointer", marginRight: "10px" }}
      >
        TR
      </button>
      <button
        onClick={(e) => {
          e.preventDefault();
          onChangeLanguage("en");
        }}
        style={{ cursor: "pointer" }}
      >
        US
      </button>
    </div>
  );
};

export default withTranslation()(LanguageSelector);
